/**
 * MessagingLib is an API to easily handle plugin messages.
 * Copyright (C) 2016  tmxx
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tmxx.messaginglib;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.tmxx.messaginglib.core.MessageManager;
import net.tmxx.messaginglib.core.exception.InvalidListenerException;
import net.tmxx.messaginglib.core.exception.PluginMessageEventException;
import net.tmxx.messaginglib.core.listener.MessageHandler;
import net.tmxx.messaginglib.core.listener.MessageListener;
import net.tmxx.messaginglib.core.message.BungeeCordMessage;
import net.tmxx.messaginglib.core.message.PluginMessage;
import net.tmxx.messaginglib.core.util.MethodContainer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tmxx on 16.01.2016
 *
 * <p>This class handles all plugin messages send to and by BungeeCord.</p>
 */
public class BungeeCordMessageManager implements MessageManager<Plugin, ProxiedPlayer> {
    /**
     * Contains all BungeeCord message listeners.
     */
    private Map<String, List<MethodContainer<Plugin>>> bungeeCordMessageListeners = new HashMap<>();

    /**
     * Contains all plugin message listeners.
     */
    private Map<Integer, List<MethodContainer<Plugin>>> pluginMessageListeners = new HashMap<>();

    /**
     * Registers the listener to the specified origin.
     * @param messageListener The listener to register
     * @param origin The origin of the listener
     */
    @Override
    public void registerListener( MessageListener messageListener, Plugin origin ) {
        for ( Method method : messageListener.getClass().getDeclaredMethods() ) {
            if ( method.isAnnotationPresent( MessageHandler.class ) ) {
                if ( method.getParameterTypes().length == 2 ) {
                    if ( PluginMessage.class.isAssignableFrom( method.getParameterTypes()[0] ) && ProxiedPlayer.class.isAssignableFrom( method.getParameterTypes()[1] ) ) {
                        if ( BungeeCordMessage.class.isAssignableFrom( method.getParameterTypes()[0] ) ) {
                            try {
                                String id = ( ( BungeeCordMessage ) method.getParameterTypes()[0].getDeclaredConstructor().newInstance() ).getSubChannel();
                                List<MethodContainer<Plugin>> methodContainers = this.bungeeCordMessageListeners.get( id );
                                if ( methodContainers == null ) {
                                    methodContainers = new ArrayList<>();
                                }
                                method.setAccessible( true );
                                methodContainers.add( new MethodContainer<>( messageListener, method, origin ) );
                                this.bungeeCordMessageListeners.put( id, methodContainers );
                            } catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
                                e.printStackTrace();
                            }
                        } else {
                            int id = method.getParameterTypes()[0].getName().hashCode();
                            List<MethodContainer<Plugin>> methodContainers = this.pluginMessageListeners.get( id );
                            if ( methodContainers == null ) {
                                methodContainers = new ArrayList<>();
                            }
                            method.setAccessible( true );
                            methodContainers.add( new MethodContainer<>( messageListener, method, origin ) );
                            this.pluginMessageListeners.put( id, methodContainers );
                        }
                    }
                } else {
                    throw new InvalidListenerException( "Found a correctly annotated method with no parameters" );
                }
            }
        }
    }

    /**
     * !! THIS IS CURRENTLY NOT SUPPORTED !!
     *
     * Registers all listener found in the specified directory.
     * @param path The path to the directory containing the listeners
     * @param origin The origin to register all found listeners to
     */
    @Override
    public void registerListeners( String path, Plugin origin ) {
        // THIS IS CURRENTLY NOT SUPPORTED
    }

    /**
     * Unregisters the specified message listener. When invoking this
     * method the message listener will no longer be used.
     * @param messageListener The message listener to unregister
     */
    @Override
    public void unregisterListener( MessageListener messageListener ) {
        // Unregister BungeeCord message listeners
        for ( String key : this.bungeeCordMessageListeners.keySet() ) {
            Iterator<MethodContainer<Plugin>> iterator = this.bungeeCordMessageListeners.get( key ).iterator();

            while ( iterator.hasNext() ) {
                MethodContainer methodContainer = iterator.next();
                if ( methodContainer.getMessageListener().equals( messageListener ) ) {
                    iterator.remove();
                }
            }
        }

        // Unregister plugin message listeners
        for ( int key : this.pluginMessageListeners.keySet() ) {
            Iterator<MethodContainer<Plugin>> iterator = this.pluginMessageListeners.get( key ).iterator();

            while ( iterator.hasNext() ) {
                MethodContainer methodContainer = iterator.next();
                if ( methodContainer.getMessageListener().equals( messageListener ) ) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Unregisters all listeners registered to the specified origin.
     * @param origin The origin to unregister all to it registered listeners
     */
    @Override
    public void unregisterListeners( Plugin origin ) {
        // Unregister BungeeCord message listeners
        for ( String key : this.bungeeCordMessageListeners.keySet() ) {
            Iterator<MethodContainer<Plugin>> iterator = this.bungeeCordMessageListeners.get( key ).iterator();

            while ( iterator.hasNext() ) {
                MethodContainer methodContainer = iterator.next();
                if ( methodContainer.getOrigin().equals( origin ) ) {
                    iterator.remove();
                }
            }
        }

        // Unregister plugin message listeners
        for ( int key : this.pluginMessageListeners.keySet() ) {
            Iterator<MethodContainer<Plugin>> iterator = this.pluginMessageListeners.get( key ).iterator();

            while ( iterator.hasNext() ) {
                MethodContainer methodContainer = iterator.next();
                if ( methodContainer.getOrigin().equals( origin ) ) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Handles an incoming plugin message.
     * @param data The data of the incoming message
     * @param proxiedPlayer The player who received the message
     */
    @Override
    public void handleIncomingMessage( byte[] data, ProxiedPlayer proxiedPlayer ) {
        try {
            DataInputStream inputStream = new DataInputStream( new ByteArrayInputStream( data ) );

            int id = inputStream.readInt();
            if ( !MessagingLib.getMessageRegistry().isPluginMessageRegistered( id ) ) {
                return;
            }

            PluginMessage pluginMessage = MessagingLib.getMessageRegistry().getPluginMessage( id ).getClass().getConstructor().newInstance();

            pluginMessage.read( inputStream );

            if ( this.pluginMessageListeners.containsKey( id ) ) {
                for ( MethodContainer methodContainer : this.pluginMessageListeners.get( id ) ) {
                    try {
                        methodContainer.getMethod().invoke( methodContainer.getMessageListener(), pluginMessage, proxiedPlayer );
                    } catch ( Exception e ) {
                        throw new PluginMessageEventException( "Error while performing a plugin message event", e );
                    }
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Handles an incoming BungeeCord message.
     * @param data The data of the incoming message
     * @param proxiedPlayer The player who received the message
     */
    @Override
    public void handleIncomingBungeeCordMessage( byte[] data, ProxiedPlayer proxiedPlayer ) {
        try {
            DataInputStream inputStream = new DataInputStream( new ByteArrayInputStream( data ) );

            String id = inputStream.readUTF();
            if ( !MessagingLib.getMessageRegistry().isBungeeCordMessageRegistered( id ) ) {
                return;
            }

            BungeeCordMessage bungeeCordMessage = MessagingLib.getMessageRegistry().getBungeeCordMessage( id ).getClass().getConstructor().newInstance();

            bungeeCordMessage.read( inputStream );

            if ( this.bungeeCordMessageListeners.containsKey( id ) ) {
                for ( MethodContainer methodContainer : this.bungeeCordMessageListeners.get( id ) ) {
                    try {
                        methodContainer.getMethod().invoke( methodContainer.getMessageListener(), bungeeCordMessage, proxiedPlayer );
                    } catch ( Exception e ) {
                        throw new PluginMessageEventException( "Error while performing a bungeecord message event", e );
                    }
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a plugin message to the specified player.
     * @param pluginMessage The message to send
     * @param proxiedPlayer The player to send the message to
     */
    @Override
    public void sendPluginMessage( PluginMessage pluginMessage, ProxiedPlayer proxiedPlayer ) {
        try {
            if ( proxiedPlayer != null ) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream outputStream = new DataOutputStream( byteArrayOutputStream );
                if ( BungeeCordMessage.class.isAssignableFrom( pluginMessage.getClass() ) ) {

                    BungeeCordMessage bungeeCordMessage = (BungeeCordMessage) pluginMessage;
                    outputStream.writeUTF( bungeeCordMessage.getSubChannel() );
                    bungeeCordMessage.write( outputStream );
                    proxiedPlayer.sendData( MessagingLib.BUNGEE_CORD_CHANNEL, byteArrayOutputStream.toByteArray() );
                } else {
                    outputStream.writeInt( pluginMessage.getClass().getName().hashCode() );
                    pluginMessage.write( outputStream );
                    proxiedPlayer.sendData( MessagingLib.MESSAGING_LIB_CHANNEL, byteArrayOutputStream.toByteArray() );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * !! THIS METHOD IS NOT SUPPORTED ON BUNGEECORD !!
     *
     * Sends a plugin message to a randomized player.
     * If no player is online the message will be added
     * to a queue and sent when the next player joins.
     * @param pluginMessage The message to send
     */
    @Override
    public void sendPluginMessage( PluginMessage pluginMessage ) {
        // THIS METHOD IS NOT SUPPORTED ON BUNGEECORD !!
    }
}

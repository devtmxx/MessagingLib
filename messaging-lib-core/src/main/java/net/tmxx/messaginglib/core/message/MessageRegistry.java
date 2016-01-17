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
package net.tmxx.messaginglib.core.message;

import lombok.NoArgsConstructor;
import net.tmxx.messaginglib.core.message.bungeecord.ConnectMessage;
import net.tmxx.messaginglib.core.message.bungeecord.ConnectOtherMessage;
import net.tmxx.messaginglib.core.message.bungeecord.GetServersMessage;
import net.tmxx.messaginglib.core.message.bungeecord.IPMessage;
import net.tmxx.messaginglib.core.message.bungeecord.MessagePlayerMessage;
import net.tmxx.messaginglib.core.message.bungeecord.PlayerCountMessage;
import net.tmxx.messaginglib.core.message.bungeecord.PlayerListMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmxx on 15.01.2016
 *
 * <p>This class is used to register and unregister messages.</p>
 */
@NoArgsConstructor
public class MessageRegistry {
    /**
     * As BungeeCord provides another message structure than we do
     * we have to handle BungeeCord messages on their own.
     */
    private Map<String, BungeeCordMessage> bungeeCordMessageMap = new HashMap<>();

    /**
     * All registered plugin messages. This will not include
     * BungeeCord messages.
     */
    private Map<Integer, PluginMessage> pluginMessageMap = new HashMap<>();

    /**
     * Registers the specified plugin message. We will check for
     * the message being a BungeeCord message and if so, we
     * put it in the {@link #bungeeCordMessageMap} and use the
     * sub channel as key. Otherwise we will put this message into
     * the {@link #pluginMessageMap} and use the hash code of the name as key.
     * @param pluginMessage The plugin message to register
     * @return Whether or not the plugin message was successfully registered
     */
    public boolean registerMessage( PluginMessage pluginMessage ) {
        if ( BungeeCordMessage.class.isAssignableFrom( pluginMessage.getClass() ) ) {
            // This is a BungeeCord message
            BungeeCordMessage message = (BungeeCordMessage) pluginMessage;
            this.bungeeCordMessageMap.put( message.getSubChannel(), message );
            return true;
        } else {
            // This is a normal plugin message
            // We use the full name of the class (including path) to prevent colliding codes
            int hashCode = pluginMessage.getClass().getName().hashCode();

            // But you never know
            if ( this.pluginMessageMap.containsKey( hashCode ) ) {
                // We have colliding hash codes. Do nothing
                return false;
            } else {
                // Put this message into the map
                this.pluginMessageMap.put( hashCode, pluginMessage );
                return true;
            }
        }
    }

    /**
     * Unregisters the specified plugin message. As described in
     * the method {@link #registerMessage(PluginMessage)} we will
     * handle both messages (BungeeCord and normal) on their own.
     * @param pluginMessage The plugin message to unregister
     * @return Whether or not the plugin message was successfully unregistered
     */
    public boolean unregisterMessage( PluginMessage pluginMessage ) {
        if ( BungeeCordMessage.class.isAssignableFrom( pluginMessage.getClass() ) ) {
            // This is a BungeeCord message
            BungeeCordMessage message = (BungeeCordMessage) pluginMessage;
            if ( this.bungeeCordMessageMap.containsKey( message.getSubChannel() ) ) {
                this.bungeeCordMessageMap.remove( message.getSubChannel() );
                return true;
            } else {
                return false;
            }
        } else {
            // This is a normal plugin message
            int hashCode = pluginMessage.getClass().getName().hashCode();

            if ( this.pluginMessageMap.containsKey( hashCode ) ) {
                this.pluginMessageMap.remove( hashCode );
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Checks whether or not the specified BungeeCord
     * message id is currently registered.
     * @param id The id to check
     * @return Whether or not the id is registered
     */
    public boolean isBungeeCordMessageRegistered( String id ) {
        return this.bungeeCordMessageMap.containsKey( id );
    }

    /**
     * Gets a BungeeCord message by its specified id.
     * @param id The id of the BungeeCord message
     * @return The BungeeCord message or null
     */
    public BungeeCordMessage getBungeeCordMessage( String id ) {
        return this.bungeeCordMessageMap.get( id );
    }

    /**
     * Checks whether or not the specified plugin message
     * id is currently registered.
     * @param id The id to check
     * @return Whether or not the id is registered
     */
    public boolean isPluginMessageRegistered( int id ) {
        return this.pluginMessageMap.containsKey( id );
    }

    /**
     * Gets a plugin message by its specified id.
     * @param id The id of the plugin message
     * @return The plugin message or
     */
    public PluginMessage getPluginMessage( int id ) {
        return this.pluginMessageMap.get( id );
    }

    /**
     * This method registers all by BungeeCord predefined messages.
     * Please make sure that this will use more memory because
     * every messages send by BungeeCord will be handled.
     */
    public void registerAllBungeeCordMessages() {
        registerMessage( new ConnectMessage() );
        registerMessage( new ConnectOtherMessage() );
        registerMessage( new GetServersMessage() );
        registerMessage( new IPMessage() );
        registerMessage( new MessagePlayerMessage() );
        registerMessage( new PlayerCountMessage() );
        registerMessage( new PlayerListMessage() );
    }
}
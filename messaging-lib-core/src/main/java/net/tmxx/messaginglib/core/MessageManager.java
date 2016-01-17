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
package net.tmxx.messaginglib.core;

import net.tmxx.messaginglib.core.listener.MessageListener;
import net.tmxx.messaginglib.core.message.PluginMessage;

/**
 * Created by tmxx on 14.01.2016
 *
 * <p>This interface has to be implemented to
 * classes which should manage incoming and
 * outgoing messages.</p>
 */
public interface MessageManager<O, P> {
    /**
     * Registers the listener to the specified origin.
     * @param messageListener The listener to register
     * @param o The origin of the listener
     */
    void registerListener( MessageListener messageListener, O o );

    /**
     * Registers all listener found in the specified directory.
     * @param path The path to the directory containing the listeners
     * @param o The origin to register all found listeners to
     */
    void registerListeners( String path, O o );

    /**
     * Unregisters the specified message listener. When invoking this
     * method the message listener will no longer be used.
     * @param messageListener The message listener to unregister
     */
    void unregisterListener( MessageListener messageListener );

    /**
     * Unregisters all listeners registered to the specified origin.
     * @param o The origin to unregister all to it registered listeners
     */
    void unregisterListeners( O o );

    /**
     * Handles an incoming plugin message.
     * @param data The data of the incoming message
     * @param p The player who received the message
     */
    void handleIncomingMessage( byte[] data, P p );

    /**
     * Handles an incoming BungeeCord message.
     * @param data The data of the incoming message
     * @param p The player who received the message
     */
    void handleIncomingBungeeCordMessage( byte[] data, P p );

    /**
     * Sends a plugin message to the specified player.
     * @param pluginMessage The message to send
     * @param p The player to send the message to
     */
    void sendPluginMessage( PluginMessage pluginMessage, P p );

    /**
     * Sends a plugin message to a randomized player.
     * If no player is online the message will be added
     * to a queue and sent when the next player joins.
     * @param pluginMessage The message to send
     */
    void sendPluginMessage( PluginMessage pluginMessage );
}
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
package net.tmxx.messaginglib.receiver;

import net.tmxx.messaginglib.MessagingLib;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Created by tmxx on 14.01.2016
 */
public class MessageReceiver implements PluginMessageListener {
    /**
     * Called when we receive a plugin message.
     * @param channel The channel we received a message from
     * @param player The player who send the message
     * @param bytes The message itself encoded in bytes
     */
    @Override
    public void onPluginMessageReceived( String channel, Player player, byte[] bytes ) {
        DataInputStream inputStream = new DataInputStream( new ByteArrayInputStream( bytes ) );
        if ( channel.equals( MessagingLib.BUNGEE_CORD_CHANNEL ) ) {
            // A BungeeCord message arrived
            MessagingLib.getMessageManager().handleIncomingBungeeCordMessage( bytes, player );
        } else if ( channel.equals( MessagingLib.MESSAGING_LIB_CHANNEL ) ) {
            // A plugin message arrived
            MessagingLib.getMessageManager().handleIncomingMessage( bytes, player );
        }
    }
}
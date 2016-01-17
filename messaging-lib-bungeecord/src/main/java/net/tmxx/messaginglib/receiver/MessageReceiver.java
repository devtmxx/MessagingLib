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

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.tmxx.messaginglib.MessagingLib;

/**
 * Created by tmxx on 16.01.2016
 */
public class MessageReceiver implements Listener {
    @EventHandler
    public void onMessage( PluginMessageEvent event ) {
        if ( event.getSender() instanceof Server && event.getReceiver() instanceof ProxiedPlayer ) {
            // To prevent player attacking the proxy we only accept messages of servers
            if ( event.getTag().equals( MessagingLib.BUNGEE_CORD_CHANNEL ) ) {
                // A BungeeCord plugin message arrived
                MessagingLib.getMessageManager().handleIncomingBungeeCordMessage( event.getData(), (ProxiedPlayer) event.getReceiver() );
            } else if ( event.getTag().equals( MessagingLib.MESSAGING_LIB_CHANNEL ) ) {
                // A MessagingLib plugin message arrived
                MessagingLib.getMessageManager().handleIncomingMessage( event.getData(), (ProxiedPlayer) event.getReceiver() );
            }
        }
    }
}

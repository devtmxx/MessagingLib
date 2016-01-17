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

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.tmxx.messaginglib.core.message.MessageRegistry;
import net.tmxx.messaginglib.core.message.bungeecord.GetServersMessage;
import net.tmxx.messaginglib.core.message.bungeecord.IPMessage;
import net.tmxx.messaginglib.core.message.bungeecord.PlayerCountMessage;
import net.tmxx.messaginglib.receiver.MessageReceiver;

/**
 * Created by tmxx on 16.01.2016
 *
 * <p>The main part of the BungeeCord implementation of MessagingLib</p>
 */
public class MessagingLib extends Plugin {
    /**
     * The channel we use for plugin messages.
     */
    public static final String MESSAGING_LIB_CHANNEL = "MessagingLib";

    /**
     * The channel BungeeCord uses for plugin messages.
     */
    public static final String BUNGEE_CORD_CHANNEL = "BungeeCord";

    /**
     * The instance of this library.
     */
    @Getter private static MessagingLib messagingLib;

    /**
     * The message registry we use.
     */
    @Getter private static MessageRegistry messageRegistry;

    /**
     * This object manages all incoming and outgoing
     * messages and calls listeners.
     */
    @Getter private static BungeeCordMessageManager messageManager;

    /**
     * This object receives all the messages.
     */
    @Getter private static MessageReceiver messageReceiver;

    /**
     * Called when the plugin is enabling.
     */
    @Override
    public void onEnable() {
        messagingLib = this;
        messageRegistry = new MessageRegistry();
        messageManager = new BungeeCordMessageManager();
        messageReceiver = new MessageReceiver();

        messageRegistry.registerMessage( new IPMessage() );
        messageRegistry.registerMessage( new PlayerCountMessage() );
        messageRegistry.registerMessage( new GetServersMessage() );

        getProxy().getPluginManager().registerListener( this, messageReceiver );

        getLogger().info( "Starting to register plugin channels" );
        this.registerChannels();
        getLogger().info( "Registered plugin channels" );
    }

    /**
     * Called when the plugin is disabling.
     */
    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Registers all needed channels.
     */
    private void registerChannels() {
        getProxy().registerChannel( MESSAGING_LIB_CHANNEL );
    }
}

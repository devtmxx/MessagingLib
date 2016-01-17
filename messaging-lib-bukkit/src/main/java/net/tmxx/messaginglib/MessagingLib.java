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
import net.tmxx.messaginglib.core.message.MessageRegistry;
import net.tmxx.messaginglib.listener.PlayerJoinListener;
import net.tmxx.messaginglib.receiver.MessageReceiver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

/**
 * Created by tmxx on 14.01.2016
 *
 * <p>The main part of the Bukkit Plugin MessagingLib.</p>
 */
public class MessagingLib extends JavaPlugin {
    /**
     * The channel we use for plugin messages.
     */
    public static final String MESSAGING_LIB_CHANNEL = "MessagingLib";

    /**
     * The channels BungeeCord uses for plugin messages.
     */
    public static final String BUNGEE_CORD_CHANNEL = "BungeeCord";

    /**
     * The MessagingLib instance.
     */
    @Getter private static MessagingLib messagingLib;

    /**
     * This object handles all received messages.
     */
    @Getter private static MessageReceiver messageReceiver;

    /**
     * The message registry we use.
     */
    @Getter private static MessageRegistry messageRegistry;

    /**
     * This object manages all incoming and outgoing
     * messages and calls listeners.
     */
    @Getter private static BukkitMessageManager messageManager;

    /**
     * Called when the plugin is enabling.
     */
    @Override
    public void onEnable() {
        messagingLib = this;
        messageRegistry = new MessageRegistry();
        messageManager = new BukkitMessageManager();
        messageReceiver = new MessageReceiver();

        getServer().getPluginManager().registerEvents( new PlayerJoinListener(), this );

        getLogger().info( "Starting to register plugin channels..." );
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
     * Registers all needed incoming and outgoing channels.
     */
    private void registerChannels() {
        // Get the bukkit messenger
        Messenger messenger = Bukkit.getMessenger();

        // Register the bungeecord channel
        messenger.registerOutgoingPluginChannel( this, BUNGEE_CORD_CHANNEL );
        messenger.registerIncomingPluginChannel( this, BUNGEE_CORD_CHANNEL, messageReceiver );

        // Register the message lib channel
        messenger.registerOutgoingPluginChannel( this, MESSAGING_LIB_CHANNEL );
        messenger.unregisterIncomingPluginChannel( this, MESSAGING_LIB_CHANNEL, messageReceiver );
    }
}
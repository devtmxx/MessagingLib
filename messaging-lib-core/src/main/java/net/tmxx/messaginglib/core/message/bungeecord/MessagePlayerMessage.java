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
package net.tmxx.messaginglib.core.message.bungeecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.tmxx.messaginglib.core.message.BungeeCordMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by tmxx on 15.01.2016
 *
 * <p>Sends a message to a player.</p>
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class MessagePlayerMessage extends BungeeCordMessage {
    /**
     * The player to send the message to
     */
    private String player;

    /**
     * The message to send to the player
     */
    private String message;

    /**
     * Constructs a new message player message
     */
    public MessagePlayerMessage() {
        setSubChannel( "Message" );
        setContent( this );
    }

    @Override
    public void write( DataOutputStream outputStream ) {
        try {
            outputStream.writeUTF( this.player );
            outputStream.writeUTF( this.message );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void read( DataInputStream inputStream ) {
        // We don't need to read this message
    }
}

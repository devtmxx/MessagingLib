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
 * Created by tmxx on 14.01.2016
 *
 * <p>This message connects a player to a server.</p>
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class ConnectMessage extends BungeeCordMessage {
    /**
     * The server to connect to
     */
    private String server;

    /**
     * Constructs a new connect message
     */
    public ConnectMessage() {
        setSubChannel( "Connect" );
        setContent( this );
    }

    @Override
    public void write( DataOutputStream outputStream ) {
        super.write( outputStream );
        try {
            outputStream.writeUTF( this.server );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void read( DataInputStream inputStream ) {
        try {
            this.server = inputStream.readUTF();
        } catch ( IOException e ) {
            // Do nothing here
        }
    }
}

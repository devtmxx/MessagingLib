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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tmxx on 14.01.2016
 *
 * <p>Queries the player list of a server.</p>
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class PlayerListMessage extends BungeeCordMessage {
    /**
     * The server to query the player list from
     */
    private String server;

    private List<String> players = new ArrayList<>();

    /**
     * Constructs a new player list message
     */
    public PlayerListMessage() {
        setSubChannel( "PlayerList" );
        setContent( this );
    }

    @Override
    public void write( DataOutputStream outputStream ) {
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

            this.players.clear();
            Collections.addAll( this.players, inputStream.readUTF().split( ", " ) );
        } catch ( IOException e ) {
            // Do nothing here
        }
    }
}

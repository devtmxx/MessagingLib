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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.tmxx.messaginglib.core.message.PluginMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by tmxx on 14.01.2016
 *
 * <p>This is a specified class managing BungeeCord
 * plugin messages because their system is "different".</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public abstract class BungeeCordMessage extends PluginMessage {
    /**
     * The sub channel of this message
     */
    private String subChannel;

    /**
     * The content of this message
     */
    private PluginMessage content;

    @Override
    public void write( DataOutputStream outputStream ) {
    }

    @Override
    public void read( DataInputStream inputStream ) {
    }
}

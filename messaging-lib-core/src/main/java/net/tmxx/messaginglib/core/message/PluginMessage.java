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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by tmxx on 14.01.2016
 *
 * <p>This class is used to manage the data transferred from
 * one server to another. In case you would like to
 * transfer objects using this library, these objects
 * must inherit from this class.</p>
 */
public abstract class PluginMessage {
    /**
     * Writes an {@link java.lang.Integer} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the integer to
     * @param value The value of the integer to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeInt( DataOutputStream outputStream, int value ) throws IOException {
        outputStream.writeInt( value );
    }

    /**
     * Writes a {@link java.lang.Boolean} to the {@link java.io.DataOutputStream}.
     * To shorten the payload instead of true and false we automatically
     * send 0 for false and 1 for true.
     * @param outputStream The output stream to write the boolean to
     * @param value The value of the boolean to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeBoolean( DataOutputStream outputStream, boolean value ) throws IOException {
        outputStream.writeBoolean( value );
    }

    /**
     * Writes a {@link java.lang.Byte} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the byte to
     * @param value The value of the byte to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeByte( DataOutputStream outputStream, byte value ) throws IOException {
        outputStream.writeByte( value );
    }

    /**
     * Writes a {@link java.lang.String} to the {@link java.io.DataOutputStream}.
     * Invoking this method will automatically set the {@link java.nio.charset.Charset} to UTF-8.
     * @param outputStream The output stream to write the string to
     * @param value The value of the string to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeString( DataOutputStream outputStream, String value ) throws IOException {
        writeString( outputStream, value, Charset.forName( "UTF-8" ) );
    }

    /**
     * Writes a {@link java.lang.String} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the string to
     * @param value The value of the string to write
     * @param charset The charset to get the bytes of the string
     * @throws IOException Thrown when an error occurs
     */
    public void writeString( DataOutputStream outputStream, String value, Charset charset ) throws IOException {
        outputStream.writeInt( value.length() );
        outputStream.write( value.getBytes( charset ) );
    }

    /**
     * Writes a {@link java.lang.Long} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the long to
     * @param value The value of the long to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeLong( DataOutputStream outputStream, long value ) throws IOException {
        outputStream.writeLong( value );
    }

    /**
     * Writes a {@link java.lang.Short} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the short to
     * @param value The value of the short to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeShort( DataOutputStream outputStream, short value ) throws IOException {
        outputStream.writeShort( value );
    }

    /**
     * Writes a {@link java.lang.Float} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the float to
     * @param value The value of the float to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeFloat( DataOutputStream outputStream, float value ) throws IOException {
        outputStream.writeFloat( value );
    }

    /**
     * Writes a {@link java.lang.Double} to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the double to
     * @param value The value of the double to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeDouble( DataOutputStream outputStream, double value ) throws IOException {
        outputStream.writeDouble( value );
    }

    /**
     * Writes an {@link java.util.UUID} to the {@link java.io.DataOutputStream}.
     * This will write out the most and least significant bits if
     * the uuid is not null. Otherwise this will only write out -1.
     * @param outputStream The output stream to write the uuid to
     * @param value The value of the uuid to write
     * @throws IOException Thrown when an error occurs
     */
    public void writeUUID( DataOutputStream outputStream, UUID value ) throws IOException {
        if ( value == null ) {
            outputStream.writeLong( -1 );
        } else {
            outputStream.writeLong( value.getMostSignificantBits() );
            outputStream.writeLong( value.getLeastSignificantBits() );
        }
    }

    /**
     * Reads an {@link java.lang.Integer} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the integer from
     * @return The integer we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public int readInt( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readInt();
        } catch ( EOFException e ) {
            return -1;
        }
    }

    /**
     * Reads a {@link java.lang.Boolean} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the boolean from
     * @return The boolean we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public boolean readBoolean( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readBoolean();
        } catch ( EOFException e) {
            return false;
        }
    }

    /**
     * Reads a {@link java.lang.Byte} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the byte from
     * @return The byte we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public byte readByte( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readByte();
        } catch ( EOFException e ) {
            return -1;
        }
    }

    /**
     * Reads a {@link java.lang.String} from the {@link java.io.DataInputStream}.
     * Invoking this method will automatically set the {@link java.nio.charset.Charset} to UTF-8.
     * @param inputStream The input stream to read the string from
     * @return The string we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public String readString( DataInputStream inputStream ) throws IOException {
        try {
            return readString( inputStream, Charset.forName( "UTF-8" ) );
        } catch ( EOFException e ) {
            return null;
        }
    }

    /**
     * Reads a {@link java.lang.String} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the string from
     * @param charset The charset to use when reading the bytes of the string
     * @return The string we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public String readString( DataInputStream inputStream, Charset charset ) throws IOException {
        try {
            byte[] payload = new byte[ inputStream.readInt() ];
            if ( inputStream.read( payload ) == -1 ) {
                // An error occurs
                throw new IOException( "Error while trying to read bytes" );
            } else {
                return new String( payload, charset );
            }
        } catch ( EOFException e ) {
            return null;
        }
    }

    /**
     * Reads a {@link java.lang.Long} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the long from
     * @return The long we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public long readLong( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readLong();
        } catch ( EOFException e ) {
            return -1;
        }
    }

    /**
     * Reads a {@link java.lang.Short} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the short from
     * @return The short we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public short readShort( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readShort();
        } catch ( EOFException e) {
            return -1;
        }
    }

    /**
     * Reads a {@link java.lang.Float} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the float from
     * @return The float we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public float readFloat( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readFloat();
        } catch ( EOFException e ) {
            return -1;
        }
    }

    /**
     * Reads a {@link java.lang.Double} from the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the double from
     * @return The double we've read out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public double readDouble( DataInputStream inputStream ) throws IOException {
        try {
            return inputStream.readDouble();
        } catch ( EOFException e ) {
            return -1;
        }
    }

    /**
     * Reads an {@link java.util.UUID} from the {@link java.io.DataInputStream}.
     * This reads out the uuid in two simple steps. First we read out a long.
     * If the uuid was null when wrote to the stream we will read out -1.
     * In this case we will return null. Otherwise we will firstly read out the
     * most and then the least significant bits of the uuid.
     * @param inputStream The input stream to read the uuid from
     * @return The uuid we've read and parsed out of the input stream
     * @throws IOException Thrown when an error occurs
     */
    public UUID readUUID( DataInputStream inputStream ) throws IOException {
        try {
            long mostSignificant = inputStream.readLong();
            if ( mostSignificant == -1 ) {
                return null;
            } else {
                return new UUID( mostSignificant, inputStream.readLong() );
            }
        } catch ( EOFException e ) {
            return null;
        }
    }

    /**
     * Writes the message to the {@link java.io.DataOutputStream}.
     * @param outputStream The output stream to write the data to
     */
    public abstract void write( DataOutputStream outputStream );

    /**
     * Reads the message out of the {@link java.io.DataInputStream}.
     * @param inputStream The input stream to read the data from
     */
    public abstract void read( DataInputStream inputStream );
}
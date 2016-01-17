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
package net.tmxx.messaginglib.core.exception;

/**
 * Created by tmxx on 15.01.2016
 *
 * <p>Exception being thrown when somebody tries to register
 * a class as listener which does not implements {@link net.tmxx.messaginglib.core.listener.MessageListener}.</p>
 */
public class InvalidListenerException extends RuntimeException {
    /**
     * Message accepting constructor.
     * @param message The error message
     */
    public InvalidListenerException( String message ) {
        super( message );
    }

    /**
     * Throwable accepting constructor.
     * @param throwable The thrown throwable
     */
    public InvalidListenerException( Throwable throwable ) {
        super( throwable );
    }

    /**
     * Message and throwable accepting constructor.
     * @param message The error message
     * @param throwable The thrown throwable
     */
    public InvalidListenerException( String message, Throwable throwable ) {
        super( message, throwable );
    }
}

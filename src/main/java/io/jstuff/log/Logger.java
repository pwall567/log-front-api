/*
 * @(#) Logger.java
 *
 * log-front-api  Logging Interface API
 * Copyright (c) 2022, 2025 Peter Wall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.jstuff.log;

import java.time.Clock;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * The main {@code Logger} interface.
 *
 * @author  Peter Wall
 */
public interface Logger {

    /**
     * Get the name associated with this {@code Logger}.  This is often (but not always) the fully-qualified class name
     * of the class with which this {@code Logger} is associated.
     *
     * @return      the name
     */
    String getName();

    /**
     * Get the minimum level to be output by this {@code Logger} (the default implementation returns {@code INFO}).
     *
     * @return      the {@link Level}
     */
    default Level getLevel() {
        return Level.INFO;
    }

    /**
     * Set the minimum level to be output by this {@code Logger} (the default implementation does nothing).
     *
     * @param   level   the new {@link Level}
     */
    default void setLevel(Level level) {
        // ignore
    }

    /**
     * Get the {@link Clock} used by this {@code Logger} (the default implementation returns the system clock).
     *
     * @return      the {@link Clock}
     */
    default Clock getClock() {
        return LoggerFactory.systemClock;
    }

    /**
     * Set the {@link Clock} used by this {@code Logger} (the default implementation does nothing).
     *
     * @param   clock   the new {@link Clock}
     */
    default void setClock(Clock clock) {
        // ignore
    }

    /**
     * Output a trace message.
     *
     * @param   message     the message
     */
    void trace(Object message);

    /**
     * Output a trace message, specifying the time as an {@link Instant} (the default implementation ignores the time).
     *
     * @param   time        the time
     * @param   message     the message
     */
     default void trace(Instant time, Object message) {
         trace(message);
     }

    /**
     * Output a debug message.
     *
     * @param   message     the message
     */
    void debug(Object message);

    /**
     * Output a debug message, specifying the time as an {@link Instant} (the default implementation ignores the time).
     *
     * @param   time        the time
     * @param   message     the message
     */
    default void debug(Instant time, Object message) {
        debug(message);
    }

    /**
     * Output an info message.
     *
     * @param   message     the message
     */
    void info(Object message);

    /**
     * Output an info message, specifying the time as an {@link Instant} (the default implementation ignores the time).
     *
     * @param   time        the time
     * @param   message     the message
     */
    default void info(Instant time, Object message) {
        info(message);
    }

    /**
     * Output a warning message.
     *
     * @param   message     the message
     */
    void warn(Object message);

    /**
     * Output a warning message, specifying the time as an {@link Instant} (the default implementation ignores the
     * time).
     *
     * @param   time        the time
     * @param   message     the message
     */
    default void warn(Instant time, Object message) {
        warn(message);
    }

    /**
     * Output an error message.
     *
     * @param   message     the message
     */
    void error(Object message);

    /**
     * Output an error message, specifying the time as an {@link Instant} (the default implementation ignores the time).
     *
     * @param   time        the time
     * @param   message     the message
     */
    default void error(Instant time, Object message) {
        error(message);
    }

    /**
     * Output an error message along with a {@link Throwable}.
     *
     * @param   throwable   the {@link Throwable}
     * @param   message     the message
     */
    void error(Throwable throwable, Object message);

    /**
     * Output an error message along with a {@link Throwable}, specifying the time as an {@link Instant} (the default
     * implementation ignores the time).
     *
     * @param   time        the time
     * @param   throwable   the {@link Throwable}
     * @param   message     the message
     */
    default void error(Instant time, Throwable throwable, Object message) {
        error(throwable, message);
    }

    /**
     * Test whether trace output is enabled for this {@code Logger}.
     *
     * @return      {@code true} if trace output is enabled
     */
    default boolean isTraceEnabled() {
        return getLevel().isEnabled(Level.TRACE);
    }

    /**
     * Test whether debug output is enabled for this {@code Logger}.
     *
     * @return      {@code true} if debug output is enabled
     */
    default boolean isDebugEnabled() {
        return getLevel().isEnabled(Level.DEBUG);
    }

    /**
     * Test whether info output is enabled for this {@code Logger}.
     *
     * @return      {@code true} if info output is enabled
     */
    default boolean isInfoEnabled() {
        return getLevel().isEnabled(Level.INFO);
    }

    /**
     * Test whether warning output is enabled for this {@code Logger}.
     *
     * @return      {@code true} if warning output is enabled
     */
    default boolean isWarnEnabled() {
        return getLevel().isEnabled(Level.WARN);
    }

    /**
     * Test whether error output is enabled for this {@code Logger}.
     *
     * @return      {@code true} if error output is enabled
     */
    default boolean isErrorEnabled() {
        return getLevel().isEnabled(Level.ERROR);
    }

    /**
     * Test whether the specified level is enabled for this {@code Logger}.
     *
     * @param   level   the {@link Level}
     * @return          {@code true} if output of the specified level is enabled
     */
    default boolean isEnabled(Level level) {
        return getLevel().isEnabled(level);
    }

    /**
     * Output a trace message supplied by a {@link Supplier} function.  The function will be called only if the logging
     * level is enabled.
     *
     * @param   messageSupplier     the message supplier
     */
    default void trace(Supplier<Object> messageSupplier) {
        if (isTraceEnabled())
            trace(messageSupplier.get());
    }

    /**
     * Output a trace message supplied by a {@link Supplier} function, specifying the time as an {@link Instant} (the
     * default implementation ignores the time).  The function will be called only if the logging level is enabled.
     *
     * @param   time                the time
     * @param   messageSupplier     the message supplier
     */
    default void trace(Instant time, Supplier<Object> messageSupplier) {
        if (isTraceEnabled())
            trace(time, messageSupplier.get());
    }

    /**
     * Output a debug message supplied by a {@link Supplier} function.  The function will be called only if the logging
     * level is enabled.
     *
     * @param   messageSupplier     the message supplier
     */
    default void debug(Supplier<Object> messageSupplier) {
        if (isDebugEnabled())
            debug(messageSupplier.get());
    }

    /**
     * Output a debug message supplied by a {@link Supplier} function, specifying the time as an {@link Instant} (the
     * default implementation ignores the time).  The function will be called only if the logging level is enabled.
     *
     * @param   time                the time
     * @param   messageSupplier     the message supplier
     */
    default void debug(Instant time, Supplier<Object> messageSupplier) {
        if (isDebugEnabled())
            debug(time, messageSupplier.get());
    }

    /**
     * Output an info message supplied by a {@link Supplier} function.  The function will be called only if the logging
     * level is enabled.
     *
     * @param   messageSupplier     the message supplier
     */
    default void info(Supplier<Object> messageSupplier) {
        if (isInfoEnabled())
            info(messageSupplier.get());
    }

    /**
     * Output an info message supplied by a {@link Supplier} function, specifying the time as an {@link Instant} (the
     * default implementation ignores the time).  The function will be called only if the logging level is enabled.
     *
     * @param   time                the time
     * @param   messageSupplier     the message supplier
     */
    default void info(Instant time, Supplier<Object> messageSupplier) {
        if (isInfoEnabled())
            info(time, messageSupplier.get());
    }

    /**
     * Output a warning message supplied by a {@link Supplier} function.  The function will be called only if the
     * logging level is enabled.
     *
     * @param   messageSupplier     the message supplier
     */
    default void warn(Supplier<Object> messageSupplier) {
        if (isWarnEnabled())
            warn(messageSupplier.get());
    }

    /**
     * Output a warning message supplied by a {@link Supplier} function, specifying the time as an {@link Instant} (the
     * default implementation ignores the time).  The function will be called only if the logging level is enabled.
     *
     * @param   time                the time
     * @param   messageSupplier     the message supplier
     */
    default void warn(Instant time, Supplier<Object> messageSupplier) {
        if (isWarnEnabled())
            warn(time, messageSupplier.get());
    }

    /**
     * Output an error message supplied by a {@link Supplier} function.  The function will be called only if the logging
     * level is enabled.
     *
     * @param   messageSupplier     the message supplier
     */
    default void error(Supplier<Object> messageSupplier) {
        if (isErrorEnabled())
            error(messageSupplier.get());
    }

    /**
     * Output an error message supplied by a {@link Supplier} function, specifying the time as an {@link Instant} (the
     * default implementation ignores the time).  The function will be called only if the logging level is enabled.
     *
     * @param   time                the time
     * @param   messageSupplier     the message supplier
     */
    default void error(Instant time, Supplier<Object> messageSupplier) {
        if (isErrorEnabled())
            error(time, messageSupplier.get());
    }

    /**
     * Output an error message supplied by a {@link Supplier} function, along with a {@link Throwable}.  The function
     * will be called only if the logging level is enabled.
     *
     * @param   messageSupplier     the message supplier
     * @param   throwable           the {@link Throwable}
     */
    default void error(Throwable throwable, Supplier<Object> messageSupplier) {
        if (isErrorEnabled())
            error(throwable, messageSupplier.get());
    }

    /**
     * Output an error message supplied by a {@link Supplier} function, specifying the time as an {@link Instant} (the
     * default implementation ignores the time), along with a {@link Throwable}.  The function will be called only if
     * the logging level is enabled.
     *
     * @param   time                the time
     * @param   messageSupplier     the message supplier
     * @param   throwable           the {@link Throwable}
     */
    default void error(Instant time, Throwable throwable, Supplier<Object> messageSupplier) {
        if (isErrorEnabled())
            error(time, throwable, messageSupplier.get());
    }

    /**
     * Output a message with a variable level.
     *
     * @param   level       the {@link Level}
     * @param   message     the message (will be output using {@link Object#toString() toString()}
     */
    default void log(Level level, Object message) {
        switch (level) {
        case TRACE:
            trace(message);
            break;
        case DEBUG:
            debug(message);
            break;
        case INFO:
            info(message);
            break;
        case WARN:
            warn(message);
            break;
        case ERROR:
            error(message);
        }
    }

    /**
     * Output a message with a variable level, specifying the time as an {@link Instant} (the default implementation
     * ignores the time).
     *
     * @param   time                the time
     * @param   level       the {@link Level}
     * @param   message     the message (will be output using {@link Object#toString() toString()}
     */
    default void log(Instant time, Level level, Object message) {
        switch (level) {
        case TRACE:
            trace(time, message);
            break;
        case DEBUG:
            debug(time, message);
            break;
        case INFO:
            info(time, message);
            break;
        case WARN:
            warn(time, message);
            break;
        case ERROR:
            error(time, message);
        }
    }

    /**
     * Output a message supplied by a {@link Supplier} function, with a variable level.  The function will be called
     * only if the logging level is enabled.
     *
     * @param   level               the {@link Level}
     * @param   messageSupplier     the message supplier
     */
    default void log(Level level, Supplier<Object> messageSupplier) {
        switch (level) {
        case TRACE:
            trace(messageSupplier);
            break;
        case DEBUG:
            debug(messageSupplier);
            break;
        case INFO:
            info(messageSupplier);
            break;
        case WARN:
            warn(messageSupplier);
            break;
        case ERROR:
            error(messageSupplier);
        }
    }

    /**
     * Output a message supplied by a {@link Supplier} function, with a variable level, specifying the time as an
     * {@link Instant} (the default implementation ignores the time).  The function will be called only if the logging
     * level is enabled.
     *
     * @param   time                the time
     * @param   level               the {@link Level}
     * @param   messageSupplier     the message supplier
     */
    default void log(Instant time, Level level, Supplier<Object> messageSupplier) {
        switch (level) {
        case TRACE:
            trace(time, messageSupplier);
            break;
        case DEBUG:
            debug(time, messageSupplier);
            break;
        case INFO:
            info(time, messageSupplier);
            break;
        case WARN:
            warn(time, messageSupplier);
            break;
        case ERROR:
            error(time, messageSupplier);
        }
    }

}

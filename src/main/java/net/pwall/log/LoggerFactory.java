/*
 * @(#) LoggerFactory.java
 *
 * log-front-api  Logging Interface API
 * Copyright (c) 2022 Peter Wall
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

package net.pwall.log;

import java.time.Clock;

/**
 * The {@code LoggerFactory} supplies a {@link Logger} of a particular type.
 *
 * @author  Peter Wall
 * @param   <L>     the Logger type
 */
public interface LoggerFactory<L extends Logger> {

    String logPackageName = "net.pwall.log"; // must match the package of this interface
    Clock systemClock = Clock.systemDefaultZone();

    /**
     * Get a {@link Logger} with the specified name, level and clock.
     *
     * @param   name    the name
     * @param   level   the level
     * @param   clock   the clock
     * @return          the {@link Logger}
     */
    L getLogger(String name, Level level, Clock clock);

    /**
     * Get a {@link Logger} with the specified name and level.
     *
     * @param   name    the name
     * @param   level   the level
     * @return          the {@link Logger}
     */
    default L getLogger(String name, Level level) {
        return getLogger(name, level, getDefaultClock());
    }

    /**
     * Get a {@link Logger} with the specified name and clock.
     *
     * @param   name    the name
     * @param   clock   the clock
     * @return          the {@link Logger}
     */
    default L getLogger(String name, Clock clock) {
        return getLogger(name, getDefaultLevel(), clock);
    }

    /**
     * Get a {@link Logger} with the specified name.
     *
     * @param   name    the name
     * @return          the {@link Logger}
     */
    default L getLogger(String name) {
        return getLogger(name, getDefaultLevel(), getDefaultClock());
    }

    /**
     * Get a {@link Logger} with the name of the calling class.
     *
     * @return          the {@link Logger}
     */
    default L getLogger() {
        return getLogger(callerInfo().getClassName(), getDefaultLevel(), getDefaultClock());
    }

    /**
     * Get a {@link Logger} with the name of the calling class and the specified level and clock.
     *
     * @param   level   the level
     * @param   clock   the clock
     * @return          the {@link Logger}
     */
    default L getLogger(Level level, Clock clock) {
        return getLogger(callerInfo().getClassName(), level, clock);
    }

    /**
     * Get a {@link Logger} with the name of the calling class and the specified level.
     *
     * @param   level   the level
     * @return          the {@link Logger}
     */
    default L getLogger(Level level) {
        return getLogger(callerInfo().getClassName(), level, getDefaultClock());
    }

    /**
     * Get a {@link Logger} with the name of the calling class and the specified level and clock.
     *
     * @param   clock   the clock
     * @return          the {@link Logger}
     */
    default L getLogger(Clock clock) {
        return getLogger(callerInfo().getClassName(), getDefaultLevel(), clock);
    }

    /**
     * Get the default {@link Level} used by this {@code LoggerFactory}.
     *
     * @return      the default {@link Level}
     */
    default Level getDefaultLevel() {
        return Level.INFO;
    }

    /**
     * Get the default {@link Clock} used by this {@code LoggerFactory}.
     *
     * @return      the default {@link Clock}
     */
    default Clock getDefaultClock() {
        return systemClock;
    }

    /**
     * Get the caller information in the form of a {@link StackTraceElement}, to allow the class name of the caller to
     * be used for the name of the {@link Logger}.
     *
     * @return      the {@link StackTraceElement} for the caller
     */
    static StackTraceElement callerInfo() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stack = throwable.getStackTrace();
        for (int i = 2, n = stack.length; i < n; i++) {
            StackTraceElement element = stack[i];
            String className = element.getClassName();
            int j = className.lastIndexOf('.');
            if (j < 0 || !className.substring(0, j).equals(logPackageName))
                return element;
        }
        return new StackTraceElement("unknown", "unknown", null, -1);
    }

}
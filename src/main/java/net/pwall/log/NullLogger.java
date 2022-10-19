/*
 * @(#) NullLogger.java
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

import java.util.Objects;

/**
 * A null implementation of the {@link Logger} interface (logs nothing).
 *
 * @author  Peter Wall
 */
public class NullLogger implements Logger {

    private final String name;

    /**
     * Construct a {@code NullLogger} with the specified name.
     *
     * @param   name    the name
     */
    public NullLogger(String name) {
        this.name = Objects.requireNonNull(name, "NullLogger name must not be null");
    }

    /**
     * Get the name associated with this {@code Logger}.
     *
     * @return      the name
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void trace(Object message) {
        // ignore
    }

    @Override
    public void debug(Object message) {
        // ignore
    }

    @Override
    public void info(Object message) {
        // ignore
    }

    @Override
    public void warn(Object message) {
        // ignore
    }

    @Override
    public void error(Object message) {
        // ignore
    }

    @Override
    public void error(Throwable throwable, Object message) {
        // ignore
    }

    @Override
    public void log(Level level, Object message) {
        // ignore
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(Level level) {
        return false;
    }

}

/*
 * @(#) NullLoggerFactory.java
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

/**
 * A null implementation of the {@link LoggerFactory} interface.
 *
 * @author  Peter Wall
 */
public class NullLoggerFactory implements LoggerFactory<NullLogger> {

    /**
     * Get a {@link NullLogger} with the specified name, level and clock.
     *
     * @param   name    the name
     * @param   level   the level (ignored)
     * @param   clock   the clock (ignored)
     * @return          the {@link NullLogger}
     * @throws  LoggerException if the name is {@code null} or contains illegal (non-ASCII) characters
     */
    @Override
    public NullLogger getLogger(String name, Level level, Clock clock) {
        LoggerFactory.validateLoggerName(name);
        return new NullLogger(name);
    }

}

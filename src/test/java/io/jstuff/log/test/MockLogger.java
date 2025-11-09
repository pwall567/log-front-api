/*
 * @(#) MockLogger.java
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

package io.jstuff.log.test;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import io.jstuff.log.Level;
import io.jstuff.log.Logger;
import io.jstuff.util.DateOutput;

public class MockLogger implements Logger {

    private final StringBuilder sb;
    private final String name;
    private Level level;
    private Clock clock;

    public MockLogger(String name, Level level, Clock clock) {
        this.name = name;
        this.level = level;
        this.clock = clock;
        sb = new StringBuilder();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void trace(Object message) {
        sb.append(name).append(" TRACE ").append(message).append('\n');
    }

    @Override
    public void trace(Instant time, Object message) {
        sb.append(name).append(" TRACE@");
        appendTime(sb, time);
        sb.append(' ').append(message).append('\n');
    }

    @Override
    public void debug(Object message) {
        sb.append(name).append(" DEBUG ").append(message).append('\n');
    }

    @Override
    public void debug(Instant time, Object message) {
        sb.append(name).append(" DEBUG@");
        appendTime(sb, time);
        sb.append(' ').append(message).append('\n');
    }

    @Override
    public void info(Object message) {
        sb.append(name).append(" INFO ").append(message).append('\n');
    }

    @Override
    public void info(Instant time, Object message) {
        sb.append(name).append(" INFO@");
        appendTime(sb, time);
        sb.append(' ').append(message).append('\n');
    }

    @Override
    public void warn(Object message) {
        sb.append(name).append(" WARN ").append(message).append('\n');
    }

    @Override
    public void warn(Instant time, Object message) {
        sb.append(name).append(" WARN@");
        appendTime(sb, time);
        sb.append(' ').append(message).append('\n');
    }

    @Override
    public void error(Object message) {
        sb.append(name).append(" ERROR ").append(message).append('\n');
    }

    @Override
    public void error(Instant time, Object message) {
        sb.append(name).append(" ERROR@");
        appendTime(sb, time);
        sb.append(' ').append(message).append('\n');
    }

    @Override
    public void error(Throwable throwable, Object message) {
        sb.append(name).append(" ERROR ").append(message).append(" : ").append(throwable.getMessage()).append('\n');
    }

    @Override
    public void error(Instant time, Throwable throwable, Object message) {
        sb.append(name).append(" ERROR@");
        appendTime(sb, time);
        sb.append(' ').append(message).append(" : ").append(throwable.getMessage()).append('\n');
    }

    public String getContents() {
        return sb.toString();
    }

    private static void appendTime(StringBuilder sb, Instant time) {
        try {
            DateOutput.appendOffsetDateTime(sb, OffsetDateTime.ofInstant(time, ZoneId.of("Australia/Sydney")));
        }
        catch (IOException ignore) {
            // can't happen - StringBuilder doesn't throw exception
        }
    }

}

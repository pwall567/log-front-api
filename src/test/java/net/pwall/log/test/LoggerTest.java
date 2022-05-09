/*
 * @(#) LoggerTest.java
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

package net.pwall.log.test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import net.pwall.log.Level;
import net.pwall.log.Logger;
import net.pwall.log.LoggerFactory;
import net.pwall.log.NullLogger;

public class LoggerTest {

    @Test
    public void shouldGetLoggerName() {
        MockLogger mockLogger = new MockLogger("Rosella", Level.INFO, LoggerFactory.systemClock);
        assertEquals("Rosella", mockLogger.getName());
    }

    @Test
    public void shouldLogInfo() {
        MockLogger mockLogger = new MockLogger("Galah", Level.INFO, LoggerFactory.systemClock);
        mockLogger.info("hello");
        assertEquals("Galah INFO hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogInfoUsingLambda() {
        MockLogger mockLogger = new MockLogger("Corella", Level.INFO, LoggerFactory.systemClock);
        mockLogger.info(() -> "hello");
        assertEquals("Corella INFO hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldNotLogInfoWhenLevelSetToWarn() {
        MockLogger mockLogger = new MockLogger("Cockatoo", Level.WARN, LoggerFactory.systemClock);
        mockLogger.info(() -> {
            fail("Should not be called");
            return null;
        });
        assertEquals("", mockLogger.getContents());
    }

    @Test
    public void shouldLogErrorWithThrowable() {
        MockLogger mockLogger = new MockLogger("Budgerigar", Level.INFO, LoggerFactory.systemClock);
        Throwable throwable = new RuntimeException("magic");
        mockLogger.error(throwable, () -> "goodbye");
        assertEquals("Budgerigar ERROR goodbye : magic\n", mockLogger.getContents());
    }

    @Test
    public void shouldIgnoreSetterIfNotOverridden() {
        Logger logger = new NullLogger("Emu");
        Clock clock = logger.getClock();
        logger.setClock(Clock.fixed(Instant.now(), ZoneOffset.UTC));
        assertSame(clock, logger.getClock());
        Level level = logger.getLevel();
        logger.setLevel(Level.TRACE);
        assertEquals(level, logger.getLevel());
    }

}

/*
 * @(#) LoggerTest.java
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

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.jstuff.log.Level;
import io.jstuff.log.Logger;
import io.jstuff.log.LoggerFactory;
import io.jstuff.log.NullLogger;

public class LoggerTest {

    @Test
    public void shouldGetLoggerName() {
        MockLogger mockLogger = new MockLogger("Rosella", Level.INFO, LoggerFactory.systemClock);
        assertEquals("Rosella", mockLogger.getName());
    }

    @Test
    public void shouldLogTrace() {
        MockLogger mockLogger = new MockLogger("Lorikeet", Level.TRACE, LoggerFactory.systemClock);
        mockLogger.trace("hello");
        assertEquals("Lorikeet TRACE hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogTraceWithTime() {
        MockLogger mockLogger = new MockLogger("Lorikeet", Level.TRACE, LoggerFactory.systemClock);
        OffsetDateTime time = OffsetDateTime.of(2025, 7, 18, 12, 20, 24, 123000000, ZoneOffset.ofHours(10));
        mockLogger.trace(time.toInstant(), "hello");
        assertEquals("Lorikeet TRACE@2025-07-18T12:20:24.123+10:00 hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogDebug() {
        MockLogger mockLogger = new MockLogger("Lorikeet", Level.DEBUG, LoggerFactory.systemClock);
        mockLogger.debug("hello");
        assertEquals("Lorikeet DEBUG hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogDebugWithTime() {
        MockLogger mockLogger = new MockLogger("Lorikeet", Level.DEBUG, LoggerFactory.systemClock);
        OffsetDateTime time = OffsetDateTime.of(2025, 7, 18, 12, 20, 24, 123000000, ZoneOffset.ofHours(10));
        mockLogger.debug(time.toInstant(), "hello");
        assertEquals("Lorikeet DEBUG@2025-07-18T12:20:24.123+10:00 hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogInfo() {
        MockLogger mockLogger = new MockLogger("Galah", Level.INFO, LoggerFactory.systemClock);
        mockLogger.info("hello");
        assertEquals("Galah INFO hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogInfoWithTime() {
        MockLogger mockLogger = new MockLogger("Galah", Level.INFO, LoggerFactory.systemClock);
        OffsetDateTime time = OffsetDateTime.of(2025, 7, 18, 12, 20, 24, 123000000, ZoneOffset.ofHours(10));
        mockLogger.info(time.toInstant(), "hello");
        assertEquals("Galah INFO@2025-07-18T12:20:24.123+10:00 hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogInfoUsingLambda() {
        MockLogger mockLogger = new MockLogger("Corella", Level.INFO, LoggerFactory.systemClock);
        mockLogger.info(() -> "hello");
        assertEquals("Corella INFO hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogInfoWithTimeUsingLambda() {
        MockLogger mockLogger = new MockLogger("Corella", Level.INFO, LoggerFactory.systemClock);
        OffsetDateTime time = OffsetDateTime.of(2025, 7, 18, 12, 20, 24, 123000000, ZoneOffset.ofHours(10));
        mockLogger.info(time.toInstant(), () -> "hello");
        assertEquals("Corella INFO@2025-07-18T12:20:24.123+10:00 hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldNotLogInfoWhenLevelSetToWarn() {
        MockLogger mockLogger = new MockLogger("Cockatoo", Level.WARN, LoggerFactory.systemClock);
        mockLogger.info(() -> {
            fail("Should not be called");
            return null;
        });
        assertEquals(0, mockLogger.getContents().length());
    }

    @Test
    public void shouldLogWarn() {
        MockLogger mockLogger = new MockLogger("Galah", Level.INFO, LoggerFactory.systemClock);
        mockLogger.warn("hello");
        assertEquals("Galah WARN hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogWarnWithTime() {
        MockLogger mockLogger = new MockLogger("Galah", Level.INFO, LoggerFactory.systemClock);
        OffsetDateTime time = OffsetDateTime.of(2025, 7, 18, 12, 20, 24, 123000000, ZoneOffset.ofHours(10));
        mockLogger.warn(time.toInstant(), "hello");
        assertEquals("Galah WARN@2025-07-18T12:20:24.123+10:00 hello\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogErrorWithThrowable() {
        MockLogger mockLogger = new MockLogger("Budgerigar", Level.INFO, LoggerFactory.systemClock);
        Throwable throwable = new RuntimeException("magic");
        mockLogger.error(throwable, () -> "goodbye");
        assertEquals("Budgerigar ERROR goodbye : magic\n", mockLogger.getContents());
    }

    @Test
    public void shouldLogErrorWithTimeAndThrowable() {
        MockLogger mockLogger = new MockLogger("Budgerigar", Level.INFO, LoggerFactory.systemClock);
        OffsetDateTime time = OffsetDateTime.of(2025, 7, 18, 12, 20, 24, 123000000, ZoneOffset.ofHours(10));
        Throwable throwable = new RuntimeException("magic");
        mockLogger.error(time.toInstant(), throwable, () -> "goodbye");
        assertEquals("Budgerigar ERROR@2025-07-18T12:20:24.123+10:00 goodbye : magic\n", mockLogger.getContents());
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

    @Test
    public void shouldTestEnabledUsingDynamicLevel() {
        MockLogger mockLogger = new MockLogger("Cockatiel", Level.INFO, LoggerFactory.systemClock);
        assertTrue(mockLogger.isEnabled(Level.INFO));
        assertFalse(mockLogger.isEnabled(Level.DEBUG));
    }

}

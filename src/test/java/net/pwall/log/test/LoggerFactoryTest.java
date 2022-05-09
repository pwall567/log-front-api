/*
 * @(#) LoggerFactoryTest.java
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

import net.pwall.log.Level;
import net.pwall.log.Logger;
import net.pwall.log.LoggerFactory;

public class LoggerFactoryTest {

    private static final String qualifiedClassName = "net.pwall.log.test.LoggerFactoryTest";

    @Test
    public void shouldCreateLoggerWithSpecifiedName() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Logger logger = mockLoggerFactory.getLogger("Elephant");
        assertEquals("Elephant", logger.getName());
        assertEquals(Level.INFO, logger.getLevel());
        assertSame(LoggerFactory.systemClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedNameAndLevel() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Logger logger = mockLoggerFactory.getLogger("Dugong", Level.DEBUG);
        assertEquals("Dugong", logger.getName());
        assertEquals(Level.DEBUG, logger.getLevel());
        assertSame(LoggerFactory.systemClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedNameAndClock() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Logger logger = mockLoggerFactory.getLogger("Walrus", fixedClock);
        assertEquals("Walrus", logger.getName());
        assertEquals(Level.INFO, logger.getLevel());
        assertSame(fixedClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedNameLevelAndClock() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Logger logger = mockLoggerFactory.getLogger("Narwhal", Level.DEBUG, fixedClock);
        assertEquals("Narwhal", logger.getName());
        assertEquals(Level.DEBUG, logger.getLevel());
        assertSame(fixedClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedClass() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Logger logger = mockLoggerFactory.getLogger(LoggerFactoryTest.class);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.INFO, logger.getLevel());
        assertSame(LoggerFactory.systemClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedClassAndLevel() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Logger logger = mockLoggerFactory.getLogger(LoggerFactoryTest.class, Level.DEBUG);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.DEBUG, logger.getLevel());
        assertSame(LoggerFactory.systemClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedClassAndClock() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Logger logger = mockLoggerFactory.getLogger(LoggerFactoryTest.class, fixedClock);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.INFO, logger.getLevel());
        assertSame(fixedClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithSpecifiedClassLevelAndClock() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Logger logger = mockLoggerFactory.getLogger(LoggerFactoryTest.class, Level.DEBUG, fixedClock);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.DEBUG, logger.getLevel());
        assertSame(fixedClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithNameDeterminedDynamically() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Logger logger = mockLoggerFactory.getLogger();
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.INFO, logger.getLevel());
        assertSame(LoggerFactory.systemClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithNameDeterminedDynamicallyAndSpecifiedLevel() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Logger logger = mockLoggerFactory.getLogger(Level.DEBUG);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.DEBUG, logger.getLevel());
        assertSame(LoggerFactory.systemClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithNameDeterminedDynamicallyAndSpecifiedClock() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Logger logger = mockLoggerFactory.getLogger(fixedClock);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.INFO, logger.getLevel());
        assertSame(fixedClock, logger.getClock());
    }

    @Test
    public void shouldCreateLoggerWithNameDeterminedDynamicallyAndSpecifiedLevelAndClock() {
        MockLoggerFactory mockLoggerFactory = new MockLoggerFactory();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Logger logger = mockLoggerFactory.getLogger(Level.DEBUG, fixedClock);
        assertEquals(qualifiedClassName, logger.getName());
        assertEquals(Level.DEBUG, logger.getLevel());
        assertSame(fixedClock, logger.getClock());
    }

}

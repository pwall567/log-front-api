/*
 * @(#) LevelTest.java
 *
 * log-front-api  Logging Interface API
 * Copyright (c) 2025 Peter Wall
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

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.jstuff.log.Level;

public class LevelTest {

    @Test
    public void shouldTestIsEnabled() {
        assertTrue(Level.TRACE.isEnabled(Level.TRACE));
        assertTrue(Level.TRACE.isEnabled(Level.DEBUG));
        assertTrue(Level.TRACE.isEnabled(Level.INFO));
        assertTrue(Level.TRACE.isEnabled(Level.WARN));
        assertTrue(Level.TRACE.isEnabled(Level.ERROR));

        assertFalse(Level.DEBUG.isEnabled(Level.TRACE));
        assertTrue(Level.DEBUG.isEnabled(Level.DEBUG));
        assertTrue(Level.DEBUG.isEnabled(Level.INFO));
        assertTrue(Level.DEBUG.isEnabled(Level.WARN));
        assertTrue(Level.DEBUG.isEnabled(Level.ERROR));

        assertFalse(Level.INFO.isEnabled(Level.TRACE));
        assertFalse(Level.INFO.isEnabled(Level.DEBUG));
        assertTrue(Level.INFO.isEnabled(Level.INFO));
        assertTrue(Level.INFO.isEnabled(Level.WARN));
        assertTrue(Level.INFO.isEnabled(Level.ERROR));

        assertFalse(Level.WARN.isEnabled(Level.TRACE));
        assertFalse(Level.WARN.isEnabled(Level.DEBUG));
        assertFalse(Level.WARN.isEnabled(Level.INFO));
        assertTrue(Level.WARN.isEnabled(Level.WARN));
        assertTrue(Level.WARN.isEnabled(Level.ERROR));

        assertFalse(Level.ERROR.isEnabled(Level.TRACE));
        assertFalse(Level.ERROR.isEnabled(Level.DEBUG));
        assertFalse(Level.ERROR.isEnabled(Level.INFO));
        assertFalse(Level.ERROR.isEnabled(Level.WARN));
        assertTrue(Level.ERROR.isEnabled(Level.ERROR));
    }

}

/*
 * Copyright (c) 2011 Petter Holmström
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.peholmst.stuff4vaadin.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test case for {@link Interval}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class IntervalTest {

    @Test
    public void createClosedInterval() {
        Interval<Integer> interval = Interval.createClosedInterval(1, 10);
        assertEquals(1, interval.getStartPoint().intValue());
        assertTrue(interval.isStartPointIncluded());
        assertEquals(10, interval.getEndPoint().intValue());
        assertTrue(interval.isEndPointIncluded());
    }

    @Test
    public void createOpenInterval() {
        Interval<Integer> interval = Interval.createOpenInterval(1, 10);
        assertEquals(1, interval.getStartPoint().intValue());
        assertFalse(interval.isStartPointIncluded());
        assertEquals(10, interval.getEndPoint().intValue());
        assertFalse(interval.isEndPointIncluded());
    }

    @Test
    public void createHalfClosedIntervalEndPointExcluded() {
        Interval<Integer> interval = Interval
                .createHalfClosedIntervalEndPointExcluded(1, 10);
        assertEquals(1, interval.getStartPoint().intValue());
        assertTrue(interval.isStartPointIncluded());
        assertEquals(10, interval.getEndPoint().intValue());
        assertFalse(interval.isEndPointIncluded());
    }

    @Test
    public void createHalfClosedIntervalStartPointExcluded() {
        Interval<Integer> interval = Interval
                .createHalfClosedIntervalStartPointExcluded(1, 10);
        assertEquals(1, interval.getStartPoint().intValue());
        assertFalse(interval.isStartPointIncluded());
        assertEquals(10, interval.getEndPoint().intValue());
        assertTrue(interval.isEndPointIncluded());
    }

    @Test
    public void getIncludedStartPoint() {
        Interval<Integer> interval = Interval.createClosedInterval(1, 10);
        assertEquals(1, interval.getIncludedStartPoint().intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void getIncludedStartPointWhenExcluded() {
        Interval<Integer> interval = Interval.createOpenInterval(1, 10);
        interval.getIncludedStartPoint();
    }

    @Test
    public void getIncludedEndPoint() {
        Interval<Integer> interval = Interval.createClosedInterval(1, 10);
        assertEquals(10, interval.getIncludedEndPoint().intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void getIncludedEndPointWhenExcluded() {
        Interval<Integer> interval = Interval.createOpenInterval(1, 10);
        interval.getIncludedEndPoint();
    }

    @Test
    public void getExcludedStartPoint() {
        Interval<Integer> interval = Interval.createOpenInterval(1, 10);
        assertEquals(1, interval.getExcludedStartPoint().intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void getExcludedStartPointWhenIncluded() {
        Interval<Integer> interval = Interval.createClosedInterval(1, 10);
        interval.getExcludedStartPoint();
    }

    @Test
    public void getExcludedEndPoint() {
        Interval<Integer> interval = Interval.createOpenInterval(1, 10);
        assertEquals(10, interval.getExcludedEndPoint().intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void getExcludedEndPointWhenIncluded() {
        Interval<Integer> interval = Interval.createClosedInterval(1, 10);
        interval.getExcludedEndPoint();
    }
}

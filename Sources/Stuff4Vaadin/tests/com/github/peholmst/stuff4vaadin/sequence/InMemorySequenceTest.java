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
package com.github.peholmst.stuff4vaadin.sequence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link InMemorySequence}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class InMemorySequenceTest {

    @Test
    public void initialReservationByConstructor() {
        InMemorySequence seq = new InMemorySequence();
        assertEquals(1L, seq.getNextValue());
        assertEquals(InMemorySequence.DEFAULT_INCREMENT + 1,
                seq.getSequenceValue());
        assertEquals(InMemorySequence.DEFAULT_INCREMENT, seq.getIncrement());
    }

    @Test
    public void loopThroughNextValuesUntilRangeRunsOut() {
        InMemorySequence seq = new InMemorySequence();
        long oldValue = 0L;
        for (int i = 0; i < InMemorySequence.DEFAULT_INCREMENT; ++i) {
            long newValue = seq.getNextValue();
            assertEquals(oldValue + 1, newValue);
            oldValue = newValue;
        }
        seq.setSequenceValue(200L);
        assertEquals(200L, seq.getNextValue());
        assertEquals(250L, seq.getSequenceValue());
    }

}

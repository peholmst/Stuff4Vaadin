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

import com.github.peholmst.stuff4vaadin.common.Interval;

/**
 * This is a sequence that maintains the sequence value in memory. It is
 * intended for testing mainly.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class InMemorySequence extends Sequence {

	private static final long serialVersionUID = -2523013313645328167L;

	public static final long DEFAULT_INCREMENT = 50;

    private long sequenceValue;
    private long increment;

    /**
     * Creates a new <code>InMemorySequence</code> that starts from 1 and uses
     * the default increment.
     */
    public InMemorySequence() {
        this(1L);
    }

    /**
     * Creates a new <code>InMemorySequence</code> that starts from the
     * specified value and uses the default increment.
     * 
     * @param startWith
     *            the value from which the sequence should start.
     */
    public InMemorySequence(long startWith) {
        this(startWith, DEFAULT_INCREMENT);
    }

    /**
     * Creates a new <code>InMemorySequence</code> that starts from the
     * specified value and uses the specified increment.
     * 
     * @param startWith
     *            the value from which the sequence should start.
     * @param increment
     *            the size of the increment.
     */
    public InMemorySequence(long startWith, long increment) {
        this.sequenceValue = startWith;
        this.increment = increment;
    }

    @Override
    protected synchronized Interval<Long> reserveSequenceValues() {
        Interval<Long> interval = Interval.createClosedInterval(sequenceValue,
                sequenceValue + increment - 1);
        sequenceValue += increment;
        return interval;
    }

    /**
     * Returns the current sequence value.
     */
    public synchronized long getSequenceValue() {
        return sequenceValue;
    }

    /**
     * Sets the current sequence value.
     */
    public synchronized void setSequenceValue(long sequenceValue) {
        this.sequenceValue = sequenceValue;
    }

    /**
     * Returns the increment.
     */
    public synchronized long getIncrement() {
        return increment;
    }

}

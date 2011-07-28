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
 * This is a base class for a sequence that generates unique long integers. It
 * reserves intervals of sequence values from some backend (e.g. a database
 * sequence) and maintains the current sequence value in memory as long as it is
 * within the reserved interval. When the sequence runs out of reserved values,
 * it reserves a new interval.
 * <p>
 * This class is thread safe.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public abstract class Sequence implements java.io.Serializable {

	private static final long serialVersionUID = 7524112058366679645L;

	private long maxValue = 0L;

    private long currentValue = 0L;

    private void reserveValues() {
        Interval<Long> reservedInterval = reserveSequenceValues();
        currentValue = reservedInterval.getIncludedStartPoint();
        maxValue = reservedInterval.getIncludedEndPoint();
    }

    /**
     * Reserves a closed interval of sequence values.
     */
    protected abstract Interval<Long> reserveSequenceValues();

    /**
     * Returns the next value of the sequence.
     */
    public final synchronized long getNextValue() {
        if (currentValue == maxValue) {
            reserveValues();
        } else {
            ++currentValue;
        }
        return currentValue;
    }
}

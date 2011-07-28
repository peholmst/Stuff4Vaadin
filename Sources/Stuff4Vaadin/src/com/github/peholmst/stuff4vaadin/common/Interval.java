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

/**
 * This class represents an interval of numbers. It can be either closed, open
 * or half-closed.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public final class Interval<TYPE extends Number & Comparable<TYPE>> implements
        java.io.Serializable {

    private static final long serialVersionUID = -3994508636582146207L;

    private final TYPE startPoint;

    private final boolean startPointIncluded;

    private final TYPE endPoint;

    private final boolean endPointIncluded;

    private Interval(TYPE startPoint, boolean startPointIncluded,
            TYPE endPoint, boolean endPointIncluded) {
        assert startPoint != null : "startPoint must not be null";
        assert endPoint != null : "endPoint must not be null";
        assert endPoint.compareTo(startPoint) >= 0 : "endPoint must be greater than or equal to startPoint";

        this.startPoint = startPoint;
        this.startPointIncluded = startPointIncluded;
        this.endPoint = endPoint;
        this.endPointIncluded = endPointIncluded;
    }

    /**
     * The start point of the interval.
     * 
     * @return the start point.
     */
    public TYPE getStartPoint() {
        return startPoint;
    }

    /**
     * Returns whether the start point is included or excluded.
     * 
     * @return true if the point is included, false if it is excluded.
     */
    public boolean isStartPointIncluded() {
        return startPointIncluded;
    }

    /**
     * Returns the start point of the interval and requires it to be included in
     * the interval.
     * 
     * @return the start point.
     * @throws IllegalStateException
     *             if the start point is not included.
     */
    public TYPE getIncludedStartPoint() throws IllegalStateException {
        if (!startPointIncluded) {
            throw new IllegalStateException("Start point is not included");
        }
        return startPoint;
    }

    /**
     * Returns the start point of the interval and requires it to be excluded
     * from the interval.
     * 
     * @return the start point.
     * @throws IllegalStateException
     *             if the start point is not excluded.
     */
    public TYPE getExcludedStartPoint() throws IllegalStateException {
        if (startPointIncluded) {
            throw new IllegalStateException("Start point is not excluded");
        }
        return startPoint;
    }

    /**
     * The end point of the interval.
     * 
     * @return the end point.
     */
    public TYPE getEndPoint() {
        return endPoint;
    }

    /**
     * Returns the end point of the interval and requires it to be included in
     * the interval.
     * 
     * @return the end point.
     * @throws IllegalStateException
     *             if the end point is not included.
     */
    public TYPE getIncludedEndPoint() throws IllegalStateException {
        if (!endPointIncluded) {
            throw new IllegalStateException("End point is not included");
        }
        return endPoint;
    }

    /**
     * Returns the end point of the interval and requires it to be excluded from
     * the interval.
     * 
     * @return the end point.
     * @throws IllegalStateException
     *             if the end point is not excluded.
     */
    public TYPE getExcludedEndPoint() throws IllegalStateException {
        if (endPointIncluded) {
            throw new IllegalStateException("End point is not excluded");
        }
        return endPoint;
    }

    /**
     * Returns whether the end point is included or excluded.
     * 
     * @return true if the point is included, false if it is excluded.
     */
    public boolean isEndPointIncluded() {
        return endPointIncluded;
    }

    /**
     * Creates a closed interval, i.e. <code>[from, to]</code>.
     * 
     * @param <TYPE>
     *            the type of numbers.
     * @param from
     *            the included start point of the interval.
     * @param to
     *            the included end point of the interval.
     * @return the new interval.
     */
    public static <TYPE extends Number & Comparable<TYPE>> Interval<TYPE> createClosedInterval(
            TYPE from, TYPE to) {
        return new Interval<TYPE>(from, true, to, true);
    }

    /**
     * Creates an open interval, i.e. <code>(from, to)</code>.
     * 
     * @param <TYPE>
     *            the type of numbers.
     * @param from
     *            the excluded start point of the interval.
     * @param to
     *            the excluded end point of the interval.
     * @return the new interval.
     */
    public static <TYPE extends Number & Comparable<TYPE>> Interval<TYPE> createOpenInterval(
            TYPE from, TYPE to) {
        return new Interval<TYPE>(from, false, to, false);
    }

    /**
     * Creates a half-closed interval where the end point is excluded, i.e.
     * <code>[from, to)</code>.
     * 
     * @param <TYPE>
     *            the type of numbers.
     * @param from
     *            the included start point of the interval.
     * @param to
     *            the excluded end point of the interval.
     * @return the new interval.
     */
    public static <TYPE extends Number & Comparable<TYPE>> Interval<TYPE> createHalfClosedIntervalEndPointExcluded(
            TYPE from, TYPE to) {
        return new Interval<TYPE>(from, true, to, false);
    }

    /**
     * Creates a half-closed interval where the start point is excluded, i.e.
     * <code>(from, to]</code>.
     * 
     * @param <TYPE>
     *            the type of numbers.
     * @param from
     *            the excluded start point of the interval.
     * @param to
     *            the included end point of the interval.
     * @return the new interval.
     */
    public static <TYPE extends Number & Comparable<TYPE>> Interval<TYPE> createHalfClosedIntervalStartPointExcluded(
            TYPE from, TYPE to) {
        return new Interval<TYPE>(from, false, to, true);
    }

}

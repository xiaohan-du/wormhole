package com.cm6123.wormhole.utils;

public class Range {
    /**
     * lower bound of range.
     */
    private int low;
    /**
     * upper bound of range.
     */
    private int high;

    /**
     * Range constructor.
     *
     * @param low
     * @param high
     */
    public Range(final int low, final int high) {
        this.low = low;
        this.high = high;
    }

    /**
     * check if the range contains an integer.
     *
     * @param number
     * @return boolean
     */
    public boolean contains(final int number) {
        return (number >= low && number <= high);
    }
}

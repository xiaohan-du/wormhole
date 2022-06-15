package com.cm6123.wormhole.utils;

public class WormholeUtils {
    /**
     * get a deep copy of matrix.
     *
     * @param matrix
     * @return deep copy of input matrix
     */
    public int[][] deepCopy(final int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }
}

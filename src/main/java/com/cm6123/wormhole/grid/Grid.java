package com.cm6123.wormhole.grid;

import java.util.List;

public interface Grid {
    /**
     * width getter.
     *
     * @return int width
     */
    int getWidth();

    /**
     * positive entrances getter.
     *
     * @return List<Integer> positiveEntrances
     */
    List<Integer> getPositiveEntrances();

    /**
     * positive entrances setter.
     *
     * @param positiveEntrances
     */
    void setPositiveEntrances(List<Integer> positiveEntrances);

    /**
     * negative entrances getter.
     *
     * @return List<Integer> negativeEntrances
     */
    List<Integer> getNegativeEntrances();

    /**
     * negative entrances setter.
     *
     * @param negativeEntrances
     */
    void setNegativeEntrances(List<Integer> negativeEntrances);

    /**
     * exits getter.
     *
     * @return List<Integer> exits
     */
    List<Integer> getExits();

    /**
     * exits setter.
     *
     * @param exits
     */
    void setExits(List<Integer> exits);
}

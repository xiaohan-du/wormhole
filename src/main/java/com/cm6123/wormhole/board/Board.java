package com.cm6123.wormhole.board;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.cm6123.wormhole.grid.Grid;
import com.cm6123.wormhole.utils.Range;
import com.cm6123.wormhole.utils.WormholeUtils;

public class Board implements Grid {
    /**
     * width of the board.
     */
    private int width;
    /**
     * an ArrayList of positive entrances.
     */
    private List<Integer> positiveEntrances = new ArrayList<Integer>();
    /**
     * an ArrayList of negative entrances.
     */
    private List<Integer> negativeEntrances = new ArrayList<Integer>();
    /**
     * an ArrayList of exits.
     */
    private List<Integer> exits = new ArrayList<Integer>();
    /**
     * a 2d array of wormholes, include positive and negative entrances, and exits.
     */
    private int[][] wormholes;
    /**
     * utility functions for the wormhole project.
     */
    private WormholeUtils wUtils = new WormholeUtils();

    /**
     * Board constructor.
     *
     * @param width int: board width
     */
    public Board(final int width) {
        this.width = width;
        this.wormholes = new int[2][width * 2];
    }

    /**
     * width getter.
     *
     * @return int this.width
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * generate number of squares.
     *
     * @return int this.width
     */
    public int generateNumberOfSquares() {
        int aNumberOfSquares = (int) Math.pow(this.width, 2);
        return aNumberOfSquares;
    }

    /**
     * positive entrances getter.
     *
     * @return List<Integer> this.positiveEntrances
     */
    @Override
    public List<Integer> getPositiveEntrances() {
        return this.positiveEntrances;
    }

    /**
     * positive entrances setter.
     *
     * @param positiveEntrances
     */
    @Override
    public void setPositiveEntrances(final List<Integer> positiveEntrances) {
        this.positiveEntrances = positiveEntrances;
    }

    /**
     * negative entrances setter.
     *
     * @param negativeEntrances
     */
    @Override
    public void setNegativeEntrances(final List<Integer> negativeEntrances) {
        this.negativeEntrances = negativeEntrances;
    }

    /**
     * negative entrances getter.
     *
     * @return List<Integer> this.negativeEntrances
     */
    @Override
    public List<Integer> getNegativeEntrances() {
        return this.negativeEntrances;
    }

    /**
     * exits getter.
     *
     * @return List<Integer> this.exits
     */
    @Override
    public List<Integer> getExits() {
        return this.exits;
    }

    /**
     * exits setter.
     *
     * @param exits
     */
    @Override
    public void setExits(final List<Integer> exits) {
        this.exits = exits;
    }

    /**
     * generate n^2 number of total wormholes.
     *
     * @return aSquareList An int[] which contains 0 to n^2 - 1 square list
     */
    private int[] generateSquareList() {
        int[] aSquareList = IntStream.range(0, (int) Math.pow(this.width, 2)).toArray();
        return aSquareList;
    }

    /**
     * generate exits.
     * update wormholes, key 2 = exits
     *
     * @param validSquares An ArrayList contains valid squares from 1 to n^2 - 2
     */
    private void generateExits(final List<Integer> validSquares) {
        Random ran = new Random();
        for (int i = 0; i < width; i++) {
            int loc = ran.nextInt(validSquares.size());
            this.wormholes[0][i] = 2;
            this.wormholes[1][i] = validSquares.get(loc);
            validSquares.remove(validSquares.get(loc));
        }
    }

    /**
     * generate entrances.
     * update wormholes, key 1 = positive entrances, key 0 = negative entrances
     *
     * @param validSquares An ArrayList contains valid squares from 1 to n^2 - 2
     */
    private void generateEntrances(final List<Integer> validSquares) {
        Random ran = new Random();
        for (int i = 0; i < width; i++) {
            int loc = ran.nextInt(validSquares.size());
            this.wormholes[0][i + width] = (int) Math.round(Math.random());
            this.wormholes[1][i + width] = validSquares.get(loc);
            validSquares.remove(validSquares.get(loc));
        }
    }

    /**
     * generate entrances and exits.
     *
     * @param arrayInput A String either "entrances" or "exits"
     * @param zeroOrOne  An int which can either be 0 or 1
     * @return indexArray An ArrayList which contains indices of 0 or 1 appearance
     */
    private List<Integer> generateZeroOrOneIndices(final int[] arrayInput, final int zeroOrOne) {
        int index;
        Integer[] labels = Arrays.stream(arrayInput).boxed().toArray(Integer[]::new);
        List<Integer> indexArray = new ArrayList<Integer>();

        for (int i = width; i < arrayInput.length; i++) {
            if (Arrays.asList(labels).indexOf(zeroOrOne) != -1) {
                index = Arrays.asList(labels).indexOf(zeroOrOne);
                indexArray.add(index);
                labels[index] = null;
            }
        }
        return indexArray;
    }

    /**
     * update this.positiveEntrances and this.negativeEntrances.
     */
    private void updateAndSortEntrances() {
        int[][] wormholesCopy = wUtils.deepCopy(wormholes);
        List<Integer> oneIndexArray = generateZeroOrOneIndices(wormholesCopy[0], 1);
        List<Integer> zeroIndexArray = generateZeroOrOneIndices(wormholesCopy[0], 0);
        for (int i = 0; i < oneIndexArray.size(); i++) {
            this.positiveEntrances.add(wormholesCopy[1][oneIndexArray.get(i)]);
        }
        for (int i = 0; i < zeroIndexArray.size(); i++) {
            this.negativeEntrances.add(wormholesCopy[1][zeroIndexArray.get(i)]);
        }
        Collections.sort(this.positiveEntrances);
        Collections.sort(this.negativeEntrances);
    }

    /**
     * update this.exits to separate positive and negative entrances.
     */
    private void updateAndSortExits() {
        int[][] wormholesCopy = wUtils.deepCopy(wormholes);
        for (int i = 0; i < wormholesCopy[0].length / 2; i++) {
            this.exits.add(wormholesCopy[1][i]);
        }
        Collections.sort(this.exits);
    }

    /**
     * run the board to generate squares, entrances and exits.
     */
    public void run() {
        Range r = new Range(3, 10);
        if (r.contains(this.width)) {
            int[] squares = this.generateSquareList();
            Random ran = new Random();
            List<Integer> validSquares = Arrays.stream(squares).boxed().collect(Collectors.toList());
            // remove first and last square when generating entrances and exits.
            validSquares.remove(0);
            validSquares.remove(validSquares.size() - 1);
            generateExits(validSquares);
            generateEntrances(validSquares);
            updateAndSortEntrances();
            updateAndSortExits();
        } else {
            throw new java.lang.IllegalArgumentException("Invalid board width");
        }
    }
}

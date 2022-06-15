package com.cm6123.wormhole;

import com.cm6123.wormhole.board.Board;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BoardChecks {

    @Test
    public void shouldSumEntranceLengthEqualToBoardWidth() {
        Board aBoard = new Board(3);
        aBoard.run();
        List<Integer> aPositiveEntrances = aBoard.getPositiveEntrances();
        List<Integer> aNegativeEntrances = aBoard.getNegativeEntrances();
        int aWidth = aBoard.getWidth();
        assertEquals(aPositiveEntrances.size() + aNegativeEntrances.size(), aWidth);
    }

    @Test
    public void shouldFailWhenBoardSizeSmallerThan3() {
        Board aBoard = new Board(2);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> aBoard.run());
        assertEquals("Invalid board width", exception.getMessage());
    }

    @Test
    public void shouldFailWhenBoardSizeExceeds10() {
        Board aBoard = new Board(11);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> aBoard.run());
        assertEquals("Invalid board width", exception.getMessage());
    }

    @Test
    public void shouldGenerateCorrectNumberOfSquares() {
        int width = 5;
        Board aBoard = new Board(width);
        assertEquals(aBoard.generateNumberOfSquares(), Math.pow(width, 2));
    }
}

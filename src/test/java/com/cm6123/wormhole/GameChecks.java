package com.cm6123.wormhole;

import com.cm6123.wormhole.app.Application;
import com.cm6123.wormhole.board.Board;
import com.cm6123.wormhole.dice.Dice;
import com.cm6123.wormhole.game.Game;
import com.cm6123.wormhole.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class GameChecks {
    Dice d1 = new Dice(6);
    Dice d2 = new Dice(6);
    int widthThree = 3;
    int widthFour = 4;
    int widthFive = 5;

    //  scenario 1
    @Test
    public void shouldPlayer1LandOn8When3And4AreRolled() {
        Board aBoard = new Board(widthFour);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        int roll1 = 3;
        int roll2 = 4;
        player1.setRoll1(roll1);
        player1.setRoll2(roll2);
        String nextPlayer = new String();
        aGame.play(widthFour, players.get(0), aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
        nextPlayer = aGame.getNextPlayer(players, 0);
        assertEquals(player1.getLoc(), 8);
        assertEquals(nextPlayer, "test player 2");
    }

    // scenario 2
    @Test
    public void shouldPlayer1WinWhenLandOnSquare9() {
        Board aBoard = new Board(widthThree);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        boolean isWinnerOut = false;
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        int roll1 = 5;
        int roll2 = 6;
        player1.setRoll1(roll1);
        player1.setRoll2(roll2);
        String winner = new String();
        for (int i = 0; i < 2; i++) {
            Player p = players.get(i);
            aGame.play(widthThree, p, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
            if (p.getLoc() >= Math.pow(widthThree, 2)) {
                isWinnerOut = true;
                System.out.println(p.getName() + " moves to " + p.getLoc());
                System.out.println("Winner is " + p.getName());
                p.setIsWinner(true);
                winner = p.getName();
                break;
            } else {
                System.out.println(p.getName() + " moves to " + p.getLoc());
            }
        }
        int p1Loc = player1.getLoc();
        assertEquals(p1Loc, 12);
        assertTrue(isWinnerOut);
        assertEquals(winner, "test player 1");
    }

    // scenario 3
    @Test
    public void shouldBePlayer1sTurnWhenAllPlayersHaveRolled() {
        Board aBoard = new Board(widthFive);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        String nextPlayer = new String();
        for (int i = 0; i < 2; i++) {
            Player p = players.get(i);
            aGame.play(widthFive, p, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
            nextPlayer = aGame.getNextPlayer(players, i);
        }
        assertEquals(nextPlayer, "test player 1");
    }

    // scenario 4 and 5
    @ParameterizedTest
    @CsvSource({"4, 2", "3, 3"})
    public void shouldPlayer1LandOn20WhenRolls4And2Or3And3(int roll1, int roll2) {
        Board aBoard = new Board(widthFive);
        List<Integer> positiveEntrances = new ArrayList<>();
        positiveEntrances.add(6 + 1);
        List<Integer> exits = new ArrayList<>();
        exits.add(20);
        aBoard.setPositiveEntrances(positiveEntrances);
        aBoard.setExits(exits);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        boolean isWinnerOut = false;
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        String nextPlayer = new String();
        player1.setRoll1(roll1);
        player1.setRoll2(roll2);
        aGame.play(widthFive, player1, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
        nextPlayer = aGame.getNextPlayer(players, 0);
        assertFalse(isWinnerOut);
        int p1Loc = player1.getLoc();
        assertEquals(p1Loc, 20);
        assertEquals(nextPlayer, "test player 2");
    }

    // scenario 6
    @Test
    public void shouldPlayer1LandOn2WhenRolls6And4() {
        Board aBoard = new Board(widthFive);
        List<Integer> negativeEntrances = new ArrayList<>();
        negativeEntrances.add(10 + 1);
        List<Integer> exits = new ArrayList<>();
        exits.add(2);
        aBoard.setNegativeEntrances(negativeEntrances);
        aBoard.setExits(exits);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        String nextPlayer = new String();
        int roll1 = 6;
        int roll2 = 4;
        player1.setRoll1(roll1);
        player1.setRoll2(roll2);
        aGame.play(widthFive, player1, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
        nextPlayer = aGame.getNextPlayer(players, 0);
        int p1Loc = player1.getLoc();
        assertEquals(p1Loc, 2);
        assertEquals(nextPlayer, "test player 2");
    }

    // scenario 7
    @Test
    public void shouldPlayer1LandOn10WhenRolls5And5() {
        Board aBoard = new Board(widthFive);
        List<Integer> negativeEntrances = new ArrayList<>();
        negativeEntrances.add(10 + 1);
        List<Integer> exits = new ArrayList<>();
        exits.add(2);
        aBoard.setNegativeEntrances(negativeEntrances);
        aBoard.setExits(exits);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        String nextPlayer = new String();
        int roll1 = 5;
        int roll2 = 5;
        player1.setRoll1(roll1);
        player1.setRoll2(roll2);
        aGame.play(widthFive, player1, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
        nextPlayer = aGame.getNextPlayer(players, 0);
        int p1Loc = player1.getLoc();
        assertEquals(p1Loc, 11);
        assertEquals(nextPlayer, "test player 2");
    }

    // scenario 8 and 9
    @ParameterizedTest
    @CsvSource({"2, 3", "3, 3"})
    public void shouldPlayer1LandOn25WhenRolls4And2AndPlayer2Rolls1And1AndPlayer1Rolls2And3Or3And3(int roll1, int roll2) {
        Board aBoard = new Board(widthFive);
        List<Integer> positiveEntrances = new ArrayList<>();
        positiveEntrances.add(6 + 1);
        List<Integer> exits = new ArrayList<>();
        exits.add(20);
        aBoard.setPositiveEntrances(positiveEntrances);
        aBoard.setExits(exits);
        Player player1 = new Player("test player 1");
        Player player2 = new Player("test player 2");
        Game aGame = new Game();
        aGame.setIsTest(true);
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        players.put(0, player1);
        players.put(1, player2);
        String nextPlayer = new String();
        boolean isWinnerOut = false;
        String winner = new String();
        // round 1
        player1.setRoll1(4);
        player1.setRoll2(2);
        player2.setRoll1(1);
        player2.setRoll2(1);
        for (int i = 0; i < 2; i++) {
            Player p = players.get(i);
            aGame.play(widthFive, p, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
            nextPlayer = aGame.getNextPlayer(players, i);
            if (p.getLoc() >= Math.pow(widthFive, 2)) {
                p.setLoc((int) Math.pow(widthFive, 2));
                isWinnerOut = true;
                winner = p.getName();
                break;
            }
        }
        // round 2
        player1.setRoll1(roll1);
        player1.setRoll2(roll2);
        for (int i = 0; i < 2; i++) {
            Player p = players.get(i);
            aGame.play(widthFive, p, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
            nextPlayer = aGame.getNextPlayer(players, i);
            if (p.getLoc() >= Math.pow(widthFive, 2)) {
                p.setLoc((int) Math.pow(widthFive, 2));
                isWinnerOut = true;
                winner = p.getName();
                break;
            }
        }
        int p1Loc = player1.getLoc();
        assertEquals(p1Loc, 25);
        assertEquals(winner, "test player 1");
    }

    // exits ahead don't exist
    @Test
    public void shouldRemainOnSameSquareIfNoExitsAhead() {
        Board aBoard = new Board(widthFive);
        List<Integer> positiveEntrances = new ArrayList<>();
        positiveEntrances.add(6 + 1);
        List<Integer> exits = new ArrayList<>();
        exits.add(3);
        aBoard.setPositiveEntrances(positiveEntrances);
        aBoard.setExits(exits);
        Player player1 = new Player("test player 1");
        Game aGame = new Game();
        aGame.setIsTest(true);
        player1.setRoll1(4);
        player1.setRoll2(2);
        aGame.play(widthFive, player1, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
        int locAfter = player1.getLoc();
        assertEquals(locAfter, 7);
    }

    // exits behind don't exist
    @Test
    public void shouldRemainOnSameSquareIfNoExitsBehind() {
        Board aBoard = new Board(widthFive);
        List<Integer> negativeEntrances = new ArrayList<>();
        negativeEntrances.add(6 + 1);
        List<Integer> exits = new ArrayList<>();
        exits.add(8);
        aBoard.setNegativeEntrances(negativeEntrances);
        aBoard.setExits(exits);
        Player player1 = new Player("test player 1");
        Game aGame = new Game();
        aGame.setIsTest(true);
        player1.setRoll1(4);
        player1.setRoll2(2);
        aGame.play(widthFive, player1, aBoard.getExits(), aBoard.getPositiveEntrances(), aBoard.getNegativeEntrances());
        int locAfter = player1.getLoc();
        assertEquals(locAfter, 7);
    }

    // test isTest getter
    @Test
    public void shouldBeTestIfItIs() {
        Game aGame = new Game();
        aGame.setIsTest(true);
        assertTrue(aGame.getIsTest());
    }

}

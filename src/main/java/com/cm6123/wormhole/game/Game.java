package com.cm6123.wormhole.game;

import com.cm6123.wormhole.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Game {

    /**
     * is this for test?
     */
    private boolean isTest = false;

    /**
     * isTest setter.
     *
     * @param isTest
     */
    public void setIsTest(final boolean isTest) {
        this.isTest = isTest;
    }

    /**
     * isTest getter.
     *
     * @return this.isTest
     */
    public boolean getIsTest() {
        return this.isTest;
    }

    /**
     * get the next player.
     *
     * @param players      HashMap of players
     * @param currentIndex index of current player
     * @return String next player name
     */
    public String getNextPlayer(final HashMap<Integer, Player> players, final int currentIndex) {
        String nextPlayer = new String();
        int nextIndex;
        if (currentIndex == players.size() - 1) {
            nextIndex = 0;
        } else {
            nextIndex = currentIndex + 1;
        }
        nextPlayer = players.get(nextIndex).getName();
        return nextPlayer;
    }

    private List<Integer> calculateExitsAheadOrBehind(final String condition, final List<Integer> exits, final int loc) {
        List<Integer> output = new ArrayList<Integer>();
        for (int i = 0; i < exits.size(); i++) {
            int e = exits.get(i);
            if (condition == "ahead") {
                if (e > loc) {
                    output.add(e);
                }
            } else if (condition == "behind") {
                if (e < loc) {
                    output.add(e);
                }
            }
        }
        return output;
    }

    /**
     * play the game.
     *
     * @param width             int of board width
     * @param p                 Player of player
     * @param exits             List<Integer> of exits
     * @param positiveEntrances List<Integer> of positive entrances
     * @param negativeEntrances List<Integer> of negative entrances
     */
    public void play(
            final int width,
            final Player p,
            final List<Integer> exits,
            final List<Integer> positiveEntrances,
            final List<Integer> negativeEntrances
    ) {
        Random ran = new Random();
        String[] wording;
        int rolls = 0;
        rolls = p.getRoll1() + p.getRoll2();
        System.out.println(p.getName() + " rolls " + p.getRoll1() + " + " + p.getRoll2() + " = " + rolls);
        p.updateLoc(rolls);
        String playerName = p.getName();
        int loc = p.getLoc();
        if (positiveEntrances.contains(loc)) {
            List<Integer> newExits = calculateExitsAheadOrBehind("ahead", exits, loc);
            wording = new String[]{"positive", "ahead"};
            System.out.println(playerName + " lands on " + wording[0] + " entrance " + loc);
            System.out.println("exits " + wording[1] + " are: " + newExits);
            if (newExits.size() == 0) {
                p.setLoc(loc);
            } else {
                p.setLoc(newExits.get(ran.nextInt(newExits.size())));
            }
        } else if (negativeEntrances.contains(loc)) {
            List<Integer> newExits = calculateExitsAheadOrBehind("behind", exits, loc);
            wording = new String[]{"negative", "behind"};
            System.out.println(playerName + " lands on " + wording[0] + " entrance " + loc);
            System.out.println("exits " + wording[1] + " are: " + newExits);
            if (p.getRoll1() == p.getRoll2()) {
                p.setLoc(loc);
                System.out.println("roll 1 = roll 2, you are saved!");
            } else if (newExits.size() == 0) {
                p.setLoc(loc);
            } else {
                p.setLoc(newExits.get(ran.nextInt(newExits.size())));
            }
        } else {
            p.setLoc(loc);
        }
    }
}


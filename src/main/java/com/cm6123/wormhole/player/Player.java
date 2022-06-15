package com.cm6123.wormhole.player;

public class Player {
    /**
     * location of the player.
     */
    private int loc;

    /**
     * getter for player location.
     *
     * @return this.loc
     */
    public int getLoc() {
        return this.loc;
    }

    /**
     * set this.loc.
     *
     * @param loc
     */
    public void setLoc(final int loc) {
        this.loc = loc;
    }

    /**
     * player name.
     */
    private String name;

    /**
     * getter for player name.
     *
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * y/n to manual roll, y = manual roll, n = auto roll.
     */
    private String yesNo;

    /**
     * set yes no value.
     *
     * @param yesNo
     */
    public void setYesNo(final String yesNo) {
        this.yesNo = yesNo;
    }

    /**
     * yesNo setter.
     *
     * @return this.yesNo
     */
    public String getYesNo() {
        return this.yesNo;
    }

    /**
     * first dice roll.
     */
    private int roll1;

    /**
     * set roll 1.
     *
     * @param roll1
     */
    public void setRoll1(final int roll1) {
        this.roll1 = roll1;
    }

    /**
     * roll1 getter.
     *
     * @return this.rolls
     */
    public int getRoll1() {
        return this.roll1;
    }

    /**
     * second dice roll.
     */
    private int roll2;

    /**
     * set roll 2.
     *
     * @param roll2
     */
    public void setRoll2(final int roll2) {
        this.roll2 = roll2;
    }

    /**
     * roll2 getter.
     *
     * @return this.rolls
     */
    public int getRoll2() {
        return this.roll2;
    }

    /**
     * am I winner?
     */
    private boolean isWinner = false;

    /**
     * Player constructor.
     *
     * @param name
     */
    public Player(final String name) {
        this.name = name;
        this.loc = 1;
    }

    /**
     * isWinner setter.
     *
     * @param isWinner
     */
    public void setIsWinner(final boolean isWinner) {
        this.isWinner = isWinner;
    }

    /**
     * update player location with a input.
     *
     * @param rolls
     * @return this.loc
     */
    public int updateLoc(final int rolls) {
        this.loc = this.loc + rolls;
        return this.loc;
    }
}

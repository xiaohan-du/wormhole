package com.cm6123.wormhole.app;

import com.cm6123.wormhole.dice.Dice;
import com.cm6123.wormhole.game.Game;
import com.cm6123.wormhole.player.Player;
import com.cm6123.wormhole.utils.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.cm6123.wormhole.board.Board;

public final class Application {

    /**
     * Create a logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private Application() {
    }

    /**
     * welcome players.
     */
    private void welcome() {
        System.out.println("Welcome to wormhole");
        System.out.println("Please enter the width dimention of your board: ");
    }

    /**
     * print thank you messages.
     *
     * @param numberOfSquares   int number of total squares
     * @param positiveEntrances List<Integer> positive entrances
     * @param negativeEntrances List<Integer> negative entrances
     * @param exits             List<Integer> exits
     */
    private void thankYou(
            final int numberOfSquares,
            final List<Integer> positiveEntrances,
            final List<Integer> negativeEntrances,
            final List<Integer> exits) {
        System.out.println("Thank you");
        System.out.println("The board has " + numberOfSquares + " squares. ");
        System.out.println("Exits at " + exits);
        System.out.println("Positive entrances at " + positiveEntrances);
        System.out.println("Negative entrances at " + negativeEntrances);
        System.out.println("Please enter number of players: ");
    }

    /**
     * main entry point.  If args provided, result is displayed and program exists. Otherwise, user is prompted for
     * input.
     *
     * @param args command line args.
     */
    public static void main(final String[] args) {
        logger.info("Application Started with args:{}", String.join(",", args));

        Range widthRange = new Range(3, 10);
        Range noOfPlayersRange = new Range(2, 6);
        Application app = new Application();
        Scanner sc = new Scanner(System.in);
        boolean isWinnerOut = false;
        boolean isPlayAgain = true;
        String yesNo = "y";
        while (isPlayAgain) {
            app.welcome();
            int inp;
            do {
                inp = sc.nextInt();
                sc.skip("\\R?");
                if (!(widthRange.contains(inp))) {
                    System.out.println("Please input an integer between 5 and 10 including 5 and 10");
                }
            } while (!(widthRange.contains(inp)));
            Board board = new Board(inp);
            board.run();
            int numberOfSquares = board.generateNumberOfSquares();
            List<Integer> sortedPositiveEntrances = board.getPositiveEntrances();
            List<Integer> sortedNegativeEntrances = board.getNegativeEntrances();
            List<Integer> sortedExits = board.getExits();
            Dice d1 = new Dice(6);
            Dice d2 = new Dice(6);
            app.thankYou(numberOfSquares, sortedPositiveEntrances, sortedNegativeEntrances, sortedExits);
            int noOfPlayers;
            do {
                noOfPlayers = sc.nextInt();
                sc.skip("\\R?");
                if (!(noOfPlayersRange.contains(noOfPlayers))) {
                    System.out.println("This game can only be played by 2 to 6 players, please try again");
                }
            } while (!(noOfPlayersRange.contains(noOfPlayers)));
            HashMap<Integer, Player> players = new HashMap<Integer, Player>();
            for (int i = 0; i < noOfPlayers; i++) {
                System.out.println("Enter the name of player " + (i + 1));
                String name;
                do {
                    name = sc.nextLine();
                    if (name.isEmpty()) {
                        System.out.println("Please enter valid input");
                    } else {
                        players.put(i, new Player(name));
                    }
                } while (name.isEmpty());
            }
            for (int i = 0; i < noOfPlayers; i++) {
                Player p = players.get(i);
                System.out.println(p.getName()
                        + " – do you want to roll the dice, or shall I do it for you? "
                        + "Type 'y' to roll yourself or 'n' to let me do it.");
                do {
                    yesNo = sc.nextLine();
                    p.setYesNo(yesNo);
                    if (!(yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("n"))) {
                        System.out.println("Please enter valid input 'y' or 'n', player " + p.getName());
                    }
                } while (!(yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("n")));
            }
            System.out.println("Let's play.");
            for (int i = 0; i < noOfPlayers; i++) {
                Player p = players.get(i);
                System.out.println(p.getName() + " is on square " + 1);
            }
            Game game = new Game();
            while (!isWinnerOut) {
                for (int i = 0; i < noOfPlayers; i++) {
                    Player p = players.get(i);
                    if (!game.getIsTest()) {
                        if (p.getYesNo().equals("y")) {
                            System.out.println(p.getName() + " - please enter your first dice roll.");
                            p.setRoll1(sc.nextInt());
                            System.out.println(p.getName() + " - please enter your second dice roll.");
                            p.setRoll2(sc.nextInt());
                            sc.skip("\\R?");
                        } else if (p.getYesNo().equals("n")) {
                            p.setRoll1(d1.roll());
                            p.setRoll2(d2.roll());
                        }
                    }
                    game.play(inp, p, sortedExits, sortedPositiveEntrances, sortedNegativeEntrances);
                    if (p.getLoc() >= Math.pow(inp, 2)) {
                        p.setLoc((int)Math.pow(inp, 2));
                        isWinnerOut = true;
                        System.out.println(p.getName() + " moves to " + p.getLoc());
                        System.out.println("Winner is " + p.getName());
                        p.setIsWinner(true);
                        break;
                    } else {
                        System.out.println(p.getName() + " moves to " + p.getLoc());
                    }
                }
            }
            System.out.println("Do you want to play again? y/n");
            do {
                yesNo = sc.nextLine();
                if (yesNo.equals("y")) {
                    isPlayAgain = true;
                    isWinnerOut = false;
                } else if (yesNo.equals("n")) {
                    isPlayAgain = false;
                }
                if (!(yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("n"))) {
                    System.out.println("Please enter valid input 'y' or 'n'");
                }
            } while (!(yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("n")));
        }
        sc.close();
        logger.info("Application closing");
    }
}

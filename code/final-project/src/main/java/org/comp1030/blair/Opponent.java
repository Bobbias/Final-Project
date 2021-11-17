package org.comp1030.blair;

import java.util.Random;

/** Opponent contains the AI which determines how the AI opponent makes guesses.
 * 
 *  <p>The AI supports 3 levels of difficulty.
 *  Easy: The AI randomly guesses with no memory or strategy.
 *  Normal: The AI guesses at random until it hits something.
 *          When it hits something, it will guess one of the surrounding locations.
 *  Hard: The AI will keep a copy of it's guesses and subsequent results. It will
 *        use this information to decide where to guess next.</p>
 */
public class Opponent {

    /** 
     * the DifficultyLevel enum sets the difficulty level to be played.
     */
    public enum DifficultyLevel {
        EASY, NORMAL, HARD
    }

    private DifficultyLevel diff;
    private Random rand;
    private GameBoard board;
    private int lastX;
    private int lastY;
    private boolean lastGuess;
    private boolean[][] opponentShips;

    /** 
     * Opponent represents an AI controlled opponent.
     *
     * @param diff the difficulty level the AI will use.
     * @param board a reference to the game board.
     */
    public Opponent(DifficultyLevel diff, GameBoard board) {
        this.diff = diff;
        this.rand = new Random();
        this.board = board;
        this.lastX = 0;
        this.lastY = 0;
        this.lastGuess = false;
        this.opponentShips = new boolean[8][8];
    }

    /**
     * haveHitSomething checks if the AI has hit any of the player's
     * ships during the course of the game.
     *
     * @return true if the AI has hit anything, false otherwise.
     */
    private boolean haveHitSomething() {
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (opponentShips[x][y] == true) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * makeGuess is the heart of the AI. Based on which difficulty level, there are 3 possible
     * code paths.<br>
     * <ul><li>If the Easy difficulty is selected, the AI will guess randomly.</li>
     * <li>If the Normal difficulty is selected, the AI will guess randomly, but if a random
     * guess hits a player's ship, the next guess will be within 1 tile of the previous guess.
     * If the second guess misses, it will return to guessing randomly.</li>
     * <li>If the Hard difficulty is selected, the AI first checks if it has hit any of the
     * player's ships yet. If not, it guesses randomly. If it has hit one of the player's
     * ships, it first checks if the previous guess was a hit. If it was, it uses the same
     * logic as above. If not, it collects all of it's previous successful guesses into a
     * linked list, and then selects one at random to apply the targeted guess logic to.</li></ul>
     */
    public void makeGuess() {
        int x;
        int y;
        switch (diff) { // Just guess randomly.
            case EASY:
                x = rand.nextInt(8);
                y = rand.nextInt(8);
                board.guess(x, y, Game.Player.AI);
                break;
            case NORMAL: // If last guess hit something, guess near it.
                if (lastGuess) {
                    x = lastX + (rand.nextInt(2) - 1);
                    y = lastY + (rand.nextInt(2) - 1);
                } else { // Otherwise make a random guess.
                    x = rand.nextInt(8);
                    y = rand.nextInt(8);
                }
                lastGuess = board.guess(x, y, Game.Player.AI);
                break;
            case HARD:
                // if nothing has been hit, make a random guess
                if (!haveHitSomething()) {
                    x = rand.nextInt(8);
                    y = rand.nextInt(8);
                    lastGuess = board.guess(x, y, Game.Player.AI);
                    // if something has been hit...
                } else { // if our last guess hit, pick another random guess near it
                    if (lastGuess) {
                        x = Math.max(lastX + (rand.nextInt(2) - 1), 7);
                        y = Math.max(lastY + (rand.nextInt(2) - 1), 7);
                    } else { // if our last guess did not hit, pick a random hit location to use
                        // temporary linked list because i dont know what size array i might need
                        MyList hits = new MyList();
                        // collect all the hit locations
                        for (int u = 0; u < 8; ++u) {
                            for (int v = 0; v < 8; ++v) {
                                if (opponentShips[u][v]) {
                                    hits.append(u, v);
                                }
                            }
                        } // and pick one at random to guess near
                        int randomHit = rand.nextInt(hits.size());
                        Node hit = hits.nodeAt(randomHit);
                        x = Math.min(hit.getX() + (rand.nextInt(3) - 1), 7); // todo: make sure this actually works
                        y = Math.min(hit.getY() + (rand.nextInt(3) - 1), 7);
                        lastGuess = board.guess(x, y, Game.Player.AI);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 
     */
    public void placeShip() {
        //
    }
}

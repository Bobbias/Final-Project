package org.comp1030.blair;

/** Opponent contains the AI which determines how the AI opponent makes guesses.
 * 
 *  The AI supports 3 levels of difficulty.
 *  Easy: The AI randomly guesses with no memory or strategy.
 *  Normal: The AI guesses at random until it hits something.
 *          When it hits something, it will guess one of the surrounding locations.
 *  Hard: The AI will keep a copy of it's guesses and subsequent results. It will
 *        use this information to decide where to guess next.
 */

import java.util.*;

 public class Opponent {
    public enum DifficultyLevel {EASY, NORMAL, HARD}
    private DifficultyLevel diff;
    private Random rand;
    private GameBoard board;
    private int lastX;
    private int lastY;
    private boolean lastGuess;
    private boolean opponentShips[][];


    public Opponent(DifficultyLevel diff, GameBoard board)
    {
        this.diff = diff;
        this.rand = new Random();
        this.board = board;
        this.lastX = 0;
        this.lastY = 0;
        this.lastGuess = false;
        this.opponentShips = new boolean[8][8];
    }
    private boolean haveHitSomething()
    {
        for (int y = 0; y < 8; ++y)
        {
            for (int x = 0; x < 8; ++x)
            {
                if (opponentShips[x][y] == true)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void makeGuess()
    {
        int x;
        int y;
        switch (diff)
        {
            case EASY:
                x = rand.nextInt(8);
                y = rand.nextInt(8);
                board.guess(x, y, Game.Player.AI);
                break;
            case NORMAL:
                if (lastGuess)
                {
                    x = lastX + (rand.nextInt(2) - 1);
                    y = lastY + (rand.nextInt(2) - 1);
                }
                else
                {
                    x = rand.nextInt(8);
                    y = rand.nextInt(8);
                }
                lastGuess = board.guess(x, y, Game.Player.AI);
                break;
            case HARD:
                // if nothing has been hit, make a random guess
                if (!haveHitSomething())
                {
                    x = rand.nextInt(8);
                    y = rand.nextInt(8);
                    lastGuess = board.guess(x, y, Game.Player.AI);
                }
                // if something has been hit...
                else
                {
                    // if our last guess hit, pick another random guess near it
                    if (lastGuess)
                    {
                        x = Math.max(lastX + (rand.nextInt(2) - 1), 7);
                        y = Math.max(lastY + (rand.nextInt(2) - 1), 7);
                    }
                    // if our last guess did not hit, pick a random hit location to use
                    else
                    {
                        // temporary linked list because i dont know what size array i might need
                        MyList hits = new MyList();
                        // collect all the hit locations, and pick one at random to guess near
                        for (int u = 0; u < 8; ++u)
                        {
                            for (int v = 0; v < 8; ++v)
                            {
                                if (opponentShips[u][v])
                                {
                                    hits.append(u, v);
                                }
                            }
                        }
                        int randomHit = rand.nextInt(hits.size());
                        Node hit = hits.nodeAt(randomHit);
                        x = Math.max(hit.getX() + (rand.nextInt(2) - 1), 7);
                        y = Math.max(hit.getY() + (rand.nextInt(2) - 1), 7);
                        lastGuess = board.guess(x, y, Game.Player.AI);
                    }
                }
                    
                break;
        }
    }
    public void placeShip() {

    }
}

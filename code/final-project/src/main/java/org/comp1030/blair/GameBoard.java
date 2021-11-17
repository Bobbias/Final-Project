package org.comp1030.blair;

/**
 * GameBoard represents the 8 by 8 board on which our battleship game takes
 * place.
 * 
 */
public class GameBoard {
    /**
     * This enum represents the possible states that a tile may take. I take
     * advantage of enums being classes to make things far easier I am aware that
     * this is advanced java beyond what we are taught in class.<br>
     * Since every enum value is itself a class, and can contain a class body, AND
     * they implicitly inherit from the base enum, I can define an abstract method
     * in the enum which each enum value class can override.
     */
    public enum GridState {
        EMPTY {
            @Override
            public String getTile() {
                return " ";
            }
        },
        SHIP {
            @Override
            public String getTile() {
                return "S";
            }
        },
        HIT {
            @Override
            public String getTile() {
                return "H";
            }
        },
        MISS {
            @Override
            public String getTile() {
                return "M";
            }
        };

        /**
         * An abstract method which allows each GridState value to provide their own
         * implementation. This allows each value to provide it's own string
         * representation rather than rely on external helper functions.
         *
         * @return the string representation of the tile.
         */
        abstract String getTile();
    }

    private GridState[][] playerShips;
    private GridState[][] opponentShips;
    private int playerShipsLeft = 0;
    private int opponentShipsLeft = 0;

    /**
     * Constructor for the GameBoard class. Generates an 8 by 8 multidimensional
     * array of GridState objects and initializes them to GridState.EMPTY.
     * Represents the individual boards on which each player places their ships.
     */
    public GameBoard() {
        playerShips = new GridState[8][8];
        opponentShips = new GridState[8][8];

        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                playerShips[x][y] = GridState.EMPTY;
                opponentShips[x][y] = GridState.EMPTY;
            }
        }
    }

    /**
     * isLegalPlacement checks the game board to ensure that a ship placement will
     * fit on the board, and does not overlap any other ships.
     *
     * @param x
     *                      the x coordinate of the ship to be placed.
     * @param y
     *                      the y coordinate of the ship to be placed.
     * @param size
     *                      the size of the ship to be placed.
     * @param direction
     *                      the direction the ship is facing.
     * @return true if the provided placement information is legal, false if not.
     */
    public boolean isLegalPlacement(int x, int y, int size, Ship.Orientation direction) {
        boolean result = true;
        try {
            // System.out.printf("size: %d\n", size);
            switch (direction) {
                case HORIZONTAL:
                    for (int a = x; a < (x + size); ++a) {
                        if (playerShips[a][y] != GridState.EMPTY) {
                            result = false;
                        }
                    }
                    break;
                case VERTICAL:
                    for (int b = y; b < (y + size); ++b) {
                        if (playerShips[x][b] != GridState.EMPTY) {
                            result = false;
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            result = false;
        }
        return result;
    }

    /**
     * Checks whether the ship's location and orientation are valid. If they are,
     * the board is updated, and the ship counters are updated.<br>
     * Internally it uses isLegalPlacement for most input validation.<br>
     * Throws IllegalArgumentException if the coordinates are invalid.
     *
     * @param ship
     *                   the ship to be placed.
     * @param player
     *                   the player who owns the ship.
     */
    public void placeShip(Ship ship, Game.Player player) {
        int x = ship.getX();
        int y = ship.getY();
        Ship.Orientation facing = ship.getDirection();
        if (isLegalPlacement(x, y, ship.getSize(), facing)) {
            if (facing == Ship.Orientation.HORIZONTAL) {
                if (x + ship.getSize() >= 8) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("X coordinate is too large. ");
                    msg.append("Please pick a different placement location.");
                    throw new IllegalArgumentException(msg.toString());
                }
                for (int i = x; i < x + ship.getSize(); ++i) {
                    if (player == Game.Player.PLAYER) {
                        playerShipsLeft++;
                        playerShips[i][y] = GridState.SHIP;
                    } else if (player == Game.Player.AI) {
                        opponentShipsLeft++;
                        opponentShips[i][y] = GridState.SHIP;
                    }
                }
            } else if (facing == Ship.Orientation.VERTICAL) {
                if (y + ship.getSize() >= 8) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Y coordinate is too large. ");
                    msg.append("pick a different placement location.");
                    throw new IllegalArgumentException(msg.toString());
                }
                for (int i = y; i < y + ship.getSize(); ++i) {
                    if (player == Game.Player.PLAYER) {
                        playerShipsLeft++;
                        playerShips[x][i] = GridState.SHIP;
                    } else if (player == Game.Player.AI) {
                        opponentShipsLeft++;
                        opponentShips[x][i] = GridState.SHIP;
                    }
                }
            }
        }
    }

    /**
     * Guess checks whether or not the grid tile at the given coordinates is
     * occupied by a ship or not.<br>
     * Throws ArrayIndexOutOfBoundsException if either coordinate is invalid.
     *
     * @param x
     *                   the x coordinate.
     * @param y
     *                   the y coordinate.
     * @param player
     *                   the player making the guess.
     * @return true if there is a shop occupying the given coordinates, false
     *         otherwise.
     */
    public boolean guess(int x, int y, Game.Player player) {
        if (x >= 8 || y >= 8) {
            throw new ArrayIndexOutOfBoundsException("Either x or y was too high, try again.");
        }

        if (player == Game.Player.PLAYER) {
            if (opponentShips[x][y] == GridState.SHIP) {
                playerShips[x][y] = GridState.HIT;
                opponentShipsLeft--;
                return true;
            } else {
                playerShips[x][y] = GridState.MISS;
                return false;
            }
        } else {
            if (playerShips[x][y] == GridState.SHIP) {
                playerShips[x][y] = GridState.HIT;
                playerShipsLeft--;
                return true;
            } else {
                playerShips[x][y] = GridState.MISS;
                return false;
            }
        }
    }

    /**
     * Checks whether the game is over. The game is over if either player has 0 grid
     * spaces containing a ship.
     *
     * @return true if either player has 0 ship grids left, false otherwise.
     */
    public boolean isGameOver() {
        if (playerShipsLeft == 0 || opponentShipsLeft == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines which player has won the game. Only returns a valid result when
     * called after the game is finished.
     *
     * @return the winning player.
     */
    public Game.Player getWinner() {
        if (playerShipsLeft == 0) {
            return Game.Player.AI;
        } else {
            return Game.Player.PLAYER;
        }
    }

    /**
     * Getter for the player's ship placement board.
     *
     * @return a multidimensional array of GridState objects representing the game
     *         board from the player's perspective.
     */
    public GridState[][] getPlayerBoard() {
        return playerShips;
    }

    /**
     * Getter for the opponent's ship placement board.
     *
     * @return a multidimensional array of GridState objects representing the game
     *         board from the opponent's perspective.
     */
    public GridState[][] getOpponentBoard() {
        return opponentShips;
    }

}

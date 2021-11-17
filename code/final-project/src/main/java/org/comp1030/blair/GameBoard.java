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

        abstract String getTile();
    }

    private GridState[][] playerShips;
    private GridState[][] opponentShips;
    private int playerShipsLeft = 0;
    private int opponentShipsLeft = 0;

    /**
     * 
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
     * 
     * @param ship
     * @param player
     */
    public void placeShip(Ship ship, Game.Player player) {
        int x = ship.getX();
        int y = ship.getY();
        Ship.Orientation facing = ship.getDirection();
        if (isLegalPlacement(x, y, ship.getSize(), facing)) {
            if (facing == Ship.Orientation.HORIZONTAL) {
                if (x + ship.getSize() >= 8) {
                    throw new IllegalArgumentException("X coordinate is too large. Please pick a different placement location.");
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
                    throw new IllegalArgumentException("Y coordinate is too large. Please pick a different placement location.");
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
     * 
     * @param x
     * @param y
     * @param player
     * @return
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
     * 
     * @return
     */
    public boolean isGameOver() {
        if (playerShipsLeft == 0 || opponentShipsLeft == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return
     */
    public Game.Player getWinner() {
        if (playerShipsLeft == 0) {
            return Game.Player.AI;
        } else {
            return Game.Player.PLAYER;
        }
    }

    /**
     * 
     * @return
     */
    public GridState[][] getPlayerBoard() {
        return playerShips;
    }

    /**
     * 
     * @return
     */
    public GridState[][] getOpponentBoard() {
        return opponentShips;
    }

}

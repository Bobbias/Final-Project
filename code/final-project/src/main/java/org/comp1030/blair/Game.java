package org.comp1030.blair;

import java.util.Scanner;
import org.comp1030.blair.GameBoard.GridState;

/**
 * Game contains the main game logic.
 *
 */
public class Game {
    // GameState represents the different phases of a game
    private enum GameState {
        SELECT_DIFFICULTY, GAME_OVER, 
        PLACE_PIECES_1, PLACE_PIECES_2, 
        OPPONENT_PLACE_PIECES, PLAYER_GUESS, 
        OPPONENT_GUESS, QUIT
    }

    /**
     * Player defines two values, PLAYER represents a human player while AI
     * represents an AI controlled player.
     */
    public enum Player {
        PLAYER, AI
    }

    // constants to define the screen location for drawing any text or
    // menu options below the playfield
    private final int menuX = 1;
    private final int menuY = 18;

    // private members
    private boolean quit;
    private String name;
    private GameState state;
    private Scanner in;
    private GameBoard board;
    private Opponent opponent;
    private int numBoatsPlaced;
    private Ship.Type currentType = null;
    private Player winner = null;

    /**
     * Game is the main class representing a single session of the battleship game.
     * 
     */
    public Game(String name, Scanner in) {
        this.quit = false;
        this.name = name;
        state = GameState.SELECT_DIFFICULTY;
        this.in = in;
        board = new GameBoard();
        opponent = new Opponent(Opponent.DifficultyLevel.EASY, board);
    }

    /**
     * play is the main game function.
     * 
     */
    public void play() {
        Main.cls();
        System.out.printf("Welcome %s", name);

        do {
            if (winner != null && board.isGameOver()) {
                quit = true;
                winner = board.getWinner();
            }
            renderGameState();
            handleInput();
        } while (!quit);
    }

    /**
     * Shows a m
     */
    private void showGameOverMessage() {
        Main.cls();
        System.out.printf("\033[5;6HYou %s!", (winner == Player.PLAYER ? "Win" : "Lose"));
        if (winner == Player.PLAYER) {
            System.out.printf("\033[6;6HCongratulations %s!", name);
        }
        in.next();
    }

    private void listShipTypes() {
        setCursorPosition(menuX, menuY);
        System.out.printf("Please enter the type of ship you want to place:\n");
        System.out.printf("\033[4GShip Name\033[25GSize\033[32GPlaced\n");
        System.out.printf("\033[4G1.\033[8GCarrier.\033[25G5");
        System.out.printf("\033[32G%s", (
                (numBoatsPlaced & Ship.Type.CARRIER.toValue()) 
                == Ship.Type.CARRIER.toValue() ? "*\n" : "\n"));
        System.out.printf("\033[4G2.\033[8GBattleship.\033[25G4");
        System.out.printf("\033[32G%s", (
                (numBoatsPlaced & Ship.Type.BATTLESHIP.toValue()) 
                 == Ship.Type.BATTLESHIP.toValue() ? "*\n" : "\n"));
        System.out.printf("\033[4G3.\033[8GDestroyer.\033[25G3");
        System.out.printf("\033[32G%s", (
                (numBoatsPlaced & Ship.Type.DESTROYER.toValue()) 
                == Ship.Type.DESTROYER.toValue() ? "*\n" : "\n"));
        System.out.printf("\033[4G4.\033[8GSubmarine.\033[25G3");
        System.out.printf("\033[32G%s", (
                (numBoatsPlaced & Ship.Type.SUBMARINE.toValue()) 
                == Ship.Type.SUBMARINE.toValue() ? "*\n" : "\n"));
        System.out.printf("\033[4G5.\033[8GPatrol Boat.\033[25G2");
        System.out.printf("\033[32G%s", (
                (numBoatsPlaced & Ship.Type.PATROLBOAT.toValue()) 
                == Ship.Type.PATROLBOAT.toValue() ? "*\n" : "\n"));
        System.out.printf("\033[4G6.\033[8GQuit.\n\n");
        //System.out.print("\033[10;1H: ");
    }

    private void listDifficultyOptions() {
        setCursorPosition(menuX, menuY);
        System.out.printf("Please enter the difficulty level you wish to play against:\n");
        System.out.printf("\033[4G1.\033[8GEasy\n");
        System.out.printf("\033[4G2.\033[8GNormal\n");
        System.out.printf("\033[4G3.\033[8GHard\n");
    }

    private void getShipParameters() {
        setCursorPosition(menuX, menuY);
        System.out.printf("Please enter the desired x and y coordinates as integers, ");
        System.out.printf("followed by the orientation (either v or h).\n");
    }

    /**
     * handleInput is responsible for reacting to player inputs.
     */
    public void handleInput() {
        String input = in.nextLine();
        Scanner helper = new Scanner(input);
        switch (state) {
            case SELECT_DIFFICULTY:
                switch (helper.nextInt()) {
                    case 1:
                        state = GameState.PLACE_PIECES_1;
                        break; // The default difficulty is Easy.
                    case 2:
                        opponent = new Opponent(Opponent.DifficultyLevel.NORMAL, board);
                        state = GameState.PLACE_PIECES_1;
                        break;
                    case 3:
                        opponent = new Opponent(Opponent.DifficultyLevel.HARD, board);
                        state = GameState.PLACE_PIECES_1;
                        break;
                    default:
                        state = GameState.PLACE_PIECES_1;
                        break; // The default is Easy.
                }
                break;
            case GAME_OVER:
                in.nextLine();
                state = GameState.QUIT;
                break;
            case PLACE_PIECES_1:
                switch (input) {
                    case "1":
                        if ((numBoatsPlaced & Ship.Type.CARRIER.toValue()) 
                                == Ship.Type.CARRIER.toValue()) {
                            break;
                        }
                        currentType = Ship.Type.CARRIER;
                        state = GameState.PLACE_PIECES_2;
                        break;
                    case "2":
                        if ((numBoatsPlaced & Ship.Type.BATTLESHIP.toValue()) 
                                == Ship.Type.BATTLESHIP.toValue()) {
                            break;
                        }
                        currentType = Ship.Type.BATTLESHIP;
                        state = GameState.PLACE_PIECES_2;
                        break;
                    case "3":
                        if ((numBoatsPlaced & Ship.Type.DESTROYER.toValue()) 
                                == Ship.Type.DESTROYER.toValue()) {
                            break;
                        }
                        currentType = Ship.Type.DESTROYER;
                        state = GameState.PLACE_PIECES_2;
                        break;
                    case "4":
                        if ((numBoatsPlaced & Ship.Type.SUBMARINE.toValue()) 
                                == Ship.Type.SUBMARINE.toValue()) {
                            break;
                        }
                        currentType = Ship.Type.SUBMARINE;
                        state = GameState.PLACE_PIECES_2;
                        break;
                    case "5":
                        if ((numBoatsPlaced & Ship.Type.PATROLBOAT.toValue()) 
                                == Ship.Type.PATROLBOAT.toValue()) {
                            break;
                        }
                        currentType = Ship.Type.PATROLBOAT;
                        state = GameState.PLACE_PIECES_2;
                        break;
                    case "6":
                        state = GameState.QUIT;
                        break;
                    default:
                        break;
                }
                break;
            case PLACE_PIECES_2:
                int shipX = helper.nextInt();
                int shipY = helper.nextInt();
                String rest = helper.nextLine().strip();
                Ship.Orientation direction = Ship.Orientation.fromChar(rest.charAt(0));
                Ship ship = new Ship(currentType, shipX, shipY, direction);
                helper.close();
                // if we have already placed this boat,
                // just go back to the previous state
                if ((numBoatsPlaced & currentType.toValue()) == 1) {
                    state = GameState.PLACE_PIECES_1;
                    break;
                }
                try {
                    board.placeShip(ship, Player.PLAYER);
                } catch (IllegalArgumentException e) {
                    System.out.printf(e.getMessage());
                    in.nextLine();
                    state = GameState.PLACE_PIECES_1;
                    break;
                }
                numBoatsPlaced |= ship.getType().toValue();
                // if all boats have now been placed
                if (numBoatsPlaced == 0b11111) {
                    state = GameState.PLAYER_GUESS;
                } else {
                    currentType = null;
                    state = GameState.PLACE_PIECES_1;
                }
                break;
            case OPPONENT_GUESS:
                opponent.makeGuess();
                state = GameState.PLAYER_GUESS;
                break;
            case OPPONENT_PLACE_PIECES:
                opponent.placeShips();
                break;
            case PLAYER_GUESS:
                int guessX = helper.nextInt();
                int guessY = helper.nextInt();
                try {
                    board.guess(guessX, guessY, Game.Player.PLAYER);
                } catch (ArrayIndexOutOfBoundsException e) {
                    // breaking early repeats the same game state.
                    // this lets the player guess again.
                    break;
                }
                state = GameState.OPPONENT_GUESS;
                break;
            case QUIT:
                quit = true;
                break;
            default:
                break;
        }
    }

    private void renderGameState() {
        Main.cls();
        switch (state) {
            case SELECT_DIFFICULTY:
                listDifficultyOptions();
                break;
            case GAME_OVER:
                showGameOverMessage();
                break;
            case PLACE_PIECES_1:
                Main.cls();
                renderGameBoard();
                renderGuesses();
                listShipTypes();
                break;
            case PLACE_PIECES_2:
                Main.cls();
                renderGameBoard();
                renderGuesses();
                getShipParameters();
                break;
            case OPPONENT_GUESS:
                break;
            case OPPONENT_PLACE_PIECES:
                break;
            case PLAYER_GUESS:
                Main.cls();
                renderGameBoard();
                renderGuesses();
                renderPlayerGuess();
                break;
            default:
                break;
        }
    }

    private void renderPlayerGuess() {
        setCursorPosition(menuX, menuY);
        System.out.printf("Player's turn. Please enter the x and y coordinates ");
        System.out.printf("you wish to guess (0 - 7)\n");
    }

    private void renderGameBoard() {
        for (int y = 0; y < 8; ++y) {
            // print coordinates to the left of the board
            setCursorPosition(1, 2 * (y + 1));
            System.out.printf("%d", y);
            for (int x = 0; x < 8; ++x) {
                // print coordinates above board
                setCursorPosition(2 * (x + 1), 1);
                System.out.printf("%d", x);
                // put a space between each grid square, horizontally and vertically
                // the +1 accounts for the ansi codes being 1 based.
                setCursorPosition(2 * (x + 1), 2 * (y + 1));
                System.out.printf("%s", board.getPlayerBoard()[x][y].getTile());
            }
        }
    }

    private void renderGuesses() {
        final int offset = 15;
        for (int y = 0; y < 8; ++y) {
            // print coordinates to the left of the guesses
            setCursorPosition(2 * offset - 1, 2 * (y + 1));
            System.out.printf("%d", y);
            for (int x = 0; x < 8; ++x) {
                setCursorPosition(2 * (x + offset), 1);
                System.out.printf("%d", x);
                // put a space between each grid square, horizontally and vertically
                // The +15 shifts the entire board over.
                setCursorPosition(2 * (x + offset), 2 * (y + 1));
                String tile = board.getOpponentBoard()[x][y].getTile();
                if (tile != GridState.SHIP.getTile()) {
                    System.out.printf("%s", tile);
                } else {
                    System.out.printf(" ");
                }
            }
        }
    }

    private void setCursorPosition(int x, int y) {
        System.out.printf("\033[%d;%dH", y, x);
    }
}
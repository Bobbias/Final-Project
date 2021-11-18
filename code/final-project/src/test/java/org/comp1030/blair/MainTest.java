package org.comp1030.blair;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.comp1030.blair.Game.Player;
import org.comp1030.blair.Ship.Orientation;
import org.comp1030.blair.Ship.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for simple App.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ MainTest.GameBoardTests.class, MainTest.ShipTests.class })
public class MainTest {
    public static class ShipTests {
        @Test
        public void ship_sizes() {
            assertTrue(new Ship(Ship.Type.CARRIER, 0, 0, Orientation.HORIZONTAL).getSize() == 5);
            assertTrue(new Ship(Ship.Type.BATTLESHIP, 0, 0, Orientation.HORIZONTAL).getSize() == 4);
            assertTrue(new Ship(Ship.Type.DESTROYER, 0, 0, Orientation.HORIZONTAL).getSize() == 3);
            assertTrue(new Ship(Ship.Type.SUBMARINE, 0, 0, Orientation.HORIZONTAL).getSize() == 3);
            assertTrue(new Ship(Ship.Type.PATROLBOAT, 0, 0, Orientation.HORIZONTAL).getSize() == 2);
        }

        @Test
        public void ship_getX() {
            Ship ship = new Ship(Type.BATTLESHIP, 5, 0, Orientation.VERTICAL);
            assertTrue(ship.getX() == 5);
        }

        @Test
        public void ship_getY() {
            Ship ship = new Ship(Type.BATTLESHIP, 0, 5, Orientation.VERTICAL);
            assertTrue(ship.getY() == 5);
        }

        @Test
        public void ship_getType() {
            Ship ship = new Ship(Type.BATTLESHIP, 0, 0, Orientation.VERTICAL);
            assertTrue(ship.getType() == Type.BATTLESHIP);
        }

        @Test
        public void ship_getDirectionV() {
            Ship ship = new Ship(Type.BATTLESHIP, 5, 0, Orientation.VERTICAL);
            assertTrue(ship.getDirection() == Orientation.VERTICAL);
        }

        @Test
        public void ship_getDirectionH() {
            Ship ship = new Ship(Type.BATTLESHIP, 5, 0, Orientation.HORIZONTAL);
            assertTrue(ship.getDirection() == Orientation.HORIZONTAL);
        }
    }

    public static class GameBoardTests {
        GameBoard board;

        @Before
        public void setUpBoard() {
            board = new GameBoard();
        }

        // ctor test
        @Test
        public void gameBoard_isInitializedEmpty() {
            for (int y = 0; y < 8; ++y) {
                for (int x = 0; x < 8; ++x) {
                    assertTrue(board.getPlayerBoard()[x][y] == GameBoard.GridState.EMPTY);
                    assertTrue(board.getOpponentBoard()[x][y] == GameBoard.GridState.EMPTY);
                }
            }
        }

        // placeShip tests

        @Test
        public void gameBoard_placeShipBattleshipH() {
            Ship ship = new Ship(Type.BATTLESHIP, 0, 0, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.PLAYER);
            for (int x = 0; x < ship.getSize(); ++x) {
                assertTrue(board.getPlayerBoard()[x][0] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipBattleshipV() {
            Ship ship = new Ship(Type.BATTLESHIP, 0, 0, Orientation.VERTICAL);
            board.placeShip(ship, Player.PLAYER);
            for (int y = 0; y < ship.getSize(); ++y) {
                assertTrue(board.getPlayerBoard()[0][y] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipBattleshipHBottom() {
            int placementX = 0;
            int placementY = 7;
            Ship ship = new Ship(Type.BATTLESHIP, placementX, placementY, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.PLAYER);
            for (int x = placementX; x < ship.getSize(); ++x) {
                assertTrue(board.getPlayerBoard()[x][placementY] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipDestroyerH() {
            Ship ship = new Ship(Type.DESTROYER, 0, 0, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.PLAYER);
            for (int x = 0; x < ship.getSize(); ++x) {
                assertTrue(board.getPlayerBoard()[x][0] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipDestroyerV() {
            Ship ship = new Ship(Type.DESTROYER, 0, 0, Orientation.VERTICAL);
            board.placeShip(ship, Player.PLAYER);
            for (int y = 0; y < ship.getSize(); ++y) {
                assertTrue(board.getPlayerBoard()[0][y] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipSubmarineH() {
            Ship ship = new Ship(Type.SUBMARINE, 0, 0, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.PLAYER);
            for (int x = 0; x < ship.getSize(); ++x) {
                assertTrue(board.getPlayerBoard()[x][0] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipSubmarineV() {
            Ship ship = new Ship(Type.SUBMARINE, 0, 0, Orientation.VERTICAL);
            board.placeShip(ship, Player.PLAYER);
            for (int y = 0; y < ship.getSize(); ++y) {
                assertTrue(board.getPlayerBoard()[0][y] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipPatrolBoatH() {
            Ship ship = new Ship(Type.PATROLBOAT, 0, 0, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.PLAYER);
            for (int x = 0; x < ship.getSize(); ++x) {
                assertTrue(board.getPlayerBoard()[x][0] == GameBoard.GridState.SHIP);
            }
        }

        @Test
        public void gameBoard_placeShipPatrolBoatV() {
            Ship ship = new Ship(Type.PATROLBOAT, 0, 0, Orientation.VERTICAL);
            board.placeShip(ship, Player.PLAYER);
            for (int y = 0; y < ship.getSize(); ++y) {
                assertTrue(board.getPlayerBoard()[0][y] == GameBoard.GridState.SHIP);
            }
        }

        // guess tests

        @Test
        public void gameBoard_guessPlayerV() {
            Ship ship = new Ship(Type.CARRIER, 0, 0, Orientation.VERTICAL);
            board.placeShip(ship, Player.PLAYER);
            for (int y = 0; y < ship.getSize(); ++y) {
                assertTrue(board.guess(0, y, Player.AI));
            }
        }

        @Test
        public void gameBoard_guessPlayerH() {
            Ship ship = new Ship(Type.CARRIER, 0, 0, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.PLAYER);
            for (int x = 0; x < ship.getSize(); ++x) {
                assertTrue(board.guess(x, 0, Player.AI));
            }
        }

        @Test
        public void gameBoard_guessAIV() {
            Ship ship = new Ship(Type.CARRIER, 0, 0, Orientation.VERTICAL);
            board.placeShip(ship, Player.AI);
            for (int y = 0; y < ship.getSize(); ++y) {
                assertTrue(board.guess(0, y, Player.PLAYER));
            }
        }

        @Test
        public void gameBoard_guessAIH() {
            Ship ship = new Ship(Type.CARRIER, 0, 0, Orientation.HORIZONTAL);
            board.placeShip(ship, Player.AI);
            for (int x = 0; x < ship.getSize(); ++x) {
                assertTrue(board.guess(x, 0, Player.PLAYER));
            }
        }
        // isLegalPlacement tests

        @Test
        public void gameBoard_isLegalPlacement_OOB() {
            assertFalse(board.isLegalPlacement(9, 9, 5, Orientation.VERTICAL));
        }

        @Test
        public void gameBoard_isLegalPlacement_ShipTooTall() {
            assertFalse(board.isLegalPlacement(5, 5, 5, Orientation.VERTICAL));
        }

        @Test
        public void gameBoard_isLegalPlacement_ShipTooWide() {
            assertFalse(board.isLegalPlacement(5, 5, 5, Orientation.HORIZONTAL));
        }

        @Test
        public void gameBoard_isLegalPlacement_OverlapVH() {
            board.placeShip(new Ship(Type.CARRIER, 0, 0, Orientation.VERTICAL), Player.PLAYER);
            assertFalse(board.isLegalPlacement(0, 1, 5, Orientation.HORIZONTAL));
        }

        @Test
        public void gameBoard_isLegalPlacement_OverlapHV() {
            board.placeShip(new Ship(Type.CARRIER, 0, 1, Orientation.HORIZONTAL), Player.PLAYER);
            assertFalse(board.isLegalPlacement(0, 0, 5, Orientation.VERTICAL));
        }

    }
}

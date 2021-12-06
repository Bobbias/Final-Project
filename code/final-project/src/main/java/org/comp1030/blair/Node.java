package org.comp1030.blair;

import java.util.NoSuchElementException;

/**
 * Node represents the individual nodes within the linked list class MyList. It
 * contains a
 */
public class Node {

    private int x;
    private int y;
    private Ship ship;

    public Node next;
    public Node prev;

    /**
     * Node constructor which takes individual x and y coordinates but no Ship.
     * 
     * @param x
     * @param y
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Node constructor which takes a Ship object and assigns the x and y
     * coordinates from the ship.
     * 
     * @param ship
     *                 A Ship object to use for the x and y coordinates.
     */
    public Node(Ship ship) {
        this.ship = ship;
        this.x = this.ship.getX();
        this.y = this.ship.getY();
    }

    /**
     * getShip returns the Ship object contained by this node object. Throws
     * NoSuchElementException if there is no ship in this node. Sets the node's
     * X and Y coordinates based on the ship's coordinates.
     * 
     * @return The ship object.
     */
    public Ship getShip() {
        if (this.ship != null) {
            return this.ship;
        } else {
            throw new NoSuchElementException(
                        "Called getShip() on a Node which does not contain a ship.");
        }
    }

    /**
     * getX returns the X coordinate this node refers to.
     * 
     * @return the X coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * getY returns the Y coordinate this node refers to.
     * 
     * @return the Y coodinate.
     */
    public int getY() {
        return this.y;
    }
}

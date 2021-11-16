package org.comp1030.blair;

import java.util.NoSuchElementException;

public class Node {
    
    private int x;
    private int y;
    private Ship ship;

    public Node next;
    public Node prev;
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(Ship ship)
    {
        this.ship = ship;
        this.x = this.ship.getX();
        this.y = this.ship.getY();
    }

    public Ship getShip()
    {
        if (this.ship != null)
        {
            return this.ship;
        }
        else
        {
            throw new NoSuchElementException("Called getShip() on a Node which does not contain a ship.");
        }
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }
}

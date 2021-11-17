package org.comp1030.blair;

/**
 * Ship represents a ship on the game board.
 */
public class Ship {
    // These enumerations are public so other objects can still
    // make use of them where necessary, while keeping the declarations
    // in the class which makes the most sense.

    /**
     * The Type enum defines several values which represent the different types of ship pieces.
     */
    public enum Type {
        CARRIER(1), BATTLESHIP(2), DESTROYER(4), SUBMARINE(8), PATROLBOAT(16);

        private final int value;

        private Type(int val) {
            this.value = val;
        }

        public int toValue() {
            return this.value;
        }

        /**
         * fromValue maps an integer value (must be a power of 2) to a Ship.Type
         * object.<br>
         * Throws IllegalArgumentException if provided with an invalid value.
         *
         * @param val
         *                the value to convert.
         * @return 
         *                one of the possible Ship.Type values.
         */
        public Type fromValue(int val) {
            switch (val) {
                case 1:
                    return CARRIER;
                case 2:
                    return BATTLESHIP;
                case 4:
                    return DESTROYER;
                case 8:
                    return SUBMARINE;
                case 16:
                    return PATROLBOAT;
                default:
                    throw new IllegalArgumentException(
                        "Argument must be a power of 2 between 1 and 16");
            }
        }
    }
    
    /**
     * The Orientation enum represents the direction that a ship is facing on the game board.
     */
    public enum Orientation {
        VERTICAL('v'),
        HORIZONTAL('h');

        private final char value;

        Orientation(char c) {
            this.value = c;
        }
        
        public char toChar() {
            return value;
        }

        /**
         * Converts a character to an Orientation. Valid characters are 'h' and 'v'.
         * Throws IllegalArgumentException for invalid characters.
         *
         * @param c the character to convert. Must be 'h' or 'v'.
         * @return HORIZONTAL for 'h' or VERTICAL for 'v'.
         */
        public static Orientation fromChar(char c) {
            switch (c) {
                case 'h':
                    return HORIZONTAL;
                case 'v':
                    return VERTICAL;
                default:
                    throw new IllegalArgumentException("Character must be 'v' or 'h'.");
            }
        }
    }

    // a hardcoded lookup table for the ship sizes.
    private final int[] sizes = { 5, 4, 3, 3, 2 };
    // instance variables
    private Type type;
    private int size;
    private int shipX;
    private int shipY;

    private Orientation orientation;
    
    /**
     * The default constructor for Ship objects.
     * Initializes x and y to 0, type to CARRIER and orientation to HORIZONTAL.
     */
    public Ship() {
        this.shipX = 0;
        this.shipY = 0;
        this.type = Type.CARRIER;
        this.orientation = Orientation.HORIZONTAL;
    }

    /**
     * Constructs a ship with the following parameters.
     *
     * @param type the type of the ship.
     * @param x the x coordinate of the ship.
     * @param y the y coordinate of the ship.
     * @param direction the direction the ship is facing.
     */
    public Ship(Type type, int x, int y, Orientation direction) {
        this.type = type;
        // use the numeric value of the enum to look up the correct size
        this.size = sizes[type.ordinal()];
        this.shipX = x;
        this.shipY = y;
        this.orientation = direction;
    }

    public int getSize() {
        return this.size;
    }

    public Type getType() {
        return this.type;
    }

    public Orientation getDirection() {
        return this.orientation;
    }

    public int getX() {
        return shipX;
    }

    public int getY() {
        return shipY;
    }
}

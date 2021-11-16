package org.comp1030.blair;

/** Ship represents a ship on the game board.
 * 
 */
public class Ship {
    // These enumerations are public so other objects can still
    // make use of them where necessary, while keeping the declarations
    // in the class which makes the most sense.
    public enum Type {
        CARRIER(1),
        BATTLESHIP(2),
        DESTROYER(4),
        SUBMARINE(8),
        PATROLBOAT(16);

        private final int value;
        private Type(int val)
        {
            this.value = val;
        }

        public int toValue()
        {
            return this.value;
        }

        public Type fromValue(int val)
        {
            switch (val)
            {
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
            }
            throw new IllegalArgumentException("Argument must be a multiple of 2 between 1 and 16");
        }

        public Type[] fromValueFlags(int flags)
        {
            int temp = flags;
            int msb = 0; // r will be lg(v)
            
            while (temp != 0) // unroll for more speed...
            {
                temp >>= 1;
                if ((temp & 1) == 1)
                {
                    msb++;
                }
            }
            Type list[] = new Type[msb];
            for (int i = 0; i < msb; ++i)
            {
                list[i] = fromValue(1 << i);
            }
            return list;
        }
    };
    public enum Orientation {
        VERTICAL('v'),
        HORIZONTAL('h');

        private final char char_value;
        Orientation(char c)
        {
            this.char_value = c;
        }
        public char toChar() {
            return char_value;
        }
        public static Orientation fromChar(char c)
        {
            switch (c)
            {
                case 'h':
                    return HORIZONTAL;
                case 'v':
                    return VERTICAL;
                default:
                    throw new IllegalArgumentException("Character must be 'v' or 'h'.");
            }
        }
};

    // a hardcoded lookup table for the ship sizes.
    private final int sizes[] = {5, 4, 3, 3, 2};
    // instance variables
    private Type type;
    private int size;

    private int x;
    private int y;

    private Orientation orientation;
    
    public Ship(Type type, int x, int y, Orientation direction)
    {
        this.type = type;
        // use the numeric value of the enum to look up the correct size
        this.size = sizes[type.ordinal()];
        this.x = x;
        this.y = y;
        this.orientation = direction;
    }

    public int getSize()
    {
        return this.size;
    }

    public Type getType()
    {
        return this.type;
    }

    public Orientation getDirection()
    {
        return this.orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

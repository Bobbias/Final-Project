package org.comp1030.blair;

import java.util.Scanner;
import javax.naming.OperationNotSupportedException;
// ANSI control sequence reference:
// 
// \033[H \033[2J  - This sequence first sets the cursor to 1/1 (top left corner) and clears the screen.
//                   The H command sets cursor position. With no parameters it assumes 1/1.
//                   The J command erases all text in the screen/viewport specified by the preceeding number.
//                   I'm not sure why the correct viewport to use here is 2, but it works.
// \033[4C         - The C command moves the cursor forward. The number specifies the number of characters to move by.
//
//
//

/**
 * Main
 */
public class Main {
    public static Game gameObject;
    public static void main(String[] args) {
        cls();
        mainMenu();
    }

    public static void cls()
    {
        System.out.print("\033[H\033[2J");
    }

    public static void mainMenu()
    {
        Scanner in = new Scanner(System.in);

        System.out.printf("Main Menu\n====================\n\n");
        System.out.printf("Menu Options:");
        System.out.printf("\n\n");
        System.out.printf("\033[3C1.\033[4CStart a new game.\n");
        System.out.printf("\033[3C2.\033[4CAbout.\n");
        System.out.printf("\033[3C3.\033[4CInstructions.\n");
        System.out.printf("\033[3C4.\033[4CQuit.\n");
        try {
            int selection = Integer.parseInt(in.nextLine());
            switch (selection)
            {
                case 1:
                    // get name and start new game
                    System.out.printf("\n\nPlease enter your name: ");
                    gameObject = new Game(in.nextLine(), in);
                    // ensure in is closed, because we will create a new object within the gameObject.run() context
                    gameObject.play();
                    break;
            
                case 2:
                    // show about page
                    System.out.printf("\n\nBattleship\n© Blair Stacey, 2021");
                    break;
                case 3:
                    // show instructions
                    break;
                case 4:
                    // ensure in is closed
                    System.exit(0);
                    break;
                default:
                    // ensure in is closed
                    in.close();
                    // throw exception because whatever you entered is wrong
                    throw new OperationNotSupportedException("You have entered an incorrect option, please enter a number between 1 and 3 and press enter.");
            }    
        }
        catch (Exception e)
        {
            System.out.printf("Encountered an error: %s", e.getMessage());
        }
    // ensure in is closed
    in.close();   
    }
}
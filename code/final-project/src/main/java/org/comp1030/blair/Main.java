package org.comp1030.blair;

/**
 * The main program class. It handles only the most basic functionality,
 * displaying the main menu, instructions, and about page.
 */

import java.util.Scanner;
import javax.naming.OperationNotSupportedException;

public class Main {
    // ANSI control sequence reference:
    // 
    // \033[H \033[2J  - This sequence first sets the cursor to 1/1 (top left corner)
    //                   and clears the screen.
    //                   The H command sets cursor position. With no parameters it
    //                   assumes 1/1.
    //                   The J command erases all text in the screen/viewport
    //                   specified by the preceeding number.
    //                   I'm not sure why the correct viewport to use here is 2, but
    //                   it works.
    // \033[4C         - The C command moves the cursor forward. The number specifies
    //                   the number of characters to move by.
    public static Game gameObject;

    public static void main(String[] args) {
        cls();
        mainMenu();
    }

    /**
     * The cls function uses ANSI control sequences to move the cursor to the
     * top left of the screen, and erase all the contents of the screen.
     */
    public static void cls() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * Displays the main menu for the game.
     */
    public static void mainMenu() {
        // Create the scanner we will be using throughout the game.
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
            switch (selection) {
                case 1:
                    // Get player name and start new game
                    System.out.printf("\n\nPlease enter your name: ");
                    gameObject = new Game(in.nextLine(), in);
                    gameObject.play();
                    break;
                case 2:
                    // Show About Page
                    cls();
                    System.out.printf("\n\nBattleship\n© Blair Stacey, 2021\n");
                    System.out.printf("Created for COMP1030.");
                    break;
                case 3:
                    // Show Instructions
                    cls();
                    System.out.printf(
                            "\n\n\033[3C1.\033[4CAll input must be either single");
                    System.out.printf(
                            " letters or numbers separated by spaces");
                    System.out.printf(
                            "\n\033[3C2.\033[4CAll coordinates refer to the top");
                    System.out.printf(
                            "left corner of a ship, regardless of orientation.");
                    System.out.printf(
                            "\n\033[3C3.\033[4CThe play field is presented with ");
                    System.out.printf(
                            "numbers across the left and top. The left playfield\n");
                    System.out.printf(
                            "\033[9Cshows your ships, and the opponent's guesses.");
                    System.out.printf(
                            " The right playfield shows your guesses.\n");
                    System.out.printf(
                            "\033[3C4.\033[4CShips are shown with the letter S. ");
                    System.out.printf(
                            "Hits and misses are shown with H and M respectively.");
                    break;
                case 4:
                    // ensure in is closed before exiting.
                    in.close();
                    System.exit(0);
                    break;
                default:
                    // ensure in is closed
                    in.close();
                    // throw exception because whatever you entered is wrong
                    StringBuilder msg = new StringBuilder(
                                    "You have entered an incorrect option, ");
                    msg.append(
                        "please enter a number between 1 and 3 and press enter.");
                    throw new OperationNotSupportedException(msg.toString());
            }
        } catch (Exception e) {
            System.out.printf("Encountered an error: %s", e.getMessage());
        }
        // ensure in is closed in case something has gone wrong
        // and in hasn't been closed yet.
        in.close();
    }
}
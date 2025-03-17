/*
 * Author: zagg294
 * Topic: ArrayList exercise
*/

import java.util.ArrayList;
import java.util.Random;

public class dvdJava {

    // Surface of the "screen"
    public static ArrayList<ArrayList<String>> surface = new ArrayList<>(); // Similar to a bidimensional array

    public static int sizeX = 20;

    public static int sizeY = 10;

    public static int posX, posY;

    public static int newPosX, newPosY;

    public static void framePrint() throws InterruptedException {
        // Clean terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i <= sizeY; i++) {
            for (int j = 0; j <= sizeX; j++) {
                System.out.print(surface.get(i).get(j));
            }
            System.out.println(); // Skip lines
        }
        Thread.sleep(200); // Waits 600ms
    }

    public static void root() {

        // Adding the columns
        for (int z = 0; z <= sizeY; z++) {
            surface.add(new ArrayList<String>());
        }

        // Creating the stage
        for (int i = 0; i <= sizeY; i++) {
            for (int j = 0; j <= sizeX; j++) {
                if (i == 0) {
                    surface.get(i).add("="); // Top border
                } else if (i == sizeY) {
                    surface.get(i).add("="); // Bottom border
                } else { // horizontal broders
                    if (j >= 1 && j < sizeX) {
                        surface.get(i).add(" ");
                    } else {
                        surface.get(i).add("|");
                    }
                }
            }
        }
    }

    public static void randomizePosition() {
        Random r = new Random();

        posX = r.nextInt(1, sizeX - 1);
        posY = r.nextInt(1, sizeY - 1);
    }

    public static String randomDirection(String beforeDirection) {
        String output = "";

        if (beforeDirection == "") {

            // Randomize direction
            Random r = new Random();
            boolean xDir = r.nextBoolean();
            boolean yDir = r.nextBoolean();

            // x axis
            if (xDir) { // left
                if ((posX - 1) <= 0) { // Check collision
                    output += "r";
                } else {
                    output += "l";
                }
            } else { // right
                if ((posX + 1) >= sizeX) { // Check collision
                    output += "l";
                } else {
                    output += "r";
                }
            }

            // y axis
            if (yDir) { // up
                if ((posY + 1) >= sizeY) { // Check collision
                    output += "d";
                } else {
                    output += "u";
                }
            } else { // down
                if ((posY - 1) <= 0) { // Check collision
                    output += "u";
                } else {
                    output += "d";
                }
            }

            return output;
        } else {
            if (beforeDirection.charAt(0) == 'l') { // try to go left
                if ((posX - 1) <= 0) { // Check collision
                    output += "r";
                } else {
                    output += "l";
                }

            }

            if (beforeDirection.charAt(0) == 'r') { // try to go right
                if ((posX + 1) >= sizeX) { // Check collision
                    output += "l";
                } else {
                    output += "r";
                }
            }

            if (beforeDirection.charAt(1) == 'd') {
                if (((posX + 1) >= sizeX) || ((posX - 1) <= 0)) { // It will touch the right and left borders?
                    if ((posY - 1) <= 0) { // Check collision and follow the movement
                        output += "u";
                    } else {
                        output += "d";
                    }
                }else{ //Touching top or bottom borders
                    if ((posY + 1) >= sizeY) { 
                        output += "d";
                    } else {
                        output += "u";
                    }
                }
            }

            if (beforeDirection.charAt(1) == 'u') { // try to go down
                if (((posX + 1) >= sizeX) || ((posX - 1) <= 0)) { // It will touch the right and left borders
                    if ((posY + 1) >= sizeY) { // Check collision and follow the movement
                        output += "d";
                    } else {
                        output += "u";
                    }
                }else{ //Touching top or bottom borders
                    if ((posY - 1) <= 0) { 
                        output += "u";
                    } else {
                        output += "d";
                    }
                }
            }

            return output;
        }
    }

    public static void force(String direction) {
        // Empty the current pos
        surface.get(posY).set(posX, " ");

        switch (direction) {
            case "lu":

                newPosX = posX - 1;
                newPosY = posY + 1;

                surface.get(newPosY).set(newPosX, "*"); // Moves the ball

                posX = newPosX;

                posY = newPosY;

                break;
            case "ld":

                newPosX = posX - 1;
                newPosY = posY - 1;

                surface.get(newPosY).set(newPosX, "*"); // Moves the ball

                posX = newPosX;

                posY = newPosY;

                break;
            case "ru":
                newPosX = posX + 1;
                newPosY = posY + 1;

                surface.get(newPosY).set(newPosX, "*"); // Moves the ball

                posX = newPosX;

                posY = newPosY;

                break;
            case "rd":
                newPosX = posX + 1;
                newPosY = posY - 1;

                surface.get(newPosY).set(newPosX, "*"); // Moves the ball

                posX = newPosX;

                posY = newPosY;
                break;

            default:
                break;
        }
    }

    public static boolean collision(String direction) {
        switch (direction) {
            case "ru":
                if ((posX + 1) >= sizeX || (posY + 1) >= sizeY) {
                    return true;
                } else {
                    return false;
                }

            case "rd":
                if ((posX + 1) >= sizeX || (posY - 1) <= 0) {
                    return true;
                } else {
                    return false;
                }
            case "lu":
                if ((posX - 1) <= 0 || (posY + 1) >= sizeY) {
                    return true;
                } else {
                    return false;
                }
            case "ld":
                if ((posX - 1) <= 0 || (posY - 1) <= 0) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public static void main(String[] args) {

        // Screen initialization
        root();

        // Position initialization
        randomizePosition();

        // Initial direction
        String direction = randomDirection("");

        while (true) { // Print loop
            try {
                // Move the ball
                if (collision(direction)) {
                    direction = randomDirection(direction);
                    force(direction);
                } else {
                    force(direction);
                }
                framePrint();// Print frame
            } catch (Exception e) {
                System.out.println("Problema en el thread");
            }
        }

    }
}
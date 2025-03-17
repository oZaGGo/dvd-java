/*
 * Author: zagg294
 * Topic: ArrayList exercise
*/

import java.util.ArrayList;
import java.util.Random;
import java.awt.Toolkit;

public class dvdJava {

    // Surface of the "screen"
    public static ArrayList<ArrayList<String>> surface = new ArrayList<>(); // Similar to a bidimensional array

    public static int sizeX = 30;

    public static int sizeY = 12;

    public static int posX, posY;

    public static int newPosX, newPosY;

    public static int velocity = 1;

    // Colors

    public static final String RESET = "\u001B[0m";

    public static String[] colors = { "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m",
            "\u001B[36m" };

    public static int currentColor = 0;

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
        Thread.sleep(120); // Waits 120ms
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

        posX = r.nextInt(velocity, sizeX - velocity);
        posY = r.nextInt(velocity, sizeY - velocity);
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
                if ((posX - velocity) <= velocity) { // Check collision
                    output += "r";
                } else {
                    output += "l";
                }
            } else { // right
                if ((posX + velocity) >= sizeX-velocity) { // Check collision
                    output += "l";
                } else {
                    output += "r";
                }
            }

            // y axis
            if (yDir) { // up
                if ((posY + velocity) >= sizeY) { // Check collision
                    output += "d";
                } else {
                    output += "u";
                }
            } else { // down
                if ((posY - velocity) <= 0) { // Check collision
                    output += "u";
                } else {
                    output += "d";
                }
            }

            return output;
        } else {
            if (beforeDirection.charAt(0) == 'l') { // try to go left
                if ((posX - velocity) <= velocity) { // Check collision
                    output += "r";
                } else {
                    output += "l";
                }

            }

            if (beforeDirection.charAt(0) == 'r') { // try to go right
                if ((posX + velocity) >= sizeX-velocity) { // Check collision
                    output += "l";
                } else {
                    output += "r";
                }
            }

            if (beforeDirection.charAt(1) == 'd') {
                if (((posX + velocity) >= sizeX-velocity) || ((posX - velocity) <= velocity)) { // It will touch the right and left
                                                                                // borders?
                    if ((posY - velocity) <= 0) { // Check collision and follow the movement
                        output += "u";
                    } else {
                        output += "d";
                    }
                } else { // Touching top or bottom borders
                    if ((posY + velocity) >= sizeY) {
                        output += "d";
                    } else {
                        output += "u";
                    }
                }
            }

            if (beforeDirection.charAt(1) == 'u') { // try to go down
                if (((posX + velocity) >= sizeX-velocity) || ((posX - velocity) <= velocity)) { // It will touch the right and left
                                                                                // borders
                    if ((posY + velocity) >= sizeY) { // Check collision and follow the movement
                        output += "d";
                    } else {
                        output += "u";
                    }
                } else { // Touching top or bottom borders
                    if ((posY - velocity) <= 0) {
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
        surface.get(posY).set(posX-1, " ");
        surface.get(posY).set(posX, " ");
        surface.get(posY).set(posX+1, " ");

        switch (direction) {
            case "lu":

                newPosX = posX - velocity;
                newPosY = posY + velocity;

                surface.get(newPosY).set(newPosX-1, colors[currentColor] + "D" + RESET); 
                surface.get(newPosY).set(newPosX, colors[currentColor] + "v" + RESET);
                surface.get(newPosY).set(newPosX+1, colors[currentColor] + "D" + RESET); 

                posX = newPosX;

                posY = newPosY;

                break;
            case "ld":

                newPosX = posX - velocity;
                newPosY = posY - velocity;

                surface.get(newPosY).set(newPosX-1, colors[currentColor] + "D" + RESET); 
                surface.get(newPosY).set(newPosX, colors[currentColor] + "v" + RESET);
                surface.get(newPosY).set(newPosX+1, colors[currentColor] + "D" + RESET); 

                posX = newPosX;

                posY = newPosY;

                break;
            case "ru":
                newPosX = posX + velocity;
                newPosY = posY + velocity;

                surface.get(newPosY).set(newPosX-1, colors[currentColor] + "D" + RESET); 
                surface.get(newPosY).set(newPosX, colors[currentColor] + "v" + RESET);
                surface.get(newPosY).set(newPosX+1, colors[currentColor] + "D" + RESET);  
                posX = newPosX;

                posY = newPosY;

                break;
            case "rd":
                newPosX = posX + velocity;
                newPosY = posY - velocity;

                surface.get(newPosY).set(newPosX-1, colors[currentColor] + "D" + RESET); 
                surface.get(newPosY).set(newPosX, colors[currentColor] + "v" + RESET);
                surface.get(newPosY).set(newPosX+1, colors[currentColor] + "D" + RESET); 

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
                if ((posX + velocity) >= sizeX-velocity || (posY + velocity) >= sizeY) {
                    return true;
                } else {
                    return false;
                }

            case "rd":
                if ((posX + velocity) >= sizeX-velocity || (posY - velocity) <= 0) {
                    return true;
                } else {
                    return false;
                }
            case "lu":
                if ((posX - velocity) <= 1 || (posY + velocity) >= sizeY) {
                    return true;
                } else {
                    return false;
                }
            case "ld":
                if ((posX - velocity) <= 1 || (posY - velocity) <= 0) {
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

        Random r = new Random();

        while (true) { // Print loop
            try {
                // Move the ball
                if (collision(direction)) {
                    if (currentColor < 5) {
                        currentColor++;
                    } else {
                        currentColor = 0;
                    }
                    direction = randomDirection(direction);
                    force(direction);
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    force(direction);
                }
                framePrint();// Print frame
            } catch (Exception e) {
                e.getMessage();
            }
        }

    }
}

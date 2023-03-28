package tictactoe;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static String currentPlayer = "X";

    public static void main(String[] args) {

        String[][] gameState = initField();
        System.out.println("Welcome to tic-tac-toe, enter co-ordinates to choose your move, X starts! Go!\n");

        renderField(gameState);
        do {
            insertOX(gameState);
            renderField(gameState);
        } while (!checkWinner(gameState));



    }
    static String[][] initField() {
        String[][] gameState = new String[3][3];
        for (int cols = 0; cols < 3; cols++) {
            for (int rows = 0; rows < 3; rows++) {
                gameState[cols][rows] = " ";
            }
        }
        return gameState;
    }

    static void insertOX(String[][] gameState) {
        while(true) {
            if (!scanner.hasNextInt()) {
                System.out.println("You should enter numbers!");
                scanner.nextLine(); //consume newline
                continue;
            }

            int userCol = scanner.nextInt();

            if (!scanner.hasNextInt()) {
                System.out.println("You should enter numbers!");
                scanner.nextLine(); //consume newline
                continue;
            }

            int userRow = scanner.nextInt();
            scanner.nextLine(); //consume newline

            if (userCol > 3 || userRow > 3 || userCol < 1 || userRow < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (Objects.equals(gameState[userCol - 1][userRow - 1], " ")) {
                gameState[userCol - 1][userRow -1] = currentPlayer;
                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }


    static void renderField(String[][] gameState) {
        System.out.println("---------");
        for (int cols = 0; cols < 3; cols++) {
            System.out.print("|");
            for (int rows = 0; rows < 3; rows++) {
                System.out.print(" " + gameState[cols][rows]);
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    static boolean checkWinner (String[][] gameState) {
        //rows and columns check
        for (int i = 0; i < 3; i++){
            if (!gameState[0][i].equals(" ")
                    && gameState[0][i].equals(gameState[1][i])
                    && gameState[0][i].equals(gameState[2][i])
                    || !gameState[i][0].equals(" ")
                    && gameState[i][0].equals(gameState[i][1])
                    && gameState[i][0].equals(gameState[i][2])

            ) {
                System.out.println(gameState[i][i] + " wins");
                return true;
            }
        }


        // Diagonals check
        if (!gameState[1][1].equals(" ")
                && gameState[0][0].equals(gameState[1][1])
                && gameState[0][0].equals(gameState[2][2])
                || !gameState[1][1].equals(" ")
                && gameState[2][0].equals(gameState[1][1])
                && gameState[2][0].equals(gameState[0][2])

        ) {
            System.out.println(gameState[1][1] + " wins");
            return true;
        }

        //Draw check:
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!gameState[i][j].equals(" ")) {
                    count++;
                }
            }
        }
        if (count == 9) {
            System.out.println("Draw");
            return true;
        }

        return false;

    }

}

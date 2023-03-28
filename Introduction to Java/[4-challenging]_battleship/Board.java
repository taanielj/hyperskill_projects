package battleship;
import java.util.Scanner;

public class Board {
    final private char[][] gameBoard;
    private final Scanner scanner = new Scanner(System.in);
    final int BOARD_SIZE = 10;

    final String ALLOWED_ROW_COORDS = "[A-J]";
    final String ALLOWED_COL_COORDS = "0*[0-9]|10";

    public Board () {
        gameBoard = new char[BOARD_SIZE][BOARD_SIZE];

        for (int rows = 0; rows < gameBoard.length; rows++) {
            for (int cols = 0; cols < gameBoard.length; cols++) {
                gameBoard[rows][cols] = '~';
            }
        }
    }

    public void drawBoard(boolean fogOfWar) {


        StringBuilder shipLegendsBuilder = new StringBuilder();
        for (Ship ship : Ship.values()) {
            shipLegendsBuilder.append(ship.getLegend());
        }
        String shipLegends = shipLegendsBuilder.toString();

        System.out.print("  ");
        for (int i = 1; i <= gameBoard.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        char rowLabel = 'A';
        for (char[] row : gameBoard) {
            System.out.print(rowLabel + " ");
            rowLabel++;
            for (int col = 0; col < gameBoard.length; col++) {

                if (fogOfWar) {
                    if (shipLegends.indexOf(row[col]) != -1) {
                        System.out.print("~ ");
                    } else {
                        System.out.print(row[col] + " ");
                    }

                } else {
                    if (shipLegends.indexOf(row[col]) != -1) {
                        System.out.print("O ");
                    } else {
                        System.out.print(row[col] + " ");
                    }
                }
            }
            System.out.println();
        }
    }



    public void addShip(Ship ship) {
        System.out.printf("\nEnter the coordinates of the %s (%d cells): \n\n", ship.getName(), ship.getLength());

        while (true) {
            try {
                String[] startInput = scanner.next().toUpperCase().split("", 2);
                String[] stopInput = scanner.next().toUpperCase().split("", 2);
                scanner.nextLine();
                System.out.println();

                if (!startInput[0].matches(ALLOWED_ROW_COORDS) ||
                        !stopInput[0].matches(ALLOWED_ROW_COORDS) ||
                        !startInput[1].matches(ALLOWED_COL_COORDS) ||
                        !stopInput[1].matches(ALLOWED_COL_COORDS)) {
                    throw new IllegalArgumentException("Error! Wrong ship location! Try again:\n");
                }

                int startRow = (int) startInput[0].charAt(0) - 65;
                int startCol = Integer.parseInt(startInput[1]) - 1;
                int stopRow = (int) stopInput[0].charAt(0) - 65;
                int stopCol = Integer.parseInt(stopInput[1]) - 1;

                if (startRow > stopRow || startCol > stopCol) {
                    int tempRow = startRow;
                    int tempCol = startCol;
                    startRow = stopRow;
                    startCol = stopCol;
                    stopRow = tempRow;
                    stopCol = tempCol;
                }

                boolean horizontal = startRow == stopRow;
                boolean vertical = startCol == stopCol;

                if (!(horizontal || vertical)) {
                    throw new IllegalArgumentException("Error! Wrong ship location! Try again:\n");
                }

                if (Math.abs((horizontal ? startCol - stopCol : startRow - stopRow)) != ship.getLength() - 1) {
                    throw new IllegalArgumentException(String.format("Error! Wrong length of %s! Try again:\n", ship.getName()));
                }

                int rowIncrement = horizontal ? 0 : 1;
                int colIncrement = vertical ? 0 : 1;

                boolean validPlacement = true;

                for (int i = 0; i < ship.getLength(); i++) {
                    int currentRow = startRow + i * rowIncrement;
                    int currentCol = startCol + i * colIncrement;

                    if (!isValidPlacement(currentRow, currentCol)) {
                        validPlacement = false;
                        break;
                    }
                }

                if (!validPlacement) {
                    continue;
                }

                for (int i = 0; i < ship.getLength(); i++) {
                    int currentRow = startRow + i * rowIncrement;
                    int currentCol = startCol + i * colIncrement;

                    gameBoard[currentRow][currentCol] = ship.getLegend();

                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public boolean takeAShot (Ship[] shipArray) {

        while (true) {
            System.out.println("\nTake a shot!\n");
            String[] playerShot = scanner.next().toUpperCase().split("", 2);

            if (!playerShot[0].matches(ALLOWED_ROW_COORDS) || !playerShot[1].matches(ALLOWED_COL_COORDS)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                continue;
            }

            int playerShotRow = (int) playerShot[0].charAt(0) - 65;
            int playerShotCol = Integer.parseInt(playerShot[1]) - 1;


            for (Ship ship : shipArray) {
                if (ship.getLegend() == gameBoard[playerShotRow][playerShotCol]) {
                    ship.setHealth(ship.getHealth() - 1);
                    gameBoard[playerShotRow][playerShotCol] = 'X';
                    if (ship.getHealth() != 0) {
                        System.out.println("\nYou hit a ship!\n");
                    } else {
                        boolean allShipsSunk = true;
                        for (Ship otherShip : shipArray) {
                            if (otherShip.getHealth() > 0) {
                                allShipsSunk = false;
                                break;
                            }
                        }
                        if (allShipsSunk) {
                            System.out.println("\nYou sank the last ship. You won.\nCongratulations!\n");
                        } else {
                            System.out.println("\nYou sank a ship! Specify a new target:\n");
                        }
                    }
                    return true;
                }
            }



            if (gameBoard[playerShotRow][playerShotCol] == 'O') {
                gameBoard[playerShotRow][playerShotCol] = 'X';
                System.out.println("\nYou hit a ship!\n");
                return true;
            }

            if (gameBoard[playerShotRow][playerShotCol] == 'X') {
                System.out.println("\nYou already hit that part!\n");
                return false;
            }


            gameBoard[playerShotRow][playerShotCol] = 'M';
            System.out.println("\nYou missed!\n");
            return false;

        }
    }

    private boolean isValidPlacement(int row, int col) {
        if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length) {
            System.out.println("Error! Wrong ship location! Try again:\n\n");
            return false; // placement is out of bounds
        }

        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        if (gameBoard[row][col] != '~') {
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            return false; // cell is already occupied
        }

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (newRow >= 0 && newRow < gameBoard.length && newCol >= 0 && newCol < gameBoard[0].length) {
                if (gameBoard[newRow][newCol] != '~') {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false; // neighboring cell is already occupied
                }
            }
        }

        return true; // placement is valid
    }

}

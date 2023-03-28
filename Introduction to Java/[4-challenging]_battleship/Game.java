package battleship;

import java.util.Scanner;

public class Game {
    final private Player player1;
    final private Player player2;
    final private Board boardPlayer1;
    final private Board boardPlayer2;
    final private Scanner scanner;

    public Game() {
        player1 = new Player();
        player2 = new Player();
        boardPlayer1 = player1.getBoard();
        boardPlayer2 = player2.getBoard();
        scanner = new Scanner(System.in);
    }

    public void start() {

        System.out.printf("%s, place your ships on the game field\n\n", player1.getName());
        boardPlayer1.drawBoard(false);
        Ship[] shipArrayPlayer1 = addAllShips(boardPlayer1);

        System.out.println("\nPress Enter and pass the move to another player\n...");
        scanner.nextLine();

        System.out.printf("%s, place your ships on the game field\n\n", player2.getName());
        boardPlayer2.drawBoard(false);
        Ship[] shipArrayPlayer2 = addAllShips(boardPlayer2);

        System.out.println("\nPress Enter and pass the move to another player\n...");
        scanner.nextLine();

        int fireShotsPlayer1 = 0;
        int fireShotsPlayer2 = 0;
        int totalShipCells = getTotalShipCells(shipArrayPlayer1);

        while (true) {
            if (playerTurn(player1, boardPlayer1, boardPlayer2, shipArrayPlayer2)) {
                fireShotsPlayer1++;
            }

            if (fireShotsPlayer1 >= totalShipCells) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return;
            }

            System.out.println("\nPress Enter and pass the move to another player\n...");
            scanner.nextLine();

            if (playerTurn(player2, boardPlayer2, boardPlayer1, shipArrayPlayer1)) {
                fireShotsPlayer2++;
            }

            if (fireShotsPlayer2 >= totalShipCells) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return;
            }

            System.out.println("\nPress Enter and pass the move to another player\n...");
            scanner.nextLine();
        }
    }

    private Ship[] addAllShips(Board board) {
        Ship[] shipArray = new Ship[Ship.values().length];
        for (int shipIndex = 0; shipIndex < Ship.values().length; shipIndex++) {
            shipArray[shipIndex] = Ship.values()[shipIndex];
            board.addShip(Ship.values()[shipIndex]);
            board.drawBoard(false);
        }
        return shipArray;
    }

    private int getTotalShipCells(Ship[] ships) {
        int total = 0;
        for (Ship ship : ships) {
            total += ship.getLength();
        }
        return total;
    }

    private void drawBoards(Board currentPlayerBoard, Board opponentBoard) {
        opponentBoard.drawBoard(true);
        System.out.println("---------------------");
        currentPlayerBoard.drawBoard(false);
    }

    private boolean playerTurn(Player player, Board currentPlayerBoard, Board opponentBoard, Ship[] opponentShips) {
        drawBoards(currentPlayerBoard, opponentBoard);
        System.out.printf("\n %s, it's your turn:\n\n", player.getName());
        return opponentBoard.takeAShot(opponentShips);
    }

}

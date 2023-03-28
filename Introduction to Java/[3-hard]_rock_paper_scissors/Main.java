package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;


import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String input;
        String userChoice;
        String computerChoice;




        Player currentPlayer = selectPlayer();

        System.out.printf("Hello, %s\n", currentPlayer.name);

        ArrayList<String> listOfMoves = generateMoves();
        System.out.println("Okay, let's start");

        do {
            input = scanner.next();
            if (input.equals("!exit")) {
                System.out.println("Bye!");
                break;
            }

            if (input.equals("!rating")) {
                System.out.println("Your rating: " + currentPlayer.score);
                continue;
            }

            if (listOfMoves.contains(input)) {
                userChoice = input;
            } else {
                System.out.println("Invalid input");
                continue;
            }


            computerChoice = randomChoice(listOfMoves);

            String result = determineResult(userChoice, computerChoice, listOfMoves);

            getResult(result, computerChoice);
            currentPlayer.score = updateScore(result, currentPlayer.score);

        } while (true);

    }

    static void getResult (String result, String computerChoice) {
        switch (result) {
            case "WIN" -> System.out.printf("Well done. The computer chose %s and failed\n", computerChoice);
            case "LOSS" -> System.out.printf("Sorry, but the computer chose %s\n", computerChoice);
            case "DRAW" -> System.out.printf("There is a draw (%s)\n", computerChoice);
        }
    }

    static int updateScore (String result, int score) {
        return switch (result) {
            case "WIN" -> score + 100;
            case "LOSS" -> score;
            case "DRAW" -> score + 50;
            default -> throw new IllegalStateException("Unexpected value: " + result);
        };

    }

    static String[][] generateResultMatrix(ArrayList<String> listOfMoves) {
        int size = listOfMoves.size();
        String[][] resultMatrix = new String[size][size];
        for (int i = 0; i < size; i++) {
            resultMatrix[i][i] = "DRAW";
        }

        // Assign "WIN" and "LOSS" values to non-diagonal elements based on rules
        for (int i = 0; i < size; i++) {
            int half = size / 2;
            for (int j = 1; j <= half; j++) {
                int index1 = (i + j) % size;
                int index2 = (i + half + j) % size;
                resultMatrix[i][index1] = "LOSS";
                resultMatrix[i][index2] = "WIN";
            }
        }

        return resultMatrix;
    }

    static String determineResult(String userMove, String computerMove, ArrayList<String> listOfMoves) {

        String[][] resultMatrix = generateResultMatrix(listOfMoves);
        int userIndex = indexOf(listOfMoves.toArray(), userMove);
        int computerIndex = indexOf(listOfMoves.toArray(), computerMove);

        return resultMatrix[userIndex][computerIndex];
    }

    static <T> int indexOf(T[] array, T element) {

        for (int i = 0; i < array.length; i++) {
            if(array[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    static ArrayList<Player> loadPlayersFromFile () {
        File file = new File ("rating.txt");
        ArrayList<Player> listOfPlayers = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                String player = scanner.next();
                int score = scanner.nextInt();
                listOfPlayers.add(new Player(player, score));
            }
            return listOfPlayers;
        } catch (FileNotFoundException e) {
            System.out.println("File not found:" + "rating.txt");
            return listOfPlayers;
        }
    }

    static String randomChoice(ArrayList<String> listOfMoves) {
        Random rand = new Random();
        return listOfMoves.get(rand.nextInt(listOfMoves.size()));
    }

    static Player selectPlayer() {
        ArrayList<Player> listOfPlayers = loadPlayersFromFile();
        Player currentPlayer = null;
        System.out.print("Enter your name: ");
        String inputName = scanner.nextLine();
        for (Player player : listOfPlayers) {
            if (player.name.equals(inputName)) {
                currentPlayer = player;
                break;
            }
        }

        if (currentPlayer == null) {
            currentPlayer = new Player(inputName);
            listOfPlayers.add(currentPlayer);
        }
        return currentPlayer;
    }

    static ArrayList<String> generateMoves () {
        String input = scanner.nextLine();
        String[] arrayMoves;


        if (input.equals("")) {
            arrayMoves = new String[]{"rock", "paper", "scissors"};
        } else {
            arrayMoves = input.split(",");
        }

        ArrayList<String> listOfMoves = new ArrayList<>();
        Collections.addAll(listOfMoves, arrayMoves);
        return listOfMoves;
    }

}


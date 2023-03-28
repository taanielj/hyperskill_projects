package bullscows;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {


        String length;
        String nChars;
        String secretCode = "";


        System.out.println("Input the length of the secret code:");
        length = scanner.nextLine();
        boolean inputOK = errorChecker(length);
        if (inputOK) {
            System.out.println("Input the number of possible symbols in the code:");
            nChars = scanner.nextLine();
            inputOK = errorChecker(nChars);

            if (inputOK) {
                inputOK = errorChecker(length,nChars);
                if (inputOK) {
                    secretCode = codeGenerator(Integer.parseInt(length), Integer.parseInt(nChars));
                    System.out.println("Okay, let's start a game!");
                }
            }
        }

        int turn = 1;
        while (true) {

            System.out.printf("Turn %d:\n", turn);
            String userGuess = scanner.nextLine();
            if (userGuess.length() != Integer.parseInt(length)) {
                System.out.printf("Invalid guess, the code is %d characters long\n", secretCode.length());
                continue;
            }
            int[] bullsCows = getScore(secretCode, userGuess);
            System.out.print("Grade: ");
            if (bullsCows[0] == 0 && bullsCows[1] == 0) {
                System.out.print("None.\n");
                turn++;
            } else if (bullsCows[0] == 0) {
                System.out.printf("%d cow(s).\n", bullsCows[1]);
                turn++;
            } else if (bullsCows[1] == 0) {
                System.out.printf("%d bulls(s).\n", bullsCows[0]);
                turn++;
                if (bullsCows[0] == Integer.parseInt(length)) {
                    System.out.println("\nCongratulations! You guessed the secret code.");
                    break;
                }

            } else {
                System.out.printf("%d bulls(s) and %d cow(s). ", bullsCows[0], bullsCows[1]);

            }

        }


    }

    static Boolean errorChecker (String length, String nChars) {

        if (Integer.parseInt(length) > Integer.parseInt(nChars)) {
            System.out.printf("""
                    Error: it's not possible to generate a code with a length of %s with %s unique symbols.
                    """, length, nChars);
            return false;

        }

        if (Integer.parseInt(nChars) > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return false;
        }
        return true;
    }

    static Boolean errorChecker (String string) {

        try {
            if (Integer.parseInt(string) > 0) {
                return true;
            }
            System.out.printf("Error: \"%s\" isn't a valid number." , string);
            return false;
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number." , string);
            return false;
        }

    }

    static String codeGenerator(int length, int nChars) {

        String characters = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, nChars);
        List<String> shuffledCharacters = Arrays.asList(characters.split(""));
        Collections.shuffle(shuffledCharacters);
        StringBuilder shuffledNumbers = new StringBuilder();
        for (String character : shuffledCharacters)
        {
            shuffledNumbers.append(character);
        }
        System.out.printf("The secret is prepared: %s" , "*".repeat(length));
        if (length <= 10){
            System.out.printf(" (0 - %s).\n",  characters.substring(characters.length() - 1));
        } else {
            System.out.printf(" (0 - 9, a - %s).\n",  characters.substring(characters.length() - 1));
        }
        return String.join("", shuffledNumbers.toString()).substring(0, length);
    }

    static int[] getScore(String secretCode, String userCode) {
        int[] bullsCows = new int[2];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            if (secretCode.toCharArray()[i] == userCode.toCharArray()[i]) {
                bulls++;
            } else if (secretCode.contains(String.valueOf(userCode.toCharArray()[i]))) {
                cows++;
            }
        }

        bullsCows[0] = bulls;
        bullsCows[1] = cows;

        return bullsCows;
    }

}

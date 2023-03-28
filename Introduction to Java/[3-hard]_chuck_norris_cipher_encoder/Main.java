package chucknorris;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String userChoice = scanner.nextLine();
            String encodedString;
            String decodedString;
            switch (userChoice) {
                case "encode" -> {
                    System.out.println("Input string:");
                    decodedString = scanner.nextLine();
                    encodedString = asciiToUnary(decodedString);
                    System.out.println("Encoded string:");
                    System.out.println(encodedString + "\n");
                }
                case "decode" -> {
                    System.out.println("Input encoded string:");
                    encodedString = scanner.nextLine();
                    if (encodedChecker(encodedString)) {
                        System.out.println("Decoded string:");
                        decodedString = unaryToASCII(encodedString);
                        System.out.println(decodedString + "\n");
                    } else {
                        System.out.println("Encoded string is not valid.\n");
                    }
                }
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.printf("There is no '%s' operation\n\n", userChoice);
            }
        }
    }


    public static boolean encodedChecker(String string) {
        String[] stringArray = string.split(" ");

        if (!string.matches("[0 ]+")) {
            return false;
        }

        for (int i = 0; i < stringArray.length / 2; i++) {
            if (!stringArray[2 * i].equals("0") && !stringArray[2 * i].equals("00")) {
                return false;
            }
        }

        if (stringArray.length % 2 != 0) {
            return false;
        }

        if (unaryToBinary(string).length() % 7 != 0) {
            return false;
        }

        return true;
    }

    public static String unaryToASCII(String inputString) {
        String binaryString = unaryToBinary(inputString);
        return binaryToASCII(binaryString);
    }

    public static String asciiToUnary(String inputString) {
        String binaryString = asciiToBinary(inputString);
        return binaryToUnary(binaryString);
    }

    public static String binaryToASCII(String string) {
        StringBuilder asciiString = new StringBuilder();
        final int BINARY_LENGTH = 7;

        for (int i = 0; i < string.length()/BINARY_LENGTH; i++) {
            char binary = (char) Integer.parseInt(string.substring(i * BINARY_LENGTH, i * BINARY_LENGTH + BINARY_LENGTH ), 2);
            asciiString.append(binary);
        }

        return asciiString.toString();
    }

    public static String unaryToBinary(String string) {
        String[] unaryArray = string.split(" ");
        StringBuilder binaryString = new StringBuilder();
        int i = 0;

        while (i < unaryArray.length / 2) {
            if (unaryArray[2 * i].equals("0")) {
                binaryString.append("1".repeat(unaryArray[2 * i +1].length()));
            } else {
                binaryString.append("0".repeat(unaryArray[2 * i +1].length()));
            }
            i++;
        }

        return binaryString.toString();
    }



    public static String asciiToBinary(String string) {

        char[] charArray = string.toCharArray();
        String[] binaryStringArr = new String[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            String binary = String.format("%7s", Integer.toBinaryString(charArray[i])).replace(' ', '0');
            binaryStringArr[i] = binary;
        }

        return String.join("", binaryStringArr);
    }

    public static String binaryToUnary(String binaryString) {
        char[] bitStream = binaryString.toCharArray();
        StringBuilder unaryString = new StringBuilder();
        int index = 0;

        while (index < bitStream.length) {

            if (bitStream[index] == '1') {
                unaryString.append("0 ");
                while (index < bitStream.length && bitStream[index] == '1') {
                    unaryString.append("0");
                    index++;
                }
            } else {
                unaryString.append("00 ");
                while (index < bitStream.length && bitStream[index] == '0') {
                    unaryString.append("0");
                    index++;
                }
            }

            unaryString.append(" ");
        }

        return (unaryString.toString().trim());
    }
}


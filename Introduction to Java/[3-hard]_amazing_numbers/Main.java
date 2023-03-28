package numbers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println(instructions());


        while(true) {
            System.out.print("\nEnter a request: ");
            String input = scanner.nextLine();

            if (!validateInput(input)) {
                continue;
            }

            String[] inputs = input.split("\\s+");
            long userNumber = Long.parseLong(inputs[0]);
            int numbersToProcess;
            if (inputs.length == 1) {
                numbersToProcess = 1;
            } else {
                numbersToProcess = Integer.parseInt(inputs[1]);
            }

            if (userNumber == 0) break;
            NumberProperties properties;
            String formattedProperties;
            switch (inputs.length) {
                case 1 -> {
                    properties = new NumberProperties(userNumber);
                    formattedProperties = properties.oneNumberString();
                    System.out.printf("\nProperties of %d:\n%s", userNumber, formattedProperties);
                    System.out.println();
                }
                case 2 -> {
                    for (int i = 0; i < numbersToProcess; i++) {
                        properties = new NumberProperties(userNumber + i);
                        formattedProperties = properties.multiNumberString();
                        System.out.printf("\n%s", formattedProperties);
                    }
                    System.out.println();
                }
                default -> {
                    List<Property> targetProperties = new ArrayList<>();
                    List<Property> excludeProperties = new ArrayList<>();
                    for (int i = 2; i < inputs.length; i++) {
                        if (!inputs[i].startsWith("-")){
                            targetProperties.add(Property.valueOf(inputs[i].toUpperCase()));
                        } else {
                            excludeProperties.add(Property
                                    .valueOf(inputs[i].replace("-", "").
                                            toUpperCase()));
                        }

                    }

                    int processedCount = 0;
                    while (processedCount < numbersToProcess) {
                        NumberProperties numProperties = new NumberProperties(userNumber);

                        boolean allPropertiesMatch = true;
                        for (Property property : targetProperties) {
                            if (!numProperties.hasProperty(property)) {
                                allPropertiesMatch = false;
                                break;
                            }
                        }
                        for (Property property : excludeProperties) {
                            if (numProperties.hasProperty(property)) {
                                allPropertiesMatch = false;
                                break;
                            }
                        }

                        if (allPropertiesMatch) {
                            System.out.printf("\n%s", numProperties.multiNumberString());
                            processedCount++;
                        }
                        userNumber++;
                    }
                    System.out.println();
                }
            }






        }
        System.out.println("\nGoodbye!");
    }

    static String instructions() {
        return """
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""";
    }

    static boolean validateInput(String input) {
        long userNumber;
        long numbersToProcess;

        String[] inputs = input.split("\\s+");

        if (input.isBlank()) {
            System.out.println(instructions());
            return false;
        }

        try {
            userNumber = Long.parseLong(inputs[0]);
            if (userNumber < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("\nThe first parameter should be a natural number or zero.");
            return false;
        }

        if (inputs.length >= 2) {
            try {
                numbersToProcess = Long.parseLong(inputs[1]);
                if (numbersToProcess <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                System.out.println("\nThe second parameter should be a natural number.");
                return false;
            }
        }

        if (inputs.length == 3) {

            try {
                Property.valueOf(inputs[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                try {
                    Property.valueOf(inputs[2].replace("-","").toUpperCase());
                } catch (IllegalArgumentException ee){
                    System.out.printf("\nThe property [%s] is wrong.\n", inputs[2].toUpperCase());
                    System.out.printf("Available properties: %s\n", Arrays.toString(Property.values()));
                    return false;
                }
            }

        }

        if (inputs.length > 3) {
            List<Property> properties = new ArrayList<>();
            List<Property> excludeProperties = new ArrayList<>();
            List<String> invalidProperties = new ArrayList<>();

            for (int i = 2; i < inputs.length; i++) {
                try {
                    properties.add(Property.valueOf(inputs[i].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    try {
                        excludeProperties.add(Property.valueOf(inputs[i].replace("-","").toUpperCase()));
                    } catch (IllegalArgumentException ee){
                        invalidProperties.add(inputs[i].toUpperCase());
                    }

                }
            }

            if (!invalidProperties.isEmpty()) {
                if (invalidProperties.size() == 1) {
                    System.out.printf("\nThe property %s is wrong.\n", invalidProperties.get(0));
                } else {
                    System.out.printf("\nThe properties %s are wrong.\n", invalidProperties);
                }
                System.out.printf("Available properties: %s\n", Arrays.toString(Property.values()));
                return false;
            }

            for (int i = 0; i < properties.size(); i++) {
                for (int j = i + 1; j < properties.size(); j++) {
                    if (Property.isMutuallyExclusive(properties.get(i), properties.get(j))) {
                        System.out.printf(
                                "\nThe request contains mutually exclusive properties: [%s, %s]\n",
                                properties.get(i),
                                properties.get(j)
                        );
                        System.out.println("There are no numbers with these properties");
                        return false;
                    }

                }

                for (Property property : excludeProperties) {
                    if (properties.contains(property)){
                        System.out.printf(
                                "\nThe request contains mutually exclusive properties: [%s, %s]\n",
                                "-" + property,
                                property
                        );
                        System.out.println("There are no numbers with these properties");
                        return false;
                    }

                }
            }

            for (int i = 0; i < excludeProperties.size(); i++) {
                for (int j = i + 1; j < excludeProperties.size(); j++) {
                    if (Property.isNegativeMutuallyExclusive(excludeProperties.get(i), excludeProperties.get(j))) {
                        System.out.printf(
                                "\nThe request contains mutually exclusive properties: [%s, %s]\n",
                                "-" + excludeProperties.get(i),
                                "-" + excludeProperties.get(j)
                        );
                        System.out.println("There are no numbers with these properties");
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
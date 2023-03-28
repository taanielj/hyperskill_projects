package machine;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

class CoffeeMachine {

    int water;
    int milk;
    int beans;
    int cups;
    int money;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine machine = new CoffeeMachine();
        machine.initMachine (400, 540,120,9,550);

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            try {
                Action action = Action.valueOf(scanner.nextLine().toUpperCase());
                if (action == Action.EXIT) break;
                machine.performAction(action);
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid input\n");
            } catch (NoSuchElementException e) {
                System.out.println("No input provided, exiting");
                break;
            }
        }


    }
    private void performAction (Action action) {
        switch (action) {
            case BUY -> buyCoffee();
            case FILL -> fillMachine();
            case TAKE -> takeMoney();
            case REMAINING -> printMachineStatus();
        }
    }
    public void buyCoffee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nWhat do you want to buy? ");

        // Print the available coffee types
        int i = 1;
        for (Coffee coffee : Coffee.values()) {
            System.out.printf("%d - %s ($%d), ", i++, coffee.name().toLowerCase(), coffee.getPrice());
        }
        System.out.println("back - to main menu");

        // Get the user's choice
        String userChoice = scanner.nextLine();
        if (userChoice.equals("back")) return;

        try {
            // Convert the user's choice to an integer
            int choice = Integer.parseInt(userChoice);

            // Get the coffee corresponding to the user's choice
            Coffee coffee = Coffee.values()[choice - 1];

            // Check if the machine has enough ingredients to make the coffee
            if (coffee.getWater() > water) {
                System.out.println("Sorry, not enough water");
                return;
            }
            if (coffee.getMilk() > milk) {
                System.out.println("Sorry, not enough milk");
                return;
            }
            if (coffee.getBeans() > beans) {
                System.out.println("Sorry, not enough beans");
                return;
            }
            if (cups <= 0) {
                System.out.println("Sorry, not enough cups");
                return;
            }

            // Make the coffee
            System.out.println("I have enough resources, making you a " + coffee.name().toLowerCase() + "!");
            water -= coffee.getWater();
            milk -= coffee.getMilk();
            beans -= coffee.getBeans();
            cups--;
            money += coffee.getPrice();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("\nInvalid input");
        }
    }

    public void initMachine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
    }

    public void fillMachine() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nWrite how many ml of water you want to add:");
            this.water += scanner.nextInt();
            System.out.println("Write how many ml of milk you want to add:");
            this.milk += scanner.nextInt();
            System.out.println("Write how many grams of coffee beans you want to add:");
            this.beans += scanner.nextInt();
            System.out.println("Write how many disposable cups you want to add: ");
            this.cups += scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scanner.nextLine();
        }
    }

    public void takeMoney() {
        System.out.printf("\nI gave you $%d\n", money);
        this.money = 0;
    }


    public void printMachineStatus() {
        System.out.printf("""
                \nThe coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                """,
                water, milk, beans, cups, money);
    }



}

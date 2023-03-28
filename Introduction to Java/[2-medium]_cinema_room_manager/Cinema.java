package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {
    static Scanner scanner = new Scanner(System.in);
    static public int rows;
    static public int seats;
    static public int customerRow;
    static public int customerSeat;
    static int purchasedTickets = 0;
    static int incomeCurrent = 0;
    static int incomeTotal;
    static String[][] seatPlan = initSeats();
    public static void main(String[] args) {
        mainMenu();
    }
    static String[][] initSeats() {
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        String[][] plan = new String[rows + 1][seats + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                if (i == 0 && j == 0) {
                    plan[i][j] = " ";
                } else if (i == 0) {
                    plan[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    plan[i][j] = String.valueOf(i);
                } else {
                    plan[i][j] = "S";
                }
            }
        }
        return plan;
    }
    static void mainMenu() {
        while (true) {
            System.out.println("""
                    
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit""");
            int userInput = scanner.nextInt();
            switch (userInput) {
                case 1 -> showSeats(rows, seats);
                case 2 -> {
                    int ticketPrice = buyTicket(rows, seats);
                    incomeCurrent += ticketPrice;
                    purchasedTickets += 1;
                    System.out.println("\nTicket price: $" + ticketPrice);
                }
                case 3 -> showStats();
                case 0 -> {
                    return;
                }
            }
        }
    }


    static void showSeats(int rows, int seats) {
        System.out.print("\nCinema:\n");
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                System.out.print(seatPlan[i][j] + " ");
            }
            System.out.println();
        }

    }


    static int buyTicket (int rows, int seats) {
        while(true) {
            System.out.println("\nEnter a row number:");
            customerRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            customerSeat = scanner.nextInt();

            if(customerRow <= rows && customerSeat <= seats) {
                if (Objects.equals(seatPlan[customerRow][customerSeat], "S")) {
                    seatPlan[customerRow][customerSeat] = "B";
                    int totalSeats = rows * seats;
                    int ticketPriceFront;
                    int ticketPriceBack;
                    int frontRows = rows/2;
                    if (totalSeats <= 60) {
                        ticketPriceBack = 10;
                    } else {
                        ticketPriceBack = 8;
                    }
                    ticketPriceFront = 10;

                    incomeTotal = ticketPriceFront * frontRows * seats + ticketPriceBack * (rows - frontRows) * seats;

                    if (customerRow <= frontRows) {
                        return ticketPriceFront;
                    } else {
                        return ticketPriceBack;
                    }
                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    static void showStats() {
        int totalSeats = rows * seats;
        int ticketPriceFront;
        int ticketPriceBack;
        int frontRows = rows/2;
        if (totalSeats <= 60) {
            ticketPriceBack = 10;
        } else {
            ticketPriceBack = 8;
        }
        ticketPriceFront = 10;

        incomeTotal = ticketPriceFront * frontRows * seats + ticketPriceBack * (rows - frontRows) * seats;
        float percentage = (float) purchasedTickets / (totalSeats)*100;
        String stats = String.format(
                """
                
                Number of purchased tickets: %d
                Percentage %.2f%%
                Current income: $%d
                Total income: $%d""", purchasedTickets, percentage, incomeCurrent, incomeTotal);
        System.out.println(stats);
    }

}






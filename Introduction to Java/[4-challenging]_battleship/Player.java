package battleship;

public class Player {
    private Board board;
    private String name;
    private static int playerCount = 0;

    Player() {
        this.name = "Player " + ++playerCount;
        board = new Board();
    }

    Player(String name) {
        this.name = name;
        board = new Board();
    }


    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;


    }

    public static int getPlayerCount() {
        return playerCount;
    }
}
package battleship;

public enum Ship {
    CARRIER("Aircraft Carrier", 5, 'C'),
    BATTLESHIP("Battleship", 4, 'B'),
    SUBMARINE("Submarine", 3, 'S'),
    CRUISER("Cruiser", 3, 'R'),
    DESTROYER("Destroyer", 2, 'D');

    final private String name;
    final private int length;
    final private char legend;
    private int health;

    Ship (String name, int length, char legend) {
        this.name = name;
        this.length = length;
        this.legend = legend;
        this.health = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public char getLegend() {
        return legend;
    }
}

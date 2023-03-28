package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
import java.util.Scanner;


public class Player {
    String name;
    int score;
    //ArrayList<String> Players = new ArrayList<String>();



    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }



}

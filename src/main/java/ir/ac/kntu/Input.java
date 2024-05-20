package ir.ac.kntu;

import java.util.Scanner;

public class Input {
    private static Scanner scn;

    public static String inputNextLine(){
        scn = new Scanner(System.in);
        return scn.nextLine().trim();
    }

    public static int inputNextInt(){
        scn = new Scanner(System.in);
        return scn.nextInt();
    }
}

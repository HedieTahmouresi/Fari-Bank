package ir.ac.kntu;

import java.util.ArrayList;

public class Menu {
    public static void mainMenu(NeoBank neoBank){
        System.out.println("Welcome to Fery Bank!");
        System.out.println("Please Enter your role!");
        System.out.println("   1.Simple User");
        System.out.println("   2.Admin");
        String answer;
        do {
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Simple User":
                    userMenu(neoBank);
                    break;
                case "2":
                    adminMenu(neoBank);
                    break;
                case "Admin":
                    //login
                    break;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER ROLE! Please retry!");
                        break;
                    }
            }
        }while(!answer.equalsIgnoreCase("quit"));
        System.out.println("Thanks for using our bank!");
    }

    public static void userMenu(NeoBank neoBank){
        System.out.println("Would you like to SignUp or SignIn?");
        System.out.println("   1.Sign Up");
        System.out.println("   2.Sign In");
        System.out.println("   3.Return");
        String answer;
        do {
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Sign Up":
                    //sign up
                    break;
                case "2", "Sign In":
                    //sign in
                    break;
                case "3", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    }
            }
        }while(!answer.equalsIgnoreCase("quit"));
    }

    public static void adminMenu(NeoBank neoBank){
        System.out.println("What would you like to do?");
        System.out.println("   1. Sign In");
        System.out.println("   2. Return");
        String answer;
        do {
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Sign In":
                    //sign in
                    break;
                case "2", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    }
            }
        }while(!answer.equalsIgnoreCase("quit"));
    }
}

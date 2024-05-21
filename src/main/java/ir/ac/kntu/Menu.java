package ir.ac.kntu;

import java.util.ArrayList;

public class Menu {
    public static void mainMenu(NeoBank neoBank){
        System.out.println("Welcome to Fery Bank!");
        String answer;
        do {
            System.out.println("Please Enter your role!");
            System.out.println("   1.Simple User");
            System.out.println("   2.Admin");
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Simple User":
                    userMenu(neoBank);
                    break;
                case "2", "Admin":
                    adminMenu(neoBank);
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
        String answer;
        do {
            System.out.println("Would you like to SignUp or SignIn?");
            System.out.println("   1.Sign Up");
            System.out.println("   2.Sign In");
            System.out.println("   3.Return");
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Sign Up":
                    boolean signUpSuccess = SimpleUser.signUp(neoBank.getSimpleUsers(), neoBank.getData());
                    if (signUpSuccess){
                        System.out.println("Here is your account ID");
                        System.out.println(neoBank.getSimpleUsers().get(neoBank.getSimpleUsers().size()-1).getAccount().getAccountId());
                        System.out.println("Here is your credit card ID");
                        System.out.println(neoBank.getSimpleUsers().get(neoBank.getSimpleUsers().size()-1).getAccount().getCreditCard().getCreditCardId());
                        System.out.println();
                        System.out.println();
                    }
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
                    } else{
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
            }
        }while(!answer.equalsIgnoreCase("quit"));
    }

    public static void adminMenu(NeoBank neoBank){
        System.out.println("What would you like to do?");
        String answer;
        do {
            System.out.println("   1. Sign In");
            System.out.println("   2. Return");
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

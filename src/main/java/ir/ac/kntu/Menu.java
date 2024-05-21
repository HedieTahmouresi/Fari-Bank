package ir.ac.kntu;


public class Menu {
    public static void mainMenu(NeoBank neoBank) {
        System.out.println("Welcome to Fery Bank!");
        String answer;
        do {
            displayMainMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Simple User":
                    userMenu(neoBank);
                    break;
                case "2", "Admin":
                    adminMenu(neoBank);
                    break;
                default:
                    if (!"quit".equalsIgnoreCase(answer)) {
                        System.out.println("THERE IS NO OTHER ROLE! Please retry!");
                        break;
                    }
            }
        } while (!"quit".equalsIgnoreCase(answer));
        System.out.println("Thanks for using our bank!");
    }

    public static void displayMainMenu(){
        System.out.println("Please Enter your role!");
        System.out.println("   1.Simple User");
        System.out.println("   2.Admin");
    }

    public static void userMenu(NeoBank neoBank) {
        String answer;
        do {
            displayUsersMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Sign Up":
                    SimpleUser.signUp(neoBank,neoBank.getData());
                    break;
                case "2", "Sign In":
                    SimpleUser currentUser =  SimpleUser.signIn(neoBank, neoBank.getData());
                    if (currentUser!=null) {
                        System.out.println("You have successfully signed in!\n\n");
                        if (currentUser.isAuthenticated()){
                            serviceMenu(neoBank);
                        } else {
                            displayServiceMenu();
                            System.out.println("You can't choose any because you have not been authenticated!");
                        }
                    } else{
                        System.out.println("haha!");
                    }
                    break;
                case "3", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
            }
        } while (!answer.equalsIgnoreCase("return"));
    }

    public static void  displayUsersMenu(){
        System.out.println("Would you like to SignUp or SignIn?");
        System.out.println("   1.Sign Up");
        System.out.println("   2.Sign In");
        System.out.println("   3.Return");
    }

    public static void adminMenu(NeoBank neoBank) {
        String answer;
        do {
            displayAdminMenu();
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
                    } else{
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                    }
            }
        } while (!answer.equalsIgnoreCase("return"));
    }

    public static void displayAdminMenu(){
        System.out.println("What would you like to do?");
        System.out.println("   1. Sign In");
        System.out.println("   2. Return");
    }

    public static void serviceMenu(NeoBank neoBank){
        String answer;
        do {
            displayServiceMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Account Management":
                    //account management
                    break;
                case "2", "Contacts":
                    //contacts
                    break;
                case "3", "Transferring Money":
                    //transfer
                    break;
                case "4", "Administer":
                    //backup
                    break;
                case "5", "Settings":
                    //settings
                    break;
                case "6", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else{
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                    }
            }
        } while (!answer.equalsIgnoreCase("return"));
    }

    public static void displayServiceMenu(){
        System.out.println("Choose : ");
        System.out.println("   1.Account Management");
        System.out.println("   2.Contacts");
        System.out.println("   3.Transferring Money");
        System.out.println("   4.Administer");
        System.out.println("   5.Settings");
        System.out.println("   6.Return");
    }
}

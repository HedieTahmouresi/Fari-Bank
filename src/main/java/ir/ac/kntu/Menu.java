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
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
        System.out.println("Thanks for trusting our bank! Bye Bye!");
        System.exit(0);
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
                            serviceUserMenu(neoBank, currentUser);
                        } else {
                            displayServiceUserMenu();
                            System.out.println("You can't choose any because you have not been authenticated!");
                            System.out.println("Would you like to edit your info?");
                            System.out.println("   1.yes");
                            System.out.println("   2.no");
                            String input = Input.inputNextLine();
                            switch (input){
                                case "1", "yes":
                                    currentUser.editSignUpInfo(neoBank);
                                    break;
                                case "2", "no":
                                    break;
                                default:
                                    if("return".equalsIgnoreCase(input)){
                                        return;
                                    } else if("quit".equalsIgnoreCase(input)){
                                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                                        System.exit(0);
                                    } else{
                                        System.out.println("Wrong input!");
                                    }
                                    break;
                            }
                        }
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
                    Admin currAdmin = Admin.signIn(neoBank);
                    if (currAdmin != null){
                        serviceAdminMenu(neoBank);
                    }
                    break;
                case "2", "Return":
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
        } while (!answer.equalsIgnoreCase("return"));
    }

    public static void displayAdminMenu(){
        System.out.println("What would you like to do?");
        System.out.println("   1. Sign In");
        System.out.println("   2. Return");
    }

    public static void serviceUserMenu(NeoBank neoBank, SimpleUser user){
        String answer;
        do {
            displayServiceUserMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Account Management":
                    managementMenu(user, neoBank);
                    break;
                case "2", "Contacts":
                    contactMenu(neoBank, user);
                    break;
                case "3", "Transferring Money":
                    user.getAccount().transferMoney(neoBank);
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
                        System.exit(0);
                    }
            }
        } while (!answer.equalsIgnoreCase("return"));
    }

    public static void displayServiceUserMenu(){
        System.out.println("Choose : ");
        System.out.println("   1.Account Management");
        System.out.println("   2.Contacts");
        System.out.println("   3.Transferring Money");
        System.out.println("   4.Administer");
        System.out.println("   5.Settings");
        System.out.println("   6.Return");
    }

    public static void serviceAdminMenu(NeoBank neoBank){
        String answer;
        do {
            displayServiceAdminMenu();
            answer = Input.inputNextLine();
            switch(answer){
                case "1", "Authentications":
                    neoBank.getData().selectUser(neoBank);
                    break;
                case "2", "Requests":
                    //list of requests
                    break;
                case "3", "Users":
                    //list of users
                    break;
                case "4", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else{
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        }while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayServiceAdminMenu(){
        System.out.println("How would you like to assist!");
        System.out.println("   1.Authentications");
        System.out.println("   2.Requests");
        System.out.println("   3.Users");
        System.out.println("   4.Return");
    }

    public static void managementMenu(SimpleUser user, NeoBank neoBank){
        String answer;
        do {
            displayManagementMenu();
            answer = Input.inputNextLine();
            switch(answer){
                case "1", "Charge Account":
                    user.getAccount().charge(neoBank);
                    break;
                case "2", "Check Balance":
                    user.getAccount().showBalance();
                    break;
                case "3", "See Transactions":
                    user.getAccount().selectTransaction(neoBank);
                    break;
                case "4", "Show Account info":
                    user.getAccount().showAccountInfo();
                case "5", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else{
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        }while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayManagementMenu(){
        System.out.println("What do you want to do?");
        System.out.println("   1.Charge Account");
        System.out.println("   2.Check Balance");
        System.out.println("   3.See Transactions");
        System.out.println("   4.Show Account info");
        System.out.println("   5.Return");
    }

    public static void contactMenu(NeoBank neoBank, SimpleUser user){
        String answer;
        do {
            displayContactMenu();
            answer = Input.inputNextLine();
            switch(answer){
                case "1", "Add Contact":
                    Contact.addContacts(neoBank, user);
                    break;
                case "2", "Show Contact list":
                    Contact currContact = Contact.selectContact(neoBank, user);
                    if(currContact!=null){
                        currContact.showInfo();
                        currContact.changeContactInfo(neoBank, user);
                        currContact.deleteContact(user);
                    }
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
                    break;
            }
        }while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayContactMenu(){
        System.out.println("What do you want to do?");
        System.out.println("   1.Add Contact");
        System.out.println("   2.Show Contact list");
        System.out.println("   3.Return");
    }
}
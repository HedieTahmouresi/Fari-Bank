package ir.ac.kntu;


public class Menu {
    public static void mainMenu(NeoBank neoBank) {
        System.out.println(ColorConsole.PURPLE + "Welcome to Fery Bank!" + ColorConsole.RESET);
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
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER ROLE! Please retry!" + ColorConsole.RESET);
                        break;
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
        System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
        System.exit(0);
    }

    public static void displayMainMenu() {
        System.out.println(ColorConsole.CYAN + "Please Enter your role!");
        System.out.println("   1.Simple User");
        System.out.println("   2.Admin" + ColorConsole.RESET);
    }

    public static void userMenu(NeoBank neoBank) {
        String answer;
        do {
            displayUsersMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Sign Up":
                    SimpleUser.signUp(neoBank, neoBank.getData());
                    break;
                case "2", "Sign In":
                    SimpleUser currentUser = SimpleUser.signIn(neoBank, neoBank.getData());
                    signInMenu(currentUser, neoBank);
                    break;
                case "3", "Return":
                    return;
                default:
                    if (!"quit".equalsIgnoreCase(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    } else {
                        System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                        System.exit(0);
                    }
            }
        } while (!"return".equalsIgnoreCase(answer));
    }

    public static void displayUsersMenu() {
        System.out.println(ColorConsole.CYAN + "Would you like to SignUp or SignIn?");
        System.out.println("   1.Sign Up");
        System.out.println("   2.Sign In");
        System.out.println("   3.Return" + ColorConsole.RESET);
    }

    public static void signInMenu(SimpleUser currentUser, NeoBank neoBank) {
        if (currentUser == null) {
            return;
        }
        System.out.println(ColorConsole.GREEN + "You have successfully signed in!\n" + ColorConsole.RESET);
        if (currentUser.isAuthenticated()) {
            serviceUserMenu(neoBank, currentUser);
            return;
        }
        System.out.println(ColorConsole.RED + "You can't use any of our services because you have not been authenticated!" + ColorConsole.RESET);
        System.out.println(ColorConsole.RED + "The reason : " + ColorConsole.YELLOW + currentUser.getAuthenticated().getAnswer() + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "Would you like to edit your info?\n   1.yes\n   2.no " + ColorConsole.RESET);
        String input = Input.inputNextLine();
        switch (input) {
            case "1", "yes":
                currentUser.editSignUpInfo(neoBank);
                break;
            case "2", "no":
                break;
            default:
                if (Input.checkInput(input)) {
                    System.out.println(ColorConsole.RED + "Wrong input!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
    }

    public static void adminMenu(NeoBank neoBank) {
        String answer;
        do {
            displayAdminMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Sign In":
                    Admin currAdmin = Admin.signIn(neoBank);
                    if (currAdmin != null) {
                        serviceAdminMenu(neoBank);
                    }
                    break;
                case "2", "Return":
                    return;
                default:
                    if (!"quit".equalsIgnoreCase(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    } else {
                        System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                        System.exit(0);
                    }
            }
        } while (!"return".equalsIgnoreCase(answer));
    }

    public static void displayAdminMenu() {
        System.out.println(ColorConsole.CYAN + "What would you like to do?");
        System.out.println("   1. Sign In");
        System.out.println("   2. Return" + ColorConsole.RESET);
    }

    public static void serviceUserMenu(NeoBank neoBank, SimpleUser user) {
        String answer = displayServiceUserMenu();
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
                administerMenu(neoBank, user);
                break;
            case "5", "Settings":
                settingsMenu(user);
                break;
            case "6", "Return":
                return;
            default:
                if (Input.checkInput(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                }
        }
        serviceUserMenu(neoBank, user);
    }

    public static String displayServiceUserMenu() {
        System.out.println(ColorConsole.CYAN + "Choose : ");
        System.out.println("   1.Account Management");
        System.out.println("   2.Contacts");
        System.out.println("   3.Transferring Money");
        System.out.println("   4.Administer");
        System.out.println("   5.Settings");
        System.out.println("   6.Return" + ColorConsole.RESET);
        return Input.inputNextLine();
    }

    public static void serviceAdminMenu(NeoBank neoBank) {
        String answer;
        do {
            displayServiceAdminMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Authentications":
                    neoBank.getData().selectUser(neoBank);
                    break;
                case "2", "Requests":
                    neoBank.getData().showRequest(neoBank);
                    break;
                case "3", "Users":
                    Admin.searchUsers(neoBank);
                    break;
                case "4", "Return":
                    return;
                default:
                    if (Input.checkInput(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayServiceAdminMenu() {
        System.out.println(ColorConsole.CYAN + "How would you like to assist!");
        System.out.println("   1.Authentications");
        System.out.println("   2.Requests");
        System.out.println("   3.Users");
        System.out.println("   4.Return" + ColorConsole.RESET);
    }

    public static void managementMenu(SimpleUser user, NeoBank neoBank) {
        String answer;
        do {
            displayManagementMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Charge Account":
                    user.getAccount().charge(neoBank);
                    break;
                case "2", "Check Balance":
                    user.getAccount().showBalance();
                    break;
                case "3", "See Transactions":
                    transactionsMenu(neoBank, user);
                    break;
                case "4", "Return":
                    return;
                default:
                    if (Input.checkInput(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayManagementMenu() {
        System.out.println(ColorConsole.CYAN + "What do you want to do?");
        System.out.println("   1.Charge Account");
        System.out.println("   2.Check Balance");
        System.out.println("   3.See Transactions");
        System.out.println("   4.Return" + ColorConsole.RESET);
    }

    public static void transactionsMenu(NeoBank neoBank, SimpleUser user) {
        String answer;
        do {
            System.out.println(ColorConsole.CYAN + "Choose : \n   1.All Transactions\n   2.Filter Transactions\n   3.Return" + ColorConsole.RESET);
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "All Transactions":
                    user.getAccount().selectTransaction(neoBank, "1");
                    break;
                case "2", "Filter Transactions":
                    user.getAccount().selectTransaction(neoBank, "2");
                    break;
                case "3", "Return":
                    return;
                default:
                    if (Input.checkInput(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public static void contactMenu(NeoBank neoBank, SimpleUser user) {
        if (!user.isContactOption()) {
            System.out.println(ColorConsole.RED + "You can't choose this option" + ColorConsole.RESET);
            return;
        }
        String answer;
        do {
            displayContactMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Add Contact":
                    Contact.addContacts(neoBank, user);
                    break;
                case "2", "Show Contact list":
                    Contact currContact = Contact.selectContact(neoBank, user);
                    if (currContact != null) {
                        currContact.doContactStuff(neoBank, user);
                    }
                    break;
                case "3", "Return":
                    return;
                default:
                    if (Input.checkInput(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayContactMenu() {
        System.out.println(ColorConsole.CYAN + "What do you want to do?");
        System.out.println("   1.Add Contact");
        System.out.println("   2.Show Contact list");
        System.out.println("   3.Return" + ColorConsole.RESET);
    }

    public static void administerMenu(NeoBank neoBank, SimpleUser user) {
        String answer;
        do {
            displayAdministerMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Add Request":
                    Request.createRequest(neoBank, user);
                    break;
                case "2", "Show Request list":
                    Request.selectRequest(neoBank, user);
                    break;
                case "3", "Return":
                    return;
                default:
                    if (!"quit".equalsIgnoreCase(answer)) {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    } else {
                        System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                        System.exit(0);
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public static void displayAdministerMenu() {
        System.out.println(ColorConsole.CYAN + "How can we help you?");
        System.out.println("   1. Add Request");
        System.out.println("   2. Show Request list");
        System.out.println("   3. Return" + ColorConsole.RESET);
    }

    public static void settingsMenu(SimpleUser user) {
        String answer = Input.inputNextLine();
        displaySettings();
        switch (answer) {
            case "1", "Change User Password":
                user.changePassword();
                break;
            case "2", "Set Credit Card Password":
                user.getAccount().getCreditCard().changeCreditCardPassword();
                break;
            case "3", "Contact Option":
                user.changeContactOption();
                break;
            case "4", "Show Account info":
                user.getAccount().showAccountInfo();
                break;
            case "5", "Return":
                return;
            default:
                if (Input.checkInput(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                }
                break;
        }
        settingsMenu(user);
    }

    public static void displaySettings() {
        System.out.println(ColorConsole.CYAN + "What do you want to do?");
        System.out.println("   1. Change User Password");
        System.out.println("   2. Set Credit Card Password");
        System.out.println("   3. Contact Option");
        System.out.println("   4.Show Account info");
        System.out.println("   5. Return" + ColorConsole.RESET);
    }


}
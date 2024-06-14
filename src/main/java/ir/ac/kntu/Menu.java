package ir.ac.kntu;

public class Menu {

    private final Input input = new Input();

    private final String main = ColorConsole.CYAN_BOLD + "Choose your role : \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Simple User\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Admin\n" + ColorConsole.RESET;

    private final String userLog = ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Sign In\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Sign Up\n" + ColorConsole.RESET;

    private final String adminLog = ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Sign In\n" + ColorConsole.RESET;

    private final String adminServiceMenu = ColorConsole.CYAN_BOLD + "How do you want to assist? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Authentications\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Requests\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "Users\n" +
            ColorConsole.PINK + "4. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;

    private final String userServiceMenu = ColorConsole.CYAN_BOLD + "How can we help you? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Account Management\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Contacts\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "Transferring Money\n" +
            ColorConsole.PINK + "4. " + ColorConsole.CYAN + "Administer\n" +
            ColorConsole.PINK + "5. " + ColorConsole.CYAN + "Settings\n" +
            ColorConsole.PINK + "6. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;

    private final String managementMenu = ColorConsole.CYAN_BOLD + "How can we help you? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Charge Account\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Check Balance\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "See Transactions\n" +
            ColorConsole.PINK + "4. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;

    private final String contactMenu = ColorConsole.CYAN_BOLD + "What do you want to do? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Add Contact\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Show Contact List\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;

    private final String transferMenu = ColorConsole.CYAN_BOLD + "How would you like to transfer money? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "by Account ID\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "by Contact\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "by Recent List\n" +
            ColorConsole.PINK + "4. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;

    private final String administerMenu = ColorConsole.CYAN_BOLD + "How can we help you? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Add Request\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Show Request List\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;

    private final String settings = ColorConsole.CYAN_BOLD + "What do you want to do? \n" +
            ColorConsole.PINK + "1. " + ColorConsole.CYAN + "Change User Password\n" +
            ColorConsole.PINK + "2. " + ColorConsole.CYAN + "Set/Change Credit Card Password\n" +
            ColorConsole.PINK + "3. " + ColorConsole.CYAN + "Contact Option\n" +
            ColorConsole.PINK + "4. " + ColorConsole.CYAN + "Show Account info\n" +
            ColorConsole.PINK + "5. " + ColorConsole.CYAN + "Return\n" + ColorConsole.RESET;


    public void mainMenu(NeoBank neoBank) {
        System.out.println(main);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Simple User":
                this.userLog(neoBank);
                break;
            case "2", "Admin":
                this.adminLog(neoBank);
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other roles." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
        this.mainMenu(neoBank);
    }

    public void userLog(NeoBank neoBank) {
        System.out.println(userLog);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Sign In":
                SimpleUser currentUser = neoBank.getBankData().signInUser();
                if (currentUser != null && currentUser.getAuthenticated().isAuthenticated()) {
                    this.userService(currentUser, neoBank);
                } else if (currentUser != null) {
                    currentUser.getAuthenticated().showRejection();
                    currentUser.changeInfo(neoBank);
                }
                break;
            case "2", "Sign Up":
                neoBank.getBankData().signUp();
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other options on the menu." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
        this.userLog(neoBank);
    }

    public void adminLog(NeoBank neoBank) {
        System.out.println(adminLog);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Sign In":
                Admin currentAdmin = neoBank.signInAdmin();
                if (currentAdmin != null) {
                    this.adminService(currentAdmin, neoBank);
                }
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other options on the menu." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
        this.adminLog(neoBank);
    }

    public void userService(SimpleUser currentUser, NeoBank neoBank) {
        System.out.println(userServiceMenu);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Account Management":
                managementMenu(neoBank, currentUser);
                break;
            case "2", "Contacts":
                contactMenu(neoBank, currentUser);
                break;
            case "3", "Transferring Money":
                transferMenu(neoBank, currentUser);
                break;
            case "4", "Administer":
                administerMenu(neoBank, currentUser);
                break;
            case "5", "Settings":
                settingsMenu(neoBank, currentUser);
                break;
            default:
                if (!input.exitPoint(answer) || "6".equalsIgnoreCase(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
        this.userService(currentUser, neoBank);
    }

    public void adminService(Admin currentAdmin, NeoBank neoBank) {
        System.out.println(adminServiceMenu);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Authentications":
                currentAdmin.getData().showAuthentications(neoBank, currentAdmin);
                break;
            case "2", "Requests":
                currentAdmin.showRequest(neoBank);
                break;
            case "3", "Users":
                currentAdmin.searchUsers(neoBank);
                break;
            case "4", "Return":
                return;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                } else {
                    return;
                }
        }
        this.adminService(currentAdmin, neoBank);
    }

    public void managementMenu(NeoBank neoBank, SimpleUser currentUser) {
        System.out.println(managementMenu);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Charge Account":
                currentUser.getAccount().chargeAccount(neoBank);
                break;
            case "2", "Check Balance":
                currentUser.getAccount().showBalance();
                break;
            case "3", "See Transactions":
                currentUser.getAccount().seeTransactions(neoBank);
                break;
            case "4", "Return":
                return;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                } else {
                    return;
                }
        }
        this.managementMenu(neoBank, currentUser);
    }

    public void contactMenu(NeoBank neoBank, SimpleUser currentUser) {
        if (!currentUser.isContactOption()) {
            System.out.println(ColorConsole.RED + "You can't choose this option" + ColorConsole.RESET);
            return;
        }
        System.out.println(contactMenu);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Add Contact":
                input.nextContact(neoBank, currentUser);
                break;
            case "2", "Show Contact List":
                Contact currentContact = currentUser.showContacts(neoBank);
                if (currentContact!=null){
                    currentContact.contactListOptions(neoBank,currentUser);
                }
                break;
            case "3", "Return":
                return;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                } else {
                    return;
                }
        }
        this.contactMenu(neoBank, currentUser);
    }

    public void transferMenu(NeoBank neoBank, SimpleUser currentUser) {
        System.out.println(transferMenu);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "by Account ID":
                currentUser.transferByAccountID(neoBank);
                break;
            case "2", "by Contact":
                if (currentUser.isContactOption()) {
                    currentUser.transferByContact(neoBank);
                } else {
                    System.out.println(ColorConsole.RED + "You can't choose this option! You have turned off your contact option!" + ColorConsole.RESET);
                }
                break;
            case "3", "by Recent List":
                currentUser.transferByRecent(neoBank);
                break;
            case "4", "Return":
                return;
            default:
                if (!input.exitPoint(answer)) {
                    return;
                } else {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                }
        }
        this.transferMenu(neoBank, currentUser);
    }

    public void administerMenu(NeoBank neoBank, SimpleUser currentUser) {
        System.out.println(administerMenu);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Add Request":
                currentUser.addRequest(neoBank);
                break;
            case "2", "Show Request list":
                currentUser.showRequests();
                break;
            case "3", "Return":
                return;
            default:
                if (!input.exitPoint(answer)) {
                    return;
                } else {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                }
        }
        this.administerMenu(neoBank, currentUser);
    }

    public void settingsMenu(NeoBank neoBank, SimpleUser currentUser) {
        System.out.println(settings);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Change User Password":
                currentUser.changePassword();
                break;
            case "2", "Set Credit Card Password":
                currentUser.changeOrSetPassCode();
                break;
            case "3", "Contact Option":
                currentUser.changeContactOption();
                break;
            case "4", "Show Account info":
                currentUser.showAccountInfo();
                break;
            case "5", "Return":
                return;
            default:
                if (input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
        this.settingsMenu(neoBank, currentUser);
    }


}

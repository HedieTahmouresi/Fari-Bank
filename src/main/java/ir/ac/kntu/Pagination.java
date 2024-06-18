package ir.ac.kntu;

import java.util.List;

public class Pagination<T> {
    private int pageSize;
    private int currentPage;
    private List<T> list;

    private final Input input = new Input();

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public Pagination(List<T> list, int pageSize) {
        this.list = list;
        setPageSize(pageSize);
        setCurrentPage(0);
    }

    public void showPage() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, list.size());
        for (int index = start; index < end; index++) {
            int num = index + 1;
            System.out.println(ColorConsole.PINK + num + " ." + ColorConsole.PURPLE + list.get(index).toString() + ColorConsole.PURPLE);
        }
    }

    public void changePage(String command) {
        if ("next".equalsIgnoreCase(command) && (currentPage + 1) * pageSize < list.size()) {
            currentPage++;
        } else if ("previous".equalsIgnoreCase(command) && currentPage > 0) {
            currentPage--;
        } else if ("next".equalsIgnoreCase(command)) {
            System.out.println(ColorConsole.RED + "No more page!This is the last Page!" + ColorConsole.RESET);
        } else if ("previous".equalsIgnoreCase(command)) {
            System.out.println(ColorConsole.RED + "No more page!This is the first Page!" + ColorConsole.RESET);
        }
    }

    public void selectUserLog(Menu menu, NeoBank neoBank, String answer, CentralBank centralBank) {
        switch (answer) {
            case "1", "Sign In":
                SimpleUser currentUser = neoBank.getBankData().signInUser();
                if (currentUser != null && currentUser.getAuthenticated().isAuthenticated()) {
                    menu.userService(currentUser, neoBank, centralBank);
                } else if (currentUser != null) {
                    currentUser.getAuthenticated().showRejection();
                    currentUser.changeInfo(neoBank);
                }
                break;
            case "2", "Sign Up":
                neoBank.getBankData().signUp(neoBank);
                break;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other options on the menu." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
    }

    public void selectMain(Menu menu, NeoBank neoBank, String answer, CentralBank centralBank) {
        switch (answer) {
            case "1", "Simple User"-> menu.userLog(neoBank, centralBank);
            case "2", "Admin" -> menu.adminLog(neoBank);
            case "3", "Manager" -> menu.managerLog(neoBank);
            case "next", "previous"-> this.changePage(answer);
            default-> System.out.println(ColorConsole.RED + "There are no other roles." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);


        }
    }

    public boolean selectUserService(Menu menu, NeoBank neoBank, SimpleUser currentUser, CentralBank centralBank) {
        String answer = input.nextLine();
        if (!input.exitPoint(answer) || "8".equalsIgnoreCase(answer)) {
            return false;
        }
        switch (answer) {
            case "1", "Account Management" -> menu.managementMenu(neoBank, currentUser);
            case "2", "Fund Management" -> menu.fundManagement(neoBank, currentUser);
            case "3", "Charge sim" -> menu.chargeSim(neoBank, currentUser);
            case "4", "Contacts" -> menu.contactMenu(neoBank, currentUser);
            case "5", "Transferring Money" -> menu.transferMenu(neoBank,centralBank, currentUser);
            case "6", "Administer" -> menu.administerMenu(neoBank, currentUser);
            case "7", "Settings" -> menu.settingsMenu(currentUser);
            case "next", "previous" -> this.changePage(answer);
            default ->
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
        return true;
    }

    public void selectAdminLog(Menu menu, NeoBank neoBank, String answer) {
        switch (answer) {
            case "1", "Sign In":
                Admin currentAdmin = neoBank.signInAdmin();
                if (currentAdmin != null) {
                    menu.adminService(currentAdmin, neoBank);
                }
                break;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other options on the menu." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
    }

    public void selectAdminService(NeoBank neoBank, Admin currentAdmin, String answer) {
        switch (answer) {
            case "1", "Requests":
                currentAdmin.showRequest(neoBank);
                break;
            case "2", "Users":
                currentAdmin.searchUsers(neoBank);
                break;
            case "3", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
    }

    public void selectAccountManagement(NeoBank neoBank, SimpleUser currentUser, String answer) {
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
            case "4", "See Sim Card Charge":
                currentUser.getSimCard().showCharge();
            case "5", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
    }

    public void selectSetting(SimpleUser currentUser, String answer) {
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
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
    }

    public void selectContactMenu(NeoBank neoBank, SimpleUser currentUser, String answer) {
        switch (answer) {
            case "1", "Add Contact":
                input.nextContact(neoBank, currentUser);
                break;
            case "2", "Show Contact List":
                Contact currentContact = currentUser.showContacts();
                if (currentContact != null) {
                    currentContact.contactListOptions(neoBank, currentUser);
                }
                break;
            case "3", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
    }

    public void selectTransfer(NeoBank neoBank, SimpleUser currentUser, String answer, CentralBank centralBank) {
        switch (answer) {
            case "1", "by Credit Card ID" :
                centralBank.transferByCard(neoBank, currentUser);
                break;
            case "2", "by Account ID":
                centralBank.transferByAccount(neoBank, currentUser);
                break;
            case "3", "by Contact":
                if (currentUser.isContactOption()) {
                    currentUser.transferByContact(neoBank);
                } else {
                    System.out.println(ColorConsole.RED + "You can't choose this option! You have turned off your contact option!" + ColorConsole.RESET);
                }
                break;
            case "4", "by Recent List":
                currentUser.transferByRecent(neoBank, centralBank);
                break;
            case "5", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
    }

    public void selectAdminister(NeoBank neoBank, SimpleUser currentUser, String answer) {
        switch (answer) {
            case "1", "Add Request":
                currentUser.addRequest(neoBank);
                break;
            case "2", "Show Request list":
                currentUser.showRequests(neoBank);
                break;
            case "3", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                break;

        }
    }

    public void selectFundManagement(NeoBank neoBank, SimpleUser currentUser, String answer) {
        switch (answer) {
            case "1", "Add Fund":
                currentUser.addFund(neoBank);
                break;
            case "2", "Show Funds":
                currentUser.showFunds(neoBank);
                break;
            case "3", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                break;

        }
    }

    public void selectChargeOption(NeoBank neoBank, SimpleUser currentUser, String answer) {
        switch (answer) {
            case "1", "My Own Sim Card":
                currentUser.getSimCard().chargeSimCard(neoBank, currentUser);
                break;
            case "2", "My Contacts":
                if (currentUser.isContactOption()) {
                    Contact currentContact = currentUser.showContacts();
                    neoBank.getManagerData().getSimCard(currentContact.getSimCard().getPhoneNumber()).chargeSimCard(neoBank, currentUser);
                } else {
                    System.out.println(ColorConsole.RED + "You can't choose this option! You have turned off your contact option!" + ColorConsole.RESET);
                }
                break;
            case "3", "Other":
                currentUser.chargeSimCard(neoBank);
                break;
            case "4", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
    }

    public void selectManagerLog(Menu menu, NeoBank neoBank, String answer){
        switch (answer) {
            case "1", "Sign In":
                Manager currentManager = neoBank.signInManager();
                if (currentManager != null) {
                    menu.managerService(currentManager, neoBank);
                }
                break;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other options on the menu." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
    }

    public void selectManagerService(NeoBank neoBank, String answer, Manager currentManager) {
        switch (answer) {
            case "1", "Settings":
                currentManager.settings(neoBank);
                break;
            case "2", "User Management":

                break;
            case "3", "Automatic Transactions":

                break;
            case "4", "Return":
                return;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
    }

    public void selectSettings(Manager manager, String answer, NeoBank neoBank) {
        switch (answer){
            case "1"-> manager.getData().changeFariWage();
            case "2"->manager.getData().changeChargeWage();
            case "3"->manager.getData().changeBonusPercentage();
            case "4"-> manager.getData().changeCardWage();
            case "5"->manager.getData().changeBridgeWage();
            case "6"->manager.getData().changeWireWage();

        }
    }
}

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
            int num = index+1;
            System.out.println(ColorConsole.PINK + num + " ." + ColorConsole.PURPLE + list.get(index).toString() + ColorConsole.PURPLE);
        }
    }

    public void changePage(String command) {
        if ("next".equalsIgnoreCase(command) && (currentPage + 1) * pageSize < list.size()) {
            currentPage++;
        } else if ("previous".equalsIgnoreCase(command) && currentPage > 0) {
            currentPage--;
        } else if ("next".equalsIgnoreCase(command)){
            System.out.println(ColorConsole.RED + "No more page!This is the last Page!" + ColorConsole.RESET);
        } else if ("previous".equalsIgnoreCase(command)){
            System.out.println(ColorConsole.RED + "No more page!This is the first Page!" + ColorConsole.RESET);
        }
    }

    public void selectUserLog(Menu menu,NeoBank neoBank, String answer){
        switch (answer) {
            case "1", "Sign In":
                SimpleUser currentUser = neoBank.getBankData().signInUser();
                if (currentUser != null && currentUser.getAuthenticated().isAuthenticated()) {
                    menu.userService(currentUser, neoBank);
                } else if (currentUser != null) {
                    currentUser.getAuthenticated().showRejection();
                    currentUser.changeInfo(neoBank);
                }
                break;
            case "2", "Sign Up":
                neoBank.getBankData().signUp();
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

    public void selectMain(Menu menu, NeoBank neoBank, String answer){
        switch (answer) {
            case "1", "Simple User":
                menu.userLog(neoBank);
                break;
            case "2", "Admin":
                menu.adminLog(neoBank);
                break;
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "There are no other roles." + ColorConsole.BLUE + " Please Try again!" + ColorConsole.RESET);
                } else {
                    return;
                }
                break;
        }
    }

    public void selectUserService(Menu menu, NeoBank neoBank, String answer, SimpleUser currentUser){
        switch (answer) {
            case "1", "Account Management"-> menu.managementMenu(neoBank, currentUser);
            case "2", "Fund Management" -> System.out.println("haha");
            case "3", "Contacts"-> menu.contactMenu(neoBank, currentUser);
            case "4", "Transferring Money"-> menu.transferMenu(neoBank, currentUser);
            case "5", "Administer"-> menu.administerMenu(neoBank, currentUser);
            case "6", "Settings"-> menu.settingsMenu(currentUser);
            case "next", "previous"-> this.changePage(answer);
            default-> System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
    }

    public void selectAdminLog(Menu menu, NeoBank neoBank, String answer){
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

    public void selectAdminService(Menu menu, NeoBank neoBank, Admin currentAdmin, String answer){
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
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
    }

    public void selectAccountManagement( NeoBank neoBank, SimpleUser currentUser, String answer){
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
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
    }

    public void selectSetting(SimpleUser currentUser, String answer){
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

    public void selectContactMenu(NeoBank neoBank, SimpleUser currentUser, String answer){
        switch (answer) {
            case "1", "Add Contact":
                input.nextContact(neoBank, currentUser);
                break;
            case "2", "Show Contact List":
                Contact currentContact = currentUser.showContacts(neoBank);
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

    public void selectTransfer(NeoBank neoBank, SimpleUser currentUser, String answer){
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
            case "next", "previous":
                this.changePage(answer);
                break;
            default:
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
    }

    public void selectAdminister(NeoBank neoBank, SimpleUser currentUser, String answer){
        switch (answer) {
            case "1", "Add Request":
                currentUser.addRequest(neoBank);
                break;
            case "2", "Show Request list":
                currentUser.showRequests();
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
}

package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private final Input input = new Input();

    private final List<String> main = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Simple User", ColorConsole.CYAN + "Admin" + ColorConsole.RESET, ColorConsole.CYAN + "Manager" + ColorConsole.RESET));

    private final List<String> userLog = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Sign In", ColorConsole.CYAN + "Sign Up" + ColorConsole.RESET));

    private final List<String> adminLog = new ArrayList<>(List.of(ColorConsole.CYAN + "Sign In"));

    private final List<String> adminServiceMenu = new ArrayList<>(Arrays.asList( ColorConsole.CYAN + "Requests" + ColorConsole.RESET, ColorConsole.CYAN + "Users", ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> userServiceMenu = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Account Management", ColorConsole.CYAN + "Fund management", ColorConsole.CYAN + "Charge Sim", ColorConsole.CYAN + "Contacts" + ColorConsole.RESET, ColorConsole.CYAN + "Transferring Money", ColorConsole.CYAN + "Administer", ColorConsole.CYAN + "Settings", ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> managementMenu = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Charge Account", ColorConsole.CYAN + "Check Balance" + ColorConsole.RESET, ColorConsole.CYAN + "See Transactions", ColorConsole.CYAN + "See Sim Card Charge", ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> contactMenu = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Add Contact", ColorConsole.CYAN + "Show Contact List" + ColorConsole.RESET, ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> transferMenu = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "by Credit Card ID",ColorConsole.CYAN + "by Account ID", ColorConsole.CYAN + "by Contact" + ColorConsole.RESET, ColorConsole.CYAN + "by Recent List", ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> administerMenu = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Add Request", ColorConsole.CYAN + "Show Request List" + ColorConsole.RESET, ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> settings = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Change User Password", ColorConsole.CYAN + "Set/Change Credit Card Password" + ColorConsole.RESET, ColorConsole.CYAN + "Contact Option", ColorConsole.CYAN + "Show Account info", ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> fundManagement = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Add Fund", ColorConsole.CYAN + "Show Funds" + ColorConsole.RESET));

    private final List<String> chargeSimOptions = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "My Own Sim Card", ColorConsole.CYAN + "My Contacts", ColorConsole.CYAN + "Other", ColorConsole.CYAN + "Return" + ColorConsole.RESET));

    private final List<String> managerService = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Settings", ColorConsole.CYAN + "User Management", ColorConsole.CYAN + "Automatic transactions" + ColorConsole.RESET));

    public void mainMenu(NeoBank neoBank, CentralBank centralBank) {
        Pagination<String> menu = new Pagination<>(main, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer)) {
                return;
            }
            menu.selectMain(this, neoBank, answer, centralBank);
        } while (!"quit".equals(answer));
    }

    public void userLog(NeoBank neoBank, CentralBank centralBank) {
        Pagination<String> menu = new Pagination<>(userLog, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer)) {
                return;
            }
            menu.selectUserLog(this, neoBank, answer, centralBank);
        } while (!"quit".equals(answer));
    }

    public void adminLog(NeoBank neoBank) {
        Pagination<String> menu = new Pagination<>(adminLog, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer)) {
                return;
            }
            menu.selectAdminLog(this, neoBank, answer);
        } while (!"quit".equals(answer));
    }

    public void userService(SimpleUser currentUser, NeoBank neoBank, CentralBank centralBank) {
        Pagination<String> menu = new Pagination<>(userServiceMenu, 5);
        do {
            menu.showPage();
            if(!menu.selectUserService(this, neoBank, currentUser, centralBank)){
                return;
            }
        } while (true);

    }

    public void adminService(Admin currentAdmin, NeoBank neoBank) {
        Pagination<String> menu = new Pagination<>(adminServiceMenu, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "3".equals(answer)) {
                return;
            }
            menu.selectAdminService(neoBank, currentAdmin, answer);
        } while (!"quit".equals(answer));

    }

    public void managementMenu(NeoBank neoBank, SimpleUser currentUser) {
        Pagination<String> menu = new Pagination<>(managementMenu, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "5".equals(answer)) {
                return;
            }
            menu.selectAccountManagement(neoBank, currentUser, answer);
        } while (!"quit".equals(answer));
    }

    public void contactMenu(NeoBank neoBank, SimpleUser currentUser) {
        if (!currentUser.isContactOption()) {
            System.out.println(ColorConsole.RED + "You can't choose this option" + ColorConsole.RESET);
            return;
        }
        Pagination<String> menu = new Pagination<>(contactMenu, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "3".equals(answer)) {
                return;
            }
            menu.selectContactMenu(neoBank, currentUser, answer);
        } while (!"quit".equals(answer));
    }

    public void transferMenu(NeoBank neoBank, CentralBank centralBank, SimpleUser currentUser) {
        Pagination<String> menu = new Pagination<>(transferMenu, 5);
        String answer;
        do {

            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "5".equals(answer)) {
                return;
            }
            menu.selectTransfer(neoBank, currentUser, answer, centralBank);
        } while (!"quit".equals(answer));
    }

    public void administerMenu(NeoBank neoBank, SimpleUser currentUser) {
        Pagination<String> menu = new Pagination<>(administerMenu, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "3".equals(answer)) {
                return;
            }
            menu.selectAdminister(neoBank, currentUser, answer);
        } while (!"quit".equals(answer));
    }

    public void settingsMenu(SimpleUser currentUser) {
        Pagination<String> menu = new Pagination<>(settings, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "5".equals(answer)) {
                return;
            }
            menu.selectSetting(currentUser, answer);
        } while (!"quit".equals(answer));
    }

    public void fundManagement(NeoBank neoBank, SimpleUser currentUser) {
        Pagination<String> menu = new Pagination<>(fundManagement, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "5".equals(answer)) {
                return;
            }
            menu.selectFundManagement(neoBank, currentUser, answer);
        } while (!"quit".equals(answer));
    }


    public void chargeSim(NeoBank neoBank, SimpleUser currentUser) {
        Pagination<String> menu = new Pagination<>(chargeSimOptions, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer) || "4".equals(answer)) {
                return;
            }
            menu.selectChargeOption(neoBank, currentUser, answer);
        } while (!"quit".equals(answer));
    }


    public void managerLog(NeoBank neoBank, CentralBank centralBank) {
        Pagination<String> menu = new Pagination<>(adminLog, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer)) {
                return;
            }
            menu.selectManagerLog(this, neoBank, answer, centralBank);
        } while (!"quit".equals(answer));
    }

    public void managerService(Manager currentManager, NeoBank neoBank, CentralBank centralBank) {
        Pagination<String> menu = new Pagination<>(managerService, 5);
        String answer;
        do{
            menu.showPage();
            answer = input.nextLine();
            if(!input.exitPoint(answer)){
                return;
            }
            menu.selectManagerService(neoBank, answer, currentManager, centralBank);
        }while (!"quit".equals(answer));
    }
}

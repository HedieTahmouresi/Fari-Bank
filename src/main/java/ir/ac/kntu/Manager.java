package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Manager {
    private String name;
    private String lastName;
    private String userName;
    private String password;
    private ManagerData data;
    private int rank;
    private boolean blocked;

    private final Input input = new Input();

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ManagerData getData() {
        return data;
    }

    public void setData(ManagerData data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Manager(String name, String lastName, String userName, String password, ManagerData data, int rank) {
        setName(name);
        setUserName(userName);
        setPassword(password);
        setData(data);
        setRank(rank);
        setBlocked(false);
        setLastName(lastName);
    }


    public void settings() {
        List<String> options = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Fari Wage", ColorConsole.CYAN + "Charge Wage", ColorConsole.CYAN + "Bonus Percentage", ColorConsole.CYAN + "Card to Card Wage", ColorConsole.CYAN + "Bridge Transfer Wage", ColorConsole.CYAN + "Wire Transfer Wage" + ColorConsole.RESET));
        Pagination<String> menu = new Pagination<>(options, 5);
        String answer;
        do {
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer)) {
                return;
            }
            menu.selectSettings(this, answer);
        } while (!"quit".equals(answer));
    }

    public void automaticTransactions(NeoBank neoBank, CentralBank centralBank) {
        System.out.println(ColorConsole.BLUE + "Choose : ");
        System.out.println("   1. Transfers");
        System.out.println("   2. Funds");
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Transfers" -> this.getData().completeTransfers(centralBank, neoBank);
            case "2", "Funds" -> this.getData().depositBonuses(neoBank);
            default -> {
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "No other Option!" + ColorConsole.RESET);
            }
        }
        this.automaticTransactions(neoBank, centralBank);
    }

    public void userManagement(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Choose :");
        System.out.println("   1. Add manager/admin");
        System.out.println("   2. Display All users");
        String answer = input.nextLine();
        switch (answer) {
            case "1" -> this.add();
            case "2" -> this.displayUsers(neoBank);
            default -> {
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "No other Option!" + ColorConsole.RESET);
            }
        }
        this.userManagement(neoBank);
    }

    public void add() {
        System.out.println(ColorConsole.BLUE + "Choose :");
        System.out.println("   1. Add Manager");
        System.out.println("   2. Add Admin");
        String answer = input.nextLine();
        switch (answer) {
            case "1" -> this.addManager();
            case "2" -> this.getData().addAdmin();
            default -> {
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "No other Option!" + ColorConsole.RESET);
            }
        }
        this.add();
    }

    public void addManager() {
        System.out.println(ColorConsole.BLUE + "Please enter the name" + ColorConsole.RESET);
        String name = input.nextLine();
        if (!input.exitPoint(name)) {
            return;
        }
        System.out.println(ColorConsole.BLUE + "Please enter the last name" + ColorConsole.RESET);
        String lastName = input.nextLine();
        if (!input.exitPoint(lastName)) {
            return;
        }
        String userName = input.nextUserNameManager(this.getData());
        if (userName == null) {
            return;
        } else if (this.getData().adminExists(userName)) {
            this.convertAdmin(name, lastName, userName);
            return;
        }
        String password = input.nextPassword();
        if (password == null) {
            return;
        }
        int rank = this.getRank() + 1;
        Manager newManager = new Manager(name, lastName, userName, password, this.getData(), rank);
        this.getData().addManager(newManager);
    }

    public void convertAdmin(String name, String lastName, String userName) {
        System.out.println("Would you like to make that admin a manager");
        String answer = input.nextLine();
        switch (answer) {
            case "yes" -> {
                String password = input.nextPassword();
                if (password == null) {
                    return;
                }
                int rank = this.getRank() + 1;
                Manager newManager = new Manager(name, lastName, userName, password, this.getData(), rank);
                this.getData().addManager(newManager);
                this.getData().removeAdmin(userName);
                return;
            }
            case "no" -> {
                return;
            }
            default -> {
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "No other option" + ColorConsole.RESET);
            }
        }
        this.convertAdmin(name, lastName, userName);
    }

    @Override
    public String toString() {
        return ColorConsole.CYAN + "Manager{" + ColorConsole.PURPLE + "Name : " + ColorConsole.PINK + name + ColorConsole.PURPLE + ", Last Name : " + ColorConsole.PINK + this.getLastName() + ColorConsole.PURPLE + ", User Name :" + ColorConsole.PINK + userName + ColorConsole.CYAN + '}' + ColorConsole.RESET;
    }


    public void displayUsers(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Choose : ");
        System.out.println("   1. All users");
        System.out.println("   2. Filtered users");
        String answer = input.nextLine();
        switch (answer) {
            case "1" -> this.showUsers(neoBank, this.getData().getAllUsers());
            case "2" -> this.displayFilteredUsers(neoBank, this.getData().getAllUsers());
            default -> {
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "No other Option!" + ColorConsole.RESET);
            }
        }
    }

    public void displayFilteredUsers(NeoBank neoBank, List<Object> users) {
        List<String> menu1 = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "by Name", ColorConsole.CYAN + "by LasT Name", ColorConsole.CYAN + "by User Name", ColorConsole.CYAN + "by Phone Number", ColorConsole.CYAN + "by Role" + ColorConsole.RESET));
        Pagination<String> menu = new Pagination<>(menu1, 5);
        String command;
        do {
            menu.showPage();
            command = input.nextLine();
            switch (command) {
                case "1" -> this.showUsers(neoBank, this.getData().searchName(users));
                case "2" -> this.showUsers(neoBank, this.getData().searchLastName(users));
                case "3" -> this.showUsers(neoBank, this.getData().searchUserName(users));
                case "4" -> this.showUsers(neoBank, this.getData().searchPhoneNumber(users));
                case "5" -> this.showUsers(neoBank, this.getData().searchRole(users));
                case "next", "previous" -> menu.changePage(command);
                default -> {
                    if (!input.exitPoint(command)) {
                        return;
                    }
                    System.out.println(ColorConsole.RED + "No other option" + ColorConsole.RESET);
                }
            }
        } while (!"return".equals(command));
    }

    public void showUsers(NeoBank neoBank, List<Object> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        Pagination userList = new Pagination<>(users, 5);
        String command;
        do {
            if (users.isEmpty()) {
                return;
            }
            userList.showPage();
            command = input.nextLine();
            if (!input.exitPoint(command)) {
                return;
            } else if (command.matches("[0-9]+")) {
                this.selectUser(users, command);
            } else if ("next".equals(command) || "previous".equals(command)) {
                userList.changePage(command);
            } else {
                System.out.println(ColorConsole.RED + "No other option! Please try again!" + ColorConsole.RESET);
            }
        } while (!"return".equals(command));
    }

    public void selectUser(List<Object> users, String answer) {
        if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) <= users.size()) {
            int index = Integer.parseInt(answer) - 1;
            Object user = users.get(index);
            if (user instanceof SimpleUser simpleUser) {
                this.manageUser(simpleUser);
            } else if (user instanceof Admin admin) {
                this.manageAdmin(admin);
            } else if (user instanceof Manager manager) {
                this.manageManager(manager);
            }
            return;
        }
        System.out.println(ColorConsole.RED + "Index out of Bound! try again!" + ColorConsole.RESET);
    }

    public void manageManager(Manager manager) {
        System.out.println(ColorConsole.BLUE + "Choose :");
        System.out.println("   1. Edit");
        System.out.println("   2. Block");
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Edit" -> {
                if (this.getRank() < manager.getRank()) {
                    manager.changeName();
                    manager.changeLastName();
                    manager.changeUserName();
                } else {
                    System.out.println(ColorConsole.RED + "You have lower rank!");
                }
            }
            case "2", "Block" -> {
                if (this.getRank() < manager.getRank()) {
                    manager.setBlocked(true);
                } else {
                    System.out.println(ColorConsole.RED + "You have lower rank!");
                }
            }
            default -> {
                if (!input.exitPoint(answer)){
                    return;
                }
                System.out.println(ColorConsole.RED + "no other option" + ColorConsole.RESET);
            }
        }
    }

    public void changeName() {
        System.out.println(ColorConsole.CYAN + "Would you like to change the name? (previous name : " + ColorConsole.PURPLE + this.getName() + ColorConsole.CYAN + ")" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            System.out.println(ColorConsole.CYAN + "Write the name you like!" + ColorConsole.RESET);
            String name = input.nextLine();
            if (input.exitPoint(name)) {
                this.setName(name);
            }
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeName();
    }

    public void changeLastName() {
        System.out.println(ColorConsole.CYAN + "Would you like to change the last name? (previous last name : " + ColorConsole.PURPLE + this.getLastName() + ColorConsole.CYAN + ")" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            System.out.println(ColorConsole.CYAN + "Write the last name you like!" + ColorConsole.RESET);
            String lastName = input.nextLine();
            if (input.exitPoint(lastName)) {
                this.setLastName(lastName);
            }
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeLastName();
    }

    public void changeUserName() {
        System.out.println(ColorConsole.CYAN + "Would you like to change the user name? (previous user name : " + ColorConsole.PURPLE + this.getUserName() + ColorConsole.CYAN + ")" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            System.out.println(ColorConsole.CYAN + "Write the last name you like!" + ColorConsole.RESET);
            String userName = input.nextUserNameManager(this.getData());
            if (userName == null) {
                return;
            } else if (!this.getData().adminExists(userName)) {
                this.setUserName(userName);
            } else {
                System.out.println("An admin with this username exists");
            }
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeUserName();
    }

    public void manageUser(SimpleUser currentUser) {
        System.out.println(ColorConsole.BLUE + "Choose :");
        System.out.println("   1. Edit");
        System.out.println("   2. Block");
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Edit" -> {
                currentUser.changeName();
                currentUser.changeLastName();
            }
            case "2", "Block" -> currentUser.setBlocked(true);
            default -> {
                if (!input.exitPoint(answer)){
                    return;
                }
                System.out.println(ColorConsole.RED + "no other option" + ColorConsole.RESET);
            }
        }
    }

    public void manageAdmin(Admin currentAdmin){
        System.out.println(ColorConsole.BLUE + "Choose :");
        System.out.println("   1. Edit");
        System.out.println("   2. Block");
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Edit" -> {
                currentAdmin.changeName();
                currentAdmin.changeLastName();
                currentAdmin.changeAbility();
            }
            case "2", "Block" -> currentAdmin.setBlocked(true);
            default -> {
                if (!input.exitPoint(answer)){
                    return;
                }
                System.out.println(ColorConsole.RED + "no other option" + ColorConsole.RESET);
            }
        }
    }

}

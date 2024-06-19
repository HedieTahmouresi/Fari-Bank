package ir.ac.kntu;

import java.util.List;

public class Admin {
    private String name;
    private String lastName;
    private String userName;
    private String password;
    private Data data;
    private AdminAbilities abilities;
    private boolean blocked;

    private final Input input = new Input();

    public Admin(String name,String lastName,  String userName, String password, Data data) {
        setName(name);
        setUserName(userName);
        setPassword(password);
        setData(data);
        setAbilities(new AdminAbilities(this));
        setBlocked(false);
        setLastName(lastName);
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


    public AdminAbilities getAbilities() {
        return abilities;
    }

    public void setAbilities(AdminAbilities abilities) {
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void showRequest(NeoBank neoBank) {
        String answer;
        displayRequestMenu();
        answer = input.nextLine();
        switch (answer) {
            case "1", "Show All requests":
                this.showRequest(this.getData().allRequests(this), neoBank);
                break;
            case "2", "Show requests by Person":
                this.showRequest(this.getData().filteredRequestsByPerson(neoBank, this), neoBank);
                break;
            case "3", "Show requests by Section":
                this.showRequest(this.getData().filteredRequestsBySection(this), neoBank);
                break;
            case "4", "Show requests by Status":
                this.showRequest(this.getData().filteredRequestsByStatus(this), neoBank);
                break;
            case "5", "Return":
                return;
            default:
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
        showRequest(neoBank);
    }

    public void displayRequestMenu() {
        System.out.println(ColorConsole.CYAN + "What would you like to do?");
        System.out.println("   1.Show All requests");
        System.out.println("   2.Show requests by Person");
        System.out.println("   3.Show requests by Section");
        System.out.println("   4.Show requests by Status");
        System.out.println("   5.Return" + ColorConsole.RESET);
    }

    public void showRequest(List<Request> requestsList, NeoBank neoBank) {
        if (requestsList == null || requestsList.isEmpty()) {
            return;
        }
        Pagination<Request> requests = new Pagination<>(requestsList, 5);
        String command;
        do {
            requests.showPage();
            System.out.println(ColorConsole.BLUE + "Enter 'next' to go to the next page, 'previous' to go back or the number of the transaction you want" + ColorConsole.RESET);
            command = input.nextLine();
            if (!input.exitPoint(command)) {
                return;
            } else if (command.matches("[0-9]+")) {
                this.selectRequest(requestsList, command, neoBank);
            } else if ("next".equals(command) || "previous".equals(command)) {
                requests.changePage(command);
            } else {
                System.out.println(ColorConsole.RED + "No other option! Please try again!" + ColorConsole.RESET);
            }
        } while (!"return".equals(command));
    }

    public void selectRequest(List<Request> requestsList, String answer, NeoBank neoBank) {
        if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < requestsList.size() + 1) {
            int index = Integer.parseInt(answer);
            if (answer.equals(Integer.toString(index))) {
                if (requestsList.get(index-1) instanceof Authentication){
                    ((Authentication) requestsList.get(index-1)).authenticateUser(neoBank);
                }else if (RequestStatus.IN_PROCESS.equals(requestsList.get(index - 1).getStatus())) {
                    requestsList.get(index - 1).showInfo(neoBank.getBankData());
                    requestsList.get(index - 1).closeRequest();
                } else if (RequestStatus.NOTED.equals(requestsList.get(index - 1).getStatus())) {
                    requestsList.get(index - 1).processRequest(neoBank);
                } else {
                    requestsList.get(index - 1).showInfo(neoBank.getBankData());
                }
            }

        } else {
            System.out.println(ColorConsole.RED + "Index Out of Bound! Try again!" + ColorConsole.RESET);
        }
    }

    public void searchUsers(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Would you like to search the users?" + ColorConsole.RESET);
        String answer = input.nextLine();
        switch (answer) {
            case "yes":
                List<SimpleUser> users = this.getData().search();
                if (users != null) {
                    showUsers(neoBank, users);
                    return;
                }
            case "no":
                showUsers(neoBank, this.getData().getAllUsers());
                return;
            default:
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED_BOLD + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                break;

        }
        searchUsers(neoBank);
    }

    public void showUsers(NeoBank neoBank, List<SimpleUser> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        Pagination<SimpleUser> userPages = new Pagination<>(users, 5);
        String command;
        do {
            userPages.showPage();
            System.out.println(ColorConsole.BLUE + "Enter 'next' to go to the next page, 'previous' to go back or the number of the transaction you want" + ColorConsole.RESET);
            command = input.nextLine();
            if (!input.exitPoint(command)) {
                return;
            } else if (command.matches("[0-9]+")) {
                this.selectUser(neoBank, users, command);
            } else if ("next".equals(command) || "previous".equals(command)) {
                userPages.changePage(command);
            } else {
                System.out.println(ColorConsole.RED + "No other option! Please try again!" + ColorConsole.RESET);
            }
        } while (!"return".equals(command));
    }

    public void selectUser(NeoBank neoBank, List<SimpleUser> users, String answer) {
        if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < users.size() + 1) {
            int index = Integer.parseInt(answer);
            users.get(index - 1).showUserInfo(neoBank);
        }
        System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);

    }

    @Override
    public String toString() {
        return ColorConsole.CYAN + "Admin{" +ColorConsole.PURPLE +  "Name : "+ ColorConsole.PINK + name + ColorConsole.PURPLE + ", Last Name : " + ColorConsole.PINK + this.getLastName() + ColorConsole.PURPLE + ", User Name :" + ColorConsole.PINK+ userName  +ColorConsole.CYAN + '}' + ColorConsole.RESET;
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

    public void changeUserName(ManagerData data) {
        System.out.println(ColorConsole.CYAN + "Would you like to change the user name? (previous user name : " + ColorConsole.PURPLE + this.getUserName() + ColorConsole.CYAN + ")" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            System.out.println(ColorConsole.CYAN + "Write the last name you like!" + ColorConsole.RESET);
            String userName = input.nextUserNameAdmin(data);
            if (userName == null) {
                return;
            } else if (!data.managerExists(userName)) {
                this.setUserName(userName);
            } else {
                System.out.println("A manager with this username exists");
            }
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeUserName(data);
    }

    public void changeAbility(){
        System.out.println(ColorConsole.CYAN + "Would you like to change the abilities?" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.getAbilities().changeAbilities();
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeAbility();
    }
}

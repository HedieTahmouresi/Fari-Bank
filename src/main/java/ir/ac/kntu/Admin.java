package ir.ac.kntu;

import java.util.List;

public class Admin {
    private String fullName;
    private String userName;
    private String password;
    private Data data;


    private final Input input = new Input();

    public Admin(String fullName, String userName, String password, Data data) {
        setFullName(fullName);
        setUserName(userName);
        setPassword(password);
        setData(data);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
                this.showRequest(this.getData().allRequests(), neoBank);
                break;
            case "2", "Show requests by Person":
                this.showRequest(this.getData().filteredRequestsByPerson(neoBank), neoBank);
                break;
            case "3", "Show requests by Section":
                this.showRequest(this.getData().filteredRequestsBySection(), neoBank);
                break;
            case "4", "Show requests by Status":
                this.showRequest(this.getData().filteredRequestsByStatus(), neoBank);
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
                    requestsList.get(index - 1).showInfo();
                    requestsList.get(index - 1).closeRequest();
                } else if (RequestStatus.NOTED.equals(requestsList.get(index - 1).getStatus())) {
                    requestsList.get(index - 1).processRequest();
                } else {
                    requestsList.get(index - 1).showInfo();
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
}

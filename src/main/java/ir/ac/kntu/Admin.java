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

    public void selectAuthentication(NeoBank neoBank) {
        int size = this.getData().showAuthentications();
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return;
        } else if (!answer.matches("-?\\d+(\\.\\d+)?")) {
            System.out.println(ColorConsole.RED + "Wrong Format! Please input the number of the user" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) <= size) {
            for (int index = 1; index <= size; index++) {
                if (answer.equals(Integer.toString(index))) {
                    this.getData().authenticateUser(neoBank, index - 1);
                    break;
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Out of bound!" + ColorConsole.RESET);
        }
        this.selectAuthentication(neoBank);
    }

    public void showRequest(NeoBank neoBank) {
        String answer;
        displayRequestMenu();
        answer = input.nextLine();
        switch (answer) {
            case "1", "Show All requests":
                this.selectRequest(neoBank, this.getData().displayRequests());
                break;
            case "2", "Show requests by Person":
                this.selectRequest(neoBank, this.getData().displayRequestsByPerson(neoBank));
                break;
            case "3", "Show requests by Section":
                this.selectRequest(neoBank, this.getData().displayRequestsBySection());
                break;
            case "4", "Show requests by Status":
                this.selectRequest(neoBank, this.getData().displayRequestsByStatus());
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

    public void selectRequest(NeoBank neoBank, List<Request> requestsList) {
        String answer = input.nextLine();
        if (requestsList == null) {
            return;
        }
        if (!input.exitPoint(answer)) {
            return;
        } else if (!answer.matches("[0-9]+")) {
            System.out.println(ColorConsole.RED + "Wrong format! Try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < requestsList.size() + 1) {
            for (int index = 1; index < requestsList.size() + 1; index++) {
                if (answer.equals(Integer.toString(index))) {
                    if (RequestStatus.IN_PROCESS.equals(requestsList.get(index - 1).getStatus())) {
                        requestsList.get(index - 1).showInfo();
                        requestsList.get(index - 1).closeRequest();
                    } else if (RequestStatus.NOTED.equals(requestsList.get(index - 1).getStatus())) {
                        requestsList.get(index - 1).processRequest();
                    }
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Index Out of Bound! Try again!" + ColorConsole.RESET);
        }
        this.selectRequest(neoBank, requestsList);
    }

    public void showUsers(List<SimpleUser> users) {
        int index = 1;
        for (SimpleUser user : users) {
            System.out.println(ColorConsole.CYAN + index + ". " + ColorConsole.BLUE + user + ColorConsole.RESET);
            index++;
        }
    }

    public void searchUsers(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Would you like to search the users?" + ColorConsole.RESET);
        String answer = input.nextLine();
        switch (answer) {
            case "yes":
                List<SimpleUser> users = this.getData().search(neoBank);
                if (users != null) {
                    selectUser(users, neoBank);
                    return;
                }
            case "no":
                selectUser(this.getData().getAllUsers(neoBank), neoBank);
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


    public void selectUser(List<SimpleUser> users, NeoBank neoBank) {
        this.getData().showUsers(users);
        if (users.isEmpty()) {
            return;
        }
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return;
        } else if (!answer.matches("\\d+")) {
            System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < users.size() + 1) {
            for (int index = 1; index <= users.size(); index++) {
                if (answer.equals(Integer.toString(index))) {
                    users.get(index - 1).showUserInfo(neoBank);
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
        }

        this.selectUser(users, neoBank);
    }
}

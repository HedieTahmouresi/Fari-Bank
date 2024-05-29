package ir.ac.kntu;


import java.util.ArrayList;
import java.util.List;

public class Admin extends Person {
    private String password;
    private Data data;

    public Admin(String name, String userName, NeoBank neoBank, String password) {
        super(name, userName);
        setPassword(password);
        setData(neoBank.getData());
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

    public static Admin signIn(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Please Enter your username" + ColorConsole.RESET);
        String userName;
        Admin wantedAdmin;
        do {
            userName = Input.inputNextLine();
            if (!Input.checkInput(userName)) {
                return null;
            }
            wantedAdmin = neoBank.getSpecificAdmin(userName);
            if (wantedAdmin == null) {
                System.out.println(ColorConsole.RED_BOLD + "Username not available!" + ColorConsole.RESET);
            }
        } while (wantedAdmin == null);
        System.out.println(ColorConsole.BLUE + "Please Enter your password" + ColorConsole.RESET);
        String password;
        do {
            password = Input.inputNextLine();
            if (!Input.checkInput(password)) {
                return null;
            } else if (!password.equals(wantedAdmin.getPassword())) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong Password! If you would like to change the user name return to the previous menu" + ColorConsole.RESET);
            }
        } while (!password.equals(wantedAdmin.getPassword()));
        return wantedAdmin;
    }

    public static String gettingSearchName() {
        System.out.println(ColorConsole.BLUE + "Do you want to enter a name?" + ColorConsole.RESET);
        String answer = Input.inputNextLine();
        switch (answer) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Enter the name you are looking for!" + ColorConsole.RESET);
                String name = Input.inputNextLine();
                if (!Input.checkInput(answer)) {
                    return null;
                } else {
                    return name;
                }
            case "no":
                return null;
            default:
                if (!Input.checkInput(answer)) {
                    return null;
                } else {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                }
        }
        return gettingSearchName();
    }

    public static String gettingSearchLastName() {
        System.out.println(ColorConsole.BLUE + "Do you want to enter a last name?" + ColorConsole.RESET);
        String answer = Input.inputNextLine();
        switch (answer) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Please enter the last name you are looking for" + ColorConsole.RESET);
                String lastName = Input.inputNextLine();
                if (!Input.checkInput(answer)) {
                    return null;
                } else {
                    return lastName;
                }
            case "no":
                return null;
            default:
                if (!Input.checkInput(answer)) {
                    return null;
                } else {
                    System.out.println(ColorConsole.RED_BOLD + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                }
        }
        return gettingSearchLastName();
    }

    public static String gettingSearchPhoneNumber() {
        System.out.println(ColorConsole.BLUE + "Do you want to enter a Phone number?" + ColorConsole.RESET);
        String answer;
        answer = Input.inputNextLine();
        switch (answer) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Please enter the phone number you are looking for" + ColorConsole.RESET);
                String phoneNumber = Input.inputNextLine();
                if (!Input.checkInput(answer)) {
                    return null;
                } else if (Input.checkPhoneNumber(phoneNumber)) {
                    return phoneNumber;
                } else {
                    return null;
                }
            case "no":
                return null;
            default:
                if (!Input.checkInput(answer)) {
                    return null;
                } else {
                    System.out.println(ColorConsole.RED_BOLD + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                }
        }
        return gettingSearchPhoneNumber();
    }

    public static void searchUsers(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Would you like to search the users?" + ColorConsole.RESET);
        String answer = Input.inputNextLine();
        SimpleUser wantedUser;
        switch (answer) {
            case "yes":
                List<SimpleUser> users = search(neoBank);
                if (users != null) {
                    wantedUser = selectUser(users);
                    if (wantedUser != null) {
                        wantedUser.showUserInfo(neoBank);
                    }
                    return;
                }
            case "no":
                wantedUser = selectUser(getAllUsers(neoBank));
                if (wantedUser != null) {
                    wantedUser.showUserInfo(neoBank);
                }
                return;
            default:
                if (!Input.checkInput(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED_BOLD + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                break;

        }
        searchUsers(neoBank);
    }

    public static int fuzzySim(String first, String second) {
        if (first == null || second == null) {
            if (first == null && second == null) {
                return 0;
            }
            return (first == null) ? second.length() : first.length();
        }
        int[][] distance = new int[first.length() + 1][second.length() + 1];
        for (int index = 0; index <= first.length(); index++) {
            for (int row = 0; row <= second.length(); row++) {
                if (index == 0) {
                    distance[index][row] = row;
                } else if (row == 0) {
                    distance[index][row] = index;
                } else {
                    distance[index][row] = Math.min(distance[index - 1][row - 1] + (first.charAt(index - 1) == second.charAt(row - 1) ? 0 : 1),
                            Math.min(distance[index - 1][row] + 1, distance[index][row - 1] + 1));
                }
            }
        }
        return (second.length() - distance[first.length()][second.length()]) / second.length(); // max distance 0.4
    }

    public static List<SimpleUser> fuzzySearchName(String name, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            if (fuzzySim(name, user.getName()) >= 0.4) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> fuzzySearchLastName(String lastName, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            if (fuzzySim(lastName, user.getSurname()) >= 0.4) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> fuzzySearchPhone(String phoneNumber, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            if (fuzzySim(phoneNumber, user.getPhoneNumber()) >= 0.4) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> search(NeoBank neoBank) {
        List<SimpleUser> result = new ArrayList<>();
        for (int index = 0; index < neoBank.getData().usersSize()+1; index++) {
            result.add(neoBank.getData().getUser(index));
        }
        String name = gettingSearchName();
        String lastName = gettingSearchLastName();
        String phoneNumber = gettingSearchPhoneNumber();
        if (name == null && lastName == null && phoneNumber == null) {
            return null;
        }
        if (name != null) {
            result = fuzzySearchName(name, result);
        }
        if (lastName != null) {
            result = fuzzySearchLastName(lastName, result);
        }
        if (phoneNumber != null) {
            result = fuzzySearchPhone(phoneNumber, result);
        }

        if (result.isEmpty()) {
            System.out.println(ColorConsole.RED + "no user found matching those descriptions!" + ColorConsole.RESET);
        }
        return result;
    }

    public static void showUsers(List<SimpleUser> users) {
        int index = 1;
        for (SimpleUser user : users) {
            System.out.println("haha");
            System.out.println(index + ". " + user);
            index++;
        }
    }

    public static SimpleUser selectUser(List<SimpleUser> users) {
        String answer;
        do {
            showUsers(users);
            if (users.isEmpty()) {
                return null;
            }
            answer = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(answer)) {
                System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                System.exit(0);
            } else if ("return".equalsIgnoreCase(answer)) {
                return null;
            } else if (!answer.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
            } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < users.size() + 1) {
                for (int index = 1; index < users.size(); index++) {
                    if (answer.equals(Integer.toString(index))) {
                        return users.get(index - 1);
                    }
                }
            } else {
                System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
            }
        } while (!"quit".equalsIgnoreCase(answer));
        return null;
    }

    public static List<SimpleUser> getAllUsers(NeoBank neoBank) {
        List<SimpleUser> allUsers = new ArrayList<>();
        for (int index = 0; index < neoBank.getData().usersSize(); index++) {
            allUsers.add(neoBank.getData().getUser(index));
            System.out.println("haha" + neoBank.getData().getUser(index));
        }
        return allUsers;
    }
}
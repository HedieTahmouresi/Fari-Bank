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

    public static Admin signIn(NeoBank neoBank){
        System.out.println("Please Enter your username");
        String userName;
        Admin wantedAdmin;
        do {
            userName = Input.inputNextLine();
            if ("return".equalsIgnoreCase(userName)){
                return null;
            } else if("quit".equalsIgnoreCase(userName)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }
            wantedAdmin = neoBank.getSpecificAdmin(userName);
            if (wantedAdmin == null){
                System.out.println("Username not available!");
            }
        } while (wantedAdmin ==null);
        System.out.println("Please Enter your password");
        String password;
        do {
            password = Input.inputNextLine();
            if ("return".equalsIgnoreCase(password)){
                return null;
            } else if("quit".equalsIgnoreCase(password)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }else if (!password.equals(wantedAdmin.getPassword())){
                System.out.println("Wrong Password! If you would like to change the user name return to the previous menu");
            }
        } while (!password.equals(wantedAdmin.getPassword()));
        return wantedAdmin;
    }

    public static String gettingSearchName(){
        System.out.println("Do you want to enter a name?");
        String answer;
        do{
            answer = Input.inputNextLine();
            switch(answer){
                case "yes":
                    System.out.println("Enter the name you are looking for!");
                    String name = Input.inputNextLine();
                    if ("return".equalsIgnoreCase(name)){
                        return null;
                    }else if ("quit".equalsIgnoreCase(name)) {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    } else{
                        return name;
                    }
                case "no":
                    return null;
                default:
                    if ("return".equalsIgnoreCase(answer)){
                        return null;
                    }else if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {

                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
        return null;
    }

    public static String gettingSearchLastName(){
        System.out.println("Do you want to enter a last name?");
        String answer;
        do{
            answer = Input.inputNextLine();
            switch(answer){
                case "yes":
                    System.out.println("Please enter the last name you are looking for");
                    String lastName = Input.inputNextLine();
                    if ("return".equalsIgnoreCase(lastName)){
                        return null;
                    }else if ("quit".equalsIgnoreCase(lastName)) {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    } else{
                        return lastName;
                    }
                case "no":
                    return null;
                default:
                    if ("return".equalsIgnoreCase(answer)){
                        return null;
                    }else if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {

                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
        return null;
    }

    public static String gettingSearchPhoneNumber(){
        System.out.println("Do you want to enter a Phone number?");
        String answer;
        do{
            answer = Input.inputNextLine();
            switch(answer){
                case "yes":
                    System.out.println("Please enter the phone number you are looking for");
                    String phoneNumber = Input.inputNextLine();
                    if ("return".equalsIgnoreCase(phoneNumber)){
                        return null;
                    }else if ("quit".equalsIgnoreCase(phoneNumber)) {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    } else if(Input.checkPhoneNumber(phoneNumber)){
                        return phoneNumber;
                    } else{
                        return null;
                    }
                case "no":
                    return null;
                default:
                    if ("return".equalsIgnoreCase(answer)){
                        return null;
                    }else if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {

                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
        return null;
    }

    public static void searchUsers(NeoBank neoBank){
        System.out.println("Would you like to search the users?");
        String answer;
        do{
            answer = Input.inputNextLine();
            switch(answer){
                case "yes":
                    List<SimpleUser> users = search(neoBank);
                    if (users!=null){
                        SimpleUser wantedUser = selectUser(users);
                        if (wantedUser!=null){
                            wantedUser.showUserInfo(neoBank);
                        }
                    }
                case "no":
                    List<SimpleUser> allUsers = getAllUsers(neoBank);
                    SimpleUser wantedUser = selectUser(allUsers);
                    if (wantedUser!=null){
                        wantedUser.showUserInfo(neoBank);
                    }
                    return;
                default:
                    if ("return".equalsIgnoreCase(answer)){
                        return;
                    }else if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public static int fuzzyDistance(String first, String second) {
        if (first == null || second == null) {
            if (first == null && second == null) return 0;
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
        return ((second.length()-distance[first.length()][second.length()])/second.length()); // max distance 0.4
    }

    public static List<SimpleUser> fuzzySearchName(String name, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            if (fuzzyDistance(name , user.getName()) >= 0.4) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> fuzzySearchLastName(String lastName, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            if (fuzzyDistance(lastName , user.getSurname()) >= 0.4) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> fuzzySearchPhone(String phoneNumber, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            if (fuzzyDistance(phoneNumber , user.getPhoneNumber()) >= 0.4) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> search(NeoBank neoBank){
        List<SimpleUser> result = new ArrayList<>();
        for (int index = 0 ; index < neoBank.getData().usersSize() ; index++){
            result.add(neoBank.getData().getUser(index));
        }
        String name = gettingSearchName();
        String lastName = gettingSearchLastName();
        String phoneNumber = gettingSearchPhoneNumber();
        if (name==null && lastName==null && phoneNumber==null){
            return null;
        }
        if (name != null){
            result = fuzzySearchName(name, result);
        }
        if (lastName!=null){
            result = fuzzySearchLastName(lastName, result);
        }
        if (phoneNumber!=null){
            result = fuzzySearchPhone(phoneNumber, result);
        }

        if (result.isEmpty()){
            System.out.println("no user found matching those descriptions!");
        }
        return result;
    }

    public static void showUsers(List<SimpleUser> users){
        int index = 1;
        for (SimpleUser user : users){
            System.out.println(index + ". " + user);
            index++;
        }
    }

    public static SimpleUser selectUser(List<SimpleUser> users){
        String answer;
        do{
            showUsers(users);
            if (users.isEmpty()){
                return null;
            }
            answer = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(answer)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(answer)) {
                return null;
            } else if (!answer.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println("Wrong input try again!");
            } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < users.size() + 1) {
                for (int index = 1; index < users.size() + 1; index++) {
                    if (answer.equals(Integer.toString(index))) {
                        return users.get(index-1);
                    }
                }
            } else {
                System.out.println("Wrong input try again!");
            }
        }while (!"quit".equalsIgnoreCase(answer));
        return null;
    }

    public static List<SimpleUser> getAllUsers(NeoBank neoBank){
        List<SimpleUser> allUsers = new ArrayList<>();
        for (int index = 0 ; index < neoBank.getData().usersSize() ; index++){
            allUsers.add(neoBank.getData().getUser(index));
        }
        return allUsers;
    }
}
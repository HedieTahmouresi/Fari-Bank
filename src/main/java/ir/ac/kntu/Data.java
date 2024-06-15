package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
    private List<Request> requests;
    private List<Authentication> authentications;
    private List<SimpleUser> users;

    private final Input input = new Input();

    public Data() {
        this.requests = new ArrayList<>();
        this.authentications = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public int authenticationsSize(){
        return this.authentications.size();
    }

    public void addRequest(Request request) {
        this.requests.add(request);
    }

    public void addAuthentication(Authentication authentication) {
        this.authentications.add(authentication);
    }

    public void removeAuthentication(SimpleUser user) {
        this.authentications.remove(user.getAuthenticated());
    }

    public void addUser(SimpleUser user) {
        this.users.add(user);
    }

    public boolean checkPhoneNumber(String phoneNumber, String need) {
        String phoneRegEx = "^09[0-9]{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegEx);
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid phone number Please try again" + ColorConsole.RESET);
            return false;
        }
        for (SimpleUser user : this.users) {
            if (phoneNumber.equalsIgnoreCase(user.getPhoneNumber()) && "shouldn't exist".equals(need)) {
                System.out.println(ColorConsole.RED_BOLD + "a user with this phone number already exists" + ColorConsole.RESET);
                return false;
            } else if (phoneNumber.equalsIgnoreCase(user.getPhoneNumber())) {
                return true;
            }
        }
        if ("should exist".equals(need)) {
            System.out.println(ColorConsole.RED_BOLD + "No user with this phone number exists" + ColorConsole.RESET);
            return false;
        }
        return true;
    }

    public boolean checkSecurityNumber(String securityNumber) {
        String ssnRegEx = "^[0-9]{10}$";
        Pattern ssnPattern = Pattern.compile(ssnRegEx);
        Matcher ssnMatcher = ssnPattern.matcher(securityNumber);
        if (!ssnMatcher.matches()) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid phone number Please try again" + ColorConsole.RESET);
            return false;
        }
        for (SimpleUser user : this.users) {
            if (securityNumber.equalsIgnoreCase(user.getSecurityNumber())) {
                System.out.println(ColorConsole.RED_BOLD + "This phone number already exists" + ColorConsole.RESET);
                return false;
            }
        }
        return true;
    }

    public SimpleUser getUserByPhone(String phoneNumber) {
        for (SimpleUser user : this.users) {
            if (user.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                return user;
            }
        }
        return null;
    }

    public SimpleUser getUserByAccountID(String accountID) {
        for (SimpleUser user : this.users) {
            if (user.getAccount().getAccountId().equalsIgnoreCase(accountID)) {
                return user;
            }
        }
        return null;
    }

    public void signUp() {
        String name, lastName, phoneNumber, securityNumber, password;
        System.out.println(ColorConsole.BLUE_BOLD + "Please enter your name" + ColorConsole.RESET);
        name = input.nextLine();
        if (!input.exitPoint(name)) {
            return;
        }
        System.out.println(ColorConsole.BLUE_BOLD + "Please enter your last name" + ColorConsole.RESET);
        lastName = input.nextLine();
        if (!input.exitPoint(lastName)) {
            return;
        }
        phoneNumber = input.nextPhoneNumber(this, "shouldn't exist");
        if (phoneNumber == null) {
            return;
        }
        securityNumber = input.nextSecurityNumber(this);
        if (securityNumber == null) {
            return;
        }
        password = input.nextPassword();
        if (password == null) {
            return;
        }
        Authentication newAuthentication = new Authentication(phoneNumber);
        this.addUser(new SimpleUser(name, lastName, phoneNumber, securityNumber, password, newAuthentication));
        this.addAuthentication(newAuthentication);
        System.out.println(ColorConsole.GREEN + "You have successfully signed in!" + ColorConsole.RESET);
    }

    public SimpleUser signInUser() {
        System.out.println(ColorConsole.BLUE_BOLD + "Please enter your phone number :" + ColorConsole.RESET);
        String phoneNumber;
        do {
            phoneNumber = input.nextLine();
            if (!input.exitPoint(phoneNumber)) {
                return null;
            }
            if (this.getUserByPhone(phoneNumber) == null) {
                System.out.println(ColorConsole.RED_BOLD + "this user doesn't exist! " + ColorConsole.BLUE + "if you want you can return and sign up!" + ColorConsole.RESET);
            }
        } while (this.getUserByPhone(phoneNumber) == null);
        SimpleUser currentUser = this.getUserByPhone(phoneNumber);
        String password;
        System.out.println(ColorConsole.BLUE + "Please enter your password" + ColorConsole.RESET);
        do {
            password = input.nextLine();
            if (!input.exitPoint(password)) {
                return null;
            }
            if (!password.equals(currentUser.getPassword())) {
                System.out.println(ColorConsole.RED_BOLD + "this phone number and password don't match" + ColorConsole.BLUE + "if you want you can return and try to sign in with a different number" + ColorConsole.RESET);
            }
        } while (!password.equals(currentUser.getPassword()));
        return currentUser;
    }

    public void showAuthentications(NeoBank neoBank,Admin currentAdmin) {
        if (this.authentications==null || this.authentications.isEmpty()){
            return;
        }
        Pagination authenticationList = new Pagination<>(this.authentications, 5);
        String command;
        do {
            if (this.authentications.isEmpty()){
                System.out.println(ColorConsole.RED + "No authentications!" + ColorConsole.RESET);
                return;
            }
            authenticationList.showPage();
            System.out.println(ColorConsole.BLUE +"Enter 'next' to go to the next page, 'previous' to go back or the number of the transaction you want"+ ColorConsole.RESET);
            command = input.nextLine();
            if (!input.exitPoint(command)) {
                return;
            } else if (command.matches("[0-9]+")) {
                currentAdmin.selectAuthentication(neoBank,command);
            } else if ("next".equals(command) || "previous".equals(command)) {
                authenticationList.changePage(command);
            } else {
                System.out.println(ColorConsole.RED + "No other option! Please try again!" + ColorConsole.RESET);
            }
        } while (!"return".equals(command));
    }

    public void authenticateUser(NeoBank neoBank, int index) {
        SimpleUser currentUser = this.getUserByPhone(authentications.get(index).getPhoneNumber());
        currentUser.getAuthenticated().showInfo(this);
        System.out.println(ColorConsole.BLUE + "Would you like to authenticate this user?" + ColorConsole.PURPLE + "(1. yes, 2. no)" + ColorConsole.RESET);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "yes":
                this.authentications.remove(index);
                currentUser.getAuthenticated().authenticateUser(neoBank, currentUser);
                return;
            case "2", "no":
                currentUser.getAuthenticated().rejectUser();
                return;
            default:
                if (!input.exitPoint(answer)) {
                    return;
                } else {
                    System.out.println(ColorConsole.RED + "Wrong Input" + ColorConsole.RESET);
                }
        }
        this.authenticateUser(neoBank, index);
    }

    public boolean existsCreditCard(String creditCardString) {
        for (int index = 0; index < this.users.size(); index++) {
            if (this.users.get(index).getAccount() != null) {
                if (this.users.get(index).getAccount().getCreditCard().getCreditCardId().equals(creditCardString)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existsAccountID(String accountIDString) {
        for (int index = 0; index < this.users.size(); index++) {
            if (this.users.get(index).getAccount() != null) {
                if (this.users.get(index).getAccount().getAccountId().equals(accountIDString)) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<Request> allRequests() {
        List<Request> list = new ArrayList<>();
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        for (Request request : this.requests) {
            list.add(request);
        }
        return list;
    }


    public List<Request> filteredRequestsBySection() {
        RequestSection section = input.nextRequestSection();
        if (section==null){
            return null;
        }
        List<Request> list = new ArrayList<>();
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        for (Request request : this.requests) {
            if (request.getSection().equals(section)) {
                list.add(request);
            }
        }
        return list;
    }

    public List<Request> filteredRequestsByPerson(NeoBank neoBank) {
        List<Request> list = new ArrayList<>();
        String phoneNumber = input.nextRequestPerson(neoBank);
        if (phoneNumber == null) {
            return null;
        }else if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        for (Request request : this.requests) {
            if (request.getPhoneNumber().equals(phoneNumber)) {
                list.add(request);
            }
        }
        return list;
    }

    public List<Request> filteredRequestsByStatus() {
        RequestStatus status = input.nextRequestStatus();
        List<Request> list = new ArrayList<>();
        if (status == null) {
            return null;
        }else if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        for (Request request : this.requests) {
            if (request.getStatus().equals(status)) {
                list.add(request);
            }
        }
        return list;
    }

    public List<SimpleUser> search(NeoBank neoBank) {
        List<SimpleUser> result = new ArrayList<>(this.users);
        String name = input.nextSearchName();
        String lastName = input.nextSearchLastName();
        String phoneNumber = input.nextSearchPhoneNumber();
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

    public List<SimpleUser> fuzzySearchName(String name, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            Fuzzy similarity = new Fuzzy(name, user.getName());
            if (similarity.getSimilarity() >= 0.6) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> fuzzySearchLastName(String lastName, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            Fuzzy similarity = new Fuzzy(lastName, user.getLastName());
            if (similarity.getSimilarity() >= 0.6) {
                result.add(user);
            }
        }
        return result;
    }

    public static List<SimpleUser> fuzzySearchPhone(String phoneNumber, List<SimpleUser> list) {
        List<SimpleUser> result = new ArrayList<>();
        for (SimpleUser user : list) {
            Fuzzy similarity = new Fuzzy(phoneNumber, user.getPhoneNumber());
            if (similarity.getSimilarity() >= 0.6) {
                result.add(user);
            }
        }
        return result;
    }


    public void showUsers(List<SimpleUser> userList) {
        int index = 1;
        for (SimpleUser user : userList) {
            System.out.println(ColorConsole.CYAN + index + ". " + ColorConsole.BLUE + user + ColorConsole.RESET);
            index++;
        }
    }

    public List<SimpleUser> getAllUsers(NeoBank neoBank) {
        return new ArrayList<>(this.users);
    }
}

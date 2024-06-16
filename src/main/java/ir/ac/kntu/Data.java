package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
    private List<Request> requests;
    private List<SimpleUser> users;

    private final Input input = new Input();

    public Data() {
        this.requests = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addRequest(Request request) {
        this.requests.add(request);
    }

    public void addAuthentication(Authentication authentication) {
        this.requests.add(authentication);
    }

    public void removeAuthentication(SimpleUser user) {
        this.requests.remove(user.getAuthenticated());
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
        } else if ("doesn't matter".equals(need)) {
            return true;
        }
        for (SimpleUser user : this.users) {
            if (phoneNumber.equalsIgnoreCase(user.getSimCard().getPhoneNumber()) && "shouldn't exist".equals(need)) {
                System.out.println(ColorConsole.RED_BOLD + "a user with this phone number already exists" + ColorConsole.RESET);
                return false;
            } else if (phoneNumber.equalsIgnoreCase(user.getSimCard().getPhoneNumber())) {
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
            if (user.getSimCard().getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
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

    public void signUp(NeoBank neoBank) {
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
        SimCard simCard = this.getSim(neoBank, phoneNumber);
        Authentication newAuthentication = new Authentication(phoneNumber);
        this.addUser(new SimpleUser(name, lastName, simCard, securityNumber, password, newAuthentication));
        this.addAuthentication(newAuthentication);
        System.out.println(ColorConsole.GREEN + "You have successfully signed in!" + ColorConsole.RESET);
    }

    public SimCard getSim(NeoBank neoBank, String phoneNumber){
        SimCard simCard = neoBank.getManagerData().getSimCard(phoneNumber);
        if (simCard == null) {
            simCard = new SimCard(phoneNumber, false);
            neoBank.getManagerData().addSimCard(simCard);
        }
        return simCard;
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



    public boolean existsCreditCard(String creditCardString) {
        for (SimpleUser user : this.users) {
            if (user.getAccount() != null) {
                if (user.getAccount().getCreditCard().getCreditCardId().equals(creditCardString)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existsAccountID(String accountIDString) {
        for (SimpleUser user : this.users) {
            if (user.getAccount() != null) {
                if (user.getAccount().getAccountId().equals(accountIDString)) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<Request> allRequests() {
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        return new ArrayList<>(this.requests);
    }


    public List<Request> filteredRequestsBySection() {
        RequestSection section = input.nextRequestSections();
        if (section == null) {
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
        } else if (this.requests.isEmpty()) {
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
        } else if (this.requests.isEmpty()) {
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

    public List<SimpleUser> search() {
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
            Fuzzy similarity = new Fuzzy(phoneNumber, user.getSimCard().getPhoneNumber());
            if (similarity.getSimilarity() >= 0.6) {
                result.add(user);
            }
        }
        return result;
    }


    public List<SimpleUser> getAllUsers() {
        return new ArrayList<>(this.users);
    }
}

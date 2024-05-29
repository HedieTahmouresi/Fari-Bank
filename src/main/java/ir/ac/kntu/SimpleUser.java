package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class SimpleUser extends Person {
    private String phoneNumber;
    private String securityNumber;
    private String password;
    private Account account;
    private List<Contact> contacts;
    private List<Request> requests;
    private boolean contactOption;
    private Authentication authenticated;


    public void addRequest(Request request) {
        this.requests.add(request);
    }

    public int requestSize() {
        return this.requests.size();
    }

    public Request getRequest(int index) {
        return this.requests.get(index);
    }

    public int contactSize() {
        return this.contacts.size();
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }

    public Contact getSpecificContact(int index) {
        return this.contacts.get(index);
    }

    public void addContact(Contact newContact) {
        this.contacts.add(newContact);
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isContactOption() {
        return contactOption;
    }

    public void setContactOption(boolean contactOption) {
        this.contactOption = contactOption;
    }

    public boolean isAuthenticated() {
        return authenticated.isAuthenticated();
    }

    public Authentication getAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(Authentication authenticated) {
        this.authenticated = authenticated;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SimpleUser(String name, String lastName, String phoneNumber, String securityNumber, String password) {
        super(name, lastName);
        setSecurityNumber(securityNumber);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        this.contacts = new ArrayList<>();
        this.requests = new ArrayList<>();
        setContactOption(true);
        setAuthenticated(new Authentication());
    }

    public static void signUp(NeoBank neoBank, Data data) {
        System.out.println(ColorConsole.BLUE + "Please Enter your name!" + ColorConsole.RESET);
        String name = Input.inputNextLine();
        if (!Input.checkInput(name)) {
            return;
        }
        System.out.println(ColorConsole.BLUE + "Please Enter your lastname!" + ColorConsole.RESET);
        String lastName = Input.inputNextLine();
        if (!Input.checkInput(lastName)) {
            return;
        }
        System.out.println(ColorConsole.BLUE + "Please Enter your phone number!" + ColorConsole.RESET);
        String phoneNumber = Input.takePhoneNumber(neoBank);
        if (phoneNumber == null) {
            return;
        }
        System.out.println(ColorConsole.BLUE + "Please Enter your social security number!" + ColorConsole.RESET);
        String securityNumber = Input.takeSecurityNumber(neoBank);
        if (securityNumber == null) {
            return;
        }
        System.out.println(ColorConsole.BLUE + "Please Enter your password!" + ColorConsole.RESET);
        String password = Input.takePassword();
        if (password == null) {
            return;
        }
        neoBank.addSimpleUsers(new SimpleUser(name, lastName, phoneNumber, securityNumber, password));
        data.addAuthentication(securityNumber);
        System.out.println(ColorConsole.GREEN + "You have successfully signed up! Please sign in to access your account!\n" + ColorConsole.RESET);
    }

    public static SimpleUser getUserByPhone(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Please Enter the phoneNumber!" + ColorConsole.RESET);
        String phoneNumber;
        int userIndex;
        do {
            phoneNumber = Input.inputNextLine();
            userIndex = neoBank.getSpecificUser(phoneNumber);
            if (!Input.checkInput(phoneNumber)) {
                return null;
            } else if (userIndex == -1) {
                System.out.println(ColorConsole.RED + "This phone number doesn't exist in our database!" + ColorConsole.RESET);
            }
        } while (userIndex == -1);
        return neoBank.getSpecificUser(userIndex);
    }

    public static SimpleUser signIn(NeoBank neoBank, Data data) {
        SimpleUser wantedUser = getUserByPhone(neoBank);
        if (wantedUser == null) {
            return null;
        }
        String password;
        System.out.println(ColorConsole.BLUE + "Enter your password!" + ColorConsole.RESET);
        do {
            password = Input.inputNextLine();
            if (!Input.checkInput(password)) {
                return null;
            } else if (!password.equals(wantedUser.getPassword())) {
                System.out.println(ColorConsole.RED + "Wrong password! if you would like to change the phone number return to the previous menu!" + ColorConsole.RESET);
            }
        } while (!password.equals(wantedUser.getPassword()));
        return wantedUser;
    }

    public void changePhoneNumber(NeoBank neoBank) {
        System.out.println(ColorConsole.CYAN + "Would you like to change your phone number? (previous phone number : " + ColorConsole.PURPLE + this.getPhoneNumber() + ColorConsole.CYAN + ")");
        String ans = Input.inputNextLine();
        if ("no".equalsIgnoreCase(ans)) {
            return;
        }
        do {
            if (!Input.checkInput(ans)) {
                return;
            } else if ("yes".equalsIgnoreCase(ans)) {
                System.out.println(ColorConsole.CYAN + "Write the phone number you have!" + ColorConsole.RESET);
                String phoneNumber;
                do {
                    phoneNumber = Input.inputNextLine();
                    if (!Input.checkInput(ans)) {
                        return;
                    }
                } while (!neoBank.existsPhoneNumber(phoneNumber) || !Input.checkPhoneNumber(phoneNumber));
                this.setPhoneNumber(phoneNumber);
                return;
            }
            System.out.println(ColorConsole.RED + "wrong input! try again" + ColorConsole.RESET);
            ans = Input.inputNextLine();
        } while (!"no".equalsIgnoreCase(ans));

    }

    public void changeSecurityNumber(NeoBank neoBank) {
        System.out.println(ColorConsole.CYAN + "Would you like to change your Social security number? (previous social security number : " + ColorConsole.PURPLE + this.getSecurityNumber() + ColorConsole.CYAN + ")");
        String ans = Input.inputNextLine();
        if ("no".equalsIgnoreCase(ans)) {
            return;
        }
        do {
            if (!Input.checkInput(ans)) {
                return;
            } else if ("yes".equalsIgnoreCase(ans)) {
                System.out.println(ColorConsole.CYAN + "Write the Social security number you have!" + ColorConsole.RESET);
                String securityNumber;
                do {
                    securityNumber = Input.inputNextLine();
                    if (!Input.checkInput(ans)) {
                        return;
                    }
                } while (!neoBank.existsPhoneNumber(securityNumber) && !Input.checkPhoneNumber(securityNumber));
                this.setSecurityNumber(securityNumber);
                break;
            } else {
                System.out.println(ColorConsole.RED + "wrong input! try again" + ColorConsole.RESET);
            }
            ans = Input.inputNextLine();
        } while (!"no".equalsIgnoreCase(ans));

    }

    public void changePassword() {
        System.out.println(ColorConsole.CYAN + "Would you like to change your password? (previous password : " + ColorConsole.PURPLE + this.getPassword() + ColorConsole.CYAN + ")");
        String ans = Input.inputNextLine();
        if ("no".equalsIgnoreCase(ans)) {
            return;
        }
        do {
            if (!Input.checkInput(ans)) {
                return;
            } else if ("yes".equalsIgnoreCase(ans)) {
                System.out.println(ColorConsole.CYAN + "Write the last password you like!" + ColorConsole.RESET);
                String password;
                do {
                    password = Input.inputNextLine();
                    if (!Input.checkInput(ans)) {
                        return;
                    }
                } while (!Input.checkPassword(password));
                this.setPassword(password);
                break;
            } else {
                System.out.println(ColorConsole.RED + "wrong input! try again" + ColorConsole.RESET);
            }
            ans = Input.inputNextLine();
        } while (!"no".equalsIgnoreCase(ans));

    }

    public void editSignUpInfo(NeoBank neoBank) {
        neoBank.getData().removeAuthentication(this.getSecurityNumber());
        this.changeName();
        this.changeLastName();
        this.changePhoneNumber(neoBank);
        this.changeSecurityNumber(neoBank);
        this.changePassword();
        neoBank.getData().addAuthentication(this.getSecurityNumber());
        this.setAuthenticated(new Authentication());
        System.out.println(ColorConsole.GREEN + "Sign Up info changed!" + ColorConsole.RESET);
    }

    public void showContacts() {
        for (int index = 1; index <= this.contacts.size() + 1; index++) {
            if (index == this.contacts.size() + 1) {
                break;
            }
            System.out.print(index);
            System.out.println(this.contacts.get(index - 1).toString());
        }
    }

    public void changeContactOption() {
        String changeCondition = "";
        if (this.isContactOption()) {
            changeCondition = "off";
        } else {
            changeCondition = "on";
        }
        System.out.println(ColorConsole.BLUE + "Would you like to turn it " + ColorConsole.PURPLE_BOLD + changeCondition + ColorConsole.BLUE + "?" + ColorConsole.RESET);
        String ans = Input.inputNextLine();
        if (!Input.checkInput(ans)) {
            return;
        } else if ("yes".equalsIgnoreCase(ans) && this.isContactOption()) {
            this.setContactOption(false);
        } else if ("yes".equalsIgnoreCase(ans)) {
            this.setContactOption(true);
        }
    }

    @Override
    public String toString() {
        return ColorConsole.PURPLE + "SimpleUser{" + super.toString() +
                ", Phone Number : " + phoneNumber +
                "} " + ColorConsole.RESET;
    }

    public void showUserInfo(NeoBank neoBank) {
        System.out.println(ColorConsole.PURPLE + "Name : " + ColorConsole.BLUE_BOLD + this.getName() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Last Name : " + ColorConsole.BLUE_BOLD + this.getSurname() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Phone Number : " + ColorConsole.BLUE_BOLD + this.getPhoneNumber() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Account Id : " + ColorConsole.BLUE_BOLD + this.getAccount().getAccountId() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Transactions : " + ColorConsole.RESET);
        this.getAccount().showTransaction(neoBank);
    }
}
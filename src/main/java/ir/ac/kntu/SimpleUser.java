package ir.ac.kntu;

import java.nio.channels.NonWritableChannelException;
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
    private boolean authenticated;
    private List<Person> recents;

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
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
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
    }

    public static void signUp(NeoBank neoBank, Data data) {
        System.out.println("Please Enter your name!");
        String name = Input.inputNextLine();
        if ("return".equalsIgnoreCase(name)) {
            return;
        } else if ("quit".equalsIgnoreCase(name)){
            System.exit(0);
        }
        System.out.println("Please Enter your lastname!");
        String lastName = Input.inputNextLine();
        if ("return".equalsIgnoreCase(lastName)) {
            return;
        } else if ("quit".equalsIgnoreCase(lastName)){
            System.out.println("Thanks for trusting our bank! Bye Bye!");
            System.exit(0);
        }
        System.out.println("Please Enter your phoneNumber!");
        String input;
        do {
            input = Input.inputNextLine();
            if ("return".equalsIgnoreCase(input)) {
                return;
            } else if ("quit".equalsIgnoreCase(input)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }
        } while (!Input.checkPhoneNumber(input) || !neoBank.existsPhoneNumber(input));
        String phoneNumber = input;
        System.out.println("Please Enter your social security number!");
        do {
            input = Input.inputNextLine();
            if ("return".equalsIgnoreCase(input)) {
                return;
            } else if ("quit".equalsIgnoreCase(input)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }
        } while (!Input.checkSecurityNumber(input) || !neoBank.existsSecurityNumber(input));
        String securityNumber = input;
        System.out.println("Please Enter your password!");
        do {
            input = Input.inputNextLine();
            if ("return".equalsIgnoreCase(input)) {
                return;
            } else if ("quit".equalsIgnoreCase(input)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }
        } while (!Input.checkPassword(input));
        String password = input;
        SimpleUser newUser = new SimpleUser(name, lastName, phoneNumber, securityNumber, password);
        neoBank.addSimpleUsers(newUser);
        data.addAuthentication(securityNumber);
        System.out.println("You have successfully signed up! Please sign in to access your account!\n\n");
        return;
    }

    public static SimpleUser getUserByPhone(NeoBank neoBank){
        System.out.println("Please Enter your phoneNumber!");
        String phoneNumber;
        int userIndex;
        do{
            phoneNumber = Input.inputNextLine();
            userIndex = neoBank.getSpecificUser(phoneNumber);
            if ("return".equalsIgnoreCase(phoneNumber)){
                return null;
            } else if ("quit".equalsIgnoreCase(phoneNumber)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if (userIndex==-1){
                System.out.println("This phone number doesn't exist in our database!");
            }
        }while(userIndex==-1);
        return neoBank.getSpecificUser(userIndex);
    }
    public static SimpleUser signIn(NeoBank neoBank, Data data) {
        SimpleUser wantedUser = getUserByPhone(neoBank);
        if (wantedUser==null){
            return null;
        }
        String password;
        do{
            password=Input.inputNextLine();
            if ("return".equalsIgnoreCase(password)){
                return null;
            } else if("quit".equalsIgnoreCase(password)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if(!password.equals(wantedUser.getPassword())){
                System.out.println("Wrong password! if you would like to change the phone number return to the previous menu!");
            }
        }while(!password.equals(wantedUser.getPassword()));
        return wantedUser;
    }


}

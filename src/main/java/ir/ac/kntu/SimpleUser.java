package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleUser extends Person{
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

    public SimpleUser(String name, String lastName,String phoneNumber, String securityNumber,String password){
        super(name,lastName);
        setSecurityNumber(securityNumber);
        setPassword(password);
        setPhoneNumber(phoneNumber);
    }

    public static boolean signUp(ArrayList<SimpleUser> users, Data data){
        System.out.println("Please Enter your name!");
        String name = Input.inputNextLine();
        if ("return".equalsIgnoreCase(name)){
            return false;
        }
        System.out.println("Please Enter your lastname!");
        String lastName = Input.inputNextLine();
        if ("return".equalsIgnoreCase(lastName)){
            return false;
        }
        System.out.println("Please Enter your phoneNumber!");
        String input;
        do{
            input=Input.inputNextLine();
            if ("return".equalsIgnoreCase(input)){
                return false;
            }
        }while(!Input.checkPhoneNumber(input) || !Input.existsPhoneNumber(input,users));
        String phoneNumber = input;
        System.out.println("Please Enter your social security number!");
        do{
            input =Input.inputNextLine();
            if ("return".equalsIgnoreCase(input)){
                return false;
            }
        }while(!Input.checkSecurityNumber(input));
        String securityNumber = input;
        System.out.println("Please Enter your password!");
        do{
            input=Input.inputNextLine();
            if ("return".equalsIgnoreCase(input)){
                return false;
            }
        }while(!Input.checkPassword(input));
        String password = input;
        SimpleUser newUser = new SimpleUser(name,lastName,phoneNumber,securityNumber,password);
        users.add(newUser);
        newUser.setAccount(new Account(newUser, users));
        data.addAuthentication(securityNumber);
        System.out.println("You have successfully signed up! Please sign in with your phone number and password later!");
        return true;
    }


}

package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class SimpleUser extends Person{
    private int securityNumber;
    private String password;
    private  Account account;
    private ArrayList<Transaction> transactions;
    private ArrayList<Contact> contacts;
    private ArrayList<Request> requests;
    private boolean contactOption;
    private boolean authenticated;
    private List<Person> recents;

    public int getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(int securityNumber) {
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
}

package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeoBank {
    private List<SimpleUser> simpleUsers;
    private List<Admin> admins;
    private Data data;
    private int tracingNumber;
    private final double wage = 1.5;

    public void addSimpleUsers(SimpleUser user) {
        this.simpleUsers.add(user);
    }

    public SimpleUser getSpecificUser(int index) {
        return this.simpleUsers.get(index);
    }

    public int getSpecificUser(String phoneNumber) {
        for (int index = 0; index < this.simpleUsers.size(); index++) {
            if (this.getSpecificUser(index).getPhoneNumber().equals(phoneNumber)) {
                return index;
            }
        }
        return -1;
    }

    public int getSpecificUserBySSN(String securityNumber) {
        for (int index = 0; index < this.simpleUsers.size(); index++) {
            if (this.getSpecificUser(index).getSecurityNumber().equals(securityNumber)) {
                return index;
            }
        }
        return -1;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public NeoBank() {
        simpleUsers = new ArrayList<>();
        admins = new ArrayList<>();
        Random random = new Random();
        tracingNumber = random.nextInt(8999999);
        data = new Data();
    }

    public void addAdmin(Admin admin) {
        this.admins.add(admin);
    }

    public void bank() {
        Menu.mainMenu(this);
    }

    public boolean existsSecurityNumber(String securityNumber) {
        if (this.simpleUsers.isEmpty()) {
            return true;
        }
        for (SimpleUser user : this.simpleUsers) {
            if (user.getSecurityNumber().equals(securityNumber)) {
                System.out.println("SomeBody with this social security number already an account! Please try again!");
                return false;
            }
        }
        return true;
    }

    public boolean existsPhoneNumber(String phoneNumber) {
        if (this.simpleUsers.isEmpty()) {
            return true;
        }
        for (SimpleUser user : this.simpleUsers) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("SomeBody with this phone number already exists! Please try again!");
                return false;
            }
        }
        return true;
    }

    public boolean existsAccountId(String accountID) {
        for (int index = 0; index < this.simpleUsers.size(); index++) {
            if (this.simpleUsers.get(index).getAccount() != null) {
                if (this.simpleUsers.get(index).getAccount().getAccountId().equals(accountID)) {
                    return false;
                }
            }
        }
        return true;
    }

    public SimpleUser getUserByAccountId(String accountID){
        for (int index = 0; index < this.simpleUsers.size(); index++) {
            if (this.simpleUsers.get(index).getAccount() != null) {
                if (this.simpleUsers.get(index).getAccount().getAccountId().equals(accountID)) {
                    return this.getSpecificUser(index);
                }
            }
        }
        return null;
    }

    public boolean existsCreditCard(String creditCardId) {
        for (int index = 0; index < this.simpleUsers.size() ; index++) {
            if (this.simpleUsers.get(index).getAccount() != null) {
                if (this.simpleUsers.get(index).getAccount().getCreditCard().getCreditCardId().equals(creditCardId)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getUsersSize() {
        return this.simpleUsers.size();
    }

    public Admin getSpecificAdmin(String userName) {
        for (int index = 0; index < this.admins.size(); index++) {
            if (this.admins.get(index).getSurname().equals(userName)) {
                return this.getSpecificAdmin(index);
            }
        }
        return null;
    }

    public Admin getSpecificAdmin(int index) {
        return this.admins.get(index);
    }

    public int getTracingNumber() {
        return tracingNumber;
    }

    public void setTracingNumber(int tracingNumber) {
        this.tracingNumber = tracingNumber;
    }

    public double getWage() {
        return wage;
    }
}

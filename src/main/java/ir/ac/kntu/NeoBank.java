package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class NeoBank {
    private List<SimpleUser> simpleUsers;
    private List<Admin> admins;
    private List<String> tracingNumbers;
    private Data data;

    public void addSimpleUsers(SimpleUser user) {
        this.simpleUsers.add(user);
    }

    public SimpleUser getSpecificUser(int index){
        return this.simpleUsers.get(index);
    }

    public int getSpecificUser(String phoneNumber){
        for (int index = 0 ; index < this.simpleUsers.size() ; index++){
            if (this.getSpecificUser(index).getPhoneNumber().equals(phoneNumber)){
                return index;
            }
        }
        return -1;
    }


    public List<Admin> getAdmins() {
        return admins;
    }

    public List<String> getTracingNumbers() {
        return tracingNumbers;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public NeoBank(List<Admin> admins) {
        simpleUsers = new ArrayList<>();
        admins = new ArrayList<>();
        tracingNumbers = new ArrayList<>();
        data = new Data();
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

    public boolean existsAccountId(String accountId) {
        for (int index = 0; index < this.simpleUsers.size() - 1; index++) {
            if (this.simpleUsers.get(index).getAccount().getAccountId().equals(accountId)) {
                return false;
            }
        }
        return true;
    }

    public boolean existsCreditCard(String creditCardId) {
        for (int index = 0; index < this.simpleUsers.size() - 1; index++) {
            if (this.simpleUsers.get(index).getAccount().getCreditCard().getCreditCardId().equals(creditCardId)) {
                return false;
            }
        }
        return true;
    }

    public int getUsersSize(){
        return this.simpleUsers.size();
    }

        public Admin getSpecificAdmin(String userName){
        for (int index = 0; index < this.admins.size(); index++){
            if (this.admins.get(index).getSurname().equals(userName)){
                return this.getSpecificAdmin(index);
            }
        }
        return null;
    }

    public Admin getSpecificAdmin(int index){
        return this.admins.get(index);
    }
}

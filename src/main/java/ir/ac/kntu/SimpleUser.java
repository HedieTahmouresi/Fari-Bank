package ir.ac.kntu;

import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleUser extends Person {
    private String phoneNumber;
    private String securityNumber;
    private String password;
    private Account account;
    private List<Contact> contacts;
    private List<Request> requests;
    private boolean contactOption;
    private Authentication authenticated;

    public void addRequest(Request request){
        this.requests.add(request);
    }

    public int requestSize(){
        return this.requests.size();
    }

    public Request getRequest(int index){
        return this.requests.get(index);
    }

    public int contactSize(){
        return this.contacts.size();
    }

    public void removeContact(Contact contact){
        this.contacts.remove(contact);
    }

    public Contact getSpecificContact(int index){
        return this.contacts.get(index);
    }

    public void addContact(Contact newContact){
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

    public Authentication getAuthenticated(){
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
        System.out.println("Please Enter your name!");
        String name = Input.inputNextLine();
        if ("return".equalsIgnoreCase(name)) {
            return;
        } else if ("quit".equalsIgnoreCase(name)){
            System.out.println("Thanks for trusting our bank! Bye Bye!");
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
        System.out.println("Please Enter your phone number!");
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
        System.out.println("Please Enter the phoneNumber!");
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
        System.out.println("Enter your password!");
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



    public void changePhoneNumber(NeoBank neoBank){
        System.out.println("Would you like to change your phone number? (previous phone number : " + this.getPhoneNumber() + ")");
        String ans = Input.inputNextLine();
        if (!"no".equalsIgnoreCase(ans)){
            do {
                if("return".equalsIgnoreCase(ans)){
                    return;
                }else if("quit".equalsIgnoreCase(ans)){
                    System.out.println("Thanks for trusting our bank! Bye Bye");
                    System.exit(0);
                } else if("yes".equalsIgnoreCase(ans)){
                    System.out.println("Write the phone number you have!");
                    String phoneNumber ;
                    do{
                        phoneNumber = Input.inputNextLine();
                        if ("return".equalsIgnoreCase(phoneNumber)) {
                            return;
                        } else if ("quit".equalsIgnoreCase(phoneNumber)){
                            System.out.println("Thanks for trusting our bank! Bye Bye!");
                            System.exit(0);
                        }
                    }while (!neoBank.existsPhoneNumber(phoneNumber) || !Input.checkPhoneNumber(phoneNumber));
                    this.setPhoneNumber(phoneNumber);
                    break;
                }else{
                    System.out.println("wrong input! try again");
                }
                ans = Input.inputNextLine();
            }while(!"no".equalsIgnoreCase(ans));
        }
    }

    public void changeSecurityNumber(NeoBank neoBank){
        System.out.println("Would you like to change your Social security number? (previous social security number : " + this.getSecurityNumber() + ")");
        String ans = Input.inputNextLine();
        if (!"no".equalsIgnoreCase(ans)){
            do {
                if("return".equalsIgnoreCase(ans)){
                    return;
                }else if("quit".equalsIgnoreCase(ans)){
                    System.out.println("Thanks for trusting our bank! Bye Bye");
                    System.exit(0);
                } else if("yes".equalsIgnoreCase(ans)){
                    System.out.println("Write the Social security number you have!");
                    String securityNumber;
                    do{
                        securityNumber = Input.inputNextLine();
                        if ("return".equalsIgnoreCase(securityNumber)) {
                            return;
                        } else if ("quit".equalsIgnoreCase(securityNumber)){
                            System.out.println("Thanks for trusting our bank! Bye Bye!");
                            System.exit(0);
                        }
                    }while (!neoBank.existsPhoneNumber(securityNumber) && !Input.checkPhoneNumber(securityNumber));
                    this.setSecurityNumber(securityNumber);
                    break;
                }else{
                    System.out.println("wrong input! try again");
                }
                ans = Input.inputNextLine();
            }while(!"no".equalsIgnoreCase(ans));
        }
    }

    public void changePassword(){
        System.out.println("Would you like to change your password? (previous password : " + this.getPassword() + ")");
        String ans = Input.inputNextLine();
        if ("no".equalsIgnoreCase(ans)){
            return;
        } else{
            do {
                if("return".equalsIgnoreCase(ans)){
                    return;
                }else if("quit".equalsIgnoreCase(ans)){
                    System.out.println("Thanks for trusting our bank! Bye Bye");
                    System.exit(0);
                } else if("yes".equalsIgnoreCase(ans)){
                    System.out.println("Write the last password you like!");
                    String password;
                    do {
                        password =Input.inputNextLine();
                        if("return".equalsIgnoreCase(password)){
                            return;
                        }else if("quit".equalsIgnoreCase(password)){
                            System.out.println("Thanks for trusting our bank! Bye Bye!");
                            System.exit(0);
                        }
                    }while (!Input.checkPassword(password));
                    this.setPassword(password);
                    break;
                }else{
                    System.out.println("wrong input! try again");
                }
                ans=Input.inputNextLine();
            }while(!"no".equalsIgnoreCase(ans));
        }
    }

    public void editSignUpInfo(NeoBank neoBank){
        neoBank.getData().removeAuthentication(this.getSecurityNumber());
        this.changeName();
        this.changeLastName();
        this.changePhoneNumber(neoBank);
        this.changeSecurityNumber(neoBank);
        this.changePassword();
        neoBank.getData().addAuthentication(this.getSecurityNumber());
        this.setAuthenticated(new Authentication());
        System.out.println("Sign Up info changed!");
    }

    public void showContacts(){
        for (int index = 1 ; index <= this.contacts.size()+1 ; index++){
            if (index == this.contacts.size()+1){
                break;
            }
            System.out.print(index);
            System.out.println(this.contacts.get(index-1).toString());
        }
    }

    public void changeContactOption(){
        String ans;
        if (this.isContactOption()) {
            System.out.println("Would you like to turn it off?");
            ans = Input.inputNextLine();
            if ("yes".equalsIgnoreCase(ans)){
                this.setContactOption(false);
            } else if ("no".equalsIgnoreCase(ans)){
                this.setContactOption(true);
            }else if("return".equalsIgnoreCase(ans)){
                return;
            }else if("quit".equalsIgnoreCase(ans)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }
        } else{
            System.out.println("Would you like to turn it on?");
            ans = Input.inputNextLine();
            if ("yes".equalsIgnoreCase(ans)){
                this.setContactOption(true);
            } else if ("no".equalsIgnoreCase(ans)){
                this.setContactOption(false);
            }else if("return".equalsIgnoreCase(ans)){
                return;
            }else if("quit".equalsIgnoreCase(ans)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }
        }
    }
}
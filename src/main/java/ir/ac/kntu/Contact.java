package ir.ac.kntu;

import jdk.dynalink.support.SimpleRelinkableCallSite;

import java.util.ArrayList;
import java.util.List;

public class Contact extends Person {
    private String phoneNumber;

    public Contact(String name, String lastName, String phoneNumber) {
        super(name, lastName);
        setPhoneNumber(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static boolean existsContact(SimpleUser user, String phoneNumber){
        for (int i = 0 ; i < user.contactSize() ; i++){
            if (user.getSpecificContact(i).getPhoneNumber().equals(phoneNumber)){
                return false;
            }
        }
        return true;
    }

    public static Contact getContact(SimpleUser user, String phoneNumber){
        for (int i = 0 ; i < user.contactSize() ; i++){
            if (user.getSpecificContact(i).getPhoneNumber().equals(phoneNumber)){
                return user.getSpecificContact(i);
            }
        }
        return null;
    }

    public static void addContacts(NeoBank neoBank, SimpleUser user){
        System.out.println("Please Enter the name of your new contact");
        String name = Input.inputNextLine();
        System.out.println("Please Enter the last name of your new contact");
        String lastName = Input.inputNextLine();
        System.out.println("Please Enter the phone number of your new contact");
        String phoneNumber = Input.inputNextLine();
        if (neoBank.getSpecificUser(phoneNumber)!=-1 ){
            if (existsContact(user, phoneNumber)) {
                if (neoBank.getSpecificUser(neoBank.getSpecificUser(phoneNumber)).isAuthenticated()) {
                    Contact newContact = new Contact(name, lastName, phoneNumber);
                    user.addContact(newContact);
                    System.out.println("Contact successfully added");
                } else {
                    System.out.println("This user hasn't been authenticated so they dont have an account!");
                }
            }else{
                System.out.println("You already have a contact with this number! check your contact list!");
            }
        }else {
            System.out.println("This user doesn't exist!");
        }
    }

    @Override
    public String toString() {
        return ". Name : " + this.getName() + ", Last name : " + this.getSurname() ;
    }

    public void showInfo(){
        System.out.println(this.toString());
        System.out.println("Phone Number : " + this.getPhoneNumber());
    }

    public static Contact selectContact(NeoBank neoBank, SimpleUser user){
        String input;
        List<String> securityNumbers = new ArrayList<>();
        Contact currContact;
        do{
            user.showContacts();
            if (user.contactSize()==0){
                return null;
            }
            input=Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return null;
            }else if (!input.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Wrong input try again!");
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<user.contactSize()+1){
                for (int index = 1 ; index < user.contactSize()+1 ; index++){
                    if (input.equals(Integer.toString(index))){
                        currContact = user.getSpecificContact(index-1);
                        return currContact;
                    }
                }
            } else{
                System.out.println("Wrong input try again!");
            }
        }while (!"retrun".equalsIgnoreCase(input));
        return null;
    }

    public void changePhoneNumber(NeoBank neoBank, SimpleUser user){
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
                        } else if (!neoBank.getSpecificUser(neoBank.getSpecificUser(phoneNumber)).isAuthenticated()){
                            System.out.println("User not authenticated!");
                        } else if (neoBank.getSpecificUser(phoneNumber)==-1){
                            System.out.println("User doesn't exist");
                        } else if(!Input.checkPhoneNumber(phoneNumber)){
                            System.out.println("phone number invalid!!!");
                        } else if (!existsContact(user, phoneNumber)){
                            System.out.println("You already have a contact with this number! check your contact list!");
                        }
                    }while (!existsContact(user, phoneNumber));
                    this.setPhoneNumber(phoneNumber);
                    return;
                }else{
                    System.out.println("wrong input! try again");
                }
                ans = Input.inputNextLine();
            }while(!"no".equalsIgnoreCase(ans));
        }
    }

    public void changeContactInfo(NeoBank neoBank, SimpleUser user){
        System.out.println("Would you like to edit your contact?");
        System.out.println("   1.yes");
        System.out.println("   2.no");
        String input = Input.inputNextLine();
        switch (input){
            case "1", "yes":
                this.changeName();
                this.changeLastName();
                this.changePhoneNumber(neoBank, user);
                System.out.println("Contact successfully changed");
                break;
            case "2", "no":
                break;
            default:
                if("return".equalsIgnoreCase(input)){
                    return;
                } else if("quit".equalsIgnoreCase(input)){
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                } else{
                    System.out.println("Wrong input!");
                }
                break;
        }
    }

    public void deleteContact(SimpleUser user){
        System.out.println("Would you like to delete your contact?");
        System.out.println("   1.yes");
        System.out.println("   2.no");
        String input = Input.inputNextLine();
        switch (input) {
            case "1", "yes":
                user.removeContact(this);
                break;
            case "2", "no":
                break;
            default:
                if ("return".equalsIgnoreCase(input)) {
                    return;
                } else if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                } else {
                    System.out.println("Wrong input!");
                }
                break;
        }
    }
}

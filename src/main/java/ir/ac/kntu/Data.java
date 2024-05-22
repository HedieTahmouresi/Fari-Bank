package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private List<Request> requests;
    private Map<String, Boolean> authentications;
    private List<SimpleUser> users;

    public Data() {
        this.requests = new ArrayList<>();
        this.authentications = new HashMap<>();
        this.users = new ArrayList<>();
    }

    public void addAuthentication(String securityNumber) {
        this.authentications.put(securityNumber, false);
    }

    public void removeAuthentication(String securityNumber) {
        this.authentications.remove(securityNumber);
    }

    public List<Request> getRequests() {
        List<Request> requests1 = new ArrayList<>();
        requests1 = this.requests;
        return requests1;
    }

    public Map<String, Boolean> getAuthentications() {
        Map<String, Boolean> authentications1 = new HashMap<>();
        authentications1 = this.authentications;
        return authentications1;
    }

    public List<String> displayAuthentications(){
        int index = 1;
        List<String> securityNumbers = new ArrayList<>();
        if(this.authentications.isEmpty()){
            System.out.println("There are no authentication requests");
            return null;
        }
        for (Map.Entry<String, Boolean> entry : this.authentications.entrySet()){
            String key = entry.getKey();
            System.out.println(index + ". SSN: " + key);
            securityNumbers.add(key);
            index++;
        }
        return securityNumbers;
    }

    public void selectUser(NeoBank neoBank){
        String input;
        List<String> securityNumbers = new ArrayList<>();
        do{
            securityNumbers = this.displayAuthentications();
            if (securityNumbers==null){
                return;
            }
            input=Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return;
            }else if (!input.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Wrong input try again!");
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<this.authentications.size()+1){
                for (int index = 1 ; index < this.authentications.size()+1 ; index++){
                    if (input.equals(Integer.toString(index))){
                        this.authenticateUser(neoBank,securityNumbers.get(index-1));
                        break;
                    }
                }
            } else{
                System.out.println("Wrong input try again!");
            }
        }while (!"retrun".equalsIgnoreCase(input));
    }

    public void printUserInfo(SimpleUser user){
        System.out.println("name : " + user.getName());
        System.out.println("last name : "+ user.getSurname());
        System.out.println("phone number : "+ user.getPhoneNumber());
        System.out.println("social security number : " + user.getSecurityNumber());
        System.out.println("password : " + user.getPassword());
    }

    public void authenticateUser(NeoBank neoBank, String securityNumber){
        int userIndex = neoBank.getSpecificUserBySSN(securityNumber);
        SimpleUser user = neoBank.getSpecificUser(userIndex);
        this.printUserInfo(user);
        System.out.println("would you like to authenticate this user?");
        String answer = Input.inputNextLine();
        if("yes".equalsIgnoreCase(answer)){
            user.setAuthenticated(true);
            this.removeAuthentication(securityNumber);
            System.out.println("User successfully authenticated!");
            user.setAccount(new Account(user, neoBank));
        }else if("quit".equalsIgnoreCase(answer)){
            System.out.println("Thanks for trusting our Bank! Bye Bye!");
            System.exit(0);
        }
    }
}

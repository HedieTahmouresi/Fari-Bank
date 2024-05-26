package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private List<Request> requests;
    private Map<String, Authentication> authentications;
    private List<SimpleUser> users;

    public Data() {
        this.requests = new ArrayList<>();
        this.authentications = new HashMap<>();
        this.users = new ArrayList<>();
    }

    public void addAuthentication(String securityNumber) {
        this.authentications.put(securityNumber, new Authentication());
    }

    public void removeAuthentication(String securityNumber) {
        this.authentications.remove(securityNumber);
    }

    public void addRequest(Request request){
        this.requests.add(request);
    }

    public void removeRequest(Request request){
        this.requests.remove(request);
    }

    public List<String> displayAuthentications(){
        int index = 1;
        List<String> securityNumbers = new ArrayList<>();
        if(this.authentications.isEmpty()){
            System.out.println("There are no authentication requests");
            return null;
        }
        for (Map.Entry<String, Authentication> entry : this.authentications.entrySet()){
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
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<securityNumbers.size()+1){
                for (int index = 1 ; index < securityNumbers.size()+1 ; index++){
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
            user.getAuthenticated().acceptAuthentication();
            this.removeAuthentication(securityNumber);
            System.out.println("User successfully authenticated!");
            user.setAccount(new Account(user, neoBank));
        }else if("quit".equalsIgnoreCase(answer)){
            System.out.println("Thanks for trusting our Bank! Bye Bye!");
            System.exit(0);
        }else if("no".equalsIgnoreCase(answer)){
            user.getAuthenticated().rejectAuthentication();
        }
    }

    public void displayRequests(){
        int index = 1;
        if(this.requests.isEmpty()){
            System.out.println("There are no requests");
            return;
        }
        for (Request request : this.requests){
            System.out.println(index + ". SSN : " + request.getSecurityNumber() + request.toString());
            index++;
        }
    }


    public void selectRequest(NeoBank neoBank){
        String input;
        do{
            this.displayRequests();
            input=Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return;
            }else if (!input.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Wrong input try again!");
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<this.requests.size()+1){
                for (int index = 1 ; index < this.requests.size()+1 ; index++){
                    if (input.equals(Integer.toString(index))){
                        processRequest(neoBank, this.requests.get(index-1));
                    }
                }
            } else{
                System.out.println("Wrong input try again!");
            }
        }while (!"retrun".equalsIgnoreCase(input));
    }

    public void processRequest(NeoBank neoBank,Request request){
        SimpleUser user = neoBank.getSpecificUser(neoBank.getSpecificUserBySSN(request.getSecurityNumber()));
        request.showRequestInfo();
        System.out.println("Would you like to process this request?");
        String answer = Input.inputNextLine();
        switch (answer){
            case "yes":
                request.setStatus(RequestStatus.IN_PROCESS);
                this.closingRequest(request);
                break;
            case "no":
                return;
            default:
                if ("return".equalsIgnoreCase(answer)){
                    return;
                }else if (!"quit".equalsIgnoreCase(answer)) {
                    System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                    break;
                } else{
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                }
                break;
        }
    }

    public void closingRequest(Request request){
        System.out.println("Would you like to answer this request?");
        String input = Input.inputNextLine();
        switch (input){
            case "yes":
                System.out.println("Please enter your answer!");
                String answer = Input.inputNextLine();
                if ("return".equalsIgnoreCase(answer)){
                    return;
                }else if ("quit".equalsIgnoreCase(answer)) {
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                }
                request.setStatus(RequestStatus.PROCESSED);
                request.setAnswer(answer);
                break;
            case "no":
                return;
            default:
                if ("return".equalsIgnoreCase(input)){
                    return;
                }else if (!"quit".equalsIgnoreCase(input)) {
                    System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                    break;
                } else{
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                }
                break;
        }
    }

}

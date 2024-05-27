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

    public void addRequest(Request request) {
        this.requests.add(request);
    }

    public void removeRequest(Request request) {
        this.requests.remove(request);
    }

    public List<String> displayAuthentications() {
        int index = 1;
        List<String> securityNumbers = new ArrayList<>();
        if (this.authentications.isEmpty()) {
            System.out.println("There are no authentication requests");
            return null;
        }
        for (Map.Entry<String, Authentication> entry : this.authentications.entrySet()) {
            String key = entry.getKey();
            System.out.println(index + ". SSN: " + key);
            securityNumbers.add(key);
            index++;
        }
        return securityNumbers;
    }

    public void selectUser(NeoBank neoBank) {
        String input;
        List<String> securityNumbers = new ArrayList<>();
        do {
            securityNumbers = this.displayAuthentications();
            if (securityNumbers == null) {
                return;
            }
            input = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)) {
                return;
            } else if (!input.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println("Wrong input try again!");
            } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < securityNumbers.size() + 1) {
                for (int index = 1; index < securityNumbers.size() + 1; index++) {
                    if (input.equals(Integer.toString(index))) {
                        this.authenticateUser(neoBank, securityNumbers.get(index - 1));
                        break;
                    }
                }
            } else {
                System.out.println("Wrong input try again!");
            }
        } while (!"retrun".equalsIgnoreCase(input));
    }

    public void printUserInfo(SimpleUser user) {
        System.out.println("name : " + user.getName());
        System.out.println("last name : " + user.getSurname());
        System.out.println("phone number : " + user.getPhoneNumber());
        System.out.println("social security number : " + user.getSecurityNumber());
        System.out.println("password : " + user.getPassword());
    }

    public void authenticateUser(NeoBank neoBank, String securityNumber) {
        int userIndex = neoBank.getSpecificUserBySSN(securityNumber);
        SimpleUser user = neoBank.getSpecificUser(userIndex);
        this.printUserInfo(user);
        System.out.println("would you like to authenticate this user?");
        String answer = Input.inputNextLine();
        if ("yes".equalsIgnoreCase(answer)) {
            user.getAuthenticated().acceptAuthentication();
            this.removeAuthentication(securityNumber);
            System.out.println("User successfully authenticated!");
            user.setAccount(new Account(user, neoBank));
        } else if ("quit".equalsIgnoreCase(answer)) {
            System.out.println("Thanks for trusting our Bank! Bye Bye!");
            System.exit(0);
        } else if ("no".equalsIgnoreCase(answer)) {
            user.getAuthenticated().rejectAuthentication();
        }
    }

    public List<Request> displayRequests() {
        List<Request> list = new ArrayList<>();
        int index = 1;
        if (this.requests.isEmpty()) {
            System.out.println("There are no requests");
            return null;
        }
        for (Request request : this.requests) {
            System.out.println(index + ". SSN : " + request.getSecurityNumber() + " " + request.toString());
            list.add(request);
            index++;
        }
        return list;
    }


    public List<Request> displayRequestsBySection(RequestSection section) {
        List<Request> list = new ArrayList<>();
        int index = 1;
        if (this.requests.isEmpty()) {
            System.out.println("There are no requests");
            return null;
        }
        if (section==null){
            return null;
        }
        for (Request request : this.requests) {
            if (request.getSection().equals(section)) {
                System.out.println(index + ". SSN : " + request.getSecurityNumber() + " " + request.toString());
                list.add(request);
                index++;
            }
        }
        return list;
    }

    public List<Request> displayRequestsByPerson(String securityNumber) {
        int index = 1;
        List<Request> list = new ArrayList<>();
        if (this.requests.isEmpty()) {
            System.out.println("There are no requests");
            return null;
        }
        if (securityNumber==null){
            return null;
        }
        for (Request request : this.requests) {
            if (request.getSecurityNumber().equals(securityNumber)) {
                System.out.println(index + ". SSN : " + request.getSecurityNumber() + " " + request.toString());
                list.add(request);
                index++;
            }
        }
        return list;
    }

    public List<Request> displayRequestsByStatus(RequestStatus status) {
        List<Request> list = new ArrayList<>();
        int index = 1;
        if (this.requests.isEmpty()) {
            System.out.println("There are no requests");
            return null;
        }
        if (status==null){
            return null;
        }
        for (Request request : this.requests) {
            if (request.getStatus().equals(status)) {
                System.out.println(index + ". SSN : " + request.getSecurityNumber() + " " + request.toString());
                list.add(request);
                index++;
            }
        }
        return list;
    }

    public void selectRequest(NeoBank neoBank, List<Request> requestsList) {
        String input;
        input = Input.inputNextLine();
        if (requestsList==null){
            return;
        }
        if ("quit".equalsIgnoreCase(input)) {
            System.out.println("Thanks for trusting our bank! Bye Bye!");
            System.exit(0);
        } else if ("return".equalsIgnoreCase(input)) {
            return;
        } else if (!input.matches("-?\\d+(\\.\\d+)?")) {
            System.out.println("Wrong input try again!");
        } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < requestsList.size() + 1) {
            for (int index = 1; index < requestsList.size() + 1; index++) {
                if (input.equals(Integer.toString(index))) {
                    if (RequestStatus.IN_PROCESS.equals(requestsList.get(index-1).getStatus())){
                        this.closingRequest(requestsList.get(index-1));
                    } else if (RequestStatus.NOTED.equals(requestsList.get(index-1).getStatus())) {
                        processRequest(neoBank, requestsList.get(index - 1));
                    }
                }
            }
        } else {
            System.out.println("Wrong input try again!");
        }

    }

    public void processRequest(NeoBank neoBank, Request request) {
        SimpleUser user = neoBank.getSpecificUser(neoBank.getSpecificUserBySSN(request.getSecurityNumber()));
        request.showRequestInfo();
        System.out.println("Would you like to process this request?");
        String answer = Input.inputNextLine();
        switch (answer) {
            case "yes":
                request.setStatus(RequestStatus.IN_PROCESS);
                this.closingRequest(request);
                break;
            case "no":
                return;
            default:
                if ("return".equalsIgnoreCase(answer)) {
                    return;
                } else if (!"quit".equalsIgnoreCase(answer)) {
                    System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                    break;
                } else {
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                }
                break;
        }
    }

    public void closingRequest(Request request) {
        System.out.println("Would you like to answer this request?");
        String input = Input.inputNextLine();
        switch (input) {
            case "yes":
                System.out.println("Please enter your answer!");
                String answer = Input.inputNextLine();
                if ("return".equalsIgnoreCase(answer)) {
                    return;
                } else if ("quit".equalsIgnoreCase(answer)) {
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                }
                request.setStatus(RequestStatus.PROCESSED);
                request.setAnswer(answer);
                break;
            case "no":
                return;
            default:
                if ("return".equalsIgnoreCase(input)) {
                    return;
                } else if (!"quit".equalsIgnoreCase(input)) {
                    System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                    break;
                } else {
                    System.out.println("Thanks for trusting our bank! Bye Bye!");
                    System.exit(0);
                }
                break;
        }
    }

    public void showRequest(NeoBank neoBank) {
        String answer;
        do {
            displayRequestMenu();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "Show All requests":
                    this.selectRequest(neoBank, this.displayRequests());
                    break;
                case "2", "Show requests by Person":
                    this.selectRequest(neoBank, this.displayRequestsByPerson(choosePerson(neoBank)));
                    break;
                case "3", "Show requests by Section":
                    this.selectRequest(neoBank, this.displayRequestsBySection(chooseSection()));
                    break;
                case "4", "Show requests by Status":
                    this.selectRequest(neoBank, this.displayRequestsByStatus(chooseStatus()));
                    return;
                case "5", "Return":
                    return;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(answer));
    }

    public void displayRequestMenu() {
        System.out.println("What would you like to do?");
        System.out.println("   1.Show All requests");
        System.out.println("   2.Show requests by Person");
        System.out.println("   3.Show requests by Section");
        System.out.println("   4.Show requests by Status");
        System.out.println("   5.Return");
    }

    public RequestSection chooseSection(){
        RequestSection section = RequestSection.MANAGEMENT;
        System.out.println("Choose");
        System.out.println("   1.Management");
        System.out.println("   2.Contacts");
        System.out.println("   3.Transfer");
        System.out.println("   4.Settings");
        String ans;
        do{
            ans = Input.inputNextLine();
            switch(ans){
                case "1", "Management":
                    return section;
                case "2", "Contacts":
                    section = RequestSection.CONTACTS;
                    return section;
                case "3", "Administer":
                    section = RequestSection.TRANSFER;
                    return section;
                case "4", "Settings":
                    section = RequestSection.SETTINGS;
                    return section;
                default:
                    if (!ans.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(ans));
        return section;
    }

    public RequestStatus chooseStatus(){
        RequestStatus status= RequestStatus.NOTED;
        System.out.println("Choose");
        System.out.println("   1.Noted");
        System.out.println("   2.In Process");
        System.out.println("   3.Processed");
        String ans;
        do{
            ans = Input.inputNextLine();
            switch(ans){
                case "1", "Noted":
                    return status;
                case "2", "In Process":
                    status = RequestStatus.IN_PROCESS;
                    return status;
                case "3", "Processed":
                    status = RequestStatus.PROCESSED;
                    return status;
                default:
                    if ("return".equalsIgnoreCase(ans)){
                        return null;
                    }else if (!ans.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                        break;
                    } else {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        } while (!"quit".equalsIgnoreCase(ans));
        return status;
    }

    public String choosePerson(NeoBank neoBank){
        System.out.println("Please enter the social security number of the person");
        String ans;
        do{
            ans =Input.inputNextLine();
            if ("return".equalsIgnoreCase(ans)){
                return null;
            }else if (!neoBank.checkSSN(ans)){
                System.out.println("This user doesn't exist");
            }else if (!ans.equalsIgnoreCase("quit")) {
                System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                break;
            } else {
                break;
            }
        }while(!"return".equalsIgnoreCase(ans));
        return ans;
    }

}

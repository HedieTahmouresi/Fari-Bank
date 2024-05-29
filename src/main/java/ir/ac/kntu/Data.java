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

    public void addSimpleUser(SimpleUser user) {
        this.users.add(user);
    }

    public int usersSize() {
        return this.users.size();
    }

    public SimpleUser getUser(int index) {
        return this.users.get(index);
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

    public List<String> displayAuthentications() {
        int index = 1;
        List<String> securityNumbers = new ArrayList<>();
        if (this.authentications.isEmpty()) {
            System.out.println(ColorConsole.PINK + "There are no authentication requests" + ColorConsole.RESET);
            return null;
        }
        for (Map.Entry<String, Authentication> entry : this.authentications.entrySet()) {
            String key = entry.getKey();
            System.out.println(ColorConsole.PINK + index + ". SSN: " + ColorConsole.PURPLE + key + ColorConsole.RESET);
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
            if (!Input.checkInput(input)) {
                return;
            } else if (!input.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
            } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < securityNumbers.size() + 1) {
                for (int index = 1; index < securityNumbers.size() + 1; index++) {
                    if (input.equals(Integer.toString(index))) {
                        this.authenticateUser(neoBank, securityNumbers.get(index - 1));
                        break;
                    }
                }
            } else {
                System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
            }
        } while (!"retrun".equalsIgnoreCase(input));
    }

    public void printUserInfo(SimpleUser user) {
        System.out.println(ColorConsole.BLUE + "name : " + ColorConsole.CYAN_BOLD + user.getName());
        System.out.println(ColorConsole.BLUE + "last name : " + ColorConsole.CYAN_BOLD + user.getSurname());
        System.out.println(ColorConsole.BLUE + "phone number : " + ColorConsole.CYAN_BOLD + user.getPhoneNumber());
        System.out.println(ColorConsole.BLUE + "social security number : " + ColorConsole.CYAN_BOLD + user.getSecurityNumber());
        System.out.println(ColorConsole.BLUE + "password : " + ColorConsole.CYAN_BOLD + user.getPassword() + ColorConsole.RESET);
    }

    public void authenticateUser(NeoBank neoBank, String securityNumber) {
        int userIndex = neoBank.getSpecificUserBySSN(securityNumber);
        SimpleUser user = neoBank.getSpecificUser(userIndex);
        this.printUserInfo(user);
        System.out.println(ColorConsole.GREEN_BOLD + "would you like to authenticate this user?" + ColorConsole.RESET);
        String answer = Input.inputNextLine();
        if ("yes".equalsIgnoreCase(answer)) {
            user.getAuthenticated().acceptAuthentication();
            this.removeAuthentication(securityNumber);
            System.out.println(ColorConsole.GREEN_BOLD + "User successfully authenticated!" + ColorConsole.RESET);
            this.addSimpleUser(user);
            user.setAccount(new Account(user, neoBank));
        } else if ("quit".equalsIgnoreCase(answer)) {
            System.out.println(ColorConsole.PURPLE + "Thanks for trusting our Bank! Bye Bye!" + ColorConsole.RESET);
            System.exit(0);
        } else if ("no".equalsIgnoreCase(answer)) {
            user.getAuthenticated().rejectAuthentication();
        } else {
            System.out.println(ColorConsole.RED + "Wrong input" + ColorConsole.RESET);
        }
    }

    public List<Request> displayRequests() {
        List<Request> list = new ArrayList<>();
        int index = 1;
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        for (Request request : this.requests) {
            System.out.println(ColorConsole.PURPLE + index + ". SSN : " + ColorConsole.PINK + request.getSecurityNumber() + " " + request.toString() + ColorConsole.RESET);
            list.add(request);
            index++;
        }
        return list;
    }


    public List<Request> displayRequestsBySection() {
        RequestSection section = chooseSection();
        List<Request> list = new ArrayList<>();
        int index = 1;
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        if (section == null) {
            return null;
        }
        for (Request request : this.requests) {
            if (request.getSection().equals(section)) {
                System.out.println(ColorConsole.PURPLE + index + ". SSN : " + ColorConsole.PINK + request.getSecurityNumber() + " " + request.toString() + ColorConsole.RESET);
                list.add(request);
                index++;
            }
        }
        return list;
    }

    public List<Request> displayRequestsByPerson(NeoBank neoBank) {
        int index = 1;
        List<Request> list = new ArrayList<>();
        String securityNumber = choosePerson(neoBank);
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        if (securityNumber == null) {
            return null;
        }
        for (Request request : this.requests) {
            if (request.getSecurityNumber().equals(securityNumber)) {
                System.out.println(ColorConsole.PURPLE + index + ". SSN : " + ColorConsole.PINK + request.getSecurityNumber() + " " + request.toString() + ColorConsole.RESET);
                list.add(request);
                index++;
            }
        }
        return list;
    }

    public List<Request> displayRequestsByStatus() {
        RequestStatus status = chooseStatus();
        List<Request> list = new ArrayList<>();
        int index = 1;
        if (this.requests.isEmpty()) {
            System.out.println(ColorConsole.RED + "There are no requests" + ColorConsole.RESET);
            return null;
        }
        if (status == null) {
            return null;
        }
        for (Request request : this.requests) {
            if (request.getStatus().equals(status)) {
                System.out.println(ColorConsole.PURPLE + index + ". SSN : " + ColorConsole.PINK + request.getSecurityNumber() + " " + request.toString() + ColorConsole.RESET);
                list.add(request);
                index++;
            }
        }
        return list;
    }

    public void selectRequest(NeoBank neoBank, List<Request> requestsList) {
        String input;
        input = Input.inputNextLine();
        if (requestsList == null) {
            return;
        }
        if (!Input.checkInput(input)) {
            return;
        } else if (!input.matches("-?\\d+(\\.\\d+)?") && Input.checkInput(input)) {
            System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < requestsList.size() + 1) {
            for (int index = 1; index < requestsList.size() + 1; index++) {
                if (input.equals(Integer.toString(index))) {
                    if (RequestStatus.IN_PROCESS.equals(requestsList.get(index - 1).getStatus())) {
                        this.closingRequest(requestsList.get(index - 1));
                        return;
                    } else if (RequestStatus.NOTED.equals(requestsList.get(index - 1).getStatus())) {
                        processRequest(neoBank, requestsList.get(index - 1));
                        return;
                    }
                    return;
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
        }

    }

    public void processRequest(NeoBank neoBank, Request request) {
        request.showRequestInfo();
        System.out.println(ColorConsole.BLUE + "Would you like to process this request?" + ColorConsole.RESET);
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
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                } else {
                    System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                    System.exit(0);
                }
                break;
        }
    }

    public void closingRequest(Request request) {
        System.out.println(ColorConsole.BLUE + "Would you like to answer this request?" + ColorConsole.RESET);
        String input = Input.inputNextLine();
        switch (input) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Please enter your answer!" + ColorConsole.RESET);
                String answer = Input.inputNextLine();
                if (!Input.checkInput(answer)) {
                    return;
                }
                request.setStatus(RequestStatus.PROCESSED);
                request.setAnswer(answer);
                break;
            case "no":
                return;
            default:
                if (Input.checkInput(input)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                    break;
                }
        }
    }

    public void showRequest(NeoBank neoBank) {
        String answer;
        displayRequestMenu();
        answer = Input.inputNextLine();
        switch (answer) {
            case "1", "Show All requests":
                this.selectRequest(neoBank, this.displayRequests());
                break;
            case "2", "Show requests by Person":
                this.selectRequest(neoBank, this.displayRequestsByPerson(neoBank));
                break;
            case "3", "Show requests by Section":
                this.selectRequest(neoBank, this.displayRequestsBySection());
                break;
            case "4", "Show requests by Status":
                this.selectRequest(neoBank, this.displayRequestsByStatus());
                break;
            case "5", "Return":
                return;
            default:
                if (Input.checkInput(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                }
                break;
        }
        showRequest(neoBank);
    }

    public void displayRequestMenu() {
        System.out.println(ColorConsole.CYAN + "What would you like to do?");
        System.out.println("   1.Show All requests");
        System.out.println("   2.Show requests by Person");
        System.out.println("   3.Show requests by Section");
        System.out.println("   4.Show requests by Status");
        System.out.println("   5.Return" + ColorConsole.RESET);
    }

    public RequestSection chooseSection() {
        System.out.println(ColorConsole.BLUE + "Choose");
        System.out.println("   1.Management\n   2.Contacts\n   3.Transfer\n   4.Settings" + ColorConsole.RESET);
        String ans;
        do {
            ans = Input.inputNextLine();
            switch (ans) {
                case "1", "Management":
                    return RequestSection.MANAGEMENT;
                case "2", "Contacts":
                    return RequestSection.CONTACTS;
                case "3", "Administer":
                    return RequestSection.TRANSFER;
                case "4", "Settings":
                    return RequestSection.SETTINGS;
                default:
                    if (!Input.checkInput(ans)) {
                        return null;
                    } else {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    }
            }
        } while (!"quit".equalsIgnoreCase(ans));
        return null;
    }

    public RequestStatus chooseStatus() {
        System.out.println(ColorConsole.CYAN + "Choose:\n   1.Noted\n   2.In Process\n   3.Processed" + ColorConsole.RESET);
        String ans;
        do {
            ans = Input.inputNextLine();
            switch (ans) {
                case "1", "Noted":
                    return RequestStatus.NOTED;
                case "2", "In Process":
                    return RequestStatus.IN_PROCESS;
                case "3", "Processed":
                    return RequestStatus.PROCESSED;
                default:
                    if (!Input.checkInput(ans)) {
                        return null;
                    } else {
                        System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                        break;
                    }
            }
        } while (!"quit".equalsIgnoreCase(ans));
        return null;
    }

    public String choosePerson(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Please enter the social security number of the person" + ColorConsole.RESET);
        String ans;
        do {
            ans = Input.inputNextLine();
            if (!Input.checkInput(ans)) {
                return null;
            } else if (!neoBank.checkSSN(ans)) {
                System.out.println(ColorConsole.RED + "This user doesn't exist" + ColorConsole.RESET);
            } else if (neoBank.checkSSN(ans)) {
                return ans;
            } else {
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
            }
        } while (!"return".equalsIgnoreCase(ans));
        return null;
    }


}
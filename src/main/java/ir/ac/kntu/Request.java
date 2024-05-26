package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Request {
    private String request;
    private String answer;
    private RequestStatus status;
    private RequestSection section;
    private String securityNumber;

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public RequestStatus getStatus() {
        return status;
    }


    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public RequestSection getSection() {
        return section;
    }

    public void setSection(RequestSection section) {
        this.section = section;
    }

    public Request(String request, RequestSection section, String securityNumber){
        setRequest(request);
        setSection(section);
        setAnswer("No answer");
        setStatus(RequestStatus.NOTED);
        setSecurityNumber(securityNumber);
    }

    public static void displaySections(){
        System.out.println("What section is your problem?");
        System.out.println("   1. Account Management");
        System.out.println("   2. Contacts");
        System.out.println("   3. Transfer");
        System.out.println("   4. Settings");
        System.out.println("   5. Return");
    }

    public static RequestSection createRequestSection(){
        String answer;
        do {
            displaySections();
            answer = Input.inputNextLine();
            switch(answer){
                case "1", "Account Management":
                    return RequestSection.MANAGEMENT;
                case "2", "Contacts":
                    return RequestSection.CONTACTS;
                case "3", "Transfer":
                    return RequestSection.TRANSFER;
                case "4", "Settings":
                    return RequestSection.SETTINGS;
                case "5", "Return":
                    return null;
                default:
                    if (!answer.equalsIgnoreCase("quit")) {
                        System.out.println("THERE IS NO OTHER OPTION! Please input something else!");
                    } else{
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    }
                    break;
            }
        }while (!"quit".equalsIgnoreCase(answer));
        return null;
    }

    public static void createRequest(NeoBank neoBank, SimpleUser user){
        RequestSection section1 = createRequestSection();
        System.out.println("Please Enter your problem.");
        String text = Input.inputNextLine();
        if ("return".equalsIgnoreCase(text)) {
            return;
        } else if ("quit".equalsIgnoreCase(text)){
            System.out.println("Thanks for trusting our bank! Bye Bye!");
            System.exit(0);
        }
        Request newRequest = new Request(text, section1, user.getSecurityNumber());
        neoBank.getData().addRequest(newRequest);
        user.addRequest(newRequest);
        System.out.println("Request successfully noted!");
    }

    @Override
    public String toString() {
        return "Request{" +
                " Section=" + this.getSection() +
                ", Status=" + this.getStatus() +
                '}';
    }

    public static void showRequests(SimpleUser user){
        for (int index = 1 ; index < user.requestSize()+1 ; index++){
            System.out.println(index + ". " + user.getRequest(index-1).toString());
        }
    }


    public static void selectRequest(NeoBank neoBank, SimpleUser user){
        String input;
        do{
            showRequests(user);
            if (user.requestSize()==0){
                return;
            }
            input=Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return;
            }else if (!input.matches("[0-9]+")){
                System.out.println("Wrong input try again!");
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<user.requestSize()+1){
                for (int index = 1 ; index < user.requestSize() + 1 ; index++){
                    if (input.equals(Integer.toString(index))){
                        user.getRequest(index-1).showRequestInfo();
                        return;
                    }
                }
            } else{
                System.out.println("Wrong input try again!");
            }
        }while (!"retrun".equalsIgnoreCase(input));
    }

    public void showRequestInfo(){
        System.out.println("Request Section : "+ this.getSection());
        System.out.println("Problem : " + this.getRequest());
        System.out.println("Request Status : " + this.getStatus());
        System.out.println("Admins Answer : " + this.getAnswer());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request1)) return false;
        return Objects.equals(request, request1.request) && Objects.equals(answer, request1.answer) && status == request1.status && section == request1.section;
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, answer, status, section);
    }
}

package ir.ac.kntu;

public class Authentication {
    private boolean authenticated;
    private String answer;

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Authentication(){
        setAuthenticated(false);
        setAnswer("Hasn't been checked");
    }

    public void acceptAuthentication(){
        this.setAnswer("accepted!");
        this.setAuthenticated(true);
    }

    public void rejectAuthentication(){
        this.setAuthenticated(false);
        System.out.println("Please enter the reason of rejection!");
        String input;
        do{
            input = Input.inputNextLine();
            if(input.matches("[0-9]+")){
                System.out.println("Wrong format");
            }
        }while(input.matches("[0-9]+"));
        setAnswer(input);
    }
}

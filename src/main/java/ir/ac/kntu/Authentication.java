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

    public Authentication() {
        setAuthenticated(false);
        setAnswer(ColorConsole.PINK + "Hasn't been checked" + ColorConsole.RESET);
    }

    public void acceptAuthentication() {
        this.setAnswer(ColorConsole.GREEN + "accepted!" + ColorConsole.RESET);
        this.setAuthenticated(true);
    }

    public void rejectAuthentication() {
        this.setAuthenticated(false);
        System.out.println(ColorConsole.PINK + "Please enter the reason of rejection!" + ColorConsole.RESET);
        String input;
        do {
            input = Input.inputNextLine();
            if (input.matches("[0-9]+")) {
                System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            }
        } while (input.matches("[0-9]+"));
        setAnswer(input);
    }
}

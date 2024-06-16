package ir.ac.kntu;

public class Authentication {
    private String phoneNumber;
    private boolean authenticated;
    private String reason;

    private final Input input = new Input();

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Authentication(String phoneNumber) {
        setPhoneNumber(phoneNumber);
        setAuthenticated(false);
        setReason("Hasn't been checked yet!");
    }

    public void showRejection() {
        System.out.println(ColorConsole.YELLOW_BOLD + "you haven't been authenticated!");
        System.out.println("The reason :" + this.getReason() + ColorConsole.RESET);
    }

    public void showInfo(Data data) {
        SimpleUser currentUser = data.getUserByPhone(this.getPhoneNumber());
        System.out.println(ColorConsole.PINK + "***" + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "Name : " + ColorConsole.PURPLE + currentUser.getName() + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "Last Name : " + ColorConsole.PURPLE + currentUser.getLastName() + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "Social Security Number : " + ColorConsole.PURPLE + currentUser.getSecurityNumber() + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "Phone Number : " + ColorConsole.PURPLE + currentUser.getSimCard().getPhoneNumber() + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "Password : " + ColorConsole.PURPLE + currentUser.getPassword() + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "***" + ColorConsole.RESET);
    }

    public void authenticateUser(NeoBank neoBank, SimpleUser user) {
        this.setAuthenticated(true);
        this.setReason("Accepted!");
        user.setAccount(new Account(user, neoBank));
    }

    public void rejectUser() {
        System.out.println(ColorConsole.BLUE + "Why are you rejecting this user?" + ColorConsole.RESET);
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return;
        }
        this.setAuthenticated(false);
        this.setReason(answer);
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", authenticated=" + authenticated +
                '}';
    }
}

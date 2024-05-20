package ir.ac.kntu;

import java.util.List;

public class Admin extends Person{
    private String password;
    private List<Request> requests;
    private List<SimpleUser> newUsers;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

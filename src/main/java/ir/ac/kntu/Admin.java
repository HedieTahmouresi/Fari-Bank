package ir.ac.kntu;

import java.util.List;

public class Admin extends Person{
    private String password;
    private Data data;

    public Admin(String name, String lastName) {
        super(name, lastName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}


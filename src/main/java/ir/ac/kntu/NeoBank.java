package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class NeoBank {
    private List<SimpleUser> simpleUsers;
    private List<Admin> admins;
    private List<String> tracingNumbers;
    private Data data;

    public ArrayList<SimpleUser> getSimpleUsers() {
        return (ArrayList<SimpleUser>) simpleUsers;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public List<String> getTracingNumbers() {
        return tracingNumbers;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public NeoBank(){
        simpleUsers = new ArrayList<>();
        admins = new ArrayList<>();
        tracingNumbers = new ArrayList<>();
        data = new Data();
    }

    public void bank(){
        Menu.mainMenu(this);
    }
}

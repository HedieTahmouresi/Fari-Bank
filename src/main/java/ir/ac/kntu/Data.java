package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private List<Request> requests;
    private Map<String,Boolean> authentications;
    private List<SimpleUser> users;

    public Data() {
        this.requests = new ArrayList<>();
        this.authentications = new HashMap<>();
        this.users = new ArrayList<>();
    }

    public void addAuthentication(String securityNumber){
        this.authentications.put(securityNumber,false);
    }

    public void removeAuthentication(String securityNumber){
        this.authentications.remove(securityNumber);
    }

    public List<Request> getRequests() {
        List<Request> requests1 = new ArrayList<>();
        requests1 = this.requests;
        return requests1;
    }

    public Map<String,Boolean> getAuthentications() {
        Map<String,Boolean> authentications1 = new HashMap<>();
        authentications1 = this.authentications;
        return authentications1;
    }
}

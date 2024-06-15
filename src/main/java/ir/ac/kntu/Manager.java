package ir.ac.kntu;

public class Manager {
    private String fullName;
    private String userName;
    private String password;
    private ManagerData data;
    private double wage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ManagerData getData() {
        return data;
    }

    public void setData(ManagerData data) {
        this.data = data;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Manager(String fullName, String userName, String password, ManagerData data) {
        setFullName(fullName);
        setUserName(userName);
        setPassword(password);
        setData(data);
    }


    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }
}

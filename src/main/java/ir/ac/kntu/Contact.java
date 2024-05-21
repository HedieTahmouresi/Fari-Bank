package ir.ac.kntu;

public class Contact extends Person {
    private String phoneNumber;

    public Contact(String name, String lastName) {
        super(name, lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

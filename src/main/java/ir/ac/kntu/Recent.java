package ir.ac.kntu;

public class Recent {
    private SimpleUser person;
    private boolean byContact;
    private String receiverInfo;

    public SimpleUser getPerson() {
        return person;
    }

    public void setPerson(SimpleUser person) {
        this.person = person;
    }

    public boolean isByContact() {
        return byContact;
    }

    public void setByContact(boolean byContact) {
        this.byContact = byContact;
    }

    public String getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(String receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public Recent(SimpleUser person, boolean byContact) {
        setPerson(person);
        setByContact(byContact);
        if (byContact) {
            setReceiverInfo(person.getPhoneNumber());
        } else {
            setReceiverInfo(person.getAccount().getAccountId());
        }
    }

    @Override
    public String toString() {
        return this.getPerson().getName() +" "+ this.getPerson().getLastName();
    }
}

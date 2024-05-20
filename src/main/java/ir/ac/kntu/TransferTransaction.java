package ir.ac.kntu;

public class TransferTransaction extends Transaction{
    private Person reciever;

    public Person getReciever() {
        return reciever;
    }

    public void setReciever(Person reciever) {
        this.reciever = reciever;
    }


}

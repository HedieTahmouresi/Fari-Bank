package ir.ac.kntu;

public class TransferTransaction extends Transaction {
    private Person receiver;
    


    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }


}

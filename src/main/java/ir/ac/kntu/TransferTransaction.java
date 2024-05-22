package ir.ac.kntu;

public class TransferTransaction extends Transaction {
    private Person receiver;

    public TransferTransaction(int value, int tracingNumber) {
        super(value, tracingNumber);
    }


    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

}

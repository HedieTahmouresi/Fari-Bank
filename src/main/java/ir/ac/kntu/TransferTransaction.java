package ir.ac.kntu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TransferTransaction extends Transaction {
    private Person receiver;
    private SimpleUser sender;
    private boolean byContact;
    private String receiverInfo;

    public SimpleUser getSender() {
        return sender;
    }

    public void setSender(SimpleUser sender) {
        this.sender = sender;
    }

    public String getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(String receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public boolean isByContact() {
        return byContact;
    }

    public void setByContact(boolean byContact) {
        this.byContact = byContact;
    }

    public TransferTransaction(double value, int tracingNumber, Person receiver, boolean byContact, String receiverInfo, String sign, SimpleUser sender) {
        super(value, tracingNumber, sign);
        setByContact(byContact);
        setReceiver(receiver);
        setReceiverInfo(receiverInfo);
        setSender(sender);
    }


    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }
    @Override
    public void showInfo(){
        ZonedDateTime zonedDateTime = this.getDateAndTime().atZone(ZoneId.systemDefault());
        LocalDate datePart = zonedDateTime.toLocalDate();
        LocalTime timePart = zonedDateTime.toLocalTime();
        System.out.println("Transfer Transaction:");
        System.out.println("FullName sender : " + this.getSender().getName() +" " +  this.getSender().getSurname());
        System.out.println("Account ID sender : " + this.getSender().getAccount().getAccountId());
        System.out.println("FullName receiver : " + this.getReceiver().getName() +" " + this.getReceiver().getSurname());
        if (!this.isByContact()){
            System.out.println("Account ID receiver : " + this.getReceiverInfo());
        } else{
            System.out.println("Phone Number receiver : " + this.getReceiverInfo());
        }

        System.out.println("Value: "+this.getSign() + this.getValue());
        System.out.println("Date: " + datePart  + "Time: "+ timePart);
        System.out.println("Tracing Number: " + this.getTracingNumber());

    }

}

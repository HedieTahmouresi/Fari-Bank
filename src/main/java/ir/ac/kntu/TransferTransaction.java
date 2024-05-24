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
    private boolean isReceiver;

    public boolean isReceiver() {
        return isReceiver;
    }

    public void setReceiver(boolean receiver) {
        isReceiver = receiver;
    }

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

    public TransferTransaction(double value, int tracingNumber, Person receiver, boolean byContact, String receiverInfo, String sign, SimpleUser sender, Boolean isReceiver) {
        super(value, tracingNumber, sign);
        setByContact(byContact);
        setReceiver(receiver);
        setReceiverInfo(receiverInfo);
        setSender(sender);
        setReceiver(isReceiver);
    }


    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }
    @Override
    public void showInfo(NeoBank neoBank){
        ZonedDateTime zonedDateTime = this.getDateAndTime().atZone(ZoneId.systemDefault());
        LocalDate datePart = zonedDateTime.toLocalDate();
        LocalTime timePart = zonedDateTime.toLocalTime();
        System.out.println("Transfer Transaction:");

        if (!this.isByContact()){
            System.out.println("FullName sender : " + this.getSender().getName() +" " +  this.getSender().getSurname());
            System.out.println("Account ID sender : " + this.getSender().getAccount().getAccountId());
            if(!Contact.existsContact(this.getSender(), neoBank.getUserByAccountId(this.getReceiverInfo()).getPhoneNumber()) && !this.isReceiver() && neoBank.getUserByAccountId(this.getReceiverInfo()).isContactOption()){
                Contact currContact = Contact.getContact(this.getSender(), neoBank.getUserByAccountId(this.getReceiverInfo()).getPhoneNumber());
                System.out.println("FullName receiver : " +  currContact.getName() +" " + currContact.getSurname());
            } else{
                System.out.println("FullName receiver : " +  this.getReceiver().getName() +" " + this.getReceiver().getSurname());
            }
            System.out.println("Account ID receiver : " + this.getReceiverInfo());
        } else if(!this.isReceiver()){
            System.out.println("FullName sender : " + this.getSender().getName() +" " +  this.getSender().getSurname());
            System.out.println("Phone number sender : " + this.getSender().getPhoneNumber());
            Contact currContact = Contact.getContact(this.getSender(), this.getReceiverInfo());
            System.out.println("FullName receiver : " +  currContact.getName() +" " + currContact.getSurname());
            System.out.println("Phone Number receiver : " + this.getReceiverInfo());
        } else{
            if (!Contact.existsContact(neoBank.getSpecificUser(neoBank.getSpecificUser(receiverInfo)), this.getSender().getPhoneNumber())) {
                Contact currContact = Contact.getContact(neoBank.getSpecificUser(neoBank.getSpecificUser(receiverInfo)), this.getSender().getPhoneNumber());
                System.out.println("FullName sender : " + currContact.getName() + " " + currContact.getSurname());
                System.out.println("Phone number sender : " + this.getSender().getPhoneNumber());
            } else{

                System.out.println("FullName sender : " + this.getSender().getName() +" " +  this.getSender().getSurname());
                System.out.println("Phone number sender : " + this.getSender().getPhoneNumber());
            }
            System.out.println("FullName receiver : " +  this.getReceiver().getName() +" " + this.getReceiver().getSurname());
            System.out.println("Phone Number receiver : " + this.getReceiverInfo());
        }

        System.out.println("Value: "+this.getSign() + this.getValue());
        System.out.println("Date: " + datePart  + "Time: "+ timePart);
        System.out.println("Tracing Number: " + this.getTracingNumber());

    }

}

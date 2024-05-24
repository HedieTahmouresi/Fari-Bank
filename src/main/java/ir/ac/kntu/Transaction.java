package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.*;

public class Transaction {
    private Instant dateAndTime;
    private double value;
    private int tracingNumber;
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Instant getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Instant dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getTracingNumber() {
        return tracingNumber;
    }

    public void setTracingNumber(int tracingNumber) {
        this.tracingNumber = tracingNumber;
    }

    public Transaction(double value, int tracingNumber, String sign) {
        setValue(value);
        setTracingNumber(tracingNumber);
        setDateAndTime(Calendar.now());
        setSign(sign);
    }

    public void showInfo(){
        ZonedDateTime zonedDateTime = dateAndTime.atZone(ZoneId.systemDefault());
        LocalDate datePart = zonedDateTime.toLocalDate();
        LocalTime timePart = zonedDateTime.toLocalTime();
        System.out.println("Transaction : ");
        System.out.println("Value: "+this.getSign() + this.getValue());
        System.out.println("Date: " + datePart  + "Time: "+ timePart);
        System.out.println("Tracing Number: " + this.getTracingNumber());
    }

    @Override
    public String toString() {
        return ", Date and Time " + this.getDateAndTime() +
                ", Value : " + this.getValue() +
                ", Tracing Number : " + this.getTracingNumber();
    }
}

package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;
import java.util.Random;

public class Transaction {
    private Instant dateAndTime;
    private int value;
    private int tracingNumber;

    public Instant getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Instant dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTracingNumber() {
        return tracingNumber;
    }

    public void setTracingNumber(int tracingNumber) {
        this.tracingNumber = tracingNumber;
    }

}

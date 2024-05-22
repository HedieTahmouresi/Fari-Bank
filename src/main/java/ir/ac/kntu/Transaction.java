package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.*;

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

    public Transaction(int value, int tracingNumber) {
        setValue(value);
        setTracingNumber(tracingNumber);
        setDateAndTime(Calendar.now());
    }

    @Override
    public String toString() {
        ZonedDateTime zonedDateTime = dateAndTime.atZone(ZoneId.systemDefault());
        LocalDate datePart = zonedDateTime.toLocalDate();
        LocalTime timePart = zonedDateTime.toLocalTime();
        return ", Date : " +datePart +", Time : " + timePart+
                ", Value : " + this.getValue() +
                ", Tracing Number : " + this.getTracingNumber();
    }
}

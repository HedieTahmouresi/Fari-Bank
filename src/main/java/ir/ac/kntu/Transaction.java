package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.*;

public class Transaction {
    private Instant dateAndTime;
    private double value;
    private int tracingNumber;
    private String sign;

    public String getSign() {
        if ("+".equalsIgnoreCase(sign)) {
            return ColorConsole.GREEN + sign;
        }
        return ColorConsole.RED + sign;
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

    public void showInfo(NeoBank neoBank) {
        ZonedDateTime zonedDateTime = dateAndTime.atZone(ZoneId.systemDefault());
        LocalDate datePart = zonedDateTime.toLocalDate();
        LocalTime timePart = zonedDateTime.toLocalTime();
        System.out.println(ColorConsole.BLUE + "Transaction : " + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "Value: " + this.getSign() + this.getValue() + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "Date: " + datePart + "Time: " + timePart + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "Tracing Number: " + this.getTracingNumber() + ColorConsole.RESET);
    }

    @Override
    public String toString() {
        return ", Date and Time " + this.getDateAndTime() +
                ", Value : " + this.getValue() +
                ", Tracing Number : " + this.getTracingNumber();
    }

    public boolean dateIsBetween(Instant start, Instant end) {
        return this.getDateAndTime().isAfter(start) && this.getDateAndTime().isBefore(end);
    }
}

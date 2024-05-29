package ir.ac.kntu;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ChargeTransaction extends Transaction {

    public ChargeTransaction(double value, int tracingNumber) {
        super(value, tracingNumber, "+");
    }

    @Override
    public void showInfo(NeoBank neoBank) {
        ZonedDateTime zonedDateTime = this.getDateAndTime().atZone(ZoneId.systemDefault());
        LocalDate datePart = zonedDateTime.toLocalDate();
        LocalTime timePart = zonedDateTime.toLocalTime();
        System.out.println(ColorConsole.PURPLE + "Charge Transaction : ");
        System.out.println("Value: " + this.getSign() + this.getValue() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Date: " + datePart + "Time: " + timePart);
        System.out.println("Tracing Number: " + this.getTracingNumber() + ColorConsole.RESET);
    }
}

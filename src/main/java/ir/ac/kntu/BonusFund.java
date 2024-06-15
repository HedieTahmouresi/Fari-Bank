package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.*;

public class BonusFund extends Fund {
    private Instant manufacture;
    private Instant expiration;

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public Instant getManufacture() {
        return manufacture;
    }

    public void setManufacture(Instant manufacture) {
        this.manufacture = manufacture;
    }

    public BonusFund(SimpleUser owner, NeoBank neoBank, int numOfMonths) {
        super(owner, neoBank);
        Instant now = Calendar.now();
        setManufacture(now);
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        ZonedDateTime futureDateTime = zonedDateTime.plusMonths(numOfMonths);
        Instant futureInstant = futureDateTime.toInstant();
        setExpiration(futureInstant);
    }
    @Override
    public void dissolveFund() {
        if (Calendar.now().isBefore(this.getExpiration())) {
            ZonedDateTime zonedDateTime = this.getExpiration().atZone(ZoneId.systemDefault());
            LocalDate datePart = zonedDateTime.toLocalDate();
            System.out.println(ColorConsole.PINK + "You can't do anything with this fund!" + ColorConsole.RESET);
            System.out.println(ColorConsole.CYAN + "The expiration date : " + ColorConsole.PURPLE + datePart + ColorConsole.RESET);
            return;
        }
        System.out.println(ColorConsole.RED + "The expiration is due!" + ColorConsole.RESET);
        this.getOwner().getAccount().setBalance(this.getOwner().getAccount().getBalance() + this.getBalance());
        this.getOwner().removeFund(this);
    }

    public void transferBonus(NeoBank neoBank) {
        double bonus = (this.getBalance() * neoBank.getManagerData().getBonusPercentage()) / 100;
        this.getOwner().getAccount().setBalance(this.getOwner().getAccount().getBalance() + bonus);
    }

    @Override
    public void manageFund(NeoBank neoBank) {
        this.dissolveFund();
    }
}

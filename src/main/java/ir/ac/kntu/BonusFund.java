package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

    public void dissolveFund() {
        if (Calendar.now().isBefore(this.getExpiration())) {
            return;
        }
        System.out.println(ColorConsole.RED + "The expiration is due!" + ColorConsole.RESET);
        this.getOwner().getAccount().setBalance(this.getOwner().getAccount().getBalance() + this.getBalance());
        this.getOwner().removeFund(this);
    }

    public void transferBonus(NeoBank neoBank) {
        double bonus = (this.getBalance() * neoBank.getBonusPercentage()) / 100;
        this.getOwner().getAccount().setBalance(this.getOwner().getAccount().getBalance() + bonus);
    }
}

package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BonusThread implements Runnable{
    NeoBank neoBank;
    BonusFund fund;

    public NeoBank getNeoBank() {
        return neoBank;
    }

    public void setNeoBank(NeoBank neoBank) {
        this.neoBank = neoBank;
    }

    public BonusFund getFund() {
        return fund;
    }

    public void setFund(BonusFund fund) {
        this.fund = fund;
    }

    public BonusThread(NeoBank neoBank, BonusFund fund){
        this.setFund(fund);
        this.setNeoBank(neoBank);
    }

    @Override
    public void run() {
        do {
            ZonedDateTime zonedDateTime = this.getFund().getLastDeposit().atZone(ZoneId.systemDefault());
            ZonedDateTime futureDateTime = zonedDateTime.plusMonths(1);
            Instant nextDeposit = futureDateTime.toInstant();
            Duration duration = Duration.between(this.getFund().getLastDeposit(), nextDeposit);
            long time = duration.toMillis() / 6000;
            try {
                Thread.sleep(time);
                this.getFund().depositBonus(this.getNeoBank());
            } catch (InterruptedException error) {
                System.out.println(ColorConsole.RED + "Thread Error" + ColorConsole.RESET);
            }
        }while (!Calendar.now().isAfter(this.getFund().getExpiration()));
    }
}

package ir.ac.kntu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreditCard {
    private String creditCardId;
    private int password;
    private boolean havePassword;

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean isHavePassword() {
        return havePassword;
    }

    public void setHavePassword(boolean havePassword) {
        this.havePassword = havePassword;
    }

    public CreditCard(NeoBank neoBank) {
        Random random = new Random();
        long creditCard;
        String creditCardString;
        String mask = "00000000";
        DecimalFormat decimalFormat = new DecimalFormat(mask);
        do {
            creditCardString = "21995282";
            creditCard = random.nextLong(100000000);
            creditCardString = creditCardString.concat(decimalFormat.format(creditCard));
        } while (!neoBank.existsCreditCard(creditCardString));
        setCreditCardId(creditCardString);
        setHavePassword(false);
    }

}

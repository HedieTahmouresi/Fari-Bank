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

    public void changeCreditCardPassword(){
        System.out.println("Enter the passcode you want.");
        String input;
        do{
            input = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return;
            }else if (!input.matches("[0-9]{4}")){
                System.out.println("Invalid passcode!");
            }
        }while(!input.matches("[0-9]{4}"));
        this.setPassword(Integer.parseInt(input));
    }

}

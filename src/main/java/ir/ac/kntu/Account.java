package ir.ac.kntu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {
    private CreditCard creditCard;
    private String accountId;
    private Person owner;
    private int balance;
    private List<Transaction> transactions;

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Account (Person owner, ArrayList<SimpleUser> users){
        setOwner(owner);
        setBalance(0);
        transactions = new ArrayList<>();
        Random random = new Random();
        String mask1 = "0000000";
        DecimalFormat df1 = new DecimalFormat(mask1);
        String mask2 = "000000";
        DecimalFormat df2 = new DecimalFormat(mask2);
        int accountId1;
        int accountId2;
        String accountIdString;
        do{
            accountId1 = random.nextInt(10000000);
            accountId2 = random.nextInt(1000000);
            accountIdString = df1.format(accountId1).concat(df2.format(accountId2));
        }while(!Input.existsAccountId(accountIdString,users));
        setAccountId(accountIdString);
        setCreditCard(new CreditCard(users));
    }

}

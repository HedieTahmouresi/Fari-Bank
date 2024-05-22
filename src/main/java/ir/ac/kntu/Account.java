package ir.ac.kntu;

import java.text.DecimalFormat;
import java.util.*;

public class Account {
    private CreditCard creditCard;
    private String accountId;
    private Person owner;
    private int balance;
    private List<Map<String, Transaction>> transactions;

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

    public Account(Person owner,NeoBank neoBank) {
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
        do {
            accountId1 = random.nextInt(10000000);
            accountId2 = random.nextInt(1000000);
            accountIdString = df1.format(accountId1).concat(df2.format(accountId2));
        } while (!neoBank.existsAccountId(accountIdString));
        setAccountId(accountIdString);
        setCreditCard(new CreditCard(neoBank));
    }

    public void showBalance(){
        System.out.println("Here is your Balance: ");
        System.out.println("*" + this.getBalance()+ "$");
    }

    public void charge(NeoBank neoBank){
        System.out.println("How much would you like to charge your account?");
        String answer;
        do{
            answer = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(answer)){
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            }else if("return".equalsIgnoreCase(answer)){
                return;
            }else if(!answer.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Wrong input! Try again!");
            } else{
                this.setBalance(this.getBalance()+Integer.parseInt(answer));
                Map<String, Transaction> map= new HashMap<>();
                map.put("charge",new Transaction(Integer.parseInt(answer), neoBank.getTracingNumber()));
                transactions.add(0,map);
                neoBank.setTracingNumber(neoBank.getTracingNumber()+1);
                System.out.println("Account successfully charged!");
                return;
            }
        }while (!"return".equalsIgnoreCase(answer));
    }

    public void showTransaction(NeoBank neoBank){
        int index = 1;
        for (Map<String, Transaction> transaction: transactions){
            for (Map.Entry<String, Transaction> entry : transaction.entrySet()){
                System.out.println(index + ". Transaction Type: " + entry.getKey() + entry.getValue().toString());
                index++;
            }
        }
    }
}

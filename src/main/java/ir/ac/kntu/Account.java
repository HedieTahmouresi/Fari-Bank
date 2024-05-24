package ir.ac.kntu;

import java.text.DecimalFormat;
import java.util.*;

public class Account {
    private CreditCard creditCard;
    private String accountId;
    private SimpleUser owner;
    private double balance;
    private List<Map<String, Transaction>> transactions;
    private List<Map<TransferTransaction, String>> recents;

    public void addRecent(TransferTransaction transaction, String phoneNumber){
        Map<TransferTransaction, String> map = new HashMap<>();
        map.put(transaction, phoneNumber);
        recents.add(map);
    }

    public void addTransaction(Transaction transaction, String type){
        Map<String, Transaction> map = new HashMap<>();
        map.put(type, transaction);
        this.transactions.add(0, map);
    }

    public Transaction getTransaction(int index){
        for (Map.Entry<String, Transaction> entry : transactions.get(index).entrySet()){
            return entry.getValue();
        }
        return null;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public SimpleUser getOwner() {
        return owner;
    }

    public void setOwner(SimpleUser owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Account(SimpleUser owner, NeoBank neoBank) {
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
        recents= new ArrayList<>();
    }

    public void showBalance() {
        System.out.println("Here is your Balance: ");
        System.out.println("*" + this.getBalance() + "$");
    }

    public void charge(NeoBank neoBank) {
        System.out.println("How much would you like to charge your account?");
        String answer;
        do {
            answer = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(answer)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(answer)) {
                return;
            } else if (!answer.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println("Wrong input! Try again!");
            } else {
                this.setBalance(this.getBalance() + Double.parseDouble(answer));
                this.addTransaction(new ChargeTransaction(Double.parseDouble(answer), neoBank.getTracingNumber()), "charge");
                neoBank.setTracingNumber(neoBank.getTracingNumber() + 1);
                System.out.println("Account successfully charged!");
                return;
            }
        } while (!"return".equalsIgnoreCase(answer));
    }

    public void showTransaction(NeoBank neoBank) {
        int index = 1;
        for (Map<String, Transaction> transaction : transactions) {
            for (Map.Entry<String, Transaction> entry : transaction.entrySet()) {
                System.out.println(index + ". Transaction Type: " + entry.getKey() + entry.getValue().toString());
                index++;
            }
        }
    }

    public int transactionsSize(){
        return this.transactions.size();
    }

    public void selectTransaction(NeoBank neoBank){
        String input;
        List<String> securityNumbers = new ArrayList<>();
        Contact currContact;
        do{
            this.showTransaction(neoBank);
            if (this.transactionsSize() == 0){
                return;
            }
            input=Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return;
            }else if (!input.matches("[0-9]+")){
                System.out.println("Wrong input try again!");
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<this.transactionsSize()+1){
                for (int index = 1 ; index < this.transactionsSize()+1 ; index++){
                    if (input.equals(Integer.toString(index))){
                        this.getTransaction(index-1).showInfo(neoBank);
                        return;
                    }
                }
            } else{
                System.out.println("Wrong input try again!");
            }
        }while (!"retrun".equalsIgnoreCase(input));
    }

    public static void displayTransferOptions() {
        System.out.println("How would you like to transfer the money!");
        System.out.println("   1.by Account ID");
        System.out.println("   2.by Contact");
        System.out.println("   3.by Recent list");
    }

    public void transferMoney(NeoBank neoBank) {
        String answer;
        do {
            displayTransferOptions();
            answer = Input.inputNextLine();
            switch (answer) {
                case "1", "by Account ID":
                    this.transferByAccount(neoBank);
                    break;
                case "2", "by Contact":
                    if (this.getOwner().isContactOption()) {
                        this.transferByContact(neoBank);
                    } else{
                        System.out.println("your contact option is off!");
                    }
                    break;
                case "3", "by Recent List":
                    this.transferByRecent(neoBank);
                    break;
                default:
                    if ("quit".equalsIgnoreCase(answer)) {
                        System.out.println("Thanks for trusting our bank! Bye Bye!");
                        System.exit(0);
                    } else if ("return".equalsIgnoreCase(answer)) {
                        return;
                    }
            }
        } while (!"return".equalsIgnoreCase(answer));
    }

    public void transferByAccount(NeoBank neoBank) {
        System.out.println("Please enter the account Id of the person you would like to transfer money to!");
        String answer;
        do {
            answer = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(answer)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(answer)) {
                return;
            } else if (!Input.checkAccountID(answer)) {
                System.out.println("This is not a correct account ID");
            } else if (neoBank.existsAccountId(answer)) {
                System.out.println("This user doesn't exist!!");
            }
        } while (neoBank.existsAccountId(answer) || !Input.checkAccountID(answer));
        SimpleUser receiver = neoBank.getUserByAccountId(answer);
        System.out.println("How much would you like to transfer to " + receiver.getName() + " " + receiver.getSurname() + "?");
        String input;
        do {
            input = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)) {
                return;
            } else if (!input.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println("Wrong input! Try again!");
            } else {
                if (this.getConfirmation(receiver, input)) {
                    if (Double.parseDouble(input) > this.getBalance() + neoBank.getWage() ) {
                        System.out.println("transfer failed! you don't have enough money!");
                        return;
                    }
                    this.setBalance(this.getBalance() - Double.parseDouble(input) - neoBank.getWage());
                    receiver.getAccount().setBalance(receiver.getAccount().getBalance() + Double.parseDouble(input));
                    TransferTransaction newTransaction = new TransferTransaction(Double.parseDouble(input), neoBank.getTracingNumber(), receiver, false, receiver.getAccount().getAccountId(), "-", this.getOwner(), false);
                    this.addTransaction(newTransaction, "transfer");
                    receiver.getAccount().addTransaction(new TransferTransaction(Double.parseDouble(input), neoBank.getTracingNumber()+1, receiver, false,  receiver.getAccount().getAccountId(), "+", this.getOwner(), true), "transfer");
                    neoBank.setTracingNumber(neoBank.getTracingNumber()+2);
                    this.addRecent(newTransaction, receiver.getPhoneNumber());
                    System.out.println("Transfer Completed!");
                    return;
                } else{
                    return;
                }
            }
        } while (!"return".equalsIgnoreCase(input));
    }

    public boolean getConfirmation(SimpleUser receiver, String value){
        System.out.println("Reciever {Name : " + receiver.getName() + ", Last Name : " + receiver.getSurname() + "}");
        System.out.println("Value : " + value + "$");
        System.out.println("Are you sure?");
        String answer = Input.inputNextLine();
        if ("quit".equalsIgnoreCase(answer)) {
            System.out.println("Thanks for trusting our bank! Bye Bye!");
            System.exit(0);
        } else if ("return".equalsIgnoreCase(answer)) {
            return false;
        } else if ("yes".equalsIgnoreCase(answer)) {
            return true;
        }
        return false;
    }

    public void showAccountInfo(){
        System.out.println("Your Account ID : " + this.getAccountId());
        System.out.println("Your Credit Card ID : " + this.getCreditCard().getCreditCardId());
    }

    public void showRecents(){
        for (int index = 1 ; index < recents.size()+1 ; index++) {
            for (Map.Entry<TransferTransaction, String> entry : recents.get(index).entrySet()){
                System.out.println(index + ". " + entry.getKey().getReceiver().getName() + " " + entry.getKey().getReceiver().getSurname());
            }
        }
    }

    public Map<SimpleUser, Boolean> selectRecent(NeoBank neoBank){
        String input;
        Map<SimpleUser, Boolean> map = new HashMap<>();
        do{
            this.showRecents();
            if (this.recents.isEmpty()){
                return null;
            }
            input=Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)){
                return null;
            }else if (!input.matches("[0-9]+")){
                System.out.println("Wrong input try again!");
            }else if (Integer.parseInt(input)>0 && Integer.parseInt(input)<this.recents.size()+1){
                for (int index = 1 ; index < recents.size()+1 ; index++) {
                    if (Integer.parseInt(input)==index) {
                        for (Map.Entry<TransferTransaction, String> entry : recents.get(index).entrySet()){
                            map.put(neoBank.getSpecificUser(neoBank.getSpecificUser(entry.getValue())),entry.getKey().isByContact());
                            return map;
                        }

                    }
                }
            } else{
                System.out.println("Wrong input try again!");
            }
        }while (!"retrun".equalsIgnoreCase(input));
        return null;
    }

    public void transferByRecent(NeoBank neoBank){
        Map<SimpleUser, Boolean> map = this.selectRecent(neoBank);
        if (map==null){
            return;
        }
        SimpleUser receiver = null;
        boolean byContact = false;
        for (Map.Entry<SimpleUser, Boolean> entry : map.entrySet()){
            receiver = entry.getKey();
            byContact = entry.getValue();
        }

        System.out.println("How much would you like to transfer to " + receiver.getName() + " " + receiver.getSurname() + "?");
        String input;
        do {
            input = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)) {
                return;
            } else if (!input.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println("Wrong input! Try again!");
            } else {
                if (this.getConfirmation(receiver, input)) {
                    if (Double.parseDouble(input) > this.getBalance() + neoBank.getWage() ) {
                        System.out.println("transfer failed! you don't have enough money!");
                        return;
                    }
                    this.setBalance(this.getBalance() - Double.parseDouble(input) - neoBank.getWage());
                    receiver.getAccount().setBalance(receiver.getAccount().getBalance() + Double.parseDouble(input));
                    TransferTransaction newTransaction = new TransferTransaction(Double.parseDouble(input), neoBank.getTracingNumber(), receiver, byContact, receiver.getAccount().getAccountId(), "-", this.getOwner(), false);
                    String name = receiver.getName() + " " + receiver.getSurname();
                    this.addTransaction(newTransaction, "transfer");
                    receiver.getAccount().addTransaction(new TransferTransaction(Double.parseDouble(input), neoBank.getTracingNumber()+1, receiver, false,  receiver.getAccount().getAccountId(), "+", this.getOwner(), true), "transfer");
                    neoBank.setTracingNumber(neoBank.getTracingNumber()+2);
                    this.addRecent(newTransaction, receiver.getPhoneNumber());
                    System.out.println("Transfer Completed!");
                    return;
                } else{
                    return;
                }
            }
        } while (!"return".equalsIgnoreCase(input));

    }

    public void transferByContact(NeoBank neoBank){
        Contact receiverMoney = Contact.selectContact(neoBank, this.getOwner());
        SimpleUser receiver = neoBank.getSpecificUser(neoBank.getSpecificUser(receiverMoney.getPhoneNumber()));
        System.out.println("How much would you like to transfer to " + receiver.getName() + " " + receiver.getSurname() + "?");
        String input;
        do {
            input = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Thanks for trusting our bank! Bye Bye!");
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)) {
                return;
            } else if (!input.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println("Wrong input! Try again!");
            } else {
                if (!Contact.existsContact(receiver, this.getOwner().getPhoneNumber()) && receiver.isContactOption()) {
                    if (this.getConfirmation(receiver, input)) {
                        if (Double.parseDouble(input) > this.getBalance() + neoBank.getWage()) {
                            System.out.println("transfer failed! you don't have enough money!");
                            return;
                        }
                        this.setBalance(this.getBalance() - Double.parseDouble(input) - neoBank.getWage());
                        receiver.getAccount().setBalance(receiver.getAccount().getBalance() + Double.parseDouble(input));
                        TransferTransaction newTransaction = new TransferTransaction(Double.parseDouble(input), neoBank.getTracingNumber(), receiver, true, receiver.getPhoneNumber(), "-", this.getOwner(), false);
                        this.addTransaction(newTransaction, "transfer");
                        receiver.getAccount().addTransaction(new TransferTransaction(Double.parseDouble(input), neoBank.getTracingNumber() + 1, receiver, true, receiver.getPhoneNumber(), "+", this.getOwner(), true), "transfer");
                        neoBank.setTracingNumber(neoBank.getTracingNumber() + 2);
                        this.addRecent(newTransaction, receiver.getPhoneNumber());
                        System.out.println("Transfer Completed!");
                        return;
                    } else {
                        return;
                    }
                } else{
                    System.out.println("You can't send money to this user by Contact!");
                }
            }
        } while (!"return".equalsIgnoreCase(input));
    }
}

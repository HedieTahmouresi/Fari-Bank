package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.*;

public class Account {
    private CreditCard creditCard;
    private String accountId;
    private SimpleUser owner;
    private double balance;
    private List<Map<String, Transaction>> transactions;
    private List<Map<TransferTransaction, String>> recents;

    public void addRecent(TransferTransaction transaction, String phoneNumber) {
        Map<TransferTransaction, String> map = new HashMap<>();
        map.put(transaction, phoneNumber);
        recents.add(map);
    }

    public void addTransaction(Transaction transaction, String type) {
        Map<String, Transaction> map = new HashMap<>();
        map.put(type, transaction);
        this.transactions.add(0, map);
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
        recents = new ArrayList<>();
    }

    public void showBalance() {
        System.out.println(ColorConsole.GREEN + "Here is your Balance: " + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN_BOLD + "~" + this.getBalance() + "$" + ColorConsole.RESET);
    }

    public void charge(NeoBank neoBank) {
        System.out.println(ColorConsole.YELLOW_BOLD + "How much would you like to charge your account?" + ColorConsole.RESET);
        String answer;
        do {
            answer = Input.inputNextLine();
            if (!Input.checkInput(answer)) {
                return;
            } else if (!answer.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input! Try again!" + ColorConsole.RESET);
            } else {
                this.setBalance(this.getBalance() + Double.parseDouble(answer));
                this.addTransaction(new ChargeTransaction(Double.parseDouble(answer), neoBank.getTracingNumber()), "charge");
                neoBank.setTracingNumber(neoBank.getTracingNumber() + 1);
                System.out.println(ColorConsole.GREEN_BOLD + "Account successfully charged!" + ColorConsole.RESET);
                return;
            }
        } while (!"return".equalsIgnoreCase(answer));
    }

    public List<Transaction> showTransaction(NeoBank neoBank) {
        List<Transaction> transactionList = new ArrayList<>();
        int index = 1;
        for (Map<String, Transaction> transaction : transactions) {
            for (Map.Entry<String, Transaction> entry : transaction.entrySet()) {
                System.out.println(ColorConsole.BLUE + index + ". Transaction Type: " + entry.getKey() + " " + entry.getValue().toString() + ColorConsole.RESET);
                transactionList.add(entry.getValue());
                index++;
            }
        }
        if (transactionList.isEmpty()) {
            System.out.println(ColorConsole.BLUE + "No matching transaction!" + ColorConsole.RESET);
        }
        return transactionList;
    }

    public void selectTransaction(NeoBank neoBank, String showMethod) {
        String input;
        List<Transaction> transactionList;
        do {
            if ("1".equalsIgnoreCase(showMethod)) {
                transactionList = this.showTransaction(neoBank);
            } else {
                transactionList = this.filteredTransactions(neoBank);
            }
            if (transactionList == null || transactionList.isEmpty()) {
                return;
            }
            input = Input.inputNextLine();
            if (!Input.checkInput(input)) {
                return;
            } else if (!input.matches("[0-9]+")) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input try again!" + ColorConsole.RESET);
            } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < transactionList.size() + 1) {
                for (int index = 1; index < transactionList.size() + 1; index++) {
                    if (input.equals(Integer.toString(index))) {
                        transactionList.get(index - 1).showInfo(neoBank);
                        return;
                    }
                }
            } else {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input try again!" + ColorConsole.RESET);
            }
        } while (!"return".equalsIgnoreCase(input));
    }

    public static void displayTransferOptions() {
        System.out.println(ColorConsole.CYAN + "How would you like to transfer the money!");
        System.out.println("   1.by Account ID");
        System.out.println("   2.by Contact");
        System.out.println("   3.by Recent list" + ColorConsole.RESET);
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
                    } else {
                        System.out.println(ColorConsole.RED + "your contact option is off!" + ColorConsole.RESET);
                    }
                    break;
                case "3", "by Recent List":
                    this.transferByRecent(neoBank);
                    break;
                default:
                    if (Input.checkInput(answer)) {
                        System.out.println(ColorConsole.RED_BOLD + "Wrong input" + ColorConsole.RESET);
                    }
            }
        } while (!"return".equalsIgnoreCase(answer));
    }

    public String getId(NeoBank neoBank) {
        System.out.println(ColorConsole.PURPLE + "Please enter the account Id of the person you would like to transfer money to!" + ColorConsole.RESET);
        String answer;
        do {
            answer = Input.inputNextLine();
            if (!Input.checkInput(answer)) {
                return null;
            } else if (!Input.checkAccountID(answer)) {
                System.out.println(ColorConsole.RED_BOLD + "This is not a correct account ID" + ColorConsole.RESET);
            } else if (neoBank.existsAccountId(answer)) {
                System.out.println(ColorConsole.RED_BOLD + "This user doesn't exist!!" + ColorConsole.RESET);
            }
        } while (neoBank.existsAccountId(answer) || !Input.checkAccountID(answer));
        return answer;
    }

    public void transferByAccount(NeoBank neoBank) {
        String answer = getId(neoBank);
        if (answer == null) {
            return;
        }
        SimpleUser receiver = neoBank.getUserByAccountId(answer);
        System.out.println(ColorConsole.PURPLE + "How much would you like to transfer to " + ColorConsole.PINK + receiver.getName() + " " + receiver.getSurname() + ColorConsole.PURPLE + "?" + ColorConsole.RESET);
        String input;
        do {
            input = Input.inputNextLine();
            if (!Input.checkInput(input)) {
                return;
            } else if (!input.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input! Try again!" + ColorConsole.RESET);
            } else {
                this.completeTransfer(neoBank, receiver, input, false);
                return;
            }
        } while (!"return".equalsIgnoreCase(input));
    }

    public void completeTransfer(NeoBank neoBank, SimpleUser receiver, String value, boolean isByContact) {
        if (this.getConfirmation(receiver, value)) {
            if (Double.parseDouble(value) > this.getBalance() + neoBank.getWage()) {
                System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
                return;
            }
            this.setBalance(this.getBalance() - Double.parseDouble(value) - neoBank.getWage());
            receiver.getAccount().setBalance(receiver.getAccount().getBalance() + Double.parseDouble(value));
            String info = receiver.getAccount().getAccountId();
            if (isByContact) {
                info = receiver.getPhoneNumber();
            }
            TransferTransaction newTransaction = new TransferTransaction(Double.parseDouble(value), neoBank.getTracingNumber(), receiver, isByContact, info, "-", this.getOwner(), false);
            this.addTransaction(newTransaction, "transfer");
            receiver.getAccount().addTransaction(new TransferTransaction(Double.parseDouble(value), neoBank.getTracingNumber() + 1, receiver, isByContact, info, "+", this.getOwner(), true), "transfer");
            neoBank.setTracingNumber(neoBank.getTracingNumber() + 2);
            this.addRecent(newTransaction, receiver.getPhoneNumber());
            System.out.println(ColorConsole.GREEN_BOLD + "Transfer Completed!" + ColorConsole.RESET);
        } else {
            System.out.println(ColorConsole.RED_BOLD + "Transfer failed!" + ColorConsole.RESET);
        }

    }

    public boolean getConfirmation(SimpleUser receiver, String value) {
        System.out.println(ColorConsole.GREEN + "Reciever {Name : " + ColorConsole.YELLOW + receiver.getName() + ColorConsole.GREEN + ", Last Name : " + ColorConsole.YELLOW + receiver.getSurname() + "}" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Value : " + ColorConsole.YELLOW + value + "$" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Are you sure?" + ColorConsole.RESET);
        String answer = Input.inputNextLine();
        if ("quit".equalsIgnoreCase(answer)) {
            System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
            System.exit(0);
        } else if ("return".equalsIgnoreCase(answer)) {
            return false;
        } else if ("yes".equalsIgnoreCase(answer)) {
            return true;
        }
        return false;
    }

    public void showAccountInfo() {
        System.out.println(ColorConsole.PINK + "Your Account ID : " + this.getAccountId());
        System.out.println("Your Credit Card ID : " + this.getCreditCard().getCreditCardId() + ColorConsole.RESET);
    }

    public void showRecents() {
        for (int index = 1; index < recents.size() + 1; index++) {
            for (Map.Entry<TransferTransaction, String> entry : recents.get(index).entrySet()) {
                System.out.println(index + ". " + entry.getKey().getReceiver().getName() + " " + entry.getKey().getReceiver().getSurname());
            }
        }
    }

    public Map<SimpleUser, Boolean> selectRecent(NeoBank neoBank) {
        String input;
        Map<SimpleUser, Boolean> map = new HashMap<>();
        do {
            this.showRecents();
            if (this.recents.isEmpty()) {
                return null;
            }
            input = Input.inputNextLine();
            if (!Input.checkInput(input)) {
                return null;
            } else if (!input.matches("[0-9]+")) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input try again!" + ColorConsole.RESET);
            } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < this.recents.size() + 1) {
                for (int index = 1; index < recents.size() + 1; index++) {
                    if (Integer.parseInt(input) == index) {
                        for (Map.Entry<TransferTransaction, String> entry : recents.get(index).entrySet()) {
                            map.put(neoBank.getSpecificUser(neoBank.getSpecificUser(entry.getValue())), entry.getKey().isByContact());
                            return map;
                        }

                    }
                }
            } else {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input try again!" + ColorConsole.RESET);
            }
        } while (!"return".equalsIgnoreCase(input));
        return null;
    }

    public void transferByRecent(NeoBank neoBank) {
        Map<SimpleUser, Boolean> map = this.selectRecent(neoBank);
        if (map == null) {
            return;
        }
        SimpleUser receiver = null;
        boolean byContact = false;
        for (Map.Entry<SimpleUser, Boolean> entry : map.entrySet()) {
            receiver = entry.getKey();
            byContact = entry.getValue();
        }

        System.out.println(ColorConsole.PURPLE + "How much would you like to transfer to " + ColorConsole.PINK + receiver.getName() + " " + receiver.getSurname() + ColorConsole.PURPLE + "?" + ColorConsole.RESET);
        String input;
        do {
            input = Input.inputNextLine();
            if (!Input.checkInput(input)) {
                return;
            } else if (!input.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong input! Try again!" + ColorConsole.RESET);
            } else {
                this.completeTransfer(neoBank, receiver, input, byContact);
                return;
            }
        } while (!"return".equalsIgnoreCase(input));

    }

    public void transferByContact(NeoBank neoBank) {
        Contact receiverMoney = Contact.selectContact(neoBank, this.getOwner());
        SimpleUser receiver = neoBank.getSpecificUser(neoBank.getSpecificUser(receiverMoney.getPhoneNumber()));
        System.out.println(ColorConsole.PURPLE + "How much would you like to transfer to " + ColorConsole.PINK + receiver.getName() + " " + receiver.getSurname() + ColorConsole.PURPLE + "?" + ColorConsole.RESET);
        String input;
        do {
            input = Input.inputNextLine();
            if (!Input.checkInput(input)) {
                return;
            } else if (!input.matches("[0-9]+\\.?[0-9]*")) {
                System.out.println(ColorConsole.RED + "Wrong input! Try again!" + ColorConsole.RESET);
            } else {
                if (!Contact.existsContact(receiver, this.getOwner().getPhoneNumber()) && receiver.isContactOption()) {
                    this.completeTransfer(neoBank, receiver, input, true);
                    return;
                } else {
                    System.out.println(ColorConsole.RED + "You can't send money to this user by Contact!" + ColorConsole.RESET);
                    return;
                }
            }
        } while (!"return".equalsIgnoreCase(input));
    }

    public List<Transaction> filteredTransactions(NeoBank neoBank) {
        List<Transaction> transactionList = new ArrayList<>();
        Instant start = getStart();
        if (start == null) {
            return null;
        }
        Instant end = getEnd(start);
        if (end == null) {
            return null;
        }
        int index = 1;
        for (Map<String, Transaction> transaction : this.transactions) {
            for (Map.Entry<String, Transaction> entry : transaction.entrySet()) {
                if (entry.getValue().dateIsBetween(start, end)) {
                    System.out.println(ColorConsole.CYAN + index + ". Transaction Type: " + entry.getKey() + " " + entry.getValue().toString() + ColorConsole.RESET);
                    transactionList.add(entry.getValue());
                    index++;
                }
            }
        }
        if (transactionList.isEmpty()) {
            System.out.println(ColorConsole.RED + "No matching transaction!" + ColorConsole.RESET);
        }
        return transactionList;
    }

    public Instant getStart() {
        System.out.println(ColorConsole.BLUE + "list of transactions from : (yyyy-mm-dd)" + ColorConsole.RESET);
        String date = Input.inputNextLine();
        if (Input.checkInput(date)) {
            return null;
        }
        if (!Input.checkDate(date)) {
            return getStart();
        }
        Instant start = null;
        try {
            start = Instant.parse(date + "T00:00:00Z");
            if (start.isAfter(Calendar.now())) {
                System.out.println(ColorConsole.RED_BOLD + "Invalid Date!This time hasn't come!" + ColorConsole.RESET);
                return getStart();
            }
        } catch (DateTimeException error) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid date!" + ColorConsole.RESET);
            return getStart();
        }
        return start;
    }

    public Instant getEnd(Instant start) {
        System.out.println(ColorConsole.BLUE + "list of transactions from : " + ColorConsole.YELLOW + start + ColorConsole.BLUE + "to : " + ColorConsole.RESET);
        String date = Input.inputNextLine();
        if (Input.checkInput(date)) {
            return null;
        }
        if (!Input.checkDate(date)) {
            return getEnd(start);
        }
        Instant end = null;
        try {
            end = Instant.parse(date + "T00:00:00Z");
            if (end.isAfter(Calendar.now())) {
                System.out.println(ColorConsole.RED_BOLD + "Invalid Date!This time hasn't come!" + ColorConsole.RESET);
                return getEnd(start);
            } else if (end.isBefore(start)) {
                return getEnd(start);
            }
        } catch (DateTimeException error) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid date!" + ColorConsole.RESET);
            return getEnd(start);
        }
        return end;
    }
}

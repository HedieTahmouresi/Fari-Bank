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
    private List<Map<TransferTransaction, String>> recentList;

    private final Input input = new Input();

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
        } while (neoBank.getBankData().existsAccountID(accountIdString));
        setAccountId(accountIdString);
        setCreditCard(new CreditCard(neoBank));
        recentList = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction, String type) {
        Map<String, Transaction> map = new HashMap<>();
        map.put(type, transaction);
        this.transactions.add(0, map);
    }

    public void addRecent(TransferTransaction transaction, String phoneNumber) {
        Map<TransferTransaction, String> map = new HashMap<>();
        map.put(transaction, phoneNumber);
        recentList.add(map);
    }

    public void chargeAccount(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE_BOLD + "How much would you like to charge your account?" + ColorConsole.RESET);
        String answer;
        answer = input.nextLine();
        if (!input.exitPoint(answer)) {
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
        this.chargeAccount(neoBank);
    }

    public void showBalance() {
        System.out.println(ColorConsole.GREEN + "Here is your Balance: " + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN_BOLD + "~" + this.getBalance() + "$" + ColorConsole.RESET);
    }

    public void seeTransactions(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "How would you like to see them?" + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "    1. All transactions" + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "    2. Filtered transactions" + ColorConsole.RESET);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "All transactions":
                this.showAllTransactions(neoBank);
                break;
            case "2", "Filtered transactions":
                this.showFilteredTransactions(neoBank);
                break;
            default:
                if (input.exitPoint(answer)) {
                    System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
                } else {
                    return;
                }
        }
        this.seeTransactions(neoBank);
    }

    private void showFilteredTransactions(NeoBank neoBank) {
        Instant start = getStart();
        if (start == null) {
            return;
        }
        Instant end = getEnd(start);
        if (end == null) {
            return;
        }
        List<Transaction> transactionList = new ArrayList<>();
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
            return;
        }
        if (!selectTransaction(neoBank, transactionList)) {
            return;
        }
        this.showFilteredTransactions(neoBank);
    }

    private Instant getStart() {
        System.out.println(ColorConsole.BLUE + "list of transactions from : (yyyy-mm-dd)" + ColorConsole.RESET);
        String date = input.nextLine();
        if (!input.exitPoint(date)) {
            return null;
        } else if (!input.checkDate(date)) {
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
        System.out.println(ColorConsole.BLUE + "list of transactions from : " + ColorConsole.YELLOW + start + ColorConsole.BLUE + " to : " + ColorConsole.RESET);
        String date = input.nextLine();
        if (!input.exitPoint(date)) {
            return null;
        } else if (!input.checkDate(date)) {
            return getEnd(start);
        }
        Instant end = null;
        try {
            end = Instant.parse(date + "T23:59:59Z");
            if (end.isAfter(Calendar.now())) {
                System.out.println(ColorConsole.RED_BOLD + "Invalid Date!This time hasn't come!" + ColorConsole.RESET);
                return getEnd(start);
            } else if (end.isBefore(start)) {
                System.out.println(ColorConsole.RED + "The end cant be before the start!" + ColorConsole.RESET);
                return getEnd(start);
            }
        } catch (DateTimeException error) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid date!" + ColorConsole.RESET);
            return getEnd(start);
        }
        return end;
    }

    public void showAllTransactions(NeoBank neoBank) {
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
            System.out.println(ColorConsole.BLUE + "Transaction List is empty" + ColorConsole.RESET);
            return;
        }
        if (!selectTransaction(neoBank, transactionList)) {
            return;
        }
        this.showAllTransactions(neoBank);
    }

    public boolean selectTransaction(NeoBank neoBank, List<Transaction> transactionList) {
        if (transactionList == null || transactionList.isEmpty()) {
            return true;
        }
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return false;
        } else if (!answer.matches("[0-9]+")) {
            System.out.println(ColorConsole.RED_BOLD + "Wrong Format! Try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < transactionList.size() + 1) {
            for (int index = 1; index < transactionList.size() + 1; index++) {
                if (answer.equals(Integer.toString(index))) {
                    transactionList.get(index - 1).showInfo(neoBank);
                    return true;
                }
            }
        } else {
            System.out.println(ColorConsole.RED_BOLD + "Index Out of Bound! Try again!" + ColorConsole.RESET);
        }
        return this.selectTransaction(neoBank, transactionList);
    }

    public void transfer(NeoBank neoBank, String value, SimpleUser receiver, boolean isByContact) {
        if (Double.parseDouble(value) + neoBank.getWage() > this.getBalance()) {
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
    }

    public void showRecentList() {
        for (int index = 1; index < recentList.size() + 1; index++) {
            for (Map.Entry<TransferTransaction, String> entry : recentList.get(index - 1).entrySet()) {
                System.out.println(ColorConsole.PINK + index + ". " + ColorConsole.PURPLE + entry.getKey().getReceiver().getName() + " " + entry.getKey().getReceiver().getLastName());
            }
        }
    }

    public Map<SimpleUser, Boolean> selectRecent(NeoBank neoBank) {
        Map<SimpleUser, Boolean> map = new HashMap<>();
        this.showRecentList();
        if (this.recentList.isEmpty()) {
            return null;
        }
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return null;
        } else if (!answer.matches("[0-9]+")) {
            System.out.println(ColorConsole.RED_BOLD + "Wrong Format! Try again! Enter a number!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) <= this.recentList.size()) {
            for (int index = 1; index <= recentList.size(); index++) {
                if (Integer.parseInt(answer) == index) {
                    for (Map.Entry<TransferTransaction, String> entry : recentList.get(index - 1).entrySet()) {
                        map.put(neoBank.getBankData().getUserByPhone(entry.getValue()), entry.getKey().isByContact());
                        return map;
                    }

                }
            }
        } else {
            System.out.println(ColorConsole.RED_BOLD + "Index Out of bound! Try again!" + ColorConsole.RESET);
        }
        return selectRecent(neoBank);
    }
}

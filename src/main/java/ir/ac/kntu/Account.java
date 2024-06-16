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
    private List<Recent> recentList;

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

    public void addRecent(TransferTransaction transaction, String phoneNumber, NeoBank neoBank) {
        recentList.add(new Recent(neoBank.getBankData().getUserByPhone(phoneNumber), transaction.isByContact()));
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
                this.showTransactionList(neoBank, this.addAllTransactions(neoBank));
                break;
            case "2", "Filtered transactions":
                this.showTransactionList(neoBank, this.addFilteredTransactions());
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

    private List<Transaction> addFilteredTransactions() {
        Instant start = getStart();
        if (start == null) {
            return null;
        }
        Instant end = getEnd(start);
        if (end == null) {
            return null;
        }
        List<Transaction> transactionList = new ArrayList<>();
        for (Map<String, Transaction> transaction : this.transactions) {
            for (Map.Entry<String, Transaction> entry : transaction.entrySet()) {
                if (entry.getValue().dateIsBetween(start, end)) {
                    transactionList.add(entry.getValue());
                }
            }
        }
        if (transactionList.isEmpty()) {
            System.out.println(ColorConsole.RED + "No matching transaction!" + ColorConsole.RESET);
        }
        return transactionList;
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

    public List<Transaction> addAllTransactions(NeoBank neoBank) {
        List<Transaction> transactionList = new ArrayList<>();
        for (Map<String, Transaction> transaction : transactions) {
            for (Map.Entry<String, Transaction> entry : transaction.entrySet()) {
                transactionList.add(entry.getValue());
            }
        }
        if (transactionList.isEmpty()) {
            System.out.println(ColorConsole.BLUE + "Transaction List is empty" + ColorConsole.RESET);
        }
        return transactionList;
    }

    public void showTransactionList(NeoBank neoBank, List<Transaction> transactionList) {
        if (transactionList == null || transactionList.isEmpty()) {
            return;
        }
        Pagination transactions = new Pagination<>(transactionList, 5);
        String command;
        do {
            transactions.showPage();
            System.out.println(ColorConsole.BLUE + "Enter 'next' to go to the next page, 'previous' to go back or the number of the transaction you want" + ColorConsole.RESET);
            command = input.nextLine();
            if (!input.exitPoint(command)) {
                return;
            } else if (command.matches("[0-9]+")) {
                this.selectTransaction(neoBank, transactionList, command);
            } else if ("next".equals(command) || "previous".equals(command)) {
                transactions.changePage(command);
            } else {
                System.out.println(ColorConsole.RED + "No other option! Please try again!" + ColorConsole.RESET);
            }
        } while (!"return".equals(command));

    }

    public void selectTransaction(NeoBank neoBank, List<Transaction> transactionList, String answer) {
        if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < transactionList.size() + 1) {
            int index = Integer.parseInt(answer);
            transactionList.get(index - 1).showInfo(neoBank);
            return;
        }
        System.out.println(ColorConsole.RED_BOLD + "Index Out of Bound! Try again!" + ColorConsole.RESET);
    }

    public void transfer(NeoBank neoBank, String value, SimpleUser receiver, boolean isByContact) {
        double remains = this.getOwner().isHasRemainsFund() ? this.getOwner().getRemainsFund().calculateRemains(value) : 0;
        if (Double.parseDouble(value) + neoBank.getManagerData().getWage() + remains > this.getBalance()) {
            System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
            return;
        }
        this.setBalance(this.getBalance() - Double.parseDouble(value) - neoBank.getManagerData().getWage() - remains);
        receiver.getAccount().setBalance(receiver.getAccount().getBalance() + Double.parseDouble(value));
        String info = receiver.getAccount().getAccountId();
        if (isByContact) {
            info = receiver.getSimCard().getPhoneNumber();
        }
        TransferTransaction newTransaction = new TransferTransaction(Double.parseDouble(value), neoBank.getTracingNumber(), receiver, isByContact, info, "-", this.getOwner(), false);
        this.addTransaction(newTransaction, "transfer");
        receiver.getAccount().addTransaction(new TransferTransaction(Double.parseDouble(value), neoBank.getTracingNumber() + 1, receiver, isByContact, info, "+", this.getOwner(), true), "transfer");
        neoBank.setTracingNumber(neoBank.getTracingNumber() + 2);
        if (this.getOwner().isHasRemainsFund()) {
            this.getOwner().getRemainsFund().saveRemains(remains, neoBank);
        }
        this.addRecent(newTransaction, receiver.getSimCard().getPhoneNumber(), neoBank);
        System.out.println(ColorConsole.GREEN_BOLD + "Transfer Completed!" + ColorConsole.RESET);
    }

    public Recent showRecentList(NeoBank neoBank) {
        if (this.recentList == null || this.recentList.isEmpty()) {
            return null;
        }
        Pagination recents = new Pagination<>(this.recentList, 5);
        String command;
        do {
            recents.showPage();
            System.out.println(ColorConsole.BLUE + "Enter 'next' to go to the next page, 'previous' to go back or the number of the transaction you want" + ColorConsole.RESET);
            command = input.nextLine();
            if (!input.exitPoint(command)) {
                return null;
            } else if (command.matches("[0-9]+")) {
                return this.selectRecent(neoBank, command);
            } else if ("next".equals(command) || "previous".equals(command)) {
                recents.changePage(command);
            } else {
                System.out.println(ColorConsole.RED + "No other option! Please try again!" + ColorConsole.RESET);
            }
        } while (!"return".equals(command));
        return null;
    }

    public Recent selectRecent(NeoBank neoBank, String answer) {
        if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) <= this.recentList.size()) {
            return this.recentList.get(Integer.parseInt(answer) - 1);
        }
        System.out.println(ColorConsole.RED_BOLD + "Index Out of bound! Try again!" + ColorConsole.RESET);
        return null;

    }
}

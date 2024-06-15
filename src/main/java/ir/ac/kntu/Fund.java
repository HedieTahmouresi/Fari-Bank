package ir.ac.kntu;

import java.text.DecimalFormat;
import java.util.Random;

public class Fund {
    private double balance;
    private SimpleUser owner;
    private String fundID; // a string like 9988-----

    private final Input input = new Input();

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public SimpleUser getOwner() {
        return owner;
    }

    public void setOwner(SimpleUser owner) {
        this.owner = owner;
    }

    public String getFundID() {
        return fundID;
    }

    public void setFundID(String fundID) {
        this.fundID = fundID;
    }

    public Fund(SimpleUser owner, NeoBank neoBank){
        String mask = "00000";
        DecimalFormat decimalFormat = new DecimalFormat(mask);
        int id = neoBank.getBaseFundID();
        neoBank.setBaseFundID(neoBank.getBaseFundID()+1);
        String fundID = "9" + decimalFormat.format(id);
        setFundID(fundID);
        setBalance(0.0);
        setOwner(owner);
    }

    public void transferToFund(NeoBank neoBank, String fundType){
        System.out.println(ColorConsole.BLUE + "How much would you like to transfer to your " +fundType + "fund?" + ColorConsole.RESET);
        String value = input.nextLine();
        if(!input.exitPoint(value)){
            return;
        } else if (!value.matches("\\d+\\.?\\d*")){
            System.out.println(ColorConsole.RED + "Wrong format! Try again!" + ColorConsole.RESET);
        } else{
            if (this.getOwner().getAccount().getBalance()> Double.parseDouble(value)){
                if (input.nextConfirmation(fundType, "Account", value)) {
                    this.setBalance(this.getBalance() + Double.parseDouble(value));
                    this.getOwner().getAccount().setBalance(this.getOwner().getAccount().getBalance() - Double.parseDouble(value));
                    Transaction newTransaction = new TransferInsideTransaction(Double.parseDouble(value), neoBank.getTracingNumber(), "Account", fundType, this.getFundID());
                    neoBank.setTracingNumber(neoBank.getTracingNumber() + 1);
                    this.getOwner().getAccount().addTransaction(newTransaction, "Inside Transfer");
                }else{
                    System.out.println(ColorConsole.RED + "Transfer failed!" + ColorConsole.RESET);
                }
                return;
            }
            System.out.println(ColorConsole.RED + "You don't have enough money!" + ColorConsole.RESET);
        }
        this.transferToFund(neoBank, fundType);
    }

    public void transferFromFund(NeoBank neoBank, String fundType){
        System.out.println(ColorConsole.BLUE + "How much would you like to transfer from your "+ fundType+ "fund to your account?" + ColorConsole.RESET);
        String value = input.nextLine();
        if(!input.exitPoint(value)){
            return;
        } else if (!value.matches("\\d+\\.?\\d*")){
            System.out.println(ColorConsole.RED + "Wrong format! Try again!" + ColorConsole.RESET);
        } else{
            if (this.getBalance() > Double.parseDouble(value)){
                if (input.nextConfirmation("Account", fundType, value)) {
                    this.setBalance(this.getBalance() - Double.parseDouble(value));
                    this.getOwner().getAccount().setBalance(this.getOwner().getAccount().getBalance() + Double.parseDouble(value));
                    Transaction newTransaction = new TransferInsideTransaction(Double.parseDouble(value), neoBank.getTracingNumber(), fundType, "Account", this.getFundID());
                    neoBank.setTracingNumber(neoBank.getTracingNumber() + 1);
                    this.getOwner().getAccount().addTransaction(newTransaction, "Inside Transfer");
                } else{
                    System.out.println(ColorConsole.RED + "Transfer failed!" + ColorConsole.RESET);
                }
                return;
            }
            System.out.println(ColorConsole.RED + "You don't have enough money!" + ColorConsole.RESET);
        }
        this.transferFromFund(neoBank, fundType);
    }
}

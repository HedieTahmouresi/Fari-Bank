package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    List<NeoBank> banks;

    private final Input input = new Input();

    public CentralBank(){
        this.banks = new ArrayList<>();
    }

    public void addBank(NeoBank neoBank){
        this.banks.add(neoBank);
    }

    public NeoBank findBankByCreditCard(String creditCardStarter){
        for (NeoBank bank : banks){
            if (bank.getCreditCardStarter().equals(creditCardStarter)){
                return bank;
            }
        }
        return null;
    }

    public Account existsAccountId(String accountId){
        for (NeoBank bank : banks){
            if (bank.getBankData().existsAccountID(accountId)){
                return bank.getBankData().getUserByAccountID(accountId).getAccount();

            }
        }
        return null;
    }

    public Account existsCreditCardId(String creditID){
        for (NeoBank bank : banks){
            if(bank.getBankData().existsCreditCard(creditID)){
                return bank.getBankData().getAccountByCard(creditID);
            }
        }
        return null;
    }

    public SimpleUser getUserBySim(String phoneNumber){
        for(NeoBank bank : banks){
            if (bank.getBankData().getUserByPhone(phoneNumber)!=null){
                return bank.getBankData().getUserByPhone(phoneNumber);
            }
        }
        return null;
    }

    public void wireTransfer(SimpleUser sender, NeoBank neoBank){

    }

    public void cardToCard(SimpleUser sender, NeoBank neoBank){
        Account receiver = input.nextCreditCardID(this);
        if (receiver==null){
            return;
        }
        String value = input.nextValue(receiver.getOwner(), 100000.0);
        if (value==null){
            return;
        }
        double remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        if (Double.parseDouble(value) + neoBank.getManagerData().getCardWage() + remains > sender.getAccount().getBalance()) {
            System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
            return;
        }
        if (!input.nextConfirmation(receiver.getOwner(), value)){
            System.out.println(ColorConsole.RED + "Transfer failed" + ColorConsole.RESET);
            return;
        }
        this.transferBetweenBanks(sender, receiver, value, neoBank);
        System.out.println(ColorConsole.GREEN_BOLD + "Transfer Completed!" + ColorConsole.RESET);
    }

    public void bridgeTransfer(SimpleUser sender, NeoBank neoBank){
        Account receiver = input.nextAccount(this);
        if (receiver==null){
            return;
        }
        String value = input.nextValue(receiver.getOwner(), 5000000.0);
        if (value==null){
            return;
        }
        double remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        if (Double.parseDouble(value) + neoBank.getManagerData().getBridgeWage() + remains > sender.getAccount().getBalance()) {
            System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
            return;
        }
        remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        if (Double.parseDouble(value) + neoBank.getManagerData().getBridgeWage() + remains > sender.getAccount().getBalance()) {
            System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
            return;
        }
        if (!input.nextConfirmation(receiver.getOwner(), value)){
            System.out.println(ColorConsole.RED + "Transfer failed" + ColorConsole.RESET);
            return;
        }
        this.transferBetweenBanks(sender, receiver, value, neoBank);
        System.out.println(ColorConsole.GREEN_BOLD + "Transfer Completed!" + ColorConsole.RESET);

    }

    public void transferBetweenBanks(SimpleUser sender, Account receiver, String value, NeoBank neoBank){
        double remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        sender.getAccount().setBalance(sender.getAccount().getBalance() - Double.parseDouble(value) - neoBank.getManagerData().getCardWage() - remains);
        receiver.setBalance(receiver.getBalance() + Double.parseDouble(value));
        TransferTransaction newTransaction = new TransferTransaction(Double.parseDouble(value) + neoBank.getManagerData().getCardWage(), neoBank.getTracingNumber(), receiver.getOwner(), false, receiver.getCreditCard().getCreditCardId(), "-", sender, false);
        sender.getAccount().addTransaction(newTransaction, "Transfer");
        receiver.addTransaction(new TransferTransaction(Double.parseDouble(value), neoBank.getTracingNumber() + 1, receiver.getOwner(), false, receiver.getCreditCard().getCreditCardId(), "+", sender, true), "transfer");
        neoBank.setTracingNumber(neoBank.getTracingNumber() + 2);
        if (sender.isHasRemainsFund()) {
            sender.getRemainsFund().saveRemains(remains, neoBank);
        }
        sender.getAccount().addRecent(newTransaction, receiver.getOwner().getSimCard().getPhoneNumber(), this);
    }
}

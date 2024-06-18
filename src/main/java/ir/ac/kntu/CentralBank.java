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

    public boolean sameBank(SimpleUser first, SimpleUser second){
        NeoBank firstBank = null;
        for(NeoBank bank : banks){
            if (bank.getBankData().getUserByPhone(first.getSimCard().getPhoneNumber())!=null){
                firstBank = bank;
            }
        }
        NeoBank secondBank =  null;
        for(NeoBank bank : banks){
            if (bank.getBankData().getUserByPhone(second.getSimCard().getPhoneNumber())!=null){
                secondBank = bank;
            }
        }
        if (secondBank.equals(firstBank)){
            return true;
        }
        return false;
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

    public void cardToCard(NeoBank neoBank, SimpleUser sender, SimpleUser receiver, String value){
        if (receiver==null){
            return;
        }
        if (value==null){
            return;
        }
        double remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        if (Double.parseDouble(value) + neoBank.getManagerData().getCardWage() + remains > sender.getAccount().getBalance()) {
            System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
            return;
        }
        Double removeValue = Double.parseDouble(value) + neoBank.getManagerData().getCardWage();
        if (!input.nextConfirmation(receiver, value)){
            System.out.println(ColorConsole.RED + "Transfer failed" + ColorConsole.RESET);
            return;
        }
        this.transferBetweenBanks(sender, receiver.getAccount(),removeValue, value, neoBank);
        System.out.println(ColorConsole.GREEN_BOLD + "Transfer Completed!" + ColorConsole.RESET);
    }

    public void bridgeTransfer(NeoBank neoBank, SimpleUser sender, SimpleUser receiver, String value){
        if (receiver==null){
            return;
        }
        if (value==null){
            return;
        }
        double remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        double removeValue = (Double.parseDouble(value)*(neoBank.getManagerData().getBridgePercentage()+100))/100 + remains;
        if (removeValue > sender.getAccount().getBalance()) {
            System.out.println(ColorConsole.RED + "transfer failed! you don't have enough money!" + ColorConsole.RESET);
            return;
        }
        if (!input.nextConfirmation(receiver, value)){
            System.out.println(ColorConsole.RED + "Transfer failed" + ColorConsole.RESET);
            return;
        }
        this.transferBetweenBanks(sender, receiver.getAccount(),removeValue, value, neoBank);
        System.out.println(ColorConsole.GREEN_BOLD + "Transfer Completed!" + ColorConsole.RESET);

    }

    public void transferBetweenBanks(SimpleUser sender, Account receiver, Double removeValue,String value, NeoBank neoBank){
        double remains = sender.isHasRemainsFund() ? sender.getRemainsFund().calculateRemains(value) : 0;
        sender.getAccount().setBalance(sender.getAccount().getBalance() - removeValue);
        receiver.setBalance(receiver.getBalance() + Double.parseDouble(value));
        TransferTransaction newTransaction = new TransferTransaction(removeValue, neoBank.getTracingNumber(), receiver.getOwner(), false, receiver.getCreditCard().getCreditCardId(), "-", sender, false);
        sender.getAccount().addTransaction(newTransaction, "Transfer");
        receiver.addTransaction(new TransferTransaction(Double.parseDouble(value), neoBank.getTracingNumber() + 1, receiver.getOwner(), false, receiver.getCreditCard().getCreditCardId(), "+", sender, true), "transfer");
        neoBank.setTracingNumber(neoBank.getTracingNumber() + 2);
        if (sender.isHasRemainsFund()) {
            sender.getRemainsFund().saveRemains(remains, neoBank);
        }
        sender.getAccount().addRecentCentral(newTransaction, receiver.getOwner().getSimCard().getPhoneNumber(), this);
    }

    public void transferByCard(NeoBank neoBank, SimpleUser currentUser){
        String creditCardID = input.nextCreditCardID(this);
        if (creditCardID==null){
            return;
        }
        SimpleUser receiver = this.existsCreditCardId(creditCardID).getOwner();
        String value = input.nextValue(receiver,8000000.0 );
        if (value==null){
            return;
        }
        String creditCardStarter = creditCardID.substring(0, 8);
        NeoBank bankReceiver = this.findBankByCreditCard(creditCardStarter);
        if (neoBank.equals(bankReceiver)){
            this.showOnlyFari(neoBank, value, currentUser, receiver);
            return;
        }
        this.showTransferOptionsForCard(value, currentUser, receiver, neoBank);
    }

    public void transferByAccount(NeoBank neoBank, SimpleUser currentUser){
        String accountID = input.nextAccountID(this);
        if (accountID==null){
            return;
        }
        SimpleUser receiver = this.existsAccountId(accountID).getOwner();
        String value = input.nextValue(receiver,8000000.0 );
        if (value==null){
            return;
        }
        String creditCardStarter = receiver.getAccount().getCreditCard().getCreditCardId().substring(0, 8);
        NeoBank bankReceiver = this.findBankByCreditCard(creditCardStarter);
        if (neoBank.equals(bankReceiver)){
            this.showOnlyFari(neoBank, value, currentUser, receiver);
            return;
        }
        this.showTransferOptionsForAccount(value, currentUser, receiver, neoBank);
    }

    public void showOnlyFari(NeoBank neoBank, String value, SimpleUser sender, SimpleUser receiver){
        String answer = this.displayTransferOptions();
        if (!input.exitPoint(answer)){
            return;
        } else if (answer.equals("4") || answer.equals("Fari Transfer")){
            sender.transferByCreditID(neoBank, value, receiver.getAccount().getCreditCard().getCreditCardId());
            return;
        }  else if (!answer.matches("[0-9]+")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer)>4){
            System.out.println(ColorConsole.RED + "No other Option!" + ColorConsole.RESET);
        }else{
            System.out.println(ColorConsole.RED + "You can't choose these!" + ColorConsole.RESET);
        }
        this.showOnlyFari(neoBank, value, sender, receiver);
    }

    public String displayTransferOptions(){
        System.out.println(ColorConsole.BLUE + "Choose way : " + ColorConsole.RESET);
        System.out.println(ColorConsole.YELLOW + "1. Wire Transfer" + ColorConsole.RESET);
        System.out.println(ColorConsole.YELLOW + "2. Bridge Transfer" + ColorConsole.RESET);
        System.out.println(ColorConsole.YELLOW + "3. Card to Card Transfer" + ColorConsole.RESET);
        System.out.println(ColorConsole.YELLOW + "4. Fari Transfer" + ColorConsole.RESET);
        return input.nextLine();
    }

    public void showTransferOptionsForCard(String value, SimpleUser sender, SimpleUser receiver, NeoBank neoBank){
        String answer = this.displayTransferOptions();
        switch (answer){
            case "1", "Wire Transfer":

            case "2", "Bridge Transfer":
                if (checkBridge(value)){
                    this.bridgeTransfer(neoBank, sender, receiver, value);
                    return;
                }
                break;
            case "3", "Card to Card Transfer" :
                if (checkCard(value, true)){
                    this.cardToCard(neoBank,sender, receiver, value);
                    return;
                }
                break;
            case "4", "Fari Transfer" :
                System.out.println(ColorConsole.RED + "You can't choose this option" + ColorConsole.RESET);
                break;
            default:
                if (!input.exitPoint(answer)){
                    return;
                }
                System.out.println(ColorConsole.RED + "No other option" + ColorConsole.RESET);
        }
        this.showTransferOptionsForCard(value, sender, receiver, neoBank);
    }

    public boolean checkCard(String value, boolean byCard){
        if (!byCard || Double.parseDouble(value)>100000.0){
            System.out.println(ColorConsole.RED + "You can't choose this");
            return false;
        }
        return true;
    }

    public boolean checkBridge(String value){
        if (Double.parseDouble(value)>5000000.0){
            System.out.println(ColorConsole.RED + "Higher than the maximum limit" + ColorConsole.RESET);
            return false;
        }
        return true;
    }

    public void showTransferOptionsForAccount(String value, SimpleUser sender, SimpleUser receiver, NeoBank neoBank){
        String answer = this.displayTransferOptions();
        switch (answer){
            case "1", "Wire Transfer":

            case "2", "Bridge Transfer":
                if (checkBridge(value)){
                    this.bridgeTransfer(neoBank, sender, receiver, value);
                    return;
                }
                break;
            case "3", "Card to Card Transfer" :
                if (checkCard(value, false)){
                    this.cardToCard(neoBank,sender, receiver, value);
                    return;
                }
                break;
            case "4", "Fari Transfer" :
                System.out.println(ColorConsole.RED + "You can't choose this option" + ColorConsole.RESET);
                break;
            default:
                if (!input.exitPoint(answer)){
                    return;
                }
                System.out.println(ColorConsole.RED + "No other option" + ColorConsole.RESET);
        }
        this.showTransferOptionsForCard(value, sender, receiver, neoBank);
    }
}

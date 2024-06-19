package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class ManagerData {
    private Data data;
    private List<BonusFund> bonusFunds;
    private List<Admin> admins;
    private List<Manager> managers;
    private double fariWage;
    private List<SimCard> simCards;
    private double chargeWage;
    private int bonusPercentage;
    private double cardWage;
    private int bridgePercentage;
    private double wireWage;
    private List<WireTransaction> wireTransactions;

    public void addTransaction(WireTransaction wireTransaction){
        this.wireTransactions.add(wireTransaction);
    }
    private final Input input = new Input();

    public double getWireWage() {
        return wireWage;
    }

    public void setWireWage(double wireWage) {
        this.wireWage = wireWage;
    }

    public int getBridgePercentage() {
        return bridgePercentage;
    }

    public void setBridgePercentage(int bridgePercentage) {
        this.bridgePercentage = bridgePercentage;
    }

    public double getCardWage() {
        return cardWage;
    }

    public void setCardWage(double cardWage) {
        this.cardWage = cardWage;
    }

    public double getChargeWage() {
        return chargeWage;
    }

    public void setChargeWage(double chargeWage) {
        this.chargeWage = chargeWage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ManagerData(Data data) {
        this.data = data;
        this.bonusFunds = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.simCards = new ArrayList<>();
        this.wireTransactions = new ArrayList<>();
        setFariWage(0.0);
        setCardWage(300.0);
        setChargeWage(0.0);
        setBridgePercentage(2);
        setWireWage(2000);
        setBonusPercentage(10);
    }

    public Admin getSpecificAdmin(String userName) {
        for (Admin admin : admins) {
            if (admin.getUserName().equals(userName)) {
                return admin;
            }
        }
        return null;
    }

    public Manager getSpecificManger(String userName) {
        for (Manager manager : managers) {
            if (manager.getUserName().equals(userName)) {
                return manager;
            }
        }
        return null;
    }

    public void addAdmin(Admin admin) {
        this.admins.add(admin);
    }

    public void addManager(Manager manager){
        this.managers.add(manager);
    }

    public double getFariWage() {
        return fariWage;
    }

    public void setFariWage(double fariWage) {
        this.fariWage = fariWage;
    }

    public int getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(int bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public void addBonusFund(BonusFund fund) {
        this.bonusFunds.add(fund);
    }

    public void addSimCard(SimCard simCard) {
        this.simCards.add(simCard);
    }

    public SimCard getSimCard(String phoneNumber) {
        for (SimCard simCard : simCards) {
            if (simCard.getPhoneNumber().equals(phoneNumber)) {
                return simCard;
            }
        }
        return null;
    }

    public void changeFariWage(){
        System.out.println(ColorConsole.BLUE + "Please enter the new wage :");
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if (!answer.matches("[0-9]+\\.?[0-9]*")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.changeFariWage();
        }
        setFariWage(Double.parseDouble(answer));
        System.out.println(ColorConsole.GREEN + "Fari Wage successfully changed");
    }

    public void changeChargeWage(){
        System.out.println(ColorConsole.BLUE + "Please enter the new wage :");
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if (!answer.matches("[0-9]+\\.?[0-9]*")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.changeChargeWage();
        }
        setChargeWage(Double.parseDouble(answer));
        System.out.println(ColorConsole.GREEN + "Charge Wage successfully changed");
    }

    public void changeBonusPercentage(){
        System.out.println(ColorConsole.BLUE + "Please enter the new percentage :");
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if (!answer.matches("[0-9]+")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.changeBonusPercentage();
        } else if (Integer.parseInt(answer)>100){
            System.out.println(ColorConsole.RED + "Invalid number" + ColorConsole.RESET);
        }
        setBonusPercentage(Integer.parseInt(answer));
        System.out.println(ColorConsole.GREEN + "Bonus Percentage successfully changed");
    }

    public void changeCardWage(){
        System.out.println(ColorConsole.BLUE + "Please enter the new wage :");
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if (!answer.matches("[0-9]+\\.?[0-9]*")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.changeCardWage();
        }
        setCardWage(Double.parseDouble(answer));
        System.out.println(ColorConsole.GREEN + "Fari Wage successfully changed");
    }

    public void changeBridgeWage(){
        System.out.println(ColorConsole.BLUE + "Please enter the new wage percentage:");
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if (!answer.matches("[0-9]+")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.changeBridgeWage();
        }else if (Integer.parseInt(answer)>100){
            System.out.println(ColorConsole.RED + "Invalid number" + ColorConsole.RESET);
        }
        setBridgePercentage(Integer.parseInt(answer));
        System.out.println(ColorConsole.GREEN + "Bridge Wage successfully changed");
    }

    public void changeWireWage(){
        System.out.println(ColorConsole.BLUE + "Please enter the new wage :");
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if (!answer.matches("[0-9]+\\.?[0-9]*")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.changeWireWage();
        }
        setWireWage(Double.parseDouble(answer));
        System.out.println(ColorConsole.GREEN + "Wire Wage successfully changed");
    }

    public void emptyTransactions(){
        this.wireTransactions.clear();
    }

    public void completeTransfers(CentralBank centralBank, NeoBank neoBank){
        if (this.wireTransactions.isEmpty()){
            System.out.println(ColorConsole.PURPLE + "No transaction on hold!" + ColorConsole.RESET);
            return;
        }
        for (WireTransaction transaction : this.wireTransactions){
            transaction.completeTransaction(centralBank, neoBank);
        }
        this.emptyTransactions();
        System.out.println(ColorConsole.GREEN + "All transactions done!");
    }


    public void depositBonuses(NeoBank neoBank){
        if (this.bonusFunds.isEmpty()){
            System.out.println(ColorConsole.PURPLE + "No bonus fund!" + ColorConsole.RESET);
            return;
        }
        boolean depositedOneFund = false;
        for (BonusFund fund : this.bonusFunds){
            boolean flag = fund.depositBonus(neoBank);
            if (flag){
                depositedOneFund = true;
            }
        }
        if (depositedOneFund) {
            System.out.println(ColorConsole.GREEN + "All bonuses were deposited");
            return;
        }
        System.out.println(ColorConsole.PURPLE  + "No available fund to deposit (the time hadn't come or they were expired!)" + ColorConsole.RESET);
    }
}

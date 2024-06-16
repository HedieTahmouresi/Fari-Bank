package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class ManagerData {
    private Data data;
    private List<Fund> bonusFunds;
    private List<Admin> admins;
    private List<Manager> managers;
    private double fariWage;
    private List<SimCard> simCards;
    private double chargeWage;
    private int bonusPercentage;
    private double cardWage;
    private double bridgeWage;

    public double getBridgeWage() {
        return bridgeWage;
    }

    public void setBridgeWage(double bridgeWage) {
        this.bridgeWage = bridgeWage;
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
        setFariWage(0.0);
        setCardWage(300.0);
        setChargeWage(0.0);
    }

    public Admin getSpecificAdmin(String userName) {
        for (Admin admin : admins) {
            if (admin.getUserName().equals(userName)) {
                return admin;
            }
        }
        return null;
    }

    public void addAdmin(Admin admin) {
        this.admins.add(admin);
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
}

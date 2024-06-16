package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class ManagerData {
    private Data data;
    private List<Fund> bonusFunds;
    private List<Admin> admins;
    private List<Manager> managers;
    private double wage;
    private List<SimCard> simCards;

    private int bonusPercentage;

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
        setWage(2.5);
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


    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
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

    public void addSimCard(SimCard simCard){
        this.simCards.add(simCard);
    }

    public SimCard getSimCard(String phoneNumber){
        for (SimCard simCard : simCards){
            if (simCard.getPhoneNumber().equals(phoneNumber)){
                return simCard;
            }
        }
        return null;
    }
}

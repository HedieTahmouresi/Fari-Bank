package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeoBank {
    private Data bankData;
    private List<Admin> admins;
    private int tracingNumber;
    private double wage;
    private int baseFundID;
    private int bonusPercentage;

    private final Input input = new Input();

    public NeoBank() {
        admins = new ArrayList<>();
        setBankData(new Data());
        Random random = new Random();
        setTracingNumber(random.nextInt(8999999) + 1000000);
        setBaseFundID(random.nextInt(999999999));
        setWage(2.5);
    }

    public int getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(int bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public int getBaseFundID() {
        return baseFundID;
    }

    public void setBaseFundID(int baseFundID) {
        this.baseFundID = baseFundID;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public int getTracingNumber() {
        return tracingNumber;
    }

    public void setTracingNumber(int tracingNumber) {
        this.tracingNumber = tracingNumber;
    }

    public Input getInput() {
        return input;
    }

    public Data getBankData() {
        return bankData;
    }

    public void setBankData(Data bankData) {
        this.bankData = bankData;
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

    public Admin signInAdmin() {
        System.out.println(ColorConsole.BLUE + "Please Enter your username" + ColorConsole.RESET);
        String userName;
        Admin wantedAdmin;
        do {
            userName = input.nextLine();
            if (!input.exitPoint(userName)) {
                return null;
            }
            wantedAdmin = this.getSpecificAdmin(userName);
            if (wantedAdmin == null) {
                System.out.println(ColorConsole.RED_BOLD + "Username not available!" + ColorConsole.RESET);
            }
        } while (wantedAdmin == null);
        System.out.println(ColorConsole.BLUE + "Please Enter your password" + ColorConsole.RESET);
        String password;
        do {
            password = input.nextLine();
            if (!input.exitPoint(password)) {
                return null;
            } else if (!password.equals(wantedAdmin.getPassword())) {
                System.out.println(ColorConsole.RED_BOLD + "Wrong Password! If you would like to change the user name return to the previous menu" + ColorConsole.RESET);
            }
        } while (!password.equals(wantedAdmin.getPassword()));
        return wantedAdmin;
    }

    public void launchBank() {
        Menu menu = new Menu();
        menu.mainMenu(this);
    }


}

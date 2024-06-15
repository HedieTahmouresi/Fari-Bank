package ir.ac.kntu;

import java.util.Random;

public class NeoBank {
    private Data bankData;
    private int tracingNumber;

    private int baseFundID;
    private ManagerData managerData;

    private final Input input = new Input();

    public NeoBank() {
        Data newData = new Data();
        setManagerData(new ManagerData(newData));
        setBankData(newData);
        Random random = new Random();
        setTracingNumber(random.nextInt(8999999) + 1000000);
        setBaseFundID(random.nextInt(999999999));

    }

    public ManagerData getManagerData() {
        return managerData;
    }

    public void setManagerData(ManagerData managerData) {
        this.managerData = managerData;
    }


    public int getBaseFundID() {
        return baseFundID;
    }

    public void setBaseFundID(int baseFundID) {
        this.baseFundID = baseFundID;
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


    public Admin signInAdmin() {
        System.out.println(ColorConsole.BLUE + "Please Enter your username" + ColorConsole.RESET);
        String userName;
        Admin wantedAdmin;
        do {
            userName = input.nextLine();
            if (!input.exitPoint(userName)) {
                return null;
            }
            wantedAdmin = this.getManagerData().getSpecificAdmin(userName);
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

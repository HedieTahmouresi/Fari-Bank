package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminAbilities {
    private Admin owner;
    private boolean authentications;
    private boolean reports;
    private boolean funds;
    private boolean contacts;
    private boolean transfer;
    private boolean simCharge;
    private boolean creditCard;
    private boolean settings;

    private final Input input = new Input();

    public Admin getOwner() {
        return owner;
    }

    public void setOwner(Admin owner) {
        this.owner = owner;
    }

    public boolean isAuthentications() {
        return authentications;
    }

    public void setAuthentications(boolean authentications) {
        this.authentications = authentications;
    }

    public boolean isReports() {
        return reports;
    }

    public void setReports(boolean reports) {
        this.reports = reports;
    }

    public boolean isFunds() {
        return funds;
    }

    public void setFunds(boolean funds) {
        this.funds = funds;
    }

    public boolean isContacts() {
        return contacts;
    }

    public void setContacts(boolean contacts) {
        this.contacts = contacts;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public boolean isSimCharge() {
        return simCharge;
    }

    public void setSimCharge(boolean simCharge) {
        this.simCharge = simCharge;
    }

    public boolean isCreditCard() {
        return creditCard;
    }

    public void setCreditCard(boolean creditCard) {
        this.creditCard = creditCard;
    }

    public boolean isSettings() {
        return settings;
    }

    public void setSettings(boolean settings) {
        this.settings = settings;
    }

    public AdminAbilities(Admin owner){
        setOwner(owner);
        setContacts(true);
        setFunds(true);
        setSettings(true);
        setAuthentications(true);
        setCreditCard(true);
        setReports(true);
        setSimCharge(true);
        setTransfer(true);
    }

    public boolean hasAbility(RequestSection section){
        switch (section){
            case REPORT -> {
                return this.isReports();
            }
            case FUNDS -> {
                return this.isFunds();
            }
            case CONTACTS -> {
                return this.isContacts();
            }
            case AUTHENTICATIONS -> {
                return this.isAuthentications();
            }
            case TRANSFER -> {
                return this.isTransfer();
            }
            case SIM_CHARGE -> {
                return this.isSimCharge();
            }
            case CREDIT_CARD -> {
                return this.isCreditCard();
            }
            case SETTINGS -> {
                return this.isSettings();
            }
            default -> {
                return false;
            }
        }
    }



    public void changeAbilities(){
        List<String> list = new ArrayList<>(Arrays.asList(ColorConsole.CYAN +"AUTHENTICATIONS", ColorConsole.CYAN +"REPORT", ColorConsole.CYAN +"FUNDS", ColorConsole.CYAN +"CONTACTS",ColorConsole.CYAN + "TRANSFER",ColorConsole.CYAN + "SIM_CHARGE",ColorConsole.CYAN + "CREDIT_CARD",ColorConsole.CYAN + "SETTINGS" + ColorConsole.RESET));
        Pagination<String> menu = new Pagination<>(list, 5);
        String answer;
        do{
            menu.showPage();
            answer = input.nextLine();
            switch(answer){
                case "1"-> this.changeAuthentications();
                case "2"-> this.changeReport();
                case "3"-> this.changeFunds();
                case "4"->this.changeContacts();
                case "5"-> this.changeTransfers();
                case "6"-> this.changeSimCharge();
                case "7"->this.changeCard();
                case "8"->this.changeSettings();
                case "next", "previous"->menu.changePage(answer);
                default ->{
                    if (!input.exitPoint(answer)){
                        return;
                    }
                    System.out.println(ColorConsole.RED + "No other Option" + ColorConsole.RESET);
                }
            }
        }while (!"return".equals(answer));
    }

    public void changeAuthentications(){
        String activity = this.isAuthentications()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setAuthentications(!this.isAuthentications());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeAuthentications();
    }

    public void changeReport(){
        String activity = this.isReports()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setReports(!this.isReports());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeReport();
    }

    public void changeFunds(){
        String activity = this.isFunds()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setFunds(!this.isFunds());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeFunds();
    }

    public void changeContacts(){
        String activity = this.isContacts()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setContacts(!this.isContacts());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeContacts();
    }

    public void changeTransfers(){
        String activity = this.isTransfer()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setTransfer(!this.isTransfer());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeTransfers();
    }

    public void changeSimCharge(){
        String activity = this.isSimCharge()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setSimCharge(!this.isSimCharge());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeSimCharge();
    }

    public void changeCard(){
        String activity = this.isCreditCard()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setCreditCard(!this.isCreditCard());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeCard();
    }

    public void changeSettings(){
        String activity = this.isSettings()? "active" : "not active";
        System.out.println(ColorConsole.BLUE + "This option is " + activity + " Would you like to change it?");
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setSettings(!this.isSettings());
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeSettings();
    }
}

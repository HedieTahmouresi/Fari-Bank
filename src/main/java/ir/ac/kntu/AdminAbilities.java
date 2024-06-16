package ir.ac.kntu;

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
}

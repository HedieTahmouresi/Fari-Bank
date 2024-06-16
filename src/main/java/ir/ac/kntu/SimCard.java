package ir.ac.kntu;

public class SimCard {
    private String phoneNumber;
    private double charge;
    private boolean hasAccount;

    private final Input input = new Input();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean hasAccount() {
        return hasAccount;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public SimCard(String phoneNumber, boolean hasAccount){
        setPhoneNumber(phoneNumber);
        setCharge(0);
        setHasAccount(hasAccount);
    }

    public void chargeSimCard(){
        System.out.println(ColorConsole.BLUE + "How much do you want to charge this sim card?" + ColorConsole.RESET);
        String answer = input.nextLine();
        if (!input.exitPoint(answer)){
            return;
        } else if(!answer.matches("[0-9]+\\.?[0-9]*")){
            System.out.println(ColorConsole.RED + "Wrong format" + ColorConsole.RESET);
            this.chargeSimCard();
        }
        this.setCharge(this.getCharge() + Double.parseDouble(answer));
        System.out.println(ColorConsole.GREEN + "Sim Card successfully charged" + ColorConsole.RESET);
    }

    public void showCharge(){
        System.out.println(ColorConsole.GREEN + "This is your charge : " + ColorConsole.YELLOW + this.getCharge() + ColorConsole.RESET);
    }
}

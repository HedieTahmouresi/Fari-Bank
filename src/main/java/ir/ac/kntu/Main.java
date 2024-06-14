package ir.ac.kntu;


public class Main {

    public static void main(String[] args) {
        NeoBank fariBank = new NeoBank();
        Helper.initiateUsers(fariBank);
        Helper.initiateAdmins(fariBank);
        fariBank.launchBank();
    }

}

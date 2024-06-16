package ir.ac.kntu;


public class Main {

    public static void main(String[] args) {
        NeoBank fariBank = new NeoBank();
        Helper helper = new Helper();
        helper.initiateUsers(fariBank);
        helper.initiateAdmins(fariBank);
        fariBank.launchBank();
    }

}

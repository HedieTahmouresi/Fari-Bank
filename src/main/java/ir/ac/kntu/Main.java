package ir.ac.kntu;


public class Main {

    public static void main(String[] args) {
        NeoBank fariBank = new NeoBank("21995282");
        Helper helper = new Helper();
        helper.initiateFari(fariBank);
        CentralBank centralBank = new CentralBank();
        helper.initiateCentralBank(centralBank, fariBank);

    }

}

package ir.ac.kntu;


public class Main {

    public static void main(String[] args) {
        NeoBank feryBank = new NeoBank();
        feryBank.addAdmin(new Admin("amir", "teymor23", feryBank, "A@tah1379"));
        feryBank.addAdmin(new Admin("hasan", "sag12sibil", feryBank, "fer@fery75"));
        feryBank.bank();
    }

}

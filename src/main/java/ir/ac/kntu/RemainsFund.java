package ir.ac.kntu;

public class RemainsFund extends Fund {
    private final Input input = new Input();

    public RemainsFund(SimpleUser owner, NeoBank neoBank) {
        super(owner, neoBank);
    }

    public double calculateRemains(String amount) {
        int len = amount.length();
        double rem = (double) (3 * len) / 4;
        len = (int) Math.ceil(rem);
        String value = amount.substring(amount.length() - len);
        Long intValue = Long.parseLong(value);
        return Math.pow(10, len) - intValue;
    }

    public void saveRemains(double value) {
        this.setBalance(this.getBalance() + value);
    }

    @Override
    public String toString() {
        return ColorConsole.PURPLE + "Remains " + ColorConsole.RESET + super.toString();
    }

    @Override
    public void transfer(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "What would you like to do?" + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "  1. Transfer from your Fund" + ColorConsole.RESET);
        System.out.println(ColorConsole.BLUE + "  1. Transfer to your Fund" + ColorConsole.RESET);
        String answer = input.nextLine();
        switch (answer) {
            case "1", "Transfer from your Fund":
                this.transferFromFund(neoBank, "Remains Fund");
                break;
            case "2", "Transfer to your Fund":
                this.transferToFund(neoBank, "Remains Fund");
                break;
            default:
                if (!input.exitPoint(answer)) {
                    return;
                }
                System.out.println(ColorConsole.RED + "No other Option! Try again! " + ColorConsole.RESET);
                break;
        }
        this.transfer(neoBank);
    }

    @Override
    public void dissolveFund() {
        super.dissolveFund();
        this.getOwner().setHasRemainsFund(false);
    }
}

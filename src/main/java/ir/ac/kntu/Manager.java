package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Manager {
    private String fullName;
    private String userName;
    private String password;
    private ManagerData data;
    private int rank;

    private final Input input = new Input();

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ManagerData getData() {
        return data;
    }

    public void setData(ManagerData data) {
        this.data = data;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Manager(String fullName, String userName, String password, ManagerData data, int rank) {
        setFullName(fullName);
        setUserName(userName);
        setPassword(password);
        setData(data);
        setRank(rank);
    }


    public void settings(NeoBank neoBank){
        List<String> options = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Fari Wage", ColorConsole.CYAN + "Charge Wage", ColorConsole.CYAN + "Bonus Percentage", ColorConsole.CYAN + "Card to Card Wage", ColorConsole.CYAN + "Bridge Transfer Wage", ColorConsole.CYAN + "Wire Transfer Wage" + ColorConsole.RESET));
        Pagination<String> menu = new Pagination<>(options, 5);
        String answer;
        do{
            menu.showPage();
            answer = input.nextLine();
            if (!input.exitPoint(answer)){
                return;
            }
            menu.selectSettings(this, answer);
        }while (!"quit".equals(answer));
    }

    public void automaticTransactions(NeoBank neoBank, CentralBank centralBank){
        System.out.println(ColorConsole.BLUE + "Choose : ");
        System.out.println("   1. Transfers");
        System.out.println("   2. Funds");
        String answer = input.nextLine();
        switch (answer){
            case "1", "Transfers" -> this.getData().completeTransfers(centralBank, neoBank);
            case "2", "Funds" -> this.getData().depositBonuses(neoBank);
            default -> {
                if (!input.exitPoint(answer)){
                    return;
                }
                System.out.println(ColorConsole.RED + "No other Option!" + ColorConsole.RESET);
            }
        }
        this.automaticTransactions(neoBank, centralBank);
    }
}

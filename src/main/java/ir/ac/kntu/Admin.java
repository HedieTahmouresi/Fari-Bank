package ir.ac.kntu;


public class Admin extends Person {
    private String password;
    private Data data;

    public Admin(String name, String userName, Data data) {
        super(name, userName);
        setData(data);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static Admin signIn(NeoBank neoBank){
        System.out.println("Please Enter your user name");
        String userName;
        Admin wantedAdmin;
        do {
            userName = Input.inputNextLine();
            wantedAdmin = neoBank.getSpecificAdmin(userName);
        } while (wantedAdmin ==null);
        String password;
        do {
            password = Input.inputNextLine();
            if (!password.equals(wantedAdmin.getPassword())){
                System.out.println("Wrong Password! If you would like to change the user name return to the previous menu");
            }
        } while (!password.equals(wantedAdmin.getPassword()));
        return wantedAdmin;
    }
}


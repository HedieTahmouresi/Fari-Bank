package ir.ac.kntu;


public class Admin extends Person {
    private String password;
    private Data data;

    public Admin(String name, String userName, NeoBank neoBank, String password) {
        super(name, userName);
        setPassword(password);
        setData(neoBank.getData());
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
        System.out.println("Please Enter your username");
        String userName;
        Admin wantedAdmin;
        do {
            userName = Input.inputNextLine();
            if ("return".equalsIgnoreCase(userName)){
                return null;
            } else if("quit".equalsIgnoreCase(userName)){
                System.exit(0);
            }
            wantedAdmin = neoBank.getSpecificAdmin(userName);
            if (wantedAdmin == null){
                System.out.println("Username not available!");
            }
        } while (wantedAdmin ==null);
        System.out.println("Please Enter your password");
        String password;
        do {
            password = Input.inputNextLine();
            if ("return".equalsIgnoreCase(password)){
                return null;
            } else if("quit".equalsIgnoreCase(password)){
                System.exit(0);
            }else if (!password.equals(wantedAdmin.getPassword())){
                System.out.println("Wrong Password! If you would like to change the user name return to the previous menu");
            }
        } while (!password.equals(wantedAdmin.getPassword()));
        return wantedAdmin;
    }


}


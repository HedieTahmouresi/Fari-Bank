package ir.ac.kntu;

public class Helper {
    public static void initiateUsers(NeoBank neoBank) {
        SimpleUser hedie = new SimpleUser("Hedie", "Tahmouresi", "09109056296", "0025755900", "H@tah1384", new Authentication("09109056296"));
        hedie.getAuthenticated().authenticateUser(neoBank, hedie);
        neoBank.getBankData().addUser(hedie);
        SimpleUser aylin = new SimpleUser("Aylin", "Jabbari", "09901917812", "0215021470", "A#Jab1384", new Authentication("09901917812"));
        aylin.getAuthenticated().authenticateUser(neoBank, aylin);
        neoBank.getBankData().addUser(aylin);
        aylin.setContactOption(false);
        SimpleUser amir = new SimpleUser("Amir", "Tahmouresi", "09028789000", "0023577410", "A@tah1379", new Authentication("09028789000"));
        neoBank.getBankData().addUser(amir);
        neoBank.getBankData().addAuthentication(amir.getAuthenticated());
        SimpleUser neda = new SimpleUser("Neda", "Abtahi", "09124464876", "0306201582", "Neda0*0abeN", new Authentication("09124464876"));
        neoBank.getBankData().addAuthentication(neda.getAuthenticated());
        neda.getAuthenticated().setAuthenticated(false);
        neda.getAuthenticated().setReason("I don't like you");
        neoBank.getBankData().addUser(neda);
        SimpleUser sepehr = new SimpleUser("Sepehr", "Ghardashi", "09111262338", "0105213054", "H@tah1384", new Authentication("09111262338"));
        neoBank.getBankData().addUser(sepehr);
        sepehr.getAuthenticated().authenticateUser(neoBank, sepehr);
        hedie.addContact(new Contact("Sepi", "<>.<>", sepehr.getPhoneNumber()));
        hedie.addContact(new Contact("Malake ziba", "Elsaii", aylin.getPhoneNumber()));
        sepehr.addContact(new Contact("humourless", " ", "09109056296"));
    }

    public static void initiateAdmins(NeoBank neoBank) {
        Admin first = new Admin("Mohsen Tahmouresi", "M.Tahmoures", "M@tah1345", neoBank.getBankData());
        neoBank.getManagerData().addAdmin(first);
        Admin second = new Admin("Shahrzad Oroji", "Sh_Taji", "Sh@oroj1384", neoBank.getBankData());
        neoBank.getManagerData().addAdmin(second);
        Admin third = new Admin("Mahdi Salman", "phoenix", "MS1384", neoBank.getBankData());
        neoBank.getManagerData().addAdmin(third);
        Admin fourth = new Admin("Ghazale Roostaei", "Ghazal12", "GH9122562348", neoBank.getBankData());
        neoBank.getManagerData().addAdmin(fourth);
        Admin fifth = new Admin("Toranj Ebrahimi", "Tori1397", "T.Eb1397", neoBank.getBankData());
        neoBank.getManagerData().addAdmin(fifth);
    }
}

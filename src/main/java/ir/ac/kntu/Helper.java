package ir.ac.kntu;

public class Helper {
    public void initiateUsers(NeoBank neoBank) {
        SimpleUser hedie = new SimpleUser("Hedie", "Tahmouresi", new SimCard("09109056296", false), "0025755900", "H@tah1384", new Authentication("09109056296"));
        hedie.getAuthenticated().authenticateUser(neoBank, hedie);
        neoBank.getBankData().addUser(hedie);
        SimpleUser aylin = new SimpleUser("Aylin", "Jabbari", new SimCard("09901917812", false), "0215021470", "A#Jab1384", new Authentication("09901917812"));
        aylin.getAuthenticated().authenticateUser(neoBank, aylin);
        neoBank.getBankData().addUser(aylin);
        aylin.setContactOption(false);
        SimpleUser amir = new SimpleUser("Amir", "Tahmouresi", new SimCard("09028789000", false), "0023577410", "A@tah1379", new Authentication("09028789000"));
        neoBank.getBankData().addUser(amir);
        neoBank.getBankData().addAuthentication(amir.getAuthenticated());
        SimpleUser neda = new SimpleUser("Neda", "Abtahi", new SimCard("09124464876", false), "0306201582", "Neda0*0abeN", new Authentication("09124464876"));
        neoBank.getBankData().addAuthentication(neda.getAuthenticated());
        neda.getAuthenticated().setAuthenticated(false);
        neda.getAuthenticated().setAnswer("I don't like you");
        neoBank.getBankData().addUser(neda);
        SimpleUser sepehr = new SimpleUser("Sepehr", "Ghardashi", new SimCard("09111262338", false), "0105213054", "H@tah1384", new Authentication("09111262338"));
        neoBank.getBankData().addUser(sepehr);
        sepehr.getAuthenticated().authenticateUser(neoBank, sepehr);
        this.initiateSimCards(neoBank, hedie, aylin, amir);
        this.initiateSimCards(neoBank, neda, sepehr);
        this.initiateContacts(hedie, sepehr);
    }

    public void initiateAdmins(NeoBank neoBank) {
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
        fifth.getAbilities().setAuthentications(false);
    }

    public void initiateContacts(SimpleUser firstUser, SimpleUser secondUser){
        firstUser.addContact(new Contact("Sepi", "<>.<>", new SimCard("09111262338", true)));
        firstUser.addContact(new Contact("Malake ziba", "Elsaii", new SimCard("09901917812", true)));
        secondUser.addContact(new Contact("humourless", " ", new SimCard("09109056296", true)));
    }

    public void initiateSimCards(NeoBank neoBank, SimpleUser hedie, SimpleUser aylin, SimpleUser amir){
        neoBank.getManagerData().addSimCard(hedie.getSimCard());
        neoBank.getManagerData().addSimCard(amir.getSimCard());
        neoBank.getManagerData().addSimCard(aylin.getSimCard());
    }

    public void initiateSimCards(NeoBank neoBank, SimpleUser neda, SimpleUser sepehr){
        neoBank.getManagerData().addSimCard(neda.getSimCard());
        neoBank.getManagerData().addSimCard(sepehr.getSimCard());
    }
}

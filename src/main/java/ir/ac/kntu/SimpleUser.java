package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleUser extends UserPerson {
    private String securityNumber;
    private String password;
    private Account account;
    private boolean contactOption;
    private Authentication authenticated;
    private List<Contact> contacts;
    private List<Request> requests;

    private final Input input = new Input();

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isContactOption() {
        return contactOption;
    }

    public void setContactOption(boolean contactOption) {
        this.contactOption = contactOption;
    }

    public Authentication getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Authentication authenticated) {
        this.authenticated = authenticated;
    }

    public SimpleUser(String name, String lastName, String phoneNumber, String securityNumber, String password, Authentication authentication) {
        super(name, lastName, phoneNumber);
        setSecurityNumber(securityNumber);
        setPassword(password);
        this.requests = new ArrayList<>();
        this.contacts = new ArrayList<>();
        setContactOption(true);
        setAuthenticated(authentication);
    }

    public boolean contactExistence(Contact currentContact) {
        for (Contact contact : contacts) {
            if (contact.equals(currentContact)) {
                return true;
            }
        }
        return false;
    }

    public void addContact(Contact newContact) {
        contacts.add(newContact);
    }

    public void addRequest(Request newRequest) {
        requests.add(newRequest);
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }

    public boolean showContacts() {
        if (contacts.isEmpty()) {
            return false;
        }
        for (int index = 0; index < this.contacts.size(); index++) {
            int num = index + 1;
            System.out.println(ColorConsole.PINK + num + ColorConsole.PURPLE + this.contacts.get(index).toString() + ColorConsole.RESET);
        }
        return true;
    }

    public void selectContact(NeoBank neoBank) {
        if (!this.showContacts()) {
            return;
        }
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return;
        } else if (!answer.matches("-?\\d+(\\.\\d+)?")) {
            System.out.println(ColorConsole.RED + "Wrong format! Try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < contacts.size() + 1) {
            for (int index = 1; index < contacts.size() + 1; index++) {
                if (answer.equals(Integer.toString(index))) {
                    Contact currentContact = this.contacts.get(index - 1);
                    currentContact.contactListOptions(neoBank, this);
                    break;
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Index Out of Bound! Try again!" + ColorConsole.RESET);
        }
        selectContact(neoBank);
    }

    public Contact getContact(NeoBank neoBank) {
        if (!this.showContacts()) {
            return null;
        }
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return null;
        } else if (!answer.matches("-?\\d+(\\.\\d+)?")) {
            System.out.println(ColorConsole.RED + "Wrong format! Try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) < contacts.size() + 1) {
            for (int index = 1; index < contacts.size() + 1; index++) {
                if (answer.equals(Integer.toString(index))) {
                    return this.contacts.get(index - 1);
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Index Out of Bound! Try again!" + ColorConsole.RESET);
        }
        return getContact(neoBank);
    }

    public void changeSecurityNumber(NeoBank neoBank) {
        System.out.println(ColorConsole.CYAN_BOLD + "Would you like to change your social security number? (previous phone number : " + ColorConsole.PURPLE + this.getSecurityNumber() + ColorConsole.CYAN_BOLD + ")" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            String securityNumber = input.nextSecurityNumber(neoBank.getBankData());
            if (securityNumber != null) {
                this.setSecurityNumber(securityNumber);
            }
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changeSecurityNumber(neoBank);
    }

    public void changePassword() {
        System.out.println(ColorConsole.CYAN_BOLD + "Would you like to change your password? (previous phone number : " + ColorConsole.PURPLE + this.getPassword() + ColorConsole.CYAN_BOLD + ")" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            String password = input.nextPassword();
            if (password != null) {
                this.setPassword(password);
            }
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again" + ColorConsole.RESET);
        }
        this.changePassword();
    }

    public void editSignUpInfo(NeoBank neoBank) {
        neoBank.getBankData().removeAuthentication(this);
        this.changeName();
        this.changeLastName();
        this.changePhoneNumber(neoBank, this, "user");
        this.changeSecurityNumber(neoBank);
        this.changePassword();
        neoBank.getBankData().addAuthentication(new Authentication(this.getPhoneNumber()));
        this.setAuthenticated(new Authentication(this.getPhoneNumber()));
        System.out.println(ColorConsole.GREEN + "Sign Up info changed!" + ColorConsole.RESET);
    }

    public void changeInfo(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Do you want to change your information?" + ColorConsole.RESET);
        String answer = input.nextLine();
        if ("no".equalsIgnoreCase(answer) || !input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.editSignUpInfo(neoBank);
            return;
        } else {
            System.out.println(ColorConsole.RED + "Wrong input! Try again!" + ColorConsole.RESET);
        }
        this.changeInfo(neoBank);
    }

    public void transferByAccountID(NeoBank neoBank) {
        String accountID = input.nextAccountID(neoBank);
        if (accountID == null) {
            return;
        }
        SimpleUser receiver = neoBank.getBankData().getUserByAccountID(accountID);
        String value = input.nextValue(neoBank, receiver);
        if (value == null) {
            return;
        }
        boolean confirmed = input.nextConfirmation(receiver, value);
        if (!confirmed) {
            System.out.println(ColorConsole.RED + "Transfer failed!" + ColorConsole.RESET);
            return;
        }
        this.getAccount().transfer(neoBank, value, receiver, false);
    }

    public void transferByContact(NeoBank neoBank) {
        Contact receiverContact = getContact(neoBank);
        if (receiverContact == null) {
            return;
        }
        SimpleUser receiver = neoBank.getBankData().getUserByPhone(receiverContact.getPhoneNumber());
        if (!this.checkContactForTransfer(receiver)) {
            return;
        }
        String value = input.nextValue(neoBank, receiver);
        if (value == null) {
            return;
        }
        boolean confirmed = input.nextConfirmation(receiver, value);
        if (!confirmed) {
            System.out.println(ColorConsole.RED + "Transfer failed!" + ColorConsole.RESET);
            return;
        }
        this.getAccount().transfer(neoBank, value, receiver, true);
    }

    public void transferByRecent(NeoBank neoBank) {
        Map<SimpleUser, Boolean> map = this.getAccount().selectRecent(neoBank);
        if (map == null) {
            return;
        }
        SimpleUser receiver = null;
        boolean byContact = false;
        for (Map.Entry<SimpleUser, Boolean> entry : map.entrySet()) {
            receiver = entry.getKey();
            byContact = entry.getValue();
        }
        if (byContact) {
            if (!checkContactForTransfer(receiver)) {
                return;
            }
        }
        String value = input.nextValue(neoBank, receiver);
        if (value == null) {
            return;
        }
        boolean confirmed = input.nextConfirmation(receiver, value);
        if (!confirmed) {
            System.out.println(ColorConsole.RED + "Transfer failed!" + ColorConsole.RESET);
            return;
        }
        this.getAccount().transfer(neoBank, value, receiver, byContact);
    }

    public boolean checkContactForTransfer(SimpleUser receiver) {
        if (!receiver.contactExistence(new Contact(" ", " ", this.getPhoneNumber()))) {
            System.out.println(ColorConsole.RED + "You can't transfer to a contact if they don't have you as a contact!" + ColorConsole.RESET);
            return false;
        } else if (!receiver.isContactOption()) {
            System.out.println(ColorConsole.RED + "You can't transfer money to this user by contact!" + ColorConsole.RESET);
            return false;
        }
        return true;
    }

    public Contact findContact(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        return null;
    }

    public void showAccountInfo() {
        System.out.println(ColorConsole.PINK + "Your Account ID : " + ColorConsole.PURPLE + this.getAccount().getAccountId() + ColorConsole.RESET);
        System.out.println(ColorConsole.PINK + "Your Credit Card ID : " + ColorConsole.PURPLE + this.getAccount().getCreditCard().getCreditCardId() + ColorConsole.RESET);
    }

    public void changeOrSetPassCode() {
        if (this.getAccount().getCreditCard().hasSetPassword()) {
            this.getAccount().getCreditCard().changePassCode();
        } else {
            this.getAccount().getCreditCard().setPassCode();
        }
    }

    public void changeContactOption() {
        String changeCondition = "";
        if (this.isContactOption()) {
            changeCondition = "off";
        } else {
            changeCondition = "on";
        }
        System.out.println(ColorConsole.BLUE + "Would you like to turn it " + ColorConsole.PURPLE_BOLD + changeCondition + ColorConsole.BLUE + "?" + ColorConsole.RESET);
        String answer = input.nextLine();
        if (!input.exitPoint(answer)) {
            return;
        } else if ("yes".equalsIgnoreCase(answer) && this.isContactOption()) {
            this.setContactOption(false);
            return;
        } else if ("yes".equalsIgnoreCase(answer)) {
            this.setContactOption(true);
            return;
        } else if ("no".equalsIgnoreCase(answer)) {
            return;
        }
        System.out.println(ColorConsole.RED + "Please enter yes or no! I can't understand this!" + ColorConsole.RESET);
        this.changeContactOption();
    }

    public void addRequest(NeoBank neoBank) {
        RequestSection section = input.nextRequestSection();
        if (section == null) {
            return;
        }
        System.out.println(ColorConsole.BLUE_BOLD + "Please Enter your problem." + ColorConsole.RESET);
        String text = input.nextLine();
        if (!input.exitPoint(text)) {
            return;
        }
        Request newRequest = new Request(text, section, this.getPhoneNumber());
        this.addRequest(newRequest);
        neoBank.getBankData().addRequest(newRequest);
        System.out.println(ColorConsole.GREEN + "Request successfully noted!" + ColorConsole.RESET);
    }

    public void showRequests() {
        for (int index = 1; index <= this.requests.size(); index++) {
            System.out.println(ColorConsole.PINK + index + ". " + ColorConsole.PURPLE + this.requests.get(index - 1) + ColorConsole.RESET);
        }
    }

    public void selectRequest(NeoBank neoBank) {
        this.showRequests();
        String answer = input.nextLine();
        if (requests.isEmpty()) {
            return;
        }
        if (!input.exitPoint(answer)) {
            return;
        } else if (!answer.matches("[0-9]+")) {
            System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
        } else if (Integer.parseInt(answer) > 0 && Integer.parseInt(answer) <= this.requests.size()) {
            for (int index = 1; index <= this.requests.size(); index++) {
                if (answer.equals(Integer.toString(index))) {
                    this.requests.get(index - 1).showInfo();
                }
            }
        } else {
            System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
        }
        this.selectRequest(neoBank);
    }


    public void showUserInfo(NeoBank neoBank) {
        System.out.println(ColorConsole.PURPLE + "Name : " + ColorConsole.BLUE_BOLD + this.getName() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Last Name : " + ColorConsole.BLUE_BOLD + this.getLastName() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Phone Number : " + ColorConsole.BLUE_BOLD + this.getPhoneNumber() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Account Id : " + ColorConsole.BLUE_BOLD + this.getAccount().getAccountId() + ColorConsole.RESET);
        System.out.println(ColorConsole.PURPLE + "Transactions : " + ColorConsole.RESET);
        this.getAccount().showAllTransactions(neoBank);
    }
}

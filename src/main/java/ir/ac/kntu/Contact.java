package ir.ac.kntu;

public class Contact extends Person {
    private String phoneNumber;

    public Contact(String name, String lastName, String phoneNumber) {
        super(name, lastName);
        setPhoneNumber(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static boolean existsContact(SimpleUser user, String phoneNumber) {
        for (int i = 0; i < user.contactSize(); i++) {
            if (user.getSpecificContact(i).getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }

    public static Contact getContact(SimpleUser user, String phoneNumber) {
        for (int i = 0; i < user.contactSize(); i++) {
            if (user.getSpecificContact(i).getPhoneNumber().equals(phoneNumber)) {
                return user.getSpecificContact(i);
            }
        }
        return null;
    }

    public static void addContacts(NeoBank neoBank, SimpleUser user) {
        System.out.println(ColorConsole.BLUE + "Please Enter the name of your new contact" + ColorConsole.RESET);
        String name = Input.inputNextLine();
        System.out.println(ColorConsole.BLUE + "Please Enter the last name of your new contact" + ColorConsole.RESET);
        String lastName = Input.inputNextLine();
        System.out.println(ColorConsole.BLUE + "Please Enter the phone number of your new contact" + ColorConsole.RESET);
        String phoneNumber = Input.inputNextLine();
        if (neoBank.getSpecificUser(phoneNumber) != -1) {
            if (existsContact(user, phoneNumber)) {
                if (neoBank.getSpecificUser(neoBank.getSpecificUser(phoneNumber)).isAuthenticated()) {
                    Contact newContact = new Contact(name, lastName, phoneNumber);
                    user.addContact(newContact);
                    System.out.println(ColorConsole.GREEN + "Contact successfully added" + ColorConsole.RESET);
                } else {
                    System.out.println(ColorConsole.RED + "This user hasn't been authenticated so they don't have an account!" + ColorConsole.RESET);
                }
            } else {
                System.out.println(ColorConsole.RED + "You already have a contact with this number! check your contact list!" + ColorConsole.RESET);
            }
        } else {
            System.out.println(ColorConsole.RED + "This user doesn't exist!" + ColorConsole.RESET);
        }
    }

    @Override
    public String toString() {
        return ". Name : " + this.getName() + ", Last name : " + this.getSurname();
    }

    public void showInfo() {
        System.out.println(ColorConsole.PURPLE + this.toString() + ColorConsole.RESET);
        System.out.println(ColorConsole.CYAN + "Phone Number : " + this.getPhoneNumber() + ColorConsole.RESET);
    }

    public static Contact selectContact(NeoBank neoBank, SimpleUser user) {
        String input;
        do {
            user.showContacts();
            if (user.contactSize() == 0) {
                return null;
            }
            input = Input.inputNextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                System.exit(0);
            } else if ("return".equalsIgnoreCase(input)) {
                return null;
            } else if (!input.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
            } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < user.contactSize() + 1) {
                for (int index = 1; index < user.contactSize() + 1; index++) {
                    if (input.equals(Integer.toString(index))) {
                        return user.getSpecificContact(index - 1);
                    }
                }
            } else {
                System.out.println(ColorConsole.RED + "Wrong input try again!" + ColorConsole.RESET);
            }
        } while (!"retrun".equalsIgnoreCase(input));
        return null;
    }

    public void changePhoneNumber(NeoBank neoBank, SimpleUser user) {
        System.out.println(ColorConsole.CYAN_BOLD + "Would you like to change your phone number? (previous phone number : " + ColorConsole.PURPLE + this.getPhoneNumber() + ColorConsole.CYAN_BOLD + ")" + ColorConsole.RESET);
        String ans = Input.inputNextLine();
        if ("no".equalsIgnoreCase(ans)) {
            return;
        }
        do {
            if (!Input.checkInput(ans)) {
                return;
            } else if ("yes".equalsIgnoreCase(ans)) {
                System.out.println(ColorConsole.BLUE + "Write the phone number you have!" + ColorConsole.RESET);
                String phoneNumber;
                do {
                    phoneNumber = Input.inputNextLine();
                    if (!Input.checkInput(ans)) {
                        return;
                    }
                } while (!checkContact(neoBank, user, phoneNumber));
                this.setPhoneNumber(phoneNumber);
                return;
            }
            System.out.println(ColorConsole.RED + "wrong input! try again" + ColorConsole.RESET);
            ans = Input.inputNextLine();
        } while (!"no".equalsIgnoreCase(ans));
    }

    public boolean checkContact(NeoBank neoBank, SimpleUser user, String phoneNumber) {
        if (!neoBank.getSpecificUser(neoBank.getSpecificUser(phoneNumber)).isAuthenticated()) {
            System.out.println(ColorConsole.RED + "User not authenticated!" + ColorConsole.RESET);
            return false;
        } else if (neoBank.getSpecificUser(phoneNumber) == -1) {
            System.out.println(ColorConsole.RED + "User doesn't exist" + ColorConsole.RESET);
            return false;
        } else if (!Input.checkPhoneNumber(phoneNumber)) {
            System.out.println(ColorConsole.RED + "phone number invalid!!!" + ColorConsole.RESET);
            return false;
        } else if (!existsContact(user, phoneNumber)) {
            System.out.println(ColorConsole.RED + "You already have a contact with this number! check your contact list!" + ColorConsole.RESET);
            return false;
        }
        return true;
    }

    public void changeContactInfo(NeoBank neoBank, SimpleUser user) {
        System.out.println(ColorConsole.BLUE + "Would you like to edit your contact?");
        System.out.println("   1.yes");
        System.out.println("   2.no" + ColorConsole.RESET);
        String input = Input.inputNextLine();
        switch (input) {
            case "1", "yes":
                this.changeName();
                this.changeLastName();
                this.changePhoneNumber(neoBank, user);
                System.out.println(ColorConsole.GREEN + "Contact successfully changed" + ColorConsole.RESET);
                break;
            case "2", "no":
                break;
            default:
                if ("return".equalsIgnoreCase(input)) {
                    return;
                } else if ("quit".equalsIgnoreCase(input)) {
                    System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                    System.exit(0);
                } else {
                    System.out.println(ColorConsole.RED + "Wrong input!" + ColorConsole.RESET);
                }
                break;
        }
    }

    public void deleteContact(SimpleUser user) {
        System.out.println(ColorConsole.BLUE + "Would you like to delete your contact?");
        System.out.println("   1.yes");
        System.out.println("   2.no" + ColorConsole.RESET);
        String input = Input.inputNextLine();
        switch (input) {
            case "1", "yes":
                user.removeContact(this);
                break;
            case "2", "no":
                break;
            default:
                if ("return".equalsIgnoreCase(input)) {
                    return;
                } else if ("quit".equalsIgnoreCase(input)) {
                    System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
                    System.exit(0);
                } else {
                    System.out.println(ColorConsole.RED + "Wrong input!" + ColorConsole.RESET);
                }
                break;
        }
    }

    public void doContactStuff(NeoBank neoBank, SimpleUser user) {
        this.showInfo();
        this.changeContactInfo(neoBank, user);
        this.deleteContact(user);
    }
}
package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    private Scanner input;

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Input() {
        setInput(new Scanner(System.in));
    }

    public String nextLine() {
        System.out.print(ColorConsole.YELLOW + "~");
        String returnValue = this.input.nextLine().trim();
        System.out.print(ColorConsole.RESET);
        return returnValue;
    }

    public boolean exitPoint(String userInput) {
        if ("quit".equalsIgnoreCase(userInput)) {
            System.out.println(ColorConsole.PURPLE + "*Thanks for trusting our Bank!*" + ColorConsole.RESET);
            System.exit(0);
        }
        return !"return".equalsIgnoreCase(userInput);
    }

    public String nextPassword() {
        System.out.println(ColorConsole.BLUE + "Please enter your password" + ColorConsole.RESET);
        String password = this.nextLine();
        if (!this.exitPoint(password)) {
            return null;
        }
        if (password.length() < 8) {
            System.out.println(ColorConsole.RED_BOLD + "your password is less than 8 letters!" + ColorConsole.RESET);
            return this.nextPassword();
        }
        String numRegEx = "[0-9]";
        Pattern numPattern = Pattern.compile(numRegEx);
        Matcher numMatcher = numPattern.matcher(password);
        String capitalRegEx = "[A-Z]";
        Pattern capitalPattern = Pattern.compile(capitalRegEx);
        Matcher capitalMatcher = capitalPattern.matcher(password);
        String smallRegEx = "[a-z]";
        Pattern smallPattern = Pattern.compile(smallRegEx);
        Matcher smallMatcher = smallPattern.matcher(password);
        String uniqueRegEx = "[@#$%^&*]";
        Pattern uniquePattern = Pattern.compile(uniqueRegEx);
        Matcher uniqueMatcher = uniquePattern.matcher(password);
        if (numMatcher.find() && capitalMatcher.find() && smallMatcher.find() && uniqueMatcher.find()) {
            return password;
        }
        System.out.println(ColorConsole.RED_BOLD + "weak password! Please try again!" + ColorConsole.RED + "\nyour password must contain at least one unique character at, one capital letter and one small letter and absolutely at least one number!" + ColorConsole.RESET);
        return this.nextPassword();
    }

    public String nextPhoneNumber(Data data, String need) {
        System.out.println(ColorConsole.BLUE + "Please enter the phone number" + ColorConsole.RESET);
        String phoneNumber;
        do {
            phoneNumber = this.nextLine();
            if (!this.exitPoint(phoneNumber)) {
                return null;
            }
        } while (!data.checkPhoneNumber(phoneNumber, need));
        return phoneNumber;
    }

    public String nextSecurityNumber(Data data) {
        String securityNumber;
        System.out.println(ColorConsole.BLUE + "Please enter your social security number" + ColorConsole.RESET);
        do {
            securityNumber = this.nextLine();
            if (!this.exitPoint(securityNumber)) {
                return null;
            }
        } while (!data.checkSecurityNumber(securityNumber));
        return securityNumber;
    }

    public boolean checkDate(String date) {
        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid date format! please enter by this pattern yyyy-mm-dd" + ColorConsole.RESET);
            return false;
        }
        return true;
    }

    public void nextContact(NeoBank neoBank, SimpleUser currentUser) {
        System.out.println(ColorConsole.BLUE + "Please Enter the name of your new contact" + ColorConsole.RESET);
        String name = this.nextLine();
        if (!this.exitPoint(name)) {
            return;
        }
        System.out.println(ColorConsole.BLUE + "Please Enter the last name of your new contact" + ColorConsole.RESET);
        String lastName = input.nextLine();
        if (!this.exitPoint(lastName)) {
            return;
        }
        String phoneNumber = this.nextPhoneNumber(neoBank.getBankData(), "should exist");
        if (phoneNumber == null) {
            return;
        }
        SimCard simCard = neoBank.getManagerData().getSimCard(phoneNumber);
        if (simCard == null) {
            simCard = new SimCard(phoneNumber, false);
        }
        Contact newContact = new Contact(name, lastName, simCard);
        newContact.addContact(neoBank, currentUser);
    }

    public String nextAccountID(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Please enter the account Id of the person you would like to transfer money to!" + ColorConsole.RESET);
        String answer = this.nextLine();
        String regexID = "[0-9]{13}";
        Pattern patternID = Pattern.compile(regexID);
        Matcher matcherID = patternID.matcher(answer);
        if (!this.exitPoint(answer)) {
            return null;
        } else if (!matcherID.matches()) {
            System.out.println(ColorConsole.RED + "Wrong format! Please try again! each account ID involves 13 digits!" + ColorConsole.RESET);
        } else if (!neoBank.getBankData().existsAccountID(answer)) {
            System.out.println(ColorConsole.RED + "No account with this ID exists! Try again!" + ColorConsole.RESET);
        } else {
            return answer;
        }
        return this.nextAccountID(neoBank);
    }

    public String nextValue(SimpleUser receiver, Double limit) {
        System.out.println(ColorConsole.PURPLE + "How much would you like to transfer to " + ColorConsole.PINK + receiver.getName() + " " + receiver.getLastName() + ColorConsole.PURPLE + "?" + ColorConsole.RESET);
        String answer = this.nextLine();
        if (!this.exitPoint(answer)) {
            return null;
        } else if (!answer.matches("[0-9]+\\.?[0-9]*")) {
            System.out.println(ColorConsole.RED_BOLD + "Wrong Format! Try again! Please Enter a number!" + ColorConsole.RESET);
        } else if (Double.parseDouble(answer)<limit){
            return answer;
        } else{
            System.out.println(ColorConsole.RED + "You can't transfer more that 8000000" + ColorConsole.RESET);
        }
        return this.nextValue(receiver, limit);
    }

    public boolean nextConfirmation(SimpleUser receiver, String value) {
        System.out.println(ColorConsole.GREEN + "Receiver {Name : " + ColorConsole.YELLOW + receiver.getName() + ColorConsole.GREEN + ", Last Name : " + ColorConsole.YELLOW + receiver.getLastName() + "}" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Value : " + ColorConsole.YELLOW + value + "$" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Are you sure?" + ColorConsole.RESET);
        String answer = this.nextLine();
        if (!this.exitPoint(answer)) {
            return false;
        }
        return "yes".equalsIgnoreCase(answer);
    }

    public boolean nextConfirmation(String receiver, String sender, String value) {
        System.out.println(ColorConsole.GREEN + "Receiver { your " + ColorConsole.YELLOW + receiver + ColorConsole.GREEN + " }" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Sender { your " + ColorConsole.YELLOW + sender + ColorConsole.GREEN + " }" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Value : " + ColorConsole.YELLOW + value + "$" + ColorConsole.RESET);
        System.out.println(ColorConsole.GREEN + "Are you sure?" + ColorConsole.RESET);
        String answer = this.nextLine();
        if (!this.exitPoint(answer)) {
            return false;
        }
        return "yes".equalsIgnoreCase(answer);
    }

    public RequestSection nextRequestSections() {
        List<String> sections = new ArrayList<>(Arrays.asList(ColorConsole.CYAN + "Authentications", ColorConsole.CYAN + "Report", ColorConsole.CYAN + "Funds", ColorConsole.CYAN + "Contacts", ColorConsole.CYAN + "Transfer", ColorConsole.CYAN + "Sim Charge", ColorConsole.CYAN + "Credit Card", ColorConsole.CYAN + "Settings", ColorConsole.CYAN + "Return" + ColorConsole.RESET));
        Pagination<String> sectionList = new Pagination<>(sections, 5);
        String answer;
        do {
            sectionList.showPage();
            answer = this.nextLine();
            if (!this.exitPoint(answer) || "9".equals(answer)) {
                return null;
            } else if ("next".equals(answer) || "previous".equals(answer)) {
                sectionList.changePage(answer);
            } else {
                return this.nextSection(answer);
            }
        } while (true);
    }

    public RequestSection nextSection(String answer) {
        switch (answer) {
            case "1", "Authentications":
                return RequestSection.AUTHENTICATIONS;
            case "2", "Report":
                return RequestSection.REPORT;
            case "3", "Funds":
                return RequestSection.FUNDS;
            case "4", "Contacts":
                return RequestSection.CONTACTS;
            case "5", "Transfer":
                return RequestSection.TRANSFER;
            case "6", "Sim Charge":
                return RequestSection.SIM_CHARGE;
            case "7", "Credit Card":
                return RequestSection.CREDIT_CARD;
            case "8", "Settings":
                return RequestSection.SETTINGS;
            default:
                if (!this.exitPoint(answer)) {
                    return null;
                }
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
        return null;
    }


    public RequestStatus nextRequestStatus() {
        System.out.println(ColorConsole.CYAN + "Choose a status:\n   1.Noted\n   2.In Process\n   3.Processed" + ColorConsole.RESET);
        String answer = this.nextLine();
        switch (answer) {
            case "1", "Noted":
                return RequestStatus.NOTED;
            case "2", "In Process":
                return RequestStatus.IN_PROCESS;
            case "3", "Processed":
                return RequestStatus.PROCESSED;
            default:
                if (!this.exitPoint(answer)) {
                    return null;
                }
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);
        }
        return this.nextRequestStatus();
    }

    public String nextRequestPerson(NeoBank neoBank) {
        System.out.println(ColorConsole.BLUE + "Please enter the phone number of the person" + ColorConsole.RESET);
        String answer = this.nextLine();
        if (!this.exitPoint(answer)) {
            return null;
        } else if (!answer.matches("^09[0-9]{9}$")) {
            System.out.println(ColorConsole.RED + "Wrong format! Please try again!");
        } else if (neoBank.getBankData().getUserByPhone(answer) == null) {
            System.out.println(ColorConsole.RED + "This user doesn't exist" + ColorConsole.RESET);
        } else {
            return answer;
        }
        return this.nextRequestPerson(neoBank);
    }

    public String nextSearchName() {
        System.out.println(ColorConsole.BLUE + "Do you want to enter a name?" + ColorConsole.RESET);
        String answer = this.nextLine();
        switch (answer) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Enter the name you are looking for!" + ColorConsole.RESET);
                String name = this.nextLine();
                if (!this.exitPoint(name)) {
                    return null;
                } else {
                    return name;
                }
            case "no":
                return null;
            default:
                if (!this.exitPoint(answer)) {
                    return null;
                }
                System.out.println(ColorConsole.RED + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
        return nextSearchName();

    }

    public String nextSearchLastName() {
        System.out.println(ColorConsole.BLUE + "Do you want to enter a last name?" + ColorConsole.RESET);
        String answer = this.nextLine();
        switch (answer) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Please enter the last name you are looking for" + ColorConsole.RESET);
                String lastName = this.nextLine();
                if (!this.exitPoint(answer)) {
                    return null;
                } else {
                    return lastName;
                }
            case "no":
                return null;
            default:
                if (!this.exitPoint(answer)) {
                    return null;
                }
                System.out.println(ColorConsole.RED_BOLD + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
        return nextSearchLastName();
    }


    public String nextSearchPhoneNumber() {
        System.out.println(ColorConsole.BLUE + "Do you want to enter a Phone number?" + ColorConsole.RESET);
        String answer = this.nextLine();
        switch (answer) {
            case "yes":
                System.out.println(ColorConsole.BLUE + "Please enter the phone number you are looking for" + ColorConsole.RESET);
                String phoneNumber = this.nextLine();
                if (!this.exitPoint(phoneNumber)) {
                    return null;
                } else if (phoneNumber.matches("^09[0-9]{9}$")) {
                    return phoneNumber;
                } else {
                    System.out.println(ColorConsole.RED + "Wrong format!" + ColorConsole.RESET);
                }
            case "no":
                return null;
            default:
                if (!this.exitPoint(answer)) {
                    return null;
                }
                System.out.println(ColorConsole.RED_BOLD + "THERE IS NO OTHER OPTION! Please input something else!" + ColorConsole.RESET);

        }
        return nextSearchPhoneNumber();
    }

    public String nextFundType() {
        System.out.println(ColorConsole.BLUE + "What kind of fund do you want to make?");
        System.out.println("   1. Savings Fund");
        System.out.println("   2. Remains Fund");
        System.out.println("   3. Bonus Fund" + ColorConsole.RESET);
        String answer = this.nextLine();
        if (!this.exitPoint(answer)) {
            return null;
        } else if ("1".equals(answer) || "2".equals(answer) || "3".equals(answer) || "4".equals(answer) || "Savings Fund".equals(answer) || "Remains Fund".equals(answer) || "Bonus Fund".equals(answer)) {
            return answer;
        }
        return nextFundType();
    }


    public Account nextAccountID(CentralBank centralBank) {
        System.out.println(ColorConsole.BLUE + "Please enter the account Id of the person you would like to transfer money to!" + ColorConsole.RESET);
        String answer = this.nextLine();
        String regexID = "^[0-9]{13}$";
        Pattern patternID = Pattern.compile(regexID);
        Matcher matcherID = patternID.matcher(answer);
        if (!this.exitPoint(answer)) {
            return null;
        } else if (!matcherID.matches()) {
            System.out.println(ColorConsole.RED + "Wrong format! Please try again! each account ID involves 13 digits!" + ColorConsole.RESET);
        } else if (centralBank.existsAccountId(answer)==null) {
            System.out.println(ColorConsole.RED + "No account with this ID exists! Try again!" + ColorConsole.RESET);
        } else {
            return centralBank.existsAccountId(answer);
        }
        return this.nextAccountID(centralBank);
    }


    public String nextCreditCardID(CentralBank centralBank){
        System.out.println(ColorConsole.BLUE + "Please enter the credit card Id of the person you would like to transfer money to!" + ColorConsole.RESET);
        String answer = this.nextLine();
        String regexID = "[0-9]{16}";
        Pattern patternID = Pattern.compile(regexID);
        Matcher matcherID = patternID.matcher(answer);
        if (!this.exitPoint(answer)) {
            return null;
        } else if (!matcherID.matches()) {
            System.out.println(ColorConsole.RED + "Wrong format! Please try again! each credit card ID involves 16 digits!" + ColorConsole.RESET);
        } else if (centralBank.existsCreditCardId(answer)==null) {
            System.out.println(ColorConsole.RED + "No card with this ID exists! Try again!" + ColorConsole.RESET);
        } else {
            return answer;
        }
        return this.nextCreditCardID(centralBank);
    }
}

package ir.ac.kntu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    private static Scanner scn;

    public static String inputNextLine() {
        scn = new Scanner(System.in);
        System.out.print(ColorConsole.YELLOW);
        String input = scn.nextLine().trim();
        System.out.print(ColorConsole.RESET);
        return input;
    }

    public static boolean checkSecurityNumber(String securityNumber) {
        String numberRegEx = "^[0-9]{10}$";
        Pattern numberPattern = Pattern.compile(numberRegEx);
        Matcher numberMatcher = numberPattern.matcher(securityNumber);
        if (!numberMatcher.matches()) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid Social Security Number Please try again" + ColorConsole.RESET);
            return false;
        }
        return true;
    }


    public static boolean checkPhoneNumber(String phoneNumber) {
        String phoneRegEx = "^09[0-9]{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegEx);
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid phone number Please try again" + ColorConsole.RESET);
            return false;
        }
        return true;
    }


    public static boolean checkPassword(String password) {
        if (password.length() < 8) {
            System.out.println(ColorConsole.RED_BOLD + "your password is less than 8 letters!" + ColorConsole.RESET);
            return false;
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
            return true;
        }
        System.out.println(ColorConsole.RED_BOLD + "weak password! Please try again!" + ColorConsole.RESET);
        System.out.println(ColorConsole.RED + "your password must contain 8 characters, a unique character, at least one capital letter and one small letter and ofcourse at least one number!" + ColorConsole.RESET);
        return false;
    }

    public static boolean checkAccountID(String accountID) {
        String regexID = "[0-9]{13}";
        Pattern patternID = Pattern.compile(regexID);
        Matcher matcherID = patternID.matcher(accountID);
        return matcherID.matches();
    }

    public static boolean checkInput(String input) {
        if ("return".equalsIgnoreCase(input)) {
            return false;
        } else if ("quit".equalsIgnoreCase(input)) {
            System.out.println(ColorConsole.PURPLE + "Thanks for trusting our bank! Bye Bye!" + ColorConsole.RESET);
            System.exit(0);
        }
        return true;
    }

    public static String takePassword() {
        String password;
        do {
            password = Input.inputNextLine();
            if (!checkInput(password)) {
                return null;
            }
        } while (!checkPassword(password));
        return password;
    }

    public static String takeSecurityNumber(NeoBank neoBank) {
        String securityNumber;
        do {
            securityNumber = Input.inputNextLine();
            if (!Input.checkInput(securityNumber)) {
                return null;
            }
        } while (!Input.checkSecurityNumber(securityNumber) || !neoBank.existsSecurityNumber(securityNumber));
        return securityNumber;
    }

    public static String takePhoneNumber(NeoBank neoBank) {
        String phoneNumber;
        do {
            phoneNumber = Input.inputNextLine();
            if (!Input.checkInput(phoneNumber)) {
                return null;
            }
        } while (!Input.checkPhoneNumber(phoneNumber) || !neoBank.existsPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    public static boolean checkDate(String date) {
        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            System.out.println(ColorConsole.RED_BOLD + "Invalid date format please enter byk this pattern yyyy-mm-dd" + ColorConsole.RESET);
            return false;
        }
        return true;
    }
}

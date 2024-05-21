package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    private static Scanner scn;

    public static String inputNextLine(){
        scn = new Scanner(System.in);
        return scn.nextLine().trim();
    }

    public static int inputNextInt(){
        scn = new Scanner(System.in);
        return scn.nextInt();
    }

    public static boolean checkSecurityNumber(String securityNumber){
        String numberRegEx = "^[0-9]{10}$";
        Pattern numberPattern = Pattern.compile(numberRegEx);
        Matcher numberMatcher = numberPattern.matcher(securityNumber);
        if (!numberMatcher.matches()){
            System.out.println("Invalid Social Security Number Please try again");
            return false;
        }
        return true;
    }

    public static boolean existsSecurityNumber(String securityNumber, ArrayList<SimpleUser> users){
        if (users.isEmpty()){
            return true;
        }
        for (SimpleUser user : users) {
            if (user.getSecurityNumber().equals(securityNumber)) {
                System.out.println("SomeBody with this social security number already an account! Please try again!");
                return false;
            }
        }
        return true;
    }

    public static boolean checkPhoneNumber (String phoneNumber){
        String phoneRegEx = "^09[0-9]{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegEx);
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            System.out.println("Invalid phone number Please try again");
            return false;
        }
        return true;
    }
    public static boolean existsPhoneNumber(String phoneNumber, ArrayList<SimpleUser> users){
        if (users.isEmpty()){
            return true;
        }
        for (SimpleUser user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("SomeBody with this phone number already exists! Please try again!");
                return false;
            }
        }
        return true;
    }

    public static boolean checkPassword(String password){
        if (password.length()<8){
            System.out.println("your password is less than 8 letters!");
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
        String uniqueRegEx = "@|#|\\$|%|\\^|&|\\*";
        Pattern uniquePattern = Pattern.compile(uniqueRegEx);
        Matcher uniqueMatcher = uniquePattern.matcher(password);
        if (numMatcher.find() && capitalMatcher.find() && smallMatcher.find() && uniqueMatcher.find()){
            return true;
        }
        System.out.println("weak password! Please try again!");
        System.out.println("your password must contain 8 characters, a unique character, at least one capital letter and one small letter and ofcourse at least one number!");
        return false;
    }

    public static boolean existsAccountId(String accountId, ArrayList<SimpleUser> users){
        for (int index = 0 ; index < users.size()-1 ; index++) {
            if (users.get(index).getAccount().getAccountId().equals(accountId)) {
                return false;
            }
        }
        return true;
    }

    public static boolean existsCreditCard(String creditCardId, ArrayList<SimpleUser> users){
        for (int index = 0 ; index < users.size()-1 ; index++) {
            if (users.get(index).getAccount().getCreditCard().getCreditCardId().equals(creditCardId)){
                return false;
            }
        }
        return true;
    }
}

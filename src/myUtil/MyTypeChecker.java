package myUtil;

public class MyTypeChecker {
    public static boolean checkString(String checkS){
        char[] charArray = checkS.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkInteger(String checks) {
        char[] charArray = checks.toCharArray();
        for (char c : charArray) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}

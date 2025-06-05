import javax.swing.JOptionPane;

public class InputValidator {
    // 기존 메서드
    public static boolean validCredit(int c) {
        return c == 1 || c == 2 || c == 3;
    }

    public static boolean validCount(int count) {
        return count >= 0;
    }

    // 새로 추가된 검증 메서드
    public static boolean isValidInteger(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String input) {
        if (!isValidInteger(input)) return false;
        return Integer.parseInt(input.trim()) > 0;
    }

    public static boolean isNonNegativeInteger(String input) {
        if (!isValidInteger(input)) return false;
        return Integer.parseInt(input.trim()) >= 0;
    }
}


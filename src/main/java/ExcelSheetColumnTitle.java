public class ExcelSheetColumnTitle {
    public static void main(String[] args) {
        System.out.println(convertToTitle(27));
    }

    public static String convertToTitle(int columnNumber) {
        //701: 701%26 = 25(Y); 701/26=26

        StringBuilder result = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            result.append(digitToLetter(columnNumber%26));
            columnNumber/=26;
        }

        return result.reverse().toString().toUpperCase();
    }

    private static char digitToLetter(int digit) {
        return (char)(digit + 'A');
    }

    public static int titleToNumber(String columnTitle) {
        int result = 0;
        for (int i = columnTitle.length() - 1, j = 0; i >= 0; i--) {
            char ch = columnTitle.charAt(i);
            result+= Math.pow(26, j++) * charToInteger(ch);
        }
        return result;
    }

    private static int charToInteger(char ch) {
        return ch - 'A' + 1;
    }
}

public class RomanToInteger {
    public static void main(String[] args) {
        for (String s : new String[]{"III", "LVIII", "MCMXCIV"}) {
            System.out.println(romanToInt(s));
        }
    }

    private static int romanToInt(String s) {
        int result = 0;
        char[] asChars = s.toCharArray();
        for (int i = 0; i < asChars.length; i++) {
            int current = map(asChars[i]);
            if (i < asChars.length - 1 && shouldBeNegated(asChars[i], asChars[i+1])) {
                current *= -1;
            }

            result += current;
        }

        return result;
    }

    private static int map(char ch) {
        switch (ch) {
            case 'I' : return 1;
            case 'V' : return 5;
            case 'X' : return 10;
            case 'L' : return 50;
            case 'C' : return 100;
            case 'D' : return 500;
            case 'M' : return 1000;

            default: throw new RuntimeException("stranger things !");
        }
    }

    private static boolean shouldBeNegated(char current, char next) {
        if (current == 'I' && (next == 'V' || next == 'X')) return true;
        if (current == 'X' && (next == 'L' || next == 'C')) return true;
        if (current == 'C' && (next == 'D' || next == 'M')) return true;

        return false;
    }
}

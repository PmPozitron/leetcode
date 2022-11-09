import java.util.Map;

public class AddBinary {
    public static void main(String[] args) {

        var inputsAndExpectedResults = Map.of(
               "11 1", "100",
                "1010 1011", "10101",

                "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101 110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011",
                "110111101100010011000101110110100000011101000101011001000011011000001100011110011010010011000000000"
        );

        inputsAndExpectedResults.entrySet().stream()
                .forEach(item -> {
                    var strings = item.getKey().split(" ");
                    var result = addBinary(strings[0], strings[1]);

                    System.out.printf("for args %s and %s expected result is %s and actual is %s\n",
                            strings[0], strings[1], item.getValue(), result);
                });

    }

    private static String addBinary(String a, String b) {
        String big;
        String small;
        StringBuilder result = new StringBuilder();

        if (a.length() >= b.length()) {
            big = a;
            small = b;
        } else {
            big = b;
            small = a;
        }

        int extra = 0;
        for (int i = 0; i < big.length(); i++) {
            char fromBig = big.charAt(big.length() - 1 - i);
            char fromSmall = i >= small.length()
                    ? '0'
                    : small.charAt(small.length() - 1 - i);

            int current = Character.digit(fromBig, 2) + Character.digit(fromSmall, 2);
            if (current == 0) {
                result.append(extra);
                extra = 0;

            } else if (current == 1) {
                if (extra == 0) {
                    result.append(current);
                } else {
                    result.append('0');
                 }
            } else if (current == 2) {
                if (extra == 0) {
                    result.append('0');
                    extra = 1;
                } else {
                    result.append('1');
                }
            } else {
                throw new RuntimeException("stranger things");
            }
        }

        if (extra == 1) {
            result.append('1');
        }

        return result.reverse().toString();
    }

    private static int parseBinary(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = (int) (Math.pow(2, s.length() - 1 - i) * Integer.parseInt(s.substring(i, i + 1)));
            result += current;
        }

//        System.out.printf("%d and should be %d\n", result, Integer.parseInt(s, 2));
//        System.out.printf("%d and should be %d\n", result, Long.parseLong(s, 2));
        return result;
    }
}

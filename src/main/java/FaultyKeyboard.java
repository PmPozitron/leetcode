import java.util.Map;

public class FaultyKeyboard {
    public static void main(String[] args) {
        Map.of("string", "rtsng", "poiinter", "ponter")
            .entrySet()
            .forEach(entry -> System.out.printf("input is %s, expected output is %s and actual output is %s\n",
                entry.getKey(), entry.getValue(), finalString(entry.getKey())));
    }

    /*
    https://leetcode.com/problems/faulty-keyboard
     */
    public static String finalString(String s) {
        StringBuilder result = new StringBuilder();
        s.chars().forEach(c -> {
            if (c != 'i') {
                result.append((char) c);
            } else {
                result.reverse();
            }
        });

        return result.toString();
    }
}

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class IsomorphicStrings {

    public static void main(String[] args) {
        Map.of(
                Map.entry("egg", "add"), true,
                Map.entry("foo", "bar"), false,
                Map.entry("paper", "title"), true)
                .entrySet().forEach(item -> System.out.printf("for %s and %s expected result is %b and actual is %b\n",
                        item.getKey().getKey(), item.getKey().getValue(), item.getValue(), isIsomorphic(item.getKey().getKey(), item.getKey().getValue())));
    }

    /*
    https://leetcode.com/problems/isomorphic-strings/
     */
    public static boolean isIsomorphic(String s, String t) {
        Map<Character, Integer> firstDictionary = new LinkedHashMap<>();
        int[] firstEncoded = new int[s.length()];
        int code = 0;
        for (int i = 0; i < s.length(); i++) {
            if (firstDictionary.containsKey(s.charAt(i))) {

            } else {
                firstDictionary.put(s.charAt(i), code++);
            }
            firstEncoded[i] = firstDictionary.get(s.charAt(i));
        }
        Map<Character, Integer> secondDictionary = new LinkedHashMap<>();
        int[] secondEncoded = new int[t.length()];
        code = 0;
        for (int i = 0; i < t.length(); i++) {
            if (secondDictionary.containsKey(t.charAt(i))) {

            } else {
                secondDictionary.put(t.charAt(i), code++);
            }
            secondEncoded[i] = secondDictionary.get(t.charAt(i));
        }

        return Arrays.equals(firstEncoded, secondEncoded);
    }
}

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordPattern {
    public static void main(String[] args) {
        // System.out.println(wordPattern("abba", "dog cat cat dog"));
        // System.out.println(wordPattern("abba", "dog cat cat fish"));
        // System.out.println(wordPattern("aaaa", "dog cat cat dog"));
        System.out.println(wordPattern("abba", "dog dog dog dog"));

    }

    /*
    https://leetcode.com/problems/word-pattern
     */
    public static boolean wordPattern(String pattern, String s) {
        String[] split = s.split(" ");
        if (pattern.length() != split.length) {
            return false;
        }

        Map<Character, String> checker = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (checker.containsKey(pattern.charAt(i)) && ! checker.put(pattern.charAt(i), split[i]).equals(split[i]))
                return false;
            if (! checker.containsKey(pattern.charAt(i)) && checker.containsValue(split[i]))
                return false;
            checker.put(pattern.charAt(i), split[i]);
        }
        return true;
    }
}

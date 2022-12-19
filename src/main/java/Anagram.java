import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class Anagram {
//  version supporting unicode's (incl surrogates) https://leetcode.com/problems/valid-anagram/solutions/66777/unicode-and-supplementary-characters/
    public static boolean isAnagram(String s, String t) {
        if (s == null && t == null) return true;
        if (s == null && t != null) return false;
        if (s != null && t == null) return false;

        char[] sAsArray = s.toCharArray();
        char[] tAsArray = t.toCharArray();
        Arrays.sort(sAsArray);
        Arrays.sort(tAsArray);
        return Arrays.equals(sAsArray, tAsArray);
    }

    // this should handle unicode's chars, but not sure about surrogates
    // (there is some logic about what combinations of high and low surrogates do form a valid character and what do not)
    public static boolean isAnagramViaHashMap(String s, String t) {
        HashMap<Character, Integer> theMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            theMap.put(c, theMap.getOrDefault(c, 1)+1);
        }

        for (char c : t.toCharArray()) {
            theMap.put(c, theMap.getOrDefault(c, 1)-1);
        }

        return !theMap.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .findAny()
                .isPresent();
    }

    public static void main(String[] args) {
        System.out.println(isAnagramViaHashMap("anagram", "nagaram"));
        System.out.println(isAnagramViaHashMap("car", "rat"));

    }

}

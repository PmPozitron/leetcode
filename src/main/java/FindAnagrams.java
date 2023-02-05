import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FindAnagrams {
    public static void main(String[] args) {

        System.out.println(findAnagrams("baa", "aa"));

    }

    //    https://leetcode.com/problems/find-all-anagrams-in-a-string/
    public static List<Integer> findAnagrams(String s, String p) {
        LinkedList<Integer> result = new LinkedList<>();
        HashMap<Character, Integer> charsSearchedFor = new HashMap<>();
        for (char c : p.toCharArray()) {
            if (charsSearchedFor.containsKey(c)) {
                charsSearchedFor.put(c, charsSearchedFor.get(c) + 1);
            } else {
                charsSearchedFor.put(c, 1);
            }
        }
        HashMap<Character, Integer> slidingWindow = new HashMap<>();

        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (slidingWindow.containsKey(c)) {
                slidingWindow.put(c, slidingWindow.get(c) + 1);
            } else {
                slidingWindow.put(c, 1);
            }
            total++;

            if (total < p.length())
                continue;
            else if (total > p.length()) {
                char headOfSlidingWindow = s.charAt(i - p.length());
                if (slidingWindow.get(headOfSlidingWindow) > 1) {
                    slidingWindow.put(headOfSlidingWindow, slidingWindow.get(headOfSlidingWindow) - 1);
                } else {
                    slidingWindow.remove(headOfSlidingWindow);
                }
                total--;
            }

            if (slidingWindow.equals(charsSearchedFor)) {
                result.add(i - p.length() + 1);
            }
        }

        return result;
    }
}

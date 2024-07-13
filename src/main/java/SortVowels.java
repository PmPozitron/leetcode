import java.util.ArrayList;
import java.util.Collections;

public class SortVowels {
    public static void main(String[] args) {
        System.out.println(sortVowels("lYmpH"));
    }
    /*
    https://leetcode.com/problems/sort-vowels-in-a-string/description/

    Input: s = "lEetcOde"
    Output: "lEOtcede"
     */
    public static String sortVowels(String s) {
        StringBuilder result = new StringBuilder(s);
        ArrayList<Character> vowels = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            if (ReverseVowels.isVowel(s.charAt(i))) {
                vowels.add(s.charAt(i));
                indices.add(i);
            }
        }

        if (vowels.isEmpty()) {
            return s;
        }

        Collections.sort(vowels);
        for (int i = 0; i < vowels.size(); i++) {
            result.setCharAt(indices.get(i), vowels.get(i));
        }
        return result.toString();
    }
}

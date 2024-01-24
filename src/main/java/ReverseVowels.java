import java.util.Map;

public class ReverseVowels {
    public static void main(String[] args) {
        Map.of("hello", "holle", "leetcode", "leotcede")
            .entrySet()
            .forEach(item -> System.out.println(item.getValue().equals(reverseVowels(item.getKey()))));
    }

    /*
        https://leetcode.com/problems/reverse-vowels-of-a-string
     */
    public static String reverseVowels(String s) {
        StringBuilder result = new StringBuilder(s);
        int i = 0, j = s.length() - 1;

        while (i < j) {
            while (!isVowel(s.charAt(i)) && i < j) {
                i++;
            }
            while (!isVowel(s.charAt(j)) && j > i) {
                j--;
            }

            if (isVowel(s.charAt(i)) && isVowel(s.charAt(j))) {
                char temp = s.charAt(i);
                result.setCharAt(i, s.charAt(j));
                result.setCharAt(j, temp);
                i++;
                j--;
            }
        }
        System.out.println(s + " " + result);
        return result.toString();
    }

    public static boolean isVowel(char c) {
        return "aeiouAEIOU".contains(String.valueOf(c));
    }
}

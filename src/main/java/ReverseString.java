import java.util.Map;

public class ReverseString {
    public static void main(String[] args) {
        // char[] input = {
        //     'A', ' ', 'm', 'a', 'n', ',', ' ', 'a', ' ', 'p', 'l', 'a', 'n', ',', ' ', 'a', ' ', 'c', 'a', 'n', 'a',
        //     'l', ':', ' ', 'P', 'a', 'n', 'a', 'm', 'a'
        // };
        // //        char[] input = {'a', 'b', 'c', 'd'};
        // System.out.println(input);
        // reverseString(input);
        // System.out.println(input);
        // System.out.println(new char[] {
        //     'a', 'm', 'a', 'n', 'a', 'P', ' ', ':', 'l', 'a', 'n', 'a', 'c', ' ', 'a', ' ', ',', 'n', 'a', 'l', 'p',
        //     ' ', 'a', ' ', ',', 'n', 'a', 'm', ' ', 'A'
        // });
        //
        // Map.of(1, 2);
        // Map.entry(1, 2);

        // System.out.println(reverseStr("abcdefg", 2));
        System.out.println(reverseStr("abcd", 2));
    }

    /*
    https://leetcode.com/problems/reverse-string/
    abc
     */
    public static void reverseString(char[] s) {
        int middle = s.length/2;

        for (int i = 0; i < middle; i++) {
            char c = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = c;
        }
    }

    /*
    https://leetcode.com/problems/reverse-string-ii/description/
    Example 1:
    Input: s = "abcdefg", k = 2
    Output: "bacdfeg"

    Example 2:
    Input: s = "abcd", k = 2
    Output: "bacd"
     */
    public static String reverseStr(String s, int k) {
        StringBuilder result = new StringBuilder(s);

        for (int i = 0; i < s.length(); i+=2*k) {
            int left = i;
            int right = (i + k) < s.length()
                ? i + k - 1
                : s.length() - 1;

            while (left < right) {
                char temp = result.charAt(left);
                result.setCharAt(left++, result.charAt(right));
                result.setCharAt(right--, temp);
            }
        }
        return result.toString();
    }
}

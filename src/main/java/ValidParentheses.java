import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

public class ValidParentheses {
    public static void main(String[] args) {
        for (String s : new String[]{"()", "()[]{}", "(]"}) {
//        for (String s : new String[]{"()[]{}"}) {
            System.out.println(s + " " + isValid(s));
        }
    }

    private static boolean isValid(String s) {
        // this gave 'beats 57,6% for running time' and '37,54% for memory consumption' on leetcode

//        var stack = new LinkedList<Character>();
        // this gave 'beats 90,71% for running time' and '57,01% for memory consumption' on leetcode
        var stack = new ArrayDeque<Character>();

        for (char ch : s.toCharArray()) {
            if (!isClosing(ch)) {
                stack.addLast(ch);

            } else {
                if (stack.isEmpty()) return false;

                if (matches(stack.peekLast(), ch)) {
                    stack.pollLast();
                } else {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static boolean isClosing(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    private static boolean matches(char opening, char closing) {
        if (opening == '(' && closing == ')') return true;
        if (opening == '[' && closing == ']') return true;
        if (opening == '{' && closing == '}') return true;

        return false;
    }
}

import java.util.Arrays;
import java.util.stream.Stream;

public class IsPalindrome {

    public static void main(String[] args) {
//        for (int i : new int[]{121, -121, 10}) {
//            System.out.println(i + " " + isPalindrome(i));
//        }

//        for (String s : new String[]{"abc", "abccba", "abcdcba", "", null, "A man, a plan, a canal: Panama"}) {
        for (String s : new String[]{"A man, a plan, a canal: Panama"}) {
            System.out.println(s + " " + isPalindrome(s));
        }
    }

    public static boolean isPalindrome(int x) {
        var asString = new StringBuilder().append(x);
        return String.valueOf(x).equals(asString.reverse().toString());
    }

    public static boolean isPalindrome(String s) {
        if (s==null) return false;
        if (s=="") return true;

        StringBuilder sb = new StringBuilder();
        for (char c : s.toLowerCase().toCharArray()) {
            if (c>='0' && c<='9') sb.append(c);
            if (c>='a' && c<='z') sb.append(c);
        }

        for (int i=0,j=sb.length()-1; i<sb.length()||j>=0;i++,j--){
            if (sb.charAt(i) != sb.charAt(j)) return false;
        }

        return true;
    }
}

public class IsPalindrome {

    public static void main(String[] args) {
        for (int i : new int[]{121, -121, 10}) {
            System.out.println(i + " " + isPalindrome(i));
        }
    }

    public static boolean isPalindrome(int x) {
        var asString = new StringBuilder().append(x);
        return String.valueOf(x).equals(asString.reverse().toString());
    }
}

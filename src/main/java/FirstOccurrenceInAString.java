public class FirstOccurrenceInAString {
    public static void main(String[] args) {
        // System.out.println(findFirstOccurrence("sadbutsad", "sad"));
        // System.out.println(findFirstOccurrence("leetcode", "leeto"));
        System.out.println(findFirstOccurrence("abc", "c"));
    }

    /*
    haystack -> sadbutsad; needle -> sad
     */
    private static int findFirstOccurrence(String haystack, String needle) {
        if (needle.equals(haystack)) {
            return 0;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }
        if (needle.length() == haystack.length() && !needle.equals(haystack)) {
            return -1;
        }
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                if (needle.length() == 1) {
                    return i;
                }
                for (int j = 1; j < needle.length(); j++) {
                    if (i + j >= haystack.length()) {
                        break;
                    }
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        break;
                    }
                    if (j == needle.length() - 1) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }
}

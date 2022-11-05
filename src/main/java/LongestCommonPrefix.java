public class LongestCommonPrefix {
    public static void main(String[] args) {
//        for (String[] current : new String[][]{{"flower", "flow", "flight"}, {"dog", "racecar", "car"}}) {
        for (String[] current : new String[][]{{"ab", "a"}}) {
            System.out.println(longestCommonPrefix(current));
        }
    }

    private static String longestCommonPrefix(String[] strings) {
        boolean showMustGoOn = true;
        int i = 0;
        var result = new StringBuilder();

        do {
            if (strings[0] == null || strings[0].isEmpty() || strings[0].length() <= i) {
                break;
            }
            char current = strings[0].charAt(i);
            for (int j = 1; j < strings.length; j++) {
                var s = strings[j];
                if (s.length() <= i || s.charAt(i) != current) {
                    showMustGoOn = false;
                    break;
                }
            }
            if (showMustGoOn) {
                result.append(current);
                i++;
            }
        } while (showMustGoOn);

        return result.toString();
    }
}

public class StringCompress {
    public static void main(String[] args) {
        var chars = "aabbccc".toCharArray();
//        var chars = "aaaabbbccd".toCharArray();
//        var chars = "aaaabccdd".toCharArray();
//        var chars = "abc".toCharArray();
        System.out.println(compress(chars));
        System.out.println(chars);

    }

    /*
    https://leetcode.com/problems/string-compression/description/
    Input: chars = ["a","a","b","b","c","c","c"]
    Output: Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
    Explanation: The groups are "aa", "bb", and "ccc". This compresses to "a2b2c3".


    chars =     ["a","a","a","a","b","b","b","c","c","d"]
    expected =  ["a","4","b","3","c","2","d"] -> 7
    https://leetcode.com/problems/string-compression/submissions/1319823707/
     */
    public static int compress(char[] chars) {
        if (chars.length == 1) {
            return 1;
        }
        int insertionPosition = 0;
        for (int i = 0; i < chars.length;) {
            int j = i;
            int charsCounter = 0;
            char cur = chars[i];
            while (j < chars.length && chars[j++] == cur) {
                charsCounter++;
            }
            if (charsCounter == 1) {
                chars[insertionPosition++] = cur;
                i++;
                continue;
            }

            i += charsCounter;
            chars[insertionPosition] = cur;
            int magnitude = magnitude(charsCounter);
            int nextInsertionPosition = insertionPosition + magnitude + 1;
            while (magnitude > 0) {
                chars[insertionPosition + magnitude--] = (char)('0' + charsCounter % 10);
                charsCounter /= 10;
            }

            insertionPosition = nextInsertionPosition;
        }

        return insertionPosition;
    }

    private static int magnitude(int num) {
        int magnitude = 1;
        while (num / 10 > 0) {
            magnitude++;
            num /= 10;
        }

        return magnitude;
    }

    public static int compressByChatGpt(char[] chars) {
        if (chars.length == 1) {
            return 1;
        }

        int insertionPosition = 0; // Position to insert the next character in the compressed array
        int i = 0;

        while (i < chars.length) {
            char cur = chars[i];
            int charsCounter = 0;

            // Count the occurrences of the current character
            while (i < chars.length && chars[i] == cur) {
                i++;
                charsCounter++;
            }

            // Insert the current character
            chars[insertionPosition++] = cur;

            // If more than one occurrence, insert the count
            if (charsCounter > 1) {
                for (char c : Integer.toString(charsCounter).toCharArray()) {
                    chars[insertionPosition++] = c;
                }
            }
        }

        return insertionPosition;
    }
}

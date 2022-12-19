import java.util.ArrayList;
import java.util.Arrays;

public class ZigzagConversion {

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));

    }

    /*
    https://leetcode.com/problems/zigzag-conversion/description/

    P A Y P A L I S H I R  I  N  G
    0 1 2 3 4 5 6 7 8 9 10 11 12 13

    3 ROWS
    P   A   H   N
    A P L S I I G
    Y   I   R

    4 ROWS
        0 1 2 3 4 5 6
        --------------
    0 | P     I     N
    1 | A   L S   I G
    2 | Y A   H R
    3 | P     I
    */
    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;

        StringBuilder[] result = new StringBuilder[(numRows)];
        Arrays.setAll(result, i -> new StringBuilder());

        for (int i = 0; i < s.length(); i++) {
            int remainder = i % (numRows + numRows - 2);

            if (remainder < numRows) {
                result[remainder].append(s.charAt(i));

            } else {
                result[result.length - 2 - (remainder - numRows)].append(s.charAt(i));

            }
        }

        for (int i = 1; i < result.length; i++) {
            result[0].append(result[i]);
        }

        return result[0].toString();
    }
}

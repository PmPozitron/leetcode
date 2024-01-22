import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class IntegersTasks {
    public static void main(String[] args) {
        //        System.out.println(Integer.toBinaryString(reverseBits(9)));
        //        System.out.println(reverseIntegerWoLong("2147483646"));
        //        System.out.println(myAtoi("-2147483647"));
        //        System.out.println(hammingWeight(3));

        //        int[] input = IntStream.concat(
        //                Arrays.stream(HugeTasksForTotalHammingDistance.HUGE_ARRAY_PART_ONE),
        //                Arrays.stream(HugeTasksForTotalHammingDistance2.HUGE_ARRAY_PART_TWO))
        //                .toArray();
        //        System.out.println(input.length);
        //        System.out.println(totalHammingDistance(input));
        //        System.out.println(isHappy(2));
        //         System.out.println(addDigits(38));
        System.out.println(isUgly(19));
    }

    //  https://leetcode.com/problems/happy-number/
    //  here is a solution without using extra storage. it is the same as I used for finding cycles in linked list.
    //  https://leetcode.com/problems/happy-number/solutions/519280/java-100-using-cycle-finding-algorithm/?orderBy=most_votes&languageTags=java
    public static boolean isHappy(int n) {
        Set<Integer> checked = new HashSet<>();
        int i = n;
        int j = 0;
        int k = 0;
        while (k != 1) {
            while (i != 0) {
                j = i % 10;
                k = k + (int) Math.pow(j, 2);
                i /= 10;
            }
            if (k != 1) {
                if (!checked.add(k)) {
                    return false;
                }
                i = k;
                k = 0;
            }
        }
        return true;
    }

    public static int hammingDistance(int x, int y) {
        int result = 0;
        int xx;
        int yy;
        for (int i = 0; i < 32; i++) {
            xx = x >> i & 1;
            yy = y >> i & 1;

            if (xx != yy) {
                result++;
            }
        }

        return result;
    }

    //    https://leetcode.com/problems/hamming-distance/solutions/1585496/6-line-easy-java-solution-0ms-100-faster/?orderBy=most_votes&languageTags=java
    public static int hammingDistanceViaXor(int x, int y) {
        int res = 0;
        int m = x ^ y;                  // take the xor of two numbers
        while (m != 0) {                // count the no of "1"s
            if ((m & 1) == 1) {
                res++;
            }
            m = m >> 1;
        }
        return res;
    }

    // fails leetcode's check with 'time limit exceeded' when given huge (length = 10_000) array
    public static int totalHammingDistance(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int result = 0;
        int current;
        for (int i = 0; i < nums.length; i++) {
            current = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                result += hammingDistance(current, nums[j]);
            }
        }

        return result;
    }

    //    https://leetcode.com/problems/total-hamming-distance/solutions/603952/java-99-70-faster-simple-easy-solution/
    //    https://leetcode.com/problems/total-hamming-distance/solutions/603952/java-99-70-faster-simple-easy-solution/comments/1714201
    public int totalHammingDistanceLinear(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int distance = 0;
        for (int i = 0; i < 32; i++) {
            int one_count = 0;
            for (int j = 0; j < nums.length; j++) {
                one_count += (nums[j] >> i) & 1;
            }
            // we have number of ones, calculate number of zeros and multiply them to get total number of combinations between them
            distance += one_count * (nums.length - one_count);
        }
        return distance;
    }

    public static int hammingWeight(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int result = 0;
        for (int i = 0; i < 32; i++) {
            if ((n >> i & 1) == 1) {
                result++;
            }
        }

        return result;
    }

    public static int[] countBits(int n) {
        int[] result = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            result[i] = hammingWeight(i);
        }
        return result;
    }

    //    https://leetcode.com/problems/counting-bits/solutions/79539/three-line-java-solution/?orderBy=most_votes&languageTags=java
    //    https://leetcode.com/problems/counting-bits/solutions/79539/three-line-java-solution/comments/242115
    public int[] countBitsOnePass(int num) {
        int[] f = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            f[i] = f[i >> 1] + (i & 1);
        }
        return f;
    }

    public static int reverseBits(int n) {
        int answer = 0;
        int nextBit = 0;
        for (int i = 0; i < 32; i++) { // 32 bit integers
            answer <<= 1; // shifts to pull extra zero at the end of current value (e.g. - was 101, became 1010)
            nextBit = ((n >> i)
                & 1);   // calculate next bit to be added to result by comparing current bit of input (n shifted i times) with 1 (& 1)
            //            int nextBitViaOr = ((n>>i) | 0);   // does not work this way cause it effectively gives back initial value of n shifted i times
            answer
                |= nextBit; // inserts next bit (after answer was shifted it has 0 at the end and that 0 is compared with just calculated nextBit)
        }
        return answer;
    }

    public static int reverseInteger(int n) {
        long y = n;
        if (y <= Integer.MIN_VALUE) {
            return 0;
        }

        int x = Math.abs(n);
        int magnitude = 0;
        while (x >= 10) {
            magnitude++;
            x /= 10;
        }

        long result = 0;
        x = Math.abs(n);
        for (int i = magnitude; i >= 0; i--) {
            result += x % 10 * Math.pow(10, i);
            x /= 10;
        }

        if (result > Integer.MAX_VALUE) {
            return 0;
        }

        return n > 0 ? (int) result : (int) result * -1;
    }

    public static int reverseIntegerWoLong(int n) {
        if (n == Integer.MIN_VALUE) {
            return 0;
        }
        int current = 0;
        int x = Math.abs(n);

        while (x != 0) {
            current = current * 10 + x % 10;
            if (x > 10 && current > Integer.MAX_VALUE / 10) {
                return 0;
            }
            if (x > 10 && current < Integer.MIN_VALUE / 10) {
                return 0;
            }
            x /= 10;
        }
        return n > 0 ? current : current * -1;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }

        return x == reverseIntegerWoLong(x);
    }

    public static int myAtoi(String s) {
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        if (charNotAllowed(s.charAt(0))) {
            return 0;
        }

        int sign = 1;
        if (s.charAt(0) == '-') {
            sign = -1;
        }

        int result = 0;
        int next = 0;
        boolean failFastOn = false;
        for (char ch : s.toCharArray()) {
            if (isDigit(ch)) {
                next = getInteger(ch);

                if ((result * sign > Integer.MAX_VALUE / 10 || (result * sign == Integer.MAX_VALUE / 10
                    && next > Integer.MAX_VALUE % 10))) {
                    return Integer.MAX_VALUE;
                }
                if (result * sign < Integer.MIN_VALUE / 10 || (result * sign == Integer.MIN_VALUE / 10 && next > -1 * (
                    Integer.MIN_VALUE % 10))) {
                    return Integer.MIN_VALUE;
                }

                result = result * 10 + getInteger(ch);
            } else if (failFastOn) {
                break;
            }
            failFastOn = true;
        }

        return result * sign;
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private static int getInteger(char ch) {
        return ch - '0';
    }

    private static boolean charNotAllowed(char ch) {
        return !(isDigit(ch) || ch == '-' || ch == '+');
    }

    /*
    https://leetcode.com/problems/power-of-two/
     */
    public static boolean isPowerOfTwo(int n) {
        if (n == 0) {
            return false;
        }
        if (n == 1) {
            return true;
        }

        while (n >= 1) {
            if (n % 2 != 0) {
                return false;
            }
            n /= 2;
        }

        return true;
    }

    /*
    https://leetcode.com/problems/add-digits/
     */
    public static int addDigits(int num) {
        if (num < 10) {
            return num;
        }

        boolean toBeContinued = true;

        int result = 0;
        while (toBeContinued) {
            while (num >= 1) {
                result += num % 10;
                num /= 10;
            }

            if (result < 10) {
                toBeContinued = false;
            } else {
                num = result;
                result = 0;
            }
        }

        return result;
    }

    public static boolean isUgly(int num) {
        if (num <= 0)
            return false;

        while (num % 2 == 0) num /= 2;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;
        return num == 1;
    }
}
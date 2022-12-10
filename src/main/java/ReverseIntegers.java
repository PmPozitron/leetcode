public class ReverseIntegers {

    public static void main(String[] args) {
//        System.out.println(Integer.toBinaryString(reverseBits(9)));
        System.out.println(reverseIntegerWoLong(-2147483648));
    }

    public static int reverseBits(int n) {
        int answer = 0;
        int nextBit = 0;
        for (int i = 0; i < 32; i++) { // 32 bit integers
            answer <<= 1; // shifts to pull extra zero at the end of current value (e.g. - was 101, became 1010)
            nextBit = ((n >> i) & 1);   // calculate next bit to be added to result by comparing current bit of input (n shifted i times) with 1 (& 1)
//            int nextBitViaOr = ((n>>i) | 0);   // does not work this way cause it effectively gives back initial value of n shifted i times
            answer |= nextBit; // inserts next bit (after answer was shifted it has 0 at the end and that 0 is compared with just calculated nextBit)
        }
        return answer;
    }

    public static int reverseInteger(int n) {
        long y = n;
        if (y <= Integer.MIN_VALUE) return 0;

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

        if (result > Integer.MAX_VALUE) return 0;

        return n > 0
                ? (int) result
                : (int) result * -1;
    }
    public static int reverseIntegerWoLong(int n) {
        if (n == Integer.MIN_VALUE) return 0;
        int current = 0;
        int x = Math.abs(n);

        while (x != 0) {
            current = current * 10 + x % 10;
            if (x > 10 && current > Integer.MAX_VALUE / 10) return 0;
            if (x > 10 && current < Integer.MIN_VALUE / 10) return 0;
            x /= 10;

        }
        return n > 0
                ? current
                : current * -1;
    }
}

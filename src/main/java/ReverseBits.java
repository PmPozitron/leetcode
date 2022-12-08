public class ReverseBits {

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(reverseBits(9)));
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
}

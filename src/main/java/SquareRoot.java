public class SquareRoot {
    public static void main(String[] args) {
        for (int i : new int[]{4,9,16,25,8,12,15}) {
            System.out.printf("for %d we have %d actual and %d expected results\n", i, mySqrt(i), (int)Math.sqrt(i));
        }
    }

    //https://leetcode.com/problems/sqrtx/solutions/2670638/easy-binary-search-solution-java/
    public static int mySqrt(int x) {
        int start = 0;
        int end = x;
        int mid = 0;

        if (x == 0) return 0;
        if (x == 1) return 1;

        while (start <= end) {
            mid = start + (end - start) / 2;

            if (mid == (x / mid)) {
                return mid;

            } else if (mid > x / mid) {

                end = mid - 1;

            } else {
                if ((mid + 1) > x / (mid + 1)) {
                    return mid;
                }
                start = mid + 1;
            }
        }
        return mid;
    }
}


import java.util.Map;
import java.util.stream.IntStream;

public class StairsClimber {

    public static void main(String[] args) {
        var tasksAndExpectedResults = Map.of(
                1,1,
          2,2,
          3,3,
                6, 13,
                7, 21,
                9, 55,
                44, 1134903170,
                45, 1836311903
        );

        tasksAndExpectedResults.entrySet().stream()
                .forEach(entry -> System.out.printf("for given %d expected result is %d and actual is %d\n", entry.getKey(), entry.getValue(), climbStairs(entry.getKey())));
    }
// this solution works, but runs out of time during leetcode checkup when n>42
    public static int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int result = 1;

        for (int i = n, j = 1; i > 0; i -= 2, j++) {
            result += countShiftsOfTwo(n-=2, j);
        }

        return result;
    }

    private static int countShiftsOfTwo(int n, int numOfTwos) {
        int result = 0;
        if (numOfTwos == 1) {
            return n + 1;
        }

        int numOfOnes = n + 1;
        for (int i = numOfOnes + 1; i > 0; i--) {
            result += countShiftsOfTwo(i-2, numOfTwos-1);
        }
        return result;
    }

    // https://leetcode.com/problems/climbing-stairs/solutions/25345/easy-solutions-for-suggestions/comments/24409
    // so it's just fibo sequence shifted one time so that we have 2 as result for input 2 while second fibo is 1
    public static int climbStairsNotMineSolution (int n) {
        int answer = 1;
        for(int i = 0, pre = 0; i < n; i++) pre = (answer += pre) - pre;
        return answer;
    }
}

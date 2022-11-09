import java.util.Arrays;
import java.util.Map;

public class PlusOne {
    public static void main(String[] args) {
        var inputsAndExpectedResults = Map.of(
                new int[]{1,2,3}, new int[]{1,2,4}
                , new int[]{4,3,2,1}, new int[]{4,3,2,2}
                , new int[]{9}, new int[]{10}
        );

        inputsAndExpectedResults.entrySet().stream()
                .forEach(entry -> System.out.printf("input is %s, expected result is %s and actual result is %s\n",
                        Arrays.toString(entry.getKey()), Arrays.toString(entry.getValue()), Arrays.toString(plusOne(entry.getKey()))));

    }

    private static int[] plusOne(int[] digits) {
        boolean needToGrow = true;
        for (int i = digits.length-1; i>=0; i--) {
            int current = digits[i] + 1;
            if (current < 10) {
                digits[i] = current;
                needToGrow = false;
                break;
            } else {
                digits[i] = 0;
            }
        }
        if (needToGrow) {
            int[] result = new int[digits.length +1];
            System.arraycopy(digits, 0, result, 1, digits.length);
            result[0] = 1;
            return result;
        } else {
            return digits;
        }
    }
}

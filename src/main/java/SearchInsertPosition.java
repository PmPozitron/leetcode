import java.util.Map;

public class SearchInsertPosition {

    public static void main(String[] args) {
        var theArray = new int[]{1,3,5,6};
        var inputsAndExpectedResults = Map.of(
                5,2
                ,2, 1
                ,7,4
        );

        inputsAndExpectedResults.entrySet().stream()
                .forEach(entry -> System.out.printf("for input %d expected %d and got %d\n",
                        entry.getKey(), entry.getValue(), searchInsert(theArray, entry.getKey())));
    }

    public static int searchInsert(int[]nums, int target) {
        int result = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                result = i;
                break;
            }
        }
        return result == -1
                ? nums.length
                : result;
    }
}

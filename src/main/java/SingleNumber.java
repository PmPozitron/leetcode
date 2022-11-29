import java.util.Arrays;
import java.util.HashMap;

public class SingleNumber {

    public static void main(String[] args) {
//        System.out.println(singleNumber(new int[]{2,2,1}));
        System.out.println(singleNumber(new int[]{4,1,2,1,2}));

    }

    public static int singleNumber(int[]nums) {
        HashMap<Integer, Boolean> theMap = new HashMap<>();

        Arrays.stream(nums)
                .forEach(i -> theMap.merge(i, Boolean.FALSE, (old, aNew) -> Boolean.TRUE));

        return theMap.entrySet().stream()
                .filter(entry -> ! entry.getValue())
                .findFirst()
                .get()
                .getKey();
    }
}

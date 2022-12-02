import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayTasks {

    public static void main(String[] args) {
        System.out.println(mostFrequentEven(new int[]{0,1,2,2,4,4,1}));
    }

    public static int majorityElement(int[] nums) {
        HashMap<Integer, Integer> weights = new HashMap<>();
        Arrays.stream(nums).parallel()
                .forEach(i -> weights.merge(i, 1, (current, aNew) -> current + aNew));

        int maxWeight = 0;
        int elementWithMaxWeight = Integer.MAX_VALUE;
        Map.Entry<Integer, Integer> someEntry = null;

        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            if (entry.getValue() > maxWeight) {
                maxWeight = entry.getValue();
                elementWithMaxWeight = entry.getKey();
            }
        }

        return elementWithMaxWeight;
    }

    // https://leetcode.com/problems/majority-element/solutions/2717375/majority-element-java-o-1-space-o-n-solution/
    // foocking magic
    public static int majorityElementVotingAlgo(int[] nums) {
        // Three Approach to solve Majority Element.

        // 1. You can do this by using two loops and finding count of each element and comparing with the whole array but it takes O(n^2) time.
        // 2. You can use HashMap for finding maximum frequency and returning the element but it takes O(n) space.
        // 3. Best approach is to use maximumVote-Algorithm(Boyer's Moore Voting Algorithm) which count with its current element in O(N) time and O(1)space.

        int count=0;
        int max=0;

        for(int ele: nums){
            if(count==0){
                max=ele;
            }

            if(ele==max){
                count=count+1;
            }else{
                count=count-1;
            }
        }
        return max;
    }

    // https://leetcode.com/problems/majority-element-ii/description/
    // Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
    // and here you can find human-readable explanation of voting algo
    // https://leetcode.com/problems/majority-element-ii/solutions/1098995/explanation-about-boyer-moore-vote/
    public static List<Integer> majorityElementII(int[] nums) {
        int oneThird = nums.length/3;
        LinkedList<Integer> result = new LinkedList<>();
        HashMap<Integer, Integer> weights = new HashMap<>();

        Arrays.stream(nums)
                .forEach(i -> weights.merge(i, 1, (current, aNew) -> current + aNew));

        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            if (entry.getValue() > oneThird) result.add(entry.getKey());
        }

        return result;
    }

    public static int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> theMap = Arrays.stream(nums)
                .filter(i -> i%2 == 0)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toMap(Function.identity(), i -> 1, (anOld, aNew) -> ++anOld));
        int max = theMap.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue()))
                .orElse(Map.entry(-1,-1))
                .getValue();
        if (max == -1) return max;

        return theMap.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .min(Comparator.comparingInt(Map.Entry::getKey))
                .get()
                .getKey();
    }
}

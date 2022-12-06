import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayTasks {

    public static void main(String[] args) {
        tasksForContainsNearbyDuplicate().entrySet().forEach(entry -> System.out.println(Arrays.toString(entry.getKey()) + " " + entry.getValue() + " " + containsNearbyDuplicate(entry.getKey(), entry.getValue())));
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

    public static int[] merge(int[] nums1, int m, int[] nums2, int n){
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);

        return nums1;
    }

    // beats 100% (runtime) and 86.77% (memory)
    public static int[] mergeLinearComplexity(int[] nums1, int m, int[] nums2, int n){
        int j = 0;
        for (int i=0; i<nums2.length; i++) {
            for ( ; j<=m; j++) {
                if (nums2[i]<= nums1[j]) {
                    insertAndShift(nums1, j, nums2[i]);
                    j++;
                    m++;
                    break;
                } else if (j >= m) {
                    nums1[j] = nums2[i];
                    m++;
                    break;
                }
            }
        }
        return nums1;
    }

    public static void insertAndShift(int[] nums, int position, int val) {
        for (int i = nums.length-1 ; i>position; i--) {
            nums[i] = nums[i-1];
        }
        nums[position] = val;
//        System.out.printf("position %d, val %d, array %s\n",position, val, Arrays.toString(nums));
    }

// с собеса ВК.Видео
// Даны интервалы на отрезке прямой.
// Каждый интервал задан парой чисел [a, b], которые обозначают левую и правую границу.
// Напишите функцию, которая выполняет объединение интервалов и возвращает список объединенных интервалов.
// Пример:
// intervals = [[1, 6], [5, 7], [9, 10], [8, 9]];
// mergeIntervals(Intervals); // -> [[1, 7], [8, 10]]
    private static int[][] mergeIntervals(int[][] intervals) {
        long start = System.nanoTime();
        var sortedAsList = new LinkedList<Integer>();
        var sorted = Arrays.stream(intervals)
                .sorted(Comparator.comparingInt(interval -> interval[0]))
                .flatMapToInt(arr -> Arrays.stream(arr))
                .peek(sortedAsList::addLast)
                .toArray();

//        System.out.println(sortedAsList);

        boolean showMustGoOn = true;
        while (showMustGoOn) {
            showMustGoOn = false;

            var iterator = sortedAsList.listIterator();
            iterator.next();
            for (int i = 1; i < sortedAsList.size() - 1; i += 2) {
                int current = iterator.next();
                int next = iterator.next();
                if (current >= next) {
                    iterator.previous();
                    iterator.remove();

                    iterator.previous();
                    iterator.remove();

                    showMustGoOn = true;
                }
            }
        }

        var result = new int[sortedAsList.size()/2][2];
        for (int i = 1, j = 0; i<sortedAsList.size(); i+=2, j++) {
            result[j] = new int[]{sortedAsList.get(i-1), sortedAsList.get(i)};
        }

//        System.out.println(System.nanoTime() - start);
        return result;
    }

    public static int[][] inputForMergeIntervals() {
        return new int[][]{{1, 6}, {5, 7}, {9, 10}, {8, 9}};
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int currentProfit;
        int leastPrice = Integer.MAX_VALUE;
        for (int i=0; i<prices.length;i++) {
            int currentPrice = prices[i];
            currentProfit = currentPrice-leastPrice;
            if (currentProfit>maxProfit)
                maxProfit=currentProfit;

            if (currentPrice<leastPrice)
                leastPrice=currentPrice;
        }
        return maxProfit;
    }

    private static Map<int[], int[]> inputAndExpectedResultsForPlusOne() {
        return Map.of(
                new int[]{1,2,3}, new int[]{1,2,4}
                , new int[]{4,3,2,1}, new int[]{4,3,2,2}
                , new int[]{9}, new int[]{10}
        );
    }
    public static int[] plusOne(int[] digits) {
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

    public static int removeDuplicates(int[] array) {
        int removedQuantity = 0;
        for (int i = 0; i < array.length-1; i++) {
            int current = array[i];
            if (current == Integer.MIN_VALUE) break;
            while(current == array[i+1]) {
                shiftForRemoveDuplicates(array, i, removedQuantity);
                removedQuantity++;
            }
        }

        return array.length - removedQuantity;
    }

    private static void shiftForRemoveDuplicates(int[] array, int index, int removedQty) {
        for (int i = index; i < array.length-1; i++) {
            array[i] = array[i+1];
        }
        array[array.length-1-removedQty] = Integer.MIN_VALUE;

        System.out.println(removedQty + " " + Arrays.toString(array));
    }

    private static int removeElement(int[]nums, int val) {
        int counter = 0;
        for (int i = 0; i < nums.length - counter; i++) {
            while (nums[i] == val && i + counter < nums.length) {
                shiftForRemoveElement(nums, i, counter);
                counter++;
            }
        }
        return nums.length - counter;
    }

    private static void shiftForRemoveElement(int[] nums, int index, int counter) {
        for (int i = index; i < nums.length-counter-1; i++) {
            nums[i] = nums[i+1];
        }
    }

    public static int searchInsertPosition(int[]nums, int target) {
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

    public static boolean containsDuplicate(int[] nums) {
//        return Arrays.stream(nums)
//                .mapToObj(Integer::valueOf)
//                .collect(Collectors.toMap(Function.identity(), i -> 1, (anOld, aNew) -> anOld+aNew))
//                .entrySet().stream()
//                .filter(entry -> entry.getValue() > 1)
//                .findAny()
//                .isPresent();

        HashSet<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (! set.add(i)) return true;
        }
        return false;
    }
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, List<Integer>> theMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            theMap.merge(nums[i], new LinkedList<>(Arrays.asList(i)), (anOld, aNew) -> {
                anOld.addAll(aNew);
                return anOld;
            });
        }

        for (Map.Entry<Integer, List<Integer>> entry : theMap.entrySet()) {
            if (entry.getValue().size() < 2) continue;

            Iterator<Integer> outer = entry.getValue().iterator();
            int shift = 0;

            while (outer.hasNext()) {
                Integer outerInt = outer.next();
                shift++;
                Iterator<Integer> inner = entry.getValue().iterator();
                IntStream.range(0, shift).forEach(i -> inner.next());

                while(inner.hasNext()) {
                    Integer innerInt = inner.next();
                    if (Math.abs(outerInt - innerInt) <= k) return true;
                }
            }
        }

        return false;
    }

//    https://leetcode.com/problems/contains-duplicate-ii/solutions/61372/simple-java-solution/
    public boolean containsNearbyDuplicateSlidingWindow(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i++){
            if(i > k) set.remove(nums[i-k-1]);
            if(!set.add(nums[i])) return true;
        }
        return false;
    }

    private static Map<int[], Integer> tasksForContainsNearbyDuplicate() {
        return Map.of(
                new int[]{1,2,3,1,2,3}, 2
        );
    }





}

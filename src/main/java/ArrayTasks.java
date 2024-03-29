
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayTasks {

    public static void main(String[] args) {
//        tasksForContainsNearbyDuplicate().entrySet().forEach(entry -> System.out.println(Arrays.toString(entry.getKey()) + " " + entry.getValue() + " " + containsNearbyDuplicate(entry.getKey(), entry.getValue())));

//        System.out.println(Arrays.toString(nextGreaterElement(new int[]{2,4}, new int[]{1,2,3,4})));
//        System.out.println(Arrays.toString(nextGreaterElementsViaStack(new int[]{1,3,4,2})));
//        System.out.println(getNumberOfCombinationsForNumIdenticalPairs(14));
//        System.out.println(numIdenticalPairs(new int[]{1,1,1,1}));
//        System.out.println(Arrays.toString(shuffle(new int[]{2,5,1,3,4,7}, 3)));
//        System.out.println(threeSum(new int[]{-1,0,1,2,-1,-4,-2,-3,3,0,4}));
//        Map.of(new int[]{3,0,1}, 2, new int[]{0,1}, 2, new int[]{9,6,4,2,3,5,7,0,1}, 8)
//                .entrySet().stream()
//                .forEach(entry -> System.out.printf("for input %s expected result is %d and actual is %d\n",
//                        Arrays.toString(entry.getKey()), entry.getValue(), missingNumber(entry.getKey())));
                Arrays.stream(mergeIntervalsV2(inputForMergeIntervals())).forEach(arr -> System.out.println(Arrays.toString(arr)));
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

        int count = 0;
        int max = 0;

        for (int ele : nums) {
            if (count == 0) {
                max = ele;
            }

            if (ele == max) {
                count = count + 1;
            } else {
                count = count - 1;
            }
        }
        return max;
    }

    // https://leetcode.com/problems/majority-element-ii/description/
    // Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
    // and here you can find human-readable explanation of voting algo
    // https://leetcode.com/problems/majority-element-ii/solutions/1098995/explanation-about-boyer-moore-vote/
    public static List<Integer> majorityElementII(int[] nums) {
        int oneThird = nums.length / 3;
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
                .filter(i -> i % 2 == 0)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toMap(Function.identity(), i -> 1, (anOld, aNew) -> ++anOld));
        int max = theMap.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue()))
                .orElse(Map.entry(-1, -1))
                .getValue();
        if (max == -1) return max;

        return theMap.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .min(Comparator.comparingInt(Map.Entry::getKey))
                .get()
                .getKey();
    }

    public static int[] merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);

        return nums1;
    }

    // beats 100% (runtime) and 86.77% (memory)
    public static int[] mergeLinearComplexity(int[] nums1, int m, int[] nums2, int n) {
        int j = 0;
        for (int i = 0; i < nums2.length; i++) {
            for (; j <= m; j++) {
                if (nums2[i] <= nums1[j]) {
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
        for (int i = nums.length - 1; i > position; i--) {
            nums[i] = nums[i - 1];
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
// нашёл его на литкоде https://leetcode.com/problems/merge-intervals/
// решение, кстати, оказалось неверным:
// input: [[1,4],[2,3]], actual output: [[1,3]], expected output: [[1,4]]
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

        var result = new int[sortedAsList.size() / 2][2];
        for (int i = 1, j = 0; i < sortedAsList.size(); i += 2, j++) {
            result[j] = new int[]{sortedAsList.get(i - 1), sortedAsList.get(i)};
        }

//        System.out.println(System.nanoTime() - start);
        return result;
    }

// https://leetcode.com/problems/merge-intervals/submissions/906423292/
//  there are other submissions with different approaches (partially stream-based)
    public static int[][] mergeIntervalsV2(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(arr -> arr[0]));
        List<int[]> asList = new ArrayList<>(intervals.length);
        for (int i = 0; i < intervals.length; i++) {
            asList.add(intervals[i]);
        }

        ListIterator<int[]> listIterator = asList.listIterator();
        while(listIterator.hasNext()) {
            int[] current = listIterator.next();
            int[] next = null;
            if (listIterator.hasNext()) {
                next = listIterator.next();
            } else {
                break;
            }
            if (current[1] >= next[0]) {
                if (current[1] >= next[1]) {
                    listIterator.remove();
                    listIterator.previous();
                } else {
                    int[]aNew = new int[]{current[0], next[1]};
                    listIterator.remove();
                    listIterator.previous();
                    listIterator.remove();
                    listIterator.add(aNew);
                    listIterator.previous();
                }
            } else {
                listIterator.previous();
            }
        }

        return asList.toArray(new int[asList.size()][2]);
//        int[][] result = new int[asList.size()][2];
//        int i = 0;
//
//        listIterator = asList.listIterator();
//        while (listIterator.hasNext()) {
//            result[i++] = listIterator.next();
//        }
//
//        return result;
    }

    public static int[][] inputForMergeIntervals() {
//        return new int[][]{{1, 6}, {5, 7}, {9, 10}, {8, 9}};
//        return new int[][]{{1, 4}, {2, 3}};
//        return new int[][]{{1,4},{0,2},{3,5}};
        return new int[][]{{2,3},{2,2},{3,3},{1,3},{5,7},{2,2},{4,6}};
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int currentProfit;
        int leastPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            int currentPrice = prices[i];
            currentProfit = currentPrice - leastPrice;
            if (currentProfit > maxProfit)
                maxProfit = currentProfit;

            if (currentPrice < leastPrice)
                leastPrice = currentPrice;
        }
        return maxProfit;
    }

    private static Map<int[], int[]> inputAndExpectedResultsForPlusOne() {
        return Map.of(
                new int[]{1, 2, 3}, new int[]{1, 2, 4}
                , new int[]{4, 3, 2, 1}, new int[]{4, 3, 2, 2}
                , new int[]{9}, new int[]{10}
        );
    }

    public static int[] plusOne(int[] digits) {
        boolean needToGrow = true;
        for (int i = digits.length - 1; i >= 0; i--) {
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
            int[] result = new int[digits.length + 1];
            System.arraycopy(digits, 0, result, 1, digits.length);
            result[0] = 1;
            return result;
        } else {
            return digits;
        }
    }

    public static int removeDuplicates(int[] array) {
        int removedQuantity = 0;
        for (int i = 0; i < array.length - 1; i++) {
            int current = array[i];
            if (current == Integer.MIN_VALUE) break;
            while (current == array[i + 1]) {
                shiftForRemoveDuplicates(array, i, removedQuantity);
                removedQuantity++;
            }
        }

        return array.length - removedQuantity;
    }

    private static void shiftForRemoveDuplicates(int[] array, int index, int removedQty) {
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        array[array.length - 1 - removedQty] = Integer.MIN_VALUE;

        System.out.println(removedQty + " " + Arrays.toString(array));
    }

    private static int removeElement(int[] nums, int val) {
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
        for (int i = index; i < nums.length - counter - 1; i++) {
            nums[i] = nums[i + 1];
        }
    }

    public static int searchInsertPosition(int[] nums, int target) {
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

    public static int singleNumber(int[] nums) {
        HashMap<Integer, Boolean> theMap = new HashMap<>();

        Arrays.stream(nums)
                .forEach(i -> theMap.merge(i, Boolean.FALSE, (old, aNew) -> Boolean.TRUE));

        return theMap.entrySet().stream()
                .filter(entry -> !entry.getValue())
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
            if (!set.add(i)) return true;
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

                while (inner.hasNext()) {
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
        for (int i = 0; i < nums.length; i++) {
            if (i > k) set.remove(nums[i - k - 1]);
            if (!set.add(nums[i])) return true;
        }
        return false;
    }

    private static Map<int[], Integer> tasksForContainsNearbyDuplicate() {
        return Map.of(
                new int[]{1, 2, 3, 1, 2, 3}, 2
        );
    }

    //    https://leetcode.com/problems/summary-ranges/
    public static List<String> summaryRanges(int[] nums) {
        if (nums.length == 0) return Collections.emptyList();
        if (nums.length == 1) return List.of(String.valueOf(nums[0]));

        List<String> result = new LinkedList<>();
        StringBuilder range = null;
        boolean rangeOpened = false;
        // [-2147483648,-2147483647,2147483647] l=3
        // -2147483648->
        for (int i = 0; i < nums.length - 1; i++) {
            if (range == null) {
                range = new StringBuilder(String.valueOf(nums[i]));
            }

            boolean nextIsBiggerByAtLeastTwo = false;
            if (nums[i] < 0 && nums[i + 1] > 0) nextIsBiggerByAtLeastTwo = true;
            else if (nums[i] > 0 && nums[i + 1] < 0) nextIsBiggerByAtLeastTwo = false;
            else if (nums[i + 1] - nums[i] > 1) nextIsBiggerByAtLeastTwo = true;
            if (nextIsBiggerByAtLeastTwo) {
                if (rangeOpened) {
                    range.append(nums[i]);
                }
                result.add(range.toString());
                range = null;
                rangeOpened = false;

            } else {
                if (rangeOpened && i != nums.length - 2) {
                    continue;

                } else if (!rangeOpened && i != nums.length - 2) {
                    range.append('-').append('>');
                    rangeOpened = true;
                }
            }

            if (i == nums.length - 2) {
                if (range == null)
                    range = new StringBuilder(String.valueOf(nums[i + 1]));
                else if (rangeOpened) {
                    range.append(nums[i + 1]);
                } else {
                    range.append('-').append('>').append(nums[i + 1]);
                }
                result.add(range.toString());
            }
        }

        return result;
    }

    //    https://leetcode.com/problems/next-greater-element-i/
//    stack + map based solution https://leetcode.com/problems/next-greater-element-i/solutions/991295/java-o-n-time-o-n-space-using-monotonic-stack-with-explanation/
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        if (nums1.length == 0) return nums1;

        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int l = -1;
            for (int j = 0; j < nums2.length; j++) {
                if (j < nums2.length - 1 && nums1[i] == nums2[j]) {
                    for (int k = j + 1; k < nums2.length; k++) {
                        if (nums2[j] < nums2[k]) {
                            l = nums2[k];
                            break;
                        }
                    }
                    if (l != -1) {
                        break;
                    }
                }
            }
            result[i] = l;
        }
        return result;
    }

    /*
    Stack is used to track unprocessed elements (by that I mean el-ts, that either have not been compared with other yet,
    or those el-s, for which there still is no 'next bigger element' found.
    we start with empty stack, so on first run the 'for loop' instantly goes to 'stack.push' call and pushes first nums[0] into the stack.
    on all the following iterations of 'for loop' we compare current num with the top of the stack,
    and, if num is bigger, than we have found 'next bigger el-t' for el-t from the top of the stack,
    so we put to resulting map an entry (the task implies nums2 having unique el-s, otherwise, assume, we would have to work with indices, but I did not check that assumption).
    if el-t is not found, then resulting map is left unchanged.
    both ways, we have to put current num onto the stack to allow its further processing.

    in the end we just iterate over nums2 and look up for 'next bigger el-t' for all its el-ts in by calling map.getOrDefault(num, -1)
    */
    public int[] nextGreaterElementViaStack(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek())
                map.put(stack.pop(), num);
            stack.push(num);
        }

        int i = 0;
        for (int num : nums1)
            result[i++] = map.getOrDefault(num, -1);
        return result;
    }

    //    https://leetcode.com/problems/next-greater-element-ii/
    /*
    1,8,-1,-100,-1,222,1111111,-111111
    1 - 8
    8 - 222
    -1 - 222?
    -100 - 222?
    -1 - 222
     */
    public static int[] nextGreaterElements(int[] nums) {
        Integer[] result = new Integer[nums.length];
        LinkedList<Integer> list = new LinkedList<>();
        Arrays.stream(nums).forEach(list::add);

        ListIterator<Integer> forward = null;
        ListIterator<Integer> reversed = null;
        for (int i = 0; i < nums.length; i++) {
            forward = list.listIterator(i + 1);
            reversed = list.listIterator();

            while (forward.hasNext()) {
                int j = forward.next();
                if (j > nums[i]) {
                    result[i] = j;
                    break;
                }
            }
            if (result[i] == null) {
                while (reversed.hasNext() && reversed.nextIndex() < i) {
                    int j = reversed.next();
                    if (j > nums[i]) {
                        result[i] = j;
                        break;
                    }
                }
            }
        }

        return Arrays.stream(result).mapToInt(i -> i == null ? -1 : i.intValue()).toArray();
    }


    /*
        https://leetcode.com/problems/next-greater-element-ii/solutions/1449789/java-damn-easy-to-understand/
        more or less similar to ArrayTasks.nextGreaterElementViaStack,
        but here we have to prepopulate stack (and also to put each el-t in nums once again after 'while-loop')
        and traverse it in rear-to-front order to allow circular search
     */

    /*
    1 3 5 2
    i=      3           2           1           0
    n[i]=   2           5           3           1
    s       2 3 5 2     5           3 5         1 3 5
    r[i]    3           -1          5           3
    r:      -1 -1 -1 3  -1 -1 -1 3  -1 5 -1 3   3 5 -1 3
    */
    public static int[] nextGreaterElementsViaStack(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            stack.push(nums[i]);
        }

        int greater[] = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            greater[i] = stack.empty()
                    ? -1
                    : stack.peek();
            stack.push(nums[i]);
        }

        return greater;
    }

    //  https://leetcode.com/problems/number-of-good-pairs/
    public static int numIdenticalPairs(int[] nums) {
        HashMap<Integer, List<Integer>> map = new HashMap();
        AtomicInteger result = new AtomicInteger();
        for (int i = 0; i < nums.length; i++) {
            map.merge(nums[i], new ArrayList<>(List.of(i)), (anOld, aNew) -> {
                Collections.addAll(anOld, aNew.get(0));
                return anOld;
            });
        }

        map.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(list -> result.getAndAdd(getNumberOfCombinationsForNumIdenticalPairsWoFactorial(list.getValue().size())));

        return result.get();
    }

    /*
    num of combinations from n to k is n!/((n-k)! * k!)
     */
    private static int getNumberOfCombinationsForNumIdenticalPairs(int x) {
        BigDecimal numerator = new BigDecimal(1);
        BigDecimal multiplier;
        for (int i = 1; i <= x; i++) {
            multiplier = new BigDecimal(i);
            numerator = numerator.multiply(multiplier);
        }
        BigDecimal denominator = new BigDecimal(2);
        for (int i = 1; i <= x - 2; i++) {
            multiplier = new BigDecimal(i);
            denominator = denominator.multiply(multiplier);
        }
        return numerator.divide(denominator).intValue();
    }
    /*
    num of combinations from n to k is n!/((n-k)! * k!)
    effectively, this equation leads up to multiplying last two numbers of n! sequence
    (the n itself and the one before it) and dividing them by two
     */
    private static int getNumberOfCombinationsForNumIdenticalPairsWoFactorial (int x) {
        int result = 1;
        for (int i = 1; i <= x; i++) {
            if (i >= x-1) {
                result*=i;
            }
        }
        return result/2;
    }

    /*
    https://leetcode.com/problems/number-of-good-pairs/solutions/1457646/java-story-based-0ms-single-pass-easy-to-understand-simple-hashmap/
    we just need to iterate over given array and increment a counter (one counter per distinct element) when encounter same element.
    so we do not need any combinatorics and can do well with just a map.
    the only small trick is in how we do increment of the result.
    when we encounter already known element we have to add current value of count for this element to result
    (new element can make n pairs with already met n elements)
    then we update count for this element and put that count back to the map
    */
    public static int numIdenticalPairsWoCombinatorics(int[] guestList) {
        HashMap<Integer, Integer> hm = new HashMap<>();

        int ans = 0;

        for (int friend : guestList) {
            int friendCount = hm.getOrDefault(friend, 0);
            ans += friendCount;
            hm.put(friend, friendCount + 1);
        }

        return ans;
    }

    /*
    https://leetcode.com/problems/shuffle-the-array/
    Input: nums = [2,5,1,3,4,7], n = 3
    Output: [2,3,5,4,1,7]
    2 5 1
    3 4 7
    Explanation: Since x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 then the answer is [2,3,5,4,1,7].
     */
    public static int[] shuffle(int[] input, int n) {
        int[] result = new int[input.length];

        for (int i = 0, j = 0; i < input.length/2; i++, j+=2) {
            result[j] = input[i];
            result[j+1] = input[i+n];
        }

        return result;
    }

    /*
    https://leetcode.com/problems/3sum/
    Input: nums = [-1,0,1,2,-1,-4]
    Output: [[-1,-1,2],[-1,0,1]]
    Explanation:
    nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
    nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
    nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
    The distinct triplets are [-1,0,1] and [-1,-1,2].
    Notice that the order of the output and the order of the triplets does not matter.
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> result = new LinkedHashSet<>();

            for (int i = 0; i < nums.length - 2; i++) {
                int j = i + 1;
                int k = nums.length - 1;

                while (j < k) {
                    int current = nums[i] + nums[j] + nums[k];
                    if (current == 0) {
                        // this three commented out lines are not needed to eliminate possible duplicates
                        // the logic of the method exclues such duplicates by itself
//                        List<Integer> partialResult = new LinkedList<>(Arrays.asList(nums[i], nums[j], nums[k]));
//                        Collections.sort(partialResult);
//                        result.add(partialResult);
                        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        j++;
                        k--;

                    } else if (current < 0) {
                        j++;
                    } else {
                        k--;
                    }
                }
            }

        return new LinkedList<>(result);
    }

    /*
    https://leetcode.com/problems/missing-number/
     */
    public static int missingNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        for (int i = 0; i <= nums.length; i++) {
            if (! set.contains(i)) {
                return i;
            }
        }
        throw new IllegalArgumentException("there should have been missing element by task definition");
    }

    public static int[] intersection(int[]one, int[]two) {
        Set<Integer> intermediate = new HashSet<>();
        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < two.length; j++) {
                if (one[i] == two[j]) {
                    intermediate.add(one[i]);
                }
            }
        }
        return intermediate.stream().mapToInt(i -> i.intValue()).toArray();
    }
}

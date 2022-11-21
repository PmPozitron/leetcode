import java.util.Arrays;
import java.util.Map;

public class ArraysMerger {
    public static void main(String[] args) {
        Map.of(
//            new int[][]{{-1,0,0,3,3,3,0,0,0},{1,2,2}}, new int[]{-1,0,0,1,2,2,3,3,3}
//            new int[][]{{4,5,6,0,0,0},{1,2,3}}, new int[]{1,2,3,4,5,6}
            new int[][]{{1,2,3,0,0,0},{2,5,6}}, new int[]{1,2,2,3,5,6}
//            new int[][]{{1}, {}}, new int[]{1},
//            new int[][]{{0}, {1}}, new int[]{1}
        ).entrySet().stream()
                .forEach(entry -> System.out.printf("for input %s expected result is %s and actual is %s\n",
                        Arrays.toString(entry.getKey()[0]) + " " + Arrays.toString(entry.getKey()[1]),
                        Arrays.toString(entry.getValue()),
                        Arrays.toString(mergeLinearComplexity(entry.getKey()[0], entry.getKey()[0].length -entry.getKey()[1].length, entry.getKey()[1], entry.getKey()[1].length))));

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
}

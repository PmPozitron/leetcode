import java.util.Arrays;
import java.util.Random;

public class SortTasks {

    public static void main(String[] args) {

//        System.out.println(Arrays.toString(shellSort(new int[]{15,  10  ,25,  20,  5,   7,   9,   13,  1,   22,  21,  17,  19,  4,   8})));
//        System.out.println(Arrays.toString(shellSort(new int[]{5, 4, 2, 1})));


//        for (int i = 0; i < 50; i++) {
        int[] input = new Random().ints(25, 0, 10000).toArray();
//        System.out.println(Arrays.toString(
            shellSort(input);
//        );
        for (int j = 0; j < input.length - 1; j++) {
            if (input[j] > input[j + 1]) System.out.println(input[j] + " is not bigger than " + input[j + 1]);
//        }
        }
    }

    /*
    use
    PS C:\Program Files\Java\jdk1.7.0_79\bin> .\appletviewer.exe E:\info\ITMO\Java\LaforeAlgorithms\Chap07\ShellSort\ShellSort.class
    to start Lafore's shellsort applet

    outer loop takes elements starting from h with step 1, copies it (int num) and puts it into inner loop.
    inner loop acts similarly to inner loop of insertion sort, but it steps h positions each time (instead of 1 position in insertionSort).
    by doing this inner loop looks for place where to insert num (analogous to what inner loop of insertion sort does).
    until position is not found element with index [inner-h] is shifted h position to the right.
    when position is found copied element (int num) is put in that position (nums[inner])

     */
    public static int[] shellSort(int[] nums) {
        int h = 1;
        while (h <= nums.length / 3) h = h * 3 + 1;

        while (h > 0) {
            for (int outer = h; outer < nums.length; outer++) {
                int num = nums[outer];
                int inner = outer;
                while (inner >= h && nums[inner - h] >= num){
                    nums[inner] = nums[inner - h];
                    inner-=h;
                }
                nums[inner] = num;
            }
            h = (h - 1) / 3;
        }
        return nums;
    }

    /*
    https://leetcode.com/problems/sort-an-array/
    5 3 1 2
    i=1, num=3 -> 3 5 1 2
    i=2, num=1 -> 1 3 5 2
    i=3, num-2 -> 1 2 3 5

     */
    public static int[] insertionSortArray(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            int j = i;
            while (j > 0 && nums[j-1] > num) {
                nums[j] = nums[j-1];
                j--;
            }
            nums[j] = num;
        }

        return nums;
    }

    /*
    0 1 2 3
     */
    private static void shiftForSortArray(int[] nums, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            nums[fromIndex + 1] = nums[fromIndex];

        } else if (fromIndex > toIndex) {
            nums[fromIndex] = nums[fromIndex - 1];
        }
    }
}


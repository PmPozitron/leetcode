import java.util.Arrays;
import java.util.Random;

public class SortTasks {

    public static void main(String[] args) {

//        System.out.println(Arrays.toString(shellSort(new int[]{15,  10  ,25,  20,  5,   7,   9,   13,  1,   22,  21,  17,  19,  4,   8})));
//        System.out.println(Arrays.toString(shellSort(new int[]{5, 4, 2, 1})));


//        for (int i = 0; i < 50; i++) {
        int[] input = new Random().ints(50, 0, 10000).toArray();
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
    0   1   2   3   4   5   6   7   8   9   10  11  12  13  14
    -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
    15  10  25  20  5   7   9   13  1   22  21  17  19  4   8 - length 15
    4   10  25  20  5   7   9   13  1   22  21  17  19  15   8
    4   8  25  20   5   7   9   13  1   22  21  17  19  15   10

    h=13
    outer=13,num=4 ->   inner=13 -> nums[13]=15
                        inner=0 -> nums[0]=4
    outer=14,num=8  ->  inner=14 -> 10>8 true -> nums[14]=10
                        inner=1 -> nums[1]=8
    h=4
    outer=4,num=5 ->    inner=4 -> 4>5 false


     */
    public static int[] shellSort(int[] nums) {
        int h = 1;
        while (h <= nums.length / 3) h = h * 3 + 1;

        System.out.println(Arrays.toString(nums));
        while (h > 0) {
            for (int outer = h; outer < nums.length; outer++) {
                int num = nums[outer];
                int inner = outer;
                System.out.printf("h=%d outer=%d num=%d inner=%d inner>h=%b nums[inner-h]>num=%b\n",h,outer,num,inner,inner>=h,nums[inner-h]>=num);
                while (inner >= h && nums[inner - h] >= num){
                    System.out.printf("nums[inner]=%d and nums[inner-h]=%d\n",nums[inner],nums[inner-h]);
                    nums[inner] = nums[inner - h];
                    inner-=h;
                    System.out.println("in while:\t\t" + Arrays.toString(nums));
                }
                nums[inner] = num;
                System.out.println("out of while:\t" + Arrays.toString(nums));

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


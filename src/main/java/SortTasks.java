import java.util.Arrays;
import java.util.Random;

public class SortTasks {

    public static void main(String[] args) {

//        System.out.println(Arrays.toString(shellSort(new int[]{15,  10  ,25,  20,  5,   7,   9,   13,  1,   22,  21,  17,  19,  4,   8})));
//        System.out.println(Arrays.toString(shellSort(new int[]{5, 4, 2, 1})));


//        for (int i = 0; i < 50; i++) {
        int bound = 100;
        int[] input = new Random().ints(bound, 0, bound).toArray();
        System.out.println(Arrays.toString(
            mergeSort(input)
        ));
//        for (int j = 0; j < input.length - 1; j++) {
//            if (input[j] > input[j + 1]) System.out.println(input[j] + " is not bigger than " + input[j + 1]);
//        }
//        System.out.println(Arrays.toString(mergeSort(new int[]{5, 1, 1, 2, 0, 0, 9})));


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
                while (inner >= h && nums[inner - h] >= num) {
                    nums[inner] = nums[inner - h];
                    inner -= h;
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
            while (j > 0 && nums[j - 1] > num) {
                nums[j] = nums[j - 1];
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

    public static int[] mergeSort(int[] array) {
        if (array == null || array.length == 0 || array.length == 1)
            return array;

        int middle = array.length / 2;
//        int[] first = new int[middle];
//        int[] second = new int[array.length - middle];
//        System.arraycopy(array, 0, first, 0, first.length);
//        System.arraycopy(array, middle, second, 0, second.length);
//        first = mergeSort(first);
//        second = mergeSort(second);
//        return mergeSorted(first, second);
        int[] copy = new int[array.length];
        mergeSort(array, copy, 0, array.length-1);

        return array;
    }

    public static void mergeSort(int[] beingSorted, int[] arrayCopy, int down, int up) {
        if (down == up) return;;

        int middle = (down+up)/2;
        mergeSort(beingSorted, arrayCopy, down, middle);
        mergeSort(beingSorted, arrayCopy, middle+1, up);

        mergeSorted(beingSorted, arrayCopy, down, middle+1, up);
    }

    /*
     */
    public static int[] mergeSorted(int[] first, int[] second) {
        int [] result = new int[first.length + second.length];
        int i = 0, j = 0, k = 0;

        while (i < first.length && j < second.length) {
            if (first[i] <= second[j]) {
                result[k++] = first[i++];
            } else {
                result[k++] = second[j++];
            }
        }

        if (i < first.length) {
            for (; i < first.length; i++) {
                result[k++] = first[i];
            }
        } else if (j < second.length) {
            for (; j < second.length; j++) {
                result[k++] = second[j];
            }
        }

        return result;
    }

    public static void mergeSorted(int[] beingSorted, int[] arrayCopy, int lowPointer, int highPointer, int top) {
        int j = 0;                       // workspace index
        int down = lowPointer;
        int mid = highPointer-1;
        int n = top-down+1;       // # of items

        while(lowPointer <= mid && highPointer <= top) {
            if (beingSorted[lowPointer] < beingSorted[highPointer]) {
                arrayCopy[j++] = beingSorted[lowPointer++];
            } else {
                arrayCopy[j++] = beingSorted[highPointer++];
            }
        }

        while (lowPointer <= mid) {
            arrayCopy[j++] = beingSorted[lowPointer++];
        }

        while (highPointer <= top) {
            arrayCopy[j++] = beingSorted[highPointer++];
        }

        for (j=0; j<n; j++) {
            beingSorted[down+j] = arrayCopy[j];
        }

    }
}


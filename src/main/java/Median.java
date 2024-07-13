// Java ----------------

//На вход дано 2 отсортированных массива, найти наиболее оптимальным способом их медиану.
//Оценить сложность  полученного алгоритма.
//Медиана — это число, которое является серединой множества чисел,
//то есть половина чисел имеют значения большие, чем медиана, а половина чисел имеют значения меньшие, чем медиана.

//Input: nums1 = [1,3], nums2 = [2]
//Output: 2.00000

//Input: nums1 = [1,2], nums2 = [3,5]
//Output: 2.50000

//Input: nums1 = [1,2,99], nums2 = [3,4]
//Output: 3.00000

// с собеса в т1/иннотех на импортозамещение, кажется, джиры

import java.util.Arrays;

public class Median {
    public static void main(String[] args) {
        System.out.println("Hello, world!");

        System.out.println(findMedian(new int[]{1,2}, new int[]{3,5}));
    }

    public static double findMedian(int[] first, int[] second) {
        int[] result = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;
        for (; i< result.length; i++) {
            if (j >= first.length)
                break;
            if (k >= second.length)
                break;

            if (first[j] <= second[k])
                result[i] = first[j++];
            else {
                result[i] = second[k++];
            }
        }

        if (j < first.length) {
            for (; j < first.length; j++) {
                result[i++] = first[j];
            }
        }

        if (k < second.length) {
            for (; k < second.length; k++) {
                result[i++] = second[k];
            }
        }
        System.out.println(Arrays.toString(result));


        if (result.length % 2 != 0) {
            return result[result.length/2];

        } else {
            return (result[result.length/2] + result[result.length/2 - 1]) / 2.0;
        }
    }
}
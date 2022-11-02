import java.util.Arrays;

public class LeetCode {

    public static void main(String[] args) {
//        isPalindrome
//        for (int i : new int[]{121, -121, 10}) {
//            System.out.println(i + " " + isPalindrome(i));
//        }

//        removeDuplicates
//        int[] array = new int[]{0,0,1,1,1,2,2,3,3,4};
        int[] array = new int[]{1,1,2};
        System.out.println(removeDuplicates(array));
        System.out.println(Arrays.toString(array));
    }

    public static boolean isPalindrome(int x) {
        var asString = new StringBuilder().append(x);
        return String.valueOf(x).equals(asString.reverse().toString());
    }

    public static int removeDuplicates(int[] array) {
        int removedQuantity = 0;
        for (int i = 0; i < array.length-1; i++) {
            int current = array[i];
            if (current == Integer.MIN_VALUE) break;
            while(current == array[i+1]) {
                shift(array, i, removedQuantity);
                removedQuantity++;
            }
        }

        return array.length - removedQuantity;
    }

    public static void shift(int[] array, int index, int removedQty) {
        for (int i = index; i < array.length-1; i++) {
            array[i] = array[i+1];
        }
        array[array.length-1-removedQty] = Integer.MIN_VALUE;

        System.out.println(removedQty + " " + Arrays.toString(array));
    }
}

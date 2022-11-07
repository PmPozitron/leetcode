import java.util.Arrays;

public class RemoveElement {
    public static void main(String[] args) {
        int[] array = new int[]{3,2,2,3};
//        int[] array = new int[]{0,1,2,2,3,0,4,2};
        System.out.println(removeElement(array, 3));
        System.out.println(Arrays.toString(array));
    }

    private static int removeElement(int[]nums, int val) {
        int counter = 0;
        for (int i = 0; i < nums.length - counter; i++) {
            while (nums[i] == val && i + counter < nums.length) {
                shift(nums, i, counter);
                counter++;
            }
        }
        return nums.length - counter;
    }

    private static void shift(int[] nums, int index, int counter) {
        for (int i = index; i < nums.length-counter-1; i++) {
            nums[i] = nums[i+1];
        }
    }
}

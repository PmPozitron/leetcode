package tinkoff;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InterviewTasks {
    // package whatever; // don't place package name!
// Installed Libraries: JSON-Simple, JUNit 4, Apache Commons Lang3

// Дан массив A длины N.
// Окрестностью элемента массива называется непрерывный максимальный
// подмассив, который содержит этот элемент, и при этом все элементы
// этого подмассива имеют одинаковую четность.
// Для каждого элемента A вывести размер его окрестности

// Пример:
// [2, 1, 3, 2] → [1, 2, 2, 1]
// [1,1,1,1,1] -> [5, 5, 5, 5, 5]

    public static void main(String[] args) {
        System.out.println("Hello Java");
        System.out.println(Arrays.toString(taskOneViaSlidingWindow(new int[]{2,1,3,2})));
//        System.out.println(Arrays.toString(taskOne(new int[]{1,1,1,1,1,1,1})));
//        System.out.println(taskTwo(new int[]{1,4,3,0}));
//        System.out.println(taskTwoViaSum(new int[]{1,4,3,0}));
    }

    private static int[] taskOne(int [] input) {
        int counter = 0;
        int[] result = new int[input.length];
        Arrays.fill(result, 1);

        for (int i = 0; i < input.length; i++) {
            int current = input[i];
            boolean isEven = current % 2 == 0;
            for (int j = i + 1; j < input.length; j++) {
                counter++;
                int next = input[j];
                boolean alsoEven = next % 2 == 0;
                if (isEven == alsoEven) {
                    result[i]++;
                } else {
                    break;
                }
            }

            for (int j = i - 1; j >= 0; j--) {
                counter++;
                int previous = input[j];
                boolean alsoEven = previous % 2 == 0;
                if (isEven == alsoEven) {
                    result[i]++;
                } else {
                    break;
                }
            }
        }

        System.out.println("count of comparisons " + counter);
        return result;
    }

    // [2, 1, 3, 2] → [1, 2, 2, 1]
    private static int[] taskOneViaSlidingWindow(int[] input) {
        if (input.length == 0) {
            return input;
        }

        if (input.length == 1) {
            return new int[]{1};
        }

        int[] result = new int[input.length];
        Arrays.fill(result, 1);

        Set<Integer> window = new HashSet<>();
        for (int i = 0; i < input.length; i++) {
            int current = input[i];
            window.add(i);
            boolean isEven = current % 2 == 0;
            for (int j = i + 1; j < input.length; j++) {
                int next = input[j];
                boolean alsoEven = next % 2 == 0;
                if (isEven == alsoEven) {
                    window.add(j);
                    i = j;
                } else {
                    if (window.size() > 1) {
                        for (int k : window) {
                            result[k] = window.size();
                        }
                    }
                    break;
                }
            }
            window.clear();
        }
        return result;
    }
    /*
    Дан неотсортированный массив чисел от 0 до N,
при этом ровно одно из чисел диапазона [0, N] пропущено, а остальные присутствуют.
Найти пропущенное число.

[1, 4, 3, 0] → 2
     */
    private static int taskTwo(int[] input) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i <= input.length; i++) {
            map.put(i, 0);
        }

        int result = -1;
        for (int i = 0; i < input.length; i++) {
            map.merge(input[i], 1, (anOld, aNew) -> anOld+aNew);
        }

        return map.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .findFirst()
                .get().getKey();
    }

    private static int taskTwoViaSum(int[] input) {
        int sum = 0;
        for (int i = 0; i <= input.length; i++) {
            sum+=i;
        }

        for (int i = 0; i < input.length; i++) {
            sum-= input[i];
        }
        return sum;
    }
}


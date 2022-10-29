import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntervalsMerger {
    public static void main(String[] args) {
        var intervals = new int[][]{{1, 6}, {5, 7}, {9, 10}, {8, 9}};
        Arrays.stream(merge(intervals)).forEach(item -> System.out.println(Arrays.toString(item)));
    }

    private static int[][] merge(int[][] intervals) {
        var sortedAsList = new LinkedList<Integer>();
        var sorted = Arrays.stream(intervals)
                .sorted(Comparator.comparingInt(interval -> interval[0]))
                .flatMapToInt(arr -> Arrays.stream(arr))
                .peek(sortedAsList::addLast)
                .toArray();

        System.out.println(sortedAsList);

        boolean showMustGoOn = true;
        while (showMustGoOn) {
            showMustGoOn = false;

            for (int i = 1; i < sortedAsList.size() - 1; i += 2) {
                if (sortedAsList.get(i) >= sortedAsList.get(i + 1)) {
                    sortedAsList.remove(i);
                    sortedAsList.remove(i);
                    showMustGoOn = true;
                }
            }
        }

        var result = new int[sortedAsList.size()/2][2];
        for (int i = 1, j = 0; i<sortedAsList.size(); i+=2, j++) {
            result[j] = new int[]{sortedAsList.get(i-1), sortedAsList.get(i)};
        }

        return result;
    }
}

// Даны интервалы на отрезке прямой.
// Каждый интервал задан парой чисел [a, b], которые обозначают левую и правую границу.
// Напишите функцию, которая выполняет объединение интервалов и возвращает список объединенных интервалов.
// Пример:

// intervals = [[1, 6], [5, 7], [9, 10], [8, 9]];
// mergeIntervals(Intervals); // -> [[1, 7], [8, 10]]

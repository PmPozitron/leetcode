import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PascalsTriangle {

    public static void main(String[] args) {
        generate(5).stream()
                .forEach(System.out::println);

    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(Arrays.asList(1));

        for (int i=1; i<numRows; i++) {
            List<Integer> list = new ArrayList<>(i+1);
            list.add(1);
            list.add(list.size()-1, 1);

            for (int j=1; j<i; j++) {
                int aNew=result.get(i-1).get(j-1) + result.get(i-1).get(j);
                list.add(j, aNew);
            }
            result.add(i, list);
        }

        return result;
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
these are two (out of three, first was very simple and thus did not get here) that were given to me by musala.com
 */
public class MusalaSoft {
    public static void main(String[] args) {
        System.out.println(countPairs(List.of(1,2,3,4), 0));

    }

    public static List<String> funWithAnagrams(List<String> text) {
        Map<String, String> result = new HashMap<>();

        for (String string : text) {
            char[] asChars = string.toCharArray();
            Arrays.sort(asChars);
            result.putIfAbsent(new String(asChars), string);
        }

        List<String> sorted = new ArrayList<>(result.values());
        Collections.sort(sorted);
        return sorted;
    }

    public static int countPairs(List<Integer> numbers, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int counter = 0;

        for (int i : numbers) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        for (int i : map.keySet()) {
            if (map.containsKey(i + k)) {
                if (k == 0 && map.get(i) == 1) {
                    continue;
                } else {
                    counter++;

                }
            }
        }


        return counter;
    }


}

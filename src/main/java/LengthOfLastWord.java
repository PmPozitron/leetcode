import java.util.Arrays;
import java.util.Map;

public class LengthOfLastWord {
    public static void main(String[] args) {
        var inputsAndExpectedResults = Map.of(
                "Hello World", 5
                , "   fly me   to   the moon  ", 4
                , "luffy is still joyboy", 6
        );

        inputsAndExpectedResults.entrySet().stream()
                .forEach(item -> System.out.printf("input is %s, expected result is %d and actual result is %d\n",
                        item.getKey(), item.getValue(), lengthOfLastWord(item.getKey())));

    }

    private static int lengthOfLastWord(String s) {
        return Arrays.stream(s.split("[ ]+"))
                .map(string -> string.trim())
                .reduce((one, two) -> two)
                .get()
                .length();
    }
}

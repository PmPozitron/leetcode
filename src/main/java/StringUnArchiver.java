import java.util.LinkedList;
import java.util.Map;

public class StringUnArchiver {

    public static void main(String[] args) {
        Map<String, String> tasksAndExpectations = Map.of(
                "a", "a",
                "2[b]", "bb",
                "10[b]", "bbbbbbbbbb",
                "2[ab]", "abab",
                "a3[b]c", "abbbc",
                "a3[b]3[c]d", "abbbcccd",
                "a2[b3[c]d]e", "abcccdbcccde",
                "a2[b13[c]d]e", "abcccccccccccccdbcccccccccccccde"
        );

        tasksAndExpectations.entrySet().stream()
                .forEach(entry -> {
                    var returned = decode(entry.getKey());
                    if (!entry.getValue().equals(returned)) {
                        System.out.println(entry.getValue().concat(" != ".concat(returned)));
                    }
                });
    }

    private static String decode(String encoded) {
        var list = new LinkedList<Character>();

        for (char ch : encoded.toCharArray()) {
            if (!isClosingBracket(ch)) {
                list.addLast(ch);

            } else {
                var letters = new StringBuilder();
                var multiplier = new StringBuilder();
                char current;

                while (!isDigit(current = list.pollLast())) {
                    if (isLetter(current)) {
                        letters.append(current);
                    }
                }
                multiplier.append(current);
                while (!list.isEmpty() && isDigit(list.peekLast())) {
                    multiplier.append(current = list.pollLast());
                }
                letters.reverse();
                multiplier.reverse();
                var multiplierParsed = Integer.parseInt(multiplier.toString());
                for (int i = 0; i < multiplierParsed; i++) {
                    for (char innerChar : letters.toString().toCharArray()) {
                        list.offerLast(innerChar);
                    }
                }
            }
        }
        var result = new StringBuilder();
        for (char ch : list) {
            result.append(ch);
        }
        return result.toString();
    }

    private static boolean isDigit(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    private static boolean isOpeningBracket(char ch) {
        return ch == '[';
    }

    private static boolean isClosingBracket(char ch) {
        return ch == ']';
    }

    private static boolean isLetter(char ch) {
        if (isDigit(ch) || isOpeningBracket(ch) || isClosingBracket(ch)) {
            return false;
        }
        return true;
    }

}


//import java.util.ArrayList;// Написать метод, который преобразует строку, раскрывая повторяющиеся подстроки, указанные в квадратных скобках:

// "a"           -> "a"
// "2[b]"       -> g"bb"
// "10[b]"         -> "bbbbbbbbbb"
// "2[ab]"         -> "abab"
// "a3[b]c"     -> "abbbc"
// "a3[b]3[c]d"     -> "abbbcccd"
// "a2[b3[c]d]e"  -> "abcccdbcccde"

// Валидность входной строки можно не проверять - считаем, что:
// 1. все скобки расставлены корректно
// 2. перед каждой открывающей скобкой указано количество повторений
// 3. цифры/скобки используются только для обозначения повторяющихся подстрок
// 4. сами подстроки состоят только из букв латинского алфавита

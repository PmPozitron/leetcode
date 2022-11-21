import com.sun.source.util.Trees;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;

public class DuplicatesRemover {

    public static void main(String[] args) {
        ListNode one = new ListNode(1, new ListNode(1, new ListNode(2)));
        ListNode two = new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3)))));

        Map.of(
                one, List.of(1, 2),
                two, List.of(1, 2, 3)
        ).entrySet().stream()
                .forEach(item -> System.out.printf("input is %s, expected result is %s, actual result is %s\n", item.getKey(), item.getValue(), deleteDuplicates(item.getKey())));

    }

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;
        ListNode current = head;
        ListNode next = head.next;

        while (true) {
            if (next == null) break;

            if (current.val == next.val) {
                current.next = next.next;
                next = current.next;
            } else {
                current = next;
            }
        }

        return head;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("" + val)
                .add("" + (next == null ? "" : String.valueOf(next)))
                .toString();
    }
}


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ListTasks {

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

    public static boolean hasCycle(ListNode head) {
        if (head == null) return false;
        HashSet<ListNode> visited = new HashSet<>();
        ListNode current = head.next;
        while (current != null) {
            if (visited.add(current))
                current = current.next;
            else return true;
        }
        return false;
    }

    public static boolean hasCycleWoSet(ListNode head) {
        if (head == null) return false;
        ListNode singleStepCursor = head.next;
        if (singleStepCursor == null) return false;
        ListNode doubleStepCursor = head.next.next;
        if (doubleStepCursor == null) return false;

        while (singleStepCursor != null && doubleStepCursor != null) {
            if (singleStepCursor == doubleStepCursor)
                return true;
            else {
                singleStepCursor = singleStepCursor.next;
                if (doubleStepCursor.next == null)
                    return false;
                else doubleStepCursor = doubleStepCursor.next.next;
            }
        }
        return false;
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


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ListTasks {

    public static void main(String[] args) {
//        expectedResultsForDeleteDuplicates().entrySet().stream()
//                .forEach(item -> System.out.printf("input is %s, expected result is %s, actual result is %s\n", item.getKey(), item.getValue(), deleteDuplicates(item.getKey())));

        expectedResultsForGetIntersectionNode().entrySet().stream()
                .forEach(item -> System.out.println("intersection on " + getIntersectionNode(item.getKey(), item.getValue())));
    }

    private static Map<ListNode,List<Integer>> expectedResultsForDeleteDuplicates() {
        ListNode one = new ListNode(1, new ListNode(1, new ListNode(2)));
        ListNode two = new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3)))));

        return Map.of(
                        one, List.of(1, 2),
                        two, List.of(1, 2, 3)
                );
    }

    private static Map<ListNode, ListNode> expectedResultsForGetIntersectionNode() {
        ListNode commonFour = new ListNode(4);
        ListNode commonTwo = new ListNode(2, commonFour);
        ListNode first1 = new ListNode(1, commonTwo);
        ListNode first9 = new ListNode(9, first1);
        ListNode firstExtra1 = new ListNode(1, first9);
        ListNode second3 = new ListNode(3, commonTwo);

        return Map.of(firstExtra1, second3);
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
            if (singleStepCursor == doubleStepCursor) return true;

            else {
                singleStepCursor = singleStepCursor.next;

                if (doubleStepCursor.next == null) return false;
                else doubleStepCursor = doubleStepCursor.next.next;
            }
        }
        return false;
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null) return null;
        if (headB == null) return null;
        if (headA == headB) return headA;

        HashSet<ListNode> visited = new HashSet<>();
        visited.add(headA);
        visited.add(headB);

        ListNode cursorA = headA.next;
        ListNode cursorB = headB.next;

        while (!(cursorA == null && cursorB == null)) {
            if (cursorA != null) {
                if (!visited.add(cursorA))
                    return cursorA;
                cursorA = cursorA.next;
            }

            if (cursorB != null) {
                if (!visited.add(cursorB))
                    return cursorB;
                cursorB = cursorB.next;
            }
        }

        return null;
    }

    // restoration (not copy-paste) of https://leetcode.com/problems/intersection-of-two-linked-lists/solutions/49785/java-solution-without-knowing-the-difference-in-len/
    // just for better memorizing
    public static ListNode getIntersectionNodeWoSet(ListNode headA, ListNode headB) {
        if (headA == null) return null;
        if (headB == null) return null;
        if (headA == headB) return headA;

        ListNode cursorA = headA;
        ListNode cursorB = headB;

        while (cursorA != cursorB) {

            cursorA = cursorA == null
                    ? headB
                    : cursorA.next;

            cursorB = cursorB == null
                    ? headA
                    : cursorB.next;
        }

        return cursorA;
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode current = head;
        ListNode reversed = null;
        ListNode node = null;

        while (current != null) {
            node = new ListNode(current.val);

            node.next = reversed;
            reversed = node;
            current = current.next;
        }

        return node;
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


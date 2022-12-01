import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ListTasks {

    public static void main(String[] args) {
//        expectedResultsForDeleteDuplicates().entrySet().stream()
//                .forEach(item -> System.out.printf("input is %s, expected result is %s, actual result is %s\n", item.getKey(), item.getValue(), deleteDuplicates(item.getKey())));

//        expectedResultsForGetIntersectionNode().entrySet().stream()
//                .forEach(item -> System.out.println("intersection on " + getIntersectionNode(item.getKey(), item.getValue())));

//        expectedResultsForIsPalindrome().entrySet().stream()
//                .forEach(item -> System.out.printf("%b\n", isPanindrome2(item.getKey())));

        expectedResultsForReverseBetween().entrySet().stream()
                .forEach(entry -> System.out.println(reverseBetween(entry.getKey(), 1, 2)));


    }

    private static Map<ListNode, List<Integer>> expectedResultsForReverseBetween() {
//        ListNode tail = new ListNode(5);
//        ListNode four = new ListNode(4, tail);
//        ListNode three = new ListNode(3, four);
//        ListNode two = new ListNode(2, three);
//        ListNode head = new ListNode(1, two);
//
//        return Map.of(head, List.of(1,4,3,2,5));

        return Map.of(new ListNode(3, new ListNode(5)), List.of(3,5));
    }
    private static Map<ListNode, Boolean> expectedResultsForIsPalindrome() {
//        ListNode tail = new ListNode(1);
//        ListNode twoBeforeTail = new ListNode(2, tail);
//        ListNode twoBeforeTwo = new ListNode(2, twoBeforeTail);
//        ListNode head = new ListNode(1, twoBeforeTwo);
//        return Map.of(head, true);

        ListNode tail = new ListNode(2);
        ListNode head = new ListNode(1, tail);

        return Map.of(head, false);
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

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        ListNode reversed = reverseList(head);
        ListNode cursor1 = head;
        ListNode cursor2 = reversed;

        while (cursor1 != null) {
            if (cursor1.val != cursor2.val) return false;
            cursor1 = cursor1.next;
            cursor2 = cursor2.next;
        }

        return true;
    }

    public static boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null) return true;
        int length = 0;
        ListNode cursor = head;
        while (cursor != null) {
            length++;
            cursor = cursor.next;
        }
        int median = 0;
        median = length%2 == 0
                ? length/2 - 1
                : length/2;

        cursor = head;
        ListNode reversed = null;
        ListNode aNew = null;
        for(int i=0;i<=median;i++) {
            aNew = new ListNode(cursor.val, reversed);
            reversed = aNew;
            cursor = cursor.next;
        }

        median = length/2;

        cursor = head;
        for (int i=0;i<median;i++) {
            cursor = cursor.next;
        }
        ListNode reversedCursor = reversed;
        while(cursor != null) {
            if (cursor.val != reversedCursor.val) return false;
            cursor = cursor.next;
            reversedCursor = reversedCursor.next;
        }
        return true;
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null)
            return head;
        if (right == left)
            return head;

        ListNode cursorOne = head;
        ListNode cursorTwo = head;
        ListNode beforeLeftInsertionPoint = head;

        for (int i=1;i<left;i++) {
            if (i==left-1)
                beforeLeftInsertionPoint = cursorOne;

            cursorOne = cursorOne.next;
            cursorTwo = cursorTwo.next;
        }

        ListNode reversed = new ListNode(cursorOne.val);
        ListNode beforeRightInsertionPoint = reversed;
        ListNode aNew = null;

        for (int i=left;i<right;i++) {
            cursorTwo = cursorTwo.next;
            aNew = new ListNode(cursorTwo.val, reversed);
            reversed = aNew;
        }
        if (left == 1)
            head = aNew;
        else
            beforeLeftInsertionPoint.next = aNew;

        beforeRightInsertionPoint.next = cursorTwo.next;

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


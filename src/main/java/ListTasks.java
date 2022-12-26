import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;

public class ListTasks {

    public static void main(String[] args) {
//        expectedResultsForDeleteDuplicates().entrySet().stream()
//                .forEach(item -> System.out.printf("input is %s, expected result is %s, actual result is %s\n", item.getKey(), item.getValue(), deleteDuplicates(item.getKey())));

//        expectedResultsForGetIntersectionNode().entrySet().stream()
//                .forEach(item -> System.out.println("intersection on " + getIntersectionNode(item.getKey(), item.getValue())));

//        expectedResultsForIsPalindrome().entrySet().stream()
//                .forEach(item -> System.out.printf("%b\n", isPanindrome2(item.getKey())));

//        expectedResultsForReverseBetween().entrySet().stream()
//                .forEach(entry -> System.out.println(reverseBetween(entry.getKey(), 1, 2)));

//        tasksForAddTwoNumbers().entrySet().stream()
//                .forEach(entry -> System.out.println(addTwoNumbers(entry.getKey(), entry.getValue())));

//        tasksAndExpectedResultsForOddEvenList().entrySet().stream()
//                .forEach(entry -> System.out.printf("input is %s, expected is %s and actual is %s\n", entry.getKey(), entry.getValue(), oddEvenList(entry.getKey())));

//        System.out.println(removeElements(new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1)))), 2));
//        long start = System.nanoTime();
//        ListNode current = removeNodesViaStack(tasksAndExpectedResultsForRemoveNodes());
//        while (current != null) {
//            System.out.print(current.val + " ");
//            current = current.next;
//        }
//        System.out.println(System.nanoTime() - start);

//        System.out.println(insertionSortList(new ListNode(2, new ListNode(1, new ListNode(3)))));
//        System.out.println(insertionSortList(new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(3))))));
//        System.out.println(insertionSortList(new ListNode(-1, new ListNode(5, new ListNode(3, new ListNode(4, new ListNode(0)))))));

//        int[]input = new Random().ints(5000, -10000, 10000).toArray();


        ListNode next = null;
        ListNode input = new ListNode(500, next);
        ListNode head = input;

        for (int i = 499; i > 0; i--) {
            input.next = new ListNode(i);
            input = input.next;
        }
//        System.out.println(insertionSortList(head));
        ListNode cursor = insertionSortList(head);
        while (cursor != null) {
            System.out.print(cursor.val + " ");
            cursor = cursor.next;
        }
    }

    public static ListNode removeElements(ListNode head, int val) {
        if (head == null)
            return head;

        ListNode previous = null;
        ListNode current = head;

        while (current != null) {
            if (current.val == val) {
                if (previous == null) {
                    head = head.next;
                    current = head;
                    continue;
                } else {
                    previous.next = current.next;
                    current = current.next;
                    continue;
                }
            }
            previous = current;
            current = current.next;
        }

        return head;
    }

    private static Map<ListNode, List<Integer>> expectedResultsForReverseBetween() {
//        ListNode tail = new ListNode(5);
//        ListNode four = new ListNode(4, tail);
//        ListNode three = new ListNode(3, four);
//        ListNode two = new ListNode(2, three);
//        ListNode head = new ListNode(1, two);
//
//        return Map.of(head, List.of(1,4,3,2,5));

        return Map.of(new ListNode(3, new ListNode(5)), List.of(3, 5));
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

    private static Map<ListNode, List<Integer>> expectedResultsForDeleteDuplicates() {
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
        median = length % 2 == 0
                ? length / 2 - 1
                : length / 2;

        cursor = head;
        ListNode reversed = null;
        ListNode aNew = null;
        for (int i = 0; i <= median; i++) {
            aNew = new ListNode(cursor.val, reversed);
            reversed = aNew;
            cursor = cursor.next;
        }

        median = length / 2;

        cursor = head;
        for (int i = 0; i < median; i++) {
            cursor = cursor.next;
        }
        ListNode reversedCursor = reversed;
        while (cursor != null) {
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

        for (int i = 1; i < left; i++) {
            if (i == left - 1)
                beforeLeftInsertionPoint = cursorOne;

            cursorOne = cursorOne.next;
            cursorTwo = cursorTwo.next;
        }

        ListNode reversed = new ListNode(cursorOne.val);
        ListNode beforeRightInsertionPoint = reversed;
        ListNode aNew = null;

        for (int i = left; i < right; i++) {
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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode firstCursor = l1;
        ListNode secondCursor = l2;
        ListNode result = null;
        ListNode previous = null;
        ListNode aNew = null;
        int carry = 0;

        while (!(firstCursor == null && secondCursor == null)) {
            int first = 0;
            if (firstCursor != null) {
                first = firstCursor.val;
                firstCursor = firstCursor.next;
            }
            int second = 0;
            if (secondCursor != null) {
                second = secondCursor.val;
                secondCursor = secondCursor.next;
            }

            int third = first + second + carry;
            if (third < 10) {
                aNew = new ListNode(third);
                carry = 0;
            } else {
                aNew = new ListNode(third % 10);
                carry = 1;
            }

            if (result == null) {
                result = aNew;
                previous = result;
            } else {
                previous.next = aNew;
                previous = aNew;
            }
        }

        if (carry != 0) {
            aNew = new ListNode(carry);
            previous.next = aNew;
        }

        return result;
    }

    private static Map<ListNode, ListNode> tasksForAddTwoNumbers() {
        return Map.of(
                new ListNode(2, new ListNode(4, new ListNode(3))), new ListNode(5, new ListNode(6, new ListNode(4))));
//                new ListNode(1, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))))))))),
//                        new ListNode(9));
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return head;

        ListNode currentOdd = head;
        ListNode currentEven = head.next;
        ListNode insertionPoint = currentEven;

        while (true) {
            currentOdd.next = currentEven.next;
            currentOdd = currentEven.next;

            if (currentOdd.next == null) {
                currentEven.next = null;
                currentOdd.next = insertionPoint;
                break;
            }

            currentEven.next = currentOdd.next;
            currentEven = currentOdd.next;

            if (currentEven.next == null) {
                currentOdd.next = insertionPoint;
                break;
            }
        }

        return head;
    }

    private static Map<ListNode, List<Integer>> tasksAndExpectedResultsForOddEvenList() {
        return Map.of(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4)))), List.of(1, 2, 3, 4));
    }

    // treat list as an array and let its values be shifted from the last node to the given one
    public static void deleteNode(ListNode node) {
        if (node == null) return;
        if (node.next == null) return;

        ListNode current = node;
        while (current != null) {
            current.val = current.next.val;
            if (current.next.next == null) {
                current.next = null;
            }
            current = current.next;
        }
    }

    //  https://leetcode.com/problems/remove-nodes-from-linked-list/description/
    // this solution was not accepted due to 'time limit exceeded' when given input with length 100_000 {3,2,1,100_000,99_999,...,6,5,4}
    public static ListNode removeNodes(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode outer = head;
        ListNode outerPrevious = null;
        /**
         *
         */
        while (outer != null) {
            ListNode inner = outer.next;
            ListNode innerPrevious = outer;
            while (inner != null && outer.val >= inner.val) {
                innerPrevious = inner;
                inner = inner.next;
            }

            if (inner != null) {
                if (outerPrevious == null) {
                    head = inner;
                    outer = inner;

                } else if (outer.next != null){
                    outer = inner;
                    outerPrevious.next = inner;

                }
            } else {
                outerPrevious = outer;
                outer = outer.next;
            }
        }
        return head;
    }

    public static ListNode removeNodesWithReverse(ListNode head) {
        head = reverseList(head);
        ListNode prev = head, ptr = head.next;
        int maxi = head.val;
        for(; ptr != null; ptr = ptr.next){
            if(ptr.val >= maxi){
                maxi = Math.max(maxi, ptr.val);
                prev.next = ptr;
                prev = ptr;
            }
        }
        prev.next = null;
        head = reverseList(head);
        return head;
    }

//    https://leetcode.com/problems/remove-nodes-from-linked-list/solutions/2851978/java-python-3-3-codes-recursive-iterative-space-o-n-and-extra-space-o-1/
//    Method 2: non-increasing Stack
//    similar to ArrayTasks.nextGreaterElementViaStack
    public static ListNode removeNodesViaStack(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        Deque<ListNode> theStack = new ArrayDeque<>();
        ListNode current = head;

        while (current != null) {
            if (!theStack.isEmpty() && theStack.peek().val < current.val) {
                theStack.pop();
                continue;
            }
            theStack.push(current);
            current = current.next;
        }

        ListNode aNew = null;
        while (theStack.size() > 1) {
            aNew = theStack.pop();
            theStack.peek().next = aNew;
        }
        return theStack.peek();
    }

    private static ListNode tasksAndExpectedResultsForRemoveNodes() {

        ListNode last = new ListNode(1);
        ListNode head = new ListNode(3, new ListNode(2, last));

        for (int i = 100000; i>3; i--) {
            last.next = new ListNode(i);
            last = last.next;
        }

//        ListNode head = new ListNode(5, new ListNode(2, new ListNode(13, new ListNode(3, new ListNode(8)))));

//        AtomicReference<ListNode> head = new AtomicReference<>();
//        AtomicReference<ListNode> previous = new AtomicReference<>();
//        Arrays.stream(("138,466,216,67,642,978,264,136,463,331,60,600,223,275,856,809,167,101,846,165,575,276,409,590,733,200,839,515,852,615,8,584,250,337,537,63,797,900,670,636,112,701,334," +
//                "422,780,552,912,506,313,474,183,792,822,661,37,164,601,271,902,792,501,184,559,140,506,94,161,167,622,288,457,953,700,464,785,203,729,725,422,76,191,195,157,854,730,577,503," +
//                "401,517,692,42,135,823,883,255,111,334,365,513,338,65,600,926,607,193,763,366,674,145,229,700,11,984,36,185,475,204,604,191,898,876,762,654,770,774,575,276,165,610,649,235,749," +
//                "440,607,962,747,891,943,839,403,655,22,705,416,904,765,905,574,214,471,451,774,41,365,703,895,327,879,414,821,363,30,130,14,754,41,494,548,76,825,899,499,188,982,8,890,563,438," +
//                "363,32,482,623,864,161,962,678,414,659,612,332,164,580,14,633,842,969,792,777,705,436,750,501,395,342,838,493,998,112,660,961,943,721,480,522,133,129,276,362,616,52,117,300,274," +
//                "862,487,715,272,232,543,275,68,144,656,623,317,63,908,565,880,12,920,467,559,91,698")
//                .split(","))
//                .forEach(i -> {
//                    ListNode aNew = new ListNode(Integer.valueOf(i).intValue());
//                    if (previous.get() != null) {
//                        previous.get().next = aNew;
//                    } else {
//                        head.set(aNew);
//                    }
//                    previous.set(aNew);
//                });

        return head;
    }

    /*
    this is updated version that sorts in-place, mutating input collection.
    somehow it runs significantly slower with input.length=5000 (tens of seconds instead of hundreds of milliseconds for initial version)
    therefore it is not accepted with 'time limit exceeded' error
     */
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode outerPrevious = head;
        ListNode outerCursor = head.next;
//        ListNode result = new ListNode(head.val);
        ListNode result = head;


        while (outerCursor != null) {
//            ListNode current = new ListNode(outerCursor.val);
            ListNode current = outerCursor;
            outerPrevious.next = outerCursor.next;

            ListNode innerPrevious = null;
            ListNode innerCursor = result;
//            while (innerCursor != null && innerCursor.val <= current.val) {
            while (innerCursor != outerPrevious.next && innerCursor.val <= current.val) {
                innerPrevious = innerCursor;
                innerCursor = innerCursor.next;
            }

            if (innerCursor == outerPrevious.next) {
                current.next = outerCursor.next;
                innerPrevious.next = current;


            } else if (innerCursor.val > current.val) {
                if (innerPrevious == null) {
                    current.next = result;
                    result = current;

                } else {
                    current.next = innerPrevious.next;
                    innerPrevious.next = current;
                }
            }

            outerPrevious = outerCursor;
            outerCursor = outerCursor.next;
        }

        return result;
    }

    /*
    this is initial version that uses extra list for storing result
    it was accepted by leetcode
    https://leetcode.com/problems/insertion-sort-list/submissions/865192495/
     */
    public static ListNode insertionSortList2(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode outerPrevious = head;
        ListNode outerCursor = head.next;
        ListNode result = new ListNode(head.val);


        while (outerCursor != null) {
            ListNode current = new ListNode(outerCursor.val);
//            ListNode current = outerCursor;
            outerPrevious.next = outerCursor.next;

            ListNode innerPrevious = null;
            ListNode innerCursor = result;
            while (innerCursor != null && innerCursor.val <= current.val) {
                innerPrevious = innerCursor;
                innerCursor = innerCursor.next;
            }

            if (innerCursor == null) {
                innerPrevious.next = current;

            } else if (innerCursor.val > current.val) {
                if (innerPrevious == null) {
                    current.next = result;
                    result = current;

                } else {
                    current.next = innerPrevious.next;
                    innerPrevious.next = current;
                }
            }

            outerPrevious = outerPrevious.next;
            outerCursor = outerCursor.next;
        }

        return result;
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


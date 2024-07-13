package atom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class  InterviewTasks <T extends Number> {
    T field;

    public InterviewTasks<T> setField(T field) {
        this.field = field;
        return this;
    }

    public static void main(String[] args) {
        arrayVsLinkedList();
    }

    private static void arrayVsLinkedList() {

        LinkedList<Integer> linkedList = new LinkedList<>();
        // ArrayList<Integer> arrayList = new ArrayList<>(1_001_000);
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            linkedList.add(i);
            arrayList.add(i);
        }

        System.out.println("linked list:");
        long start = System.nanoTime();
        for (int i = 0; i < 1_000; i++) {
            // long start = System.nanoTime();
            linkedList.add(500_000, Integer.MAX_VALUE);
            // long took = System.nanoTime() - start;
            // System.out.println(took);
        }
        System.out.println(System.nanoTime() - start);

        System.out.println("linked list by ListIterator:");
        ListIterator<Integer> iterator = null;
        start = System.nanoTime();
        for (int i = 0; i < 1_000; i++) {
            if (iterator == null) {
                iterator = linkedList.listIterator(500_000);
            }
            // long start = System.nanoTime();
            iterator.add(Integer.MAX_VALUE);
            // long took = System.nanoTime() - start;
            // System.out.println(took);
        }
        System.out.println(System.nanoTime() - start);

        System.out.println("array list WO resize:");
        start = System.nanoTime();
        for (int i = 0; i < 1_000; i++) {
            // long start = System.nanoTime();
            arrayList.add(500_000, Integer.MAX_VALUE);
            // long took = System.nanoTime() - start;
            // System.out.println(took);
        }
        System.out.println(System.nanoTime() - start);

        arrayList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            arrayList.add(i);
        }
        System.out.println("array list WITH resize:");
        start = System.nanoTime();
        for (int i = 0; i < 1_000; i++) {
            // long start = System.nanoTime();
            arrayList.add(500_000, Integer.MAX_VALUE);
            // long took = System.nanoTime() - start;
            // System.out.println(took);
        }
        System.out.println(System.nanoTime() - start);

    }

    private void lalala() {
        // field.get(0);
    }

    private void genericArgument(List<? extends Integer> list) {
        list.get(0).intValue();
//        list.add(new Integer(1));   //does not compile as ? extends Integer might be something other than Integer (not best example, cause Integer is a final class, but the idea is this)
    }
    private void genericArgument2(List<? super Integer> list) {
//        list.get(0).intValue();     // does not compile as ? super Integer might be a plain Object instance and therefore has no 'intValue()' method
        list.add(new Integer(1));
    }
}

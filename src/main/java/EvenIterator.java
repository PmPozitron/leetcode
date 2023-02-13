import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Реализовать итератор, возвращающий только чётные числа
 */
// это с собеса яндекса
public class EvenIterator implements Iterator<Integer> {
    final Collection<Integer> storage;
    Iterator<Integer> delegate;

    Integer current;


    public EvenIterator(Collection<Integer> storage) {
        this.storage = storage;
        this.delegate = storage.iterator();
    }

    public static void main(String[] args) {
        for (Collection<Integer> c : new Collection[]{List.of(1,2,3), Set.of(11,22,33,44,55), List.of(1,1,1), List.of(2,2,2)}) {
//        for (Collection<Integer> c : new Collection[]{List.of(2,2,2)}) {
            EvenIterator evenIterator = new EvenIterator(c);
//            System.out.println(evenIterator.next());
            while (evenIterator.hasNext()) {
                System.out.println(evenIterator.storage.getClass().getSimpleName());
                System.out.print(evenIterator.next());
                System.out.println();
            }
        };
    }

    public boolean hasNext() {
        if (current != null) {
            return true;
        }

        while (delegate.hasNext()) {
            int i = delegate.next();
             if (i % 2 != 0) {
                 continue;
             } else {
                 current = i;
                 return true;
             }
        }
        return false;
    }

    public Integer next() {
        if (! hasNext()) {
            throw new NoSuchElementException();
        }

        int result = current;
        boolean delegateHasNext = false;
        while (delegate.hasNext()) {
            int i = delegate.next();
            if (i % 2 != 0) {
                continue;
            } else {
                current = i;
                delegateHasNext = true;
                break;
            }
        }
        if (! delegateHasNext) {
            current = null;
        }
        return result;
    }
}

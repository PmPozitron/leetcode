package concurrencymergev2;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentMergeSortV2 {
    SplitTask splitTask;
    MergeTask mergeTask;
    Future<?> split;
    Future<?> merge;
    int[] beingSorted;
    int[] arrayCopy;
    BlockingQueue<int[]> mergeTasks = new LinkedBlockingQueue<>();
    ExecutorService splitter = Executors.newFixedThreadPool(8);
    AtomicBoolean ready = new AtomicBoolean(false);

    public ConcurrentMergeSortV2(int[] beingSorted) {
        this.beingSorted = beingSorted;
        this.arrayCopy = new int[beingSorted.length];
        this.splitTask = new SplitTask(beingSorted, arrayCopy, mergeTasks, splitter, new int[]{0, beingSorted.length / 2, beingSorted.length});
        this.mergeTask = new MergeTask(beingSorted, arrayCopy, mergeTasks, splitter, ready);
        this.split = this.splitter.submit(splitTask);
        this.merge = this.splitter.submit(mergeTask);
    }

    public static void main(String[] args) {
        for (int i = 0; i< 10; i++) {
            int[] input  = new Random(47).ints(100_000, 0, 100).toArray();
            int[] copy = new int[input.length];
            System.arraycopy(input, 0, copy, 0, input.length);
            Arrays.sort(copy);
            long start= System.nanoTime();

            ConcurrentMergeSortV2 sortV2 = new ConcurrentMergeSortV2(input);

            while (!sortV2.ready.get()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println(System.nanoTime() - start);
            sortV2.splitter.shutdownNow();
            System.out.println(Arrays.equals(input, copy));
        }
    }

    static class SplitTask implements Runnable {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks;
        ExecutorService splitter;
        int[] cursors;
        int down;
        int up;

        public SplitTask(int[] beingSorted, int[] arrayCopy, BlockingQueue<int[]> mergeTasks, ExecutorService splitter, int[] cursors) {
            this.beingSorted = beingSorted;
            this.arrayCopy = arrayCopy;
            this.mergeTasks = mergeTasks;
            this.splitter = splitter;
            this.cursors = cursors;
        }

        public void split(int[] cursors) {
            if (cursors[0] == cursors[1]) {
                mergeTasks.add(new int[]{cursors[0], cursors[0]});
                return;
            }
            int middle = (cursors[0] + cursors[1]) / 2;
            splitter.submit(() -> split(new int[]{cursors[0], middle}));
            splitter.submit(() -> split(new int[]{middle + 1, cursors[1]}));
        }

        @Override
        public void run() {
            splitter.submit(() -> split(new int[]{0, beingSorted.length-1}));
        }
    }

    static class MergeTask implements Callable<Boolean> {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks;
        ExecutorService splitter;
        AtomicBoolean ready;

        public MergeTask(int[] beingSorted, int[] arrayCopy, BlockingQueue<int[]> mergeTasks, ExecutorService splitter, AtomicBoolean ready) {
            this.ready = ready;
            this.beingSorted = beingSorted;
            this.arrayCopy = arrayCopy;
            this.mergeTasks = mergeTasks;
            this.splitter = splitter;
        }

        void mergeSorted(int lowPtr, int highPtr, int top) {
            AtomicInteger lowPointer = new AtomicInteger(lowPtr);
            AtomicInteger highPointer = new AtomicInteger(highPtr);

            int j = lowPointer.get();                       // workspace index
            int down = lowPointer.get();
            int mid = highPointer.get() - 1;
            int n = top - down + 1;       // # of items

            while (lowPointer.get() <= mid && highPointer.get() <= top) {
                if (beingSorted[lowPointer.get()] < beingSorted[highPointer.get()]) {
                    arrayCopy[j++] = beingSorted[lowPointer.getAndIncrement()];
                } else {
                    arrayCopy[j++] = beingSorted[highPointer.getAndIncrement()];
                }
            }

            while (lowPointer.get() <= mid) {
                arrayCopy[j++] = beingSorted[lowPointer.getAndIncrement()];
            }

            while (highPointer.get() <= top) {
                arrayCopy[j++] = beingSorted[highPointer.getAndIncrement()];
            }

            for (j = 0; j < n; j++) {
                beingSorted[down + j] = arrayCopy[down + j];
            }

            if (n == beingSorted.length) {
                ready.set(true);
                return;
            }

            mergeTasks.add(new int[]{lowPtr, top});
        }

        @Override
        public Boolean call() throws InterruptedException {
            while (!Thread.currentThread().isInterrupted()) {
//                synchronized (mergeTasks) {
                    int[] first = mergeTasks.take();
                    int[] second = mergeTasks.take();

                    while (first[1] != second[0]-1 && second[1] != first[0]-1) {
                        mergeTasks.add(second);
                        second = mergeTasks.take();
                    }
                    int f0 = first[0], f1 = first[1], s0 = second[0], s1 = second[1];
                    Runnable task = first[0] <= second[0]
                            ? () -> mergeSorted(f0, s0, s1)
                            : () -> mergeSorted(s0, f0, f1);
                    splitter.submit(task);
//                }
            }

            return true;
        }
    }
}

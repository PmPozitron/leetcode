package concurrencymergev2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentMergeSortV2 {

    SplitTask splitTask;
    MergeTask mergeTask;

    Future<?> split;
    Future<?> merge;

    int[] beingSorted;
    int[] arrayCopy;
    //    BlockingQueue<int[]> mergeTasks = new PriorityBlockingQueue<>(16, Comparator.comparingInt(array -> array[0]));
    BlockingQueue<int[]> mergeTasks = new LinkedBlockingQueue<>();
    BlockingQueue<int[]> splitTasks = new LinkedBlockingQueue<>();
    ExecutorService splitter = Executors.newFixedThreadPool(4);
//    ExecutorService splitter = Executors.newSingleThreadExecutor();

    AtomicBoolean ready = new AtomicBoolean(false);

    public ConcurrentMergeSortV2(int[] beingSorted) {
        this.beingSorted = beingSorted;
        this.arrayCopy = new int[beingSorted.length];
        this.splitTask = new SplitTask(beingSorted, arrayCopy, splitTasks, mergeTasks, splitter, new int[]{0, beingSorted.length / 2, beingSorted.length});
        this.mergeTask = new MergeTask(beingSorted, arrayCopy, mergeTasks, splitter, new int[]{0, beingSorted.length / 2, beingSorted.length}, ready);
        this.split = this.splitter.submit(splitTask);
        this.merge = this.splitter.submit(mergeTask);
    }

    public static void main(String[] args) {
        int[] input = new Random().ints(10, 0, 100).toArray();
        ConcurrentMergeSortV2 sortV2 = new ConcurrentMergeSortV2(input);

        while (!sortV2.ready.get()) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Arrays.toString(sortV2.beingSorted));
        sortV2.splitter.shutdownNow();
    }

    static class SplitTask implements Callable<Boolean> {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks;
        BlockingQueue<int[]> splitTasks;
        ExecutorService splitter;

        int[] cursors;
        int down;
        int up;

        public SplitTask(int[] beingSorted, int[] arrayCopy, BlockingQueue<int[]> splitTasks, BlockingQueue<int[]> mergeTasks, ExecutorService splitter, int[] cursors) {
            this.beingSorted = beingSorted;
            this.arrayCopy = arrayCopy;
            this.splitTasks = splitTasks;
            this.mergeTasks = mergeTasks;
            this.splitter = splitter;
            this.cursors = cursors;
            this.down = cursors[0];
            this.up = cursors[1];
        }

        public void split(int[] cursors) {
            if (cursors[0] == cursors[1]) {
                System.out.println("adding mergeTask for single elements " + Arrays.toString(cursors));
                mergeTasks.add(new int[]{cursors[0], cursors[0]});
                return;
            }
            int middle = (cursors[0] + cursors[1]) / 2;
//            if (cursors[1] - cursors[0] == 1) {
//                mergeTasks.add(new int[]{cursors[0], middle+1, cursors[1]});
//                return;
//            }


//            splitter.submit(() -> split(new int[]{cursors[0], middle}));
//            splitter.submit(() -> split(new int[]{middle + 1, cursors[1]}));
            try {
                splitTasks.put(new int[]{cursors[0], middle});
                splitTasks.put(new int[]{middle+1, cursors[1]});

            } catch (InterruptedException ex) {
                System.out.println(ex);
            }

        }

        @Override
        public Boolean call() throws InterruptedException {
            splitTasks.put(new int[]{0, beingSorted.length - 1});

            while (!Thread.currentThread().isInterrupted()) {
                int[] cursors = splitTasks.take();
                splitter.submit(() -> split(cursors));
            }

            return true;
        }
    }

    static class MergeTask implements Callable<Boolean> {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks;
        ExecutorService splitter;
        AtomicBoolean ready;

        public MergeTask(int[] beingSorted, int[] arrayCopy, BlockingQueue<int[]> mergeTasks, ExecutorService splitter, int[] cursors, AtomicBoolean ready) {
            this.ready = ready;
            this.beingSorted = beingSorted;
            this.arrayCopy = arrayCopy;
            this.mergeTasks = mergeTasks;
            this.splitter = splitter;
        }

        synchronized void mergeSorted(int lowPtr, int highPtr, int top) {
            System.out.println("merging " + lowPtr + " " + highPtr + " " + top);
            AtomicInteger lowPointer = null;
            AtomicInteger highPointer = null;

//            if (lowPtr <= highPtr) {
            lowPointer = new AtomicInteger(lowPtr);
            highPointer = new AtomicInteger(highPtr);
//            } else {
//                lowPointer = new AtomicInteger(highPtr);
//                highPointer = new AtomicInteger(lowPtr);
//            }


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
            System.out.println("after merge " + Arrays.toString(beingSorted));

            if (n == beingSorted.length) {
                ready.set(true);
                return;
            }

//            splitter.submit(() -> mergeSorted(lowPointer.get(), highPointer.get(), top));
            System.out.println("adding mergeTask for merged elements " + lowPtr + " " + top);

            mergeTasks.add(new int[]{lowPtr, top});

        }

        @Override
        public Boolean call() throws InterruptedException {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (mergeTasks) {
                    int[] first = mergeTasks.take();
                    int[] second = mergeTasks.take();
                    AtomicInteger f0 = new AtomicInteger(first[0]);
                    AtomicInteger f1 = new AtomicInteger(first[1]);
                    AtomicInteger s0 = new AtomicInteger(second[0]);
                    AtomicInteger s1 = new AtomicInteger(second[1]);
                    while (f1.get() != s0.get()-1 && s1.get() != f0.get()-1) {
//                    while (s0.get() != f1.get() - 1 && s1.get() - 1 != f0.get()) {
                        mergeTasks.add(second);
                        second = mergeTasks.take();
                        s0.set(second[0]);
                        s1.set(second[1]);
                    }
                    System.out.println(Arrays.toString(first) + " " + Arrays.toString(second));
                    Runnable task = first[0] <= second[0]
                            ? () -> mergeSorted(f0.get(), s0.get(), s1.get())
                            : () -> mergeSorted(s0.get(), f0.get(), f1.get());

                    splitter.submit(task);
                }
            }

            return true;
        }
    }
}

package concurrencymergev2;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentMergeSortV2 {
    private static final ConcurrentMergeSortV2 INSTANCE = new ConcurrentMergeSortV2();
    //    SplitTask splitTask;
//    MergeTask mergeTask;
//    Future<?> split;
//    Future<?> merge;
//    int[] beingSorted;
//    int[] arrayCopy;
//    BlockingQueue<int[]> mergeTasks = new LinkedBlockingQueue<>();
    ExecutorService splitter = Executors.newFixedThreadPool(16);
//    ExecutorService splitter = Executors.newCachedThreadPool();
//    AtomicBoolean ready = new AtomicBoolean(false);

    public ConcurrentMergeSortV2() {
//        this.beingSorted = beingSorted;
//        this.arrayCopy = new int[beingSorted.length];
//        this.splitTask = new SplitTask(beingSorted, arrayCopy, mergeTasks, splitter, new int[]{0, beingSorted.length / 2, beingSorted.length});
//        this.mergeTask = new MergeTask(beingSorted, arrayCopy, mergeTasks, splitter, ready);
//        this.split = this.splitter.submit(splitTask);
//        this.merge = this.splitter.submit(mergeTask);
    }

    public static SortTask submitSortTask(int[] toBeSorted) {
        return INSTANCE.new SortTask(toBeSorted);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            int[] input = new Random().ints(1000, 0, 100).toArray();
            int[] copy = new int[input.length];
            System.arraycopy(input, 0, copy, 0, input.length);
            Arrays.sort(copy);
            long start = System.nanoTime();

            SortTask sortTask = ConcurrentMergeSortV2.submitSortTask(input);

            while (!sortTask.isReady()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

//            System.out.println(sortV2.merge.get());
            System.out.println(System.nanoTime() - start);
//            INSTANCE.splitter.shutdownNow();
            System.out.println(Arrays.equals(input, copy));
        }
        INSTANCE.splitter.shutdownNow();
    }

    class SortTask {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks = new LinkedBlockingQueue<>();
        SplitTask splitTask;
        MergeTask mergeTask;
        Future<?> split;
        Future<?> merge;
        AtomicBoolean ready = new AtomicBoolean(false);
        AtomicReference<int[]>result = new AtomicReference<>();

        public SortTask(int[] beingSorted) {
            this.beingSorted = beingSorted;
            this.arrayCopy = new int[beingSorted.length];
            this.splitTask = new SplitTask(beingSorted, arrayCopy, mergeTasks, splitter, new int[]{0, beingSorted.length / 2, beingSorted.length});
            this.mergeTask = new MergeTask(beingSorted, arrayCopy, mergeTasks, splitter, ready, result);
            this.split = splitter.submit(splitTask);
            this.merge = splitter.submit(mergeTask);
        }

        public boolean isReady() {
            return ready.get();
        }

        public int[] getResult() throws InterruptedException {
            while (!isReady()) {
                TimeUnit.MILLISECONDS.sleep(500);
            }
            return beingSorted;
        }
    }

    class SplitTask implements Runnable {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks;
        ExecutorService splitter;
        int[] cursors;

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
            splitter.submit(() -> split(new int[]{0, beingSorted.length - 1}));
        }
    }

    class MergeTask implements Callable<int[]> {
        int[] beingSorted;
        int[] arrayCopy;
        BlockingQueue<int[]> mergeTasks;
        ExecutorService splitter;
        AtomicBoolean ready;
        AtomicReference<int[]> result;
        volatile boolean volatileReady;
        volatile Thread runner;

        public MergeTask(int[] beingSorted, int[] arrayCopy, BlockingQueue<int[]> mergeTasks, ExecutorService splitter, AtomicBoolean ready, AtomicReference<int[]> result) {
            this.ready = ready;
            this.beingSorted = beingSorted;
            this.arrayCopy = arrayCopy;
            this.mergeTasks = mergeTasks;
            this.splitter = splitter;
            this.result = result;
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
                result.set(beingSorted);
                ready.set(true);
//                volatileReady = true;
                System.out.println("in merge\t " + result.hashCode() + " " + result.get() + " " + ready.hashCode() + " " + ready.get() + " " + volatileReady);
//                interrupt();

                return;
            }

            mergeTasks.add(new int[]{lowPtr, top});
        }

        public void interrupt() {
            runner.interrupt();
        }

        @Override
        public int[] call() throws InterruptedException {
            System.out.println("in call o\t\t " + result.hashCode() + " " + result.get() + " " + ready.hashCode() + " " + ready.get() + " " + volatileReady);

            while (! ready.get()) {
                System.out.println("in call i\t\t " + result.hashCode() + " " + result.get() + " " + ready.hashCode() + " " + ready.get() + " " + volatileReady);

//            while (!Thread.currentThread().isInterrupted()) {
                runner = Thread.currentThread();

//                System.out.println("picking up first");
                int[] first = mergeTasks.poll(500, TimeUnit.MILLISECONDS);
//                System.out.println("picking up second");
                int[] second = mergeTasks.poll(500, TimeUnit.MILLISECONDS);

                while (!Thread.currentThread().isInterrupted() && first[1] != second[0] - 1 && second[1] != first[0] - 1) {
                    mergeTasks.add(second);
                    second = mergeTasks.take();
                }
                int f0 = first[0], f1 = first[1], s0 = second[0], s1 = second[1];
                Runnable task = first[0] <= second[0]
                        ? () -> mergeSorted(f0, s0, s1)
                        : () -> mergeSorted(s0, f0, f1);
                splitter.submit(task);
            }

            System.out.println("exiting call()");
            return result.get();
        }
    }
}

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentMergeSort {
//    static ArrayBlockingQueue<SplitTask> splitTasks;
//    static ArrayBlockingQueue<MergeTask> mergeTasks;

    static ExecutorService splitter = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);
//    static ExecutorService splitter = Executors.newCachedThreadPool();
    static ExecutorService merger = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);
//    static ExecutorService merger = Executors.newCachedThreadPool();

    static AtomicReference<int[]> result = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException {
        int bound = 1_000_000;
        int[] input = new Random(47).ints(bound, 0, bound).toArray();
//        System.out.println(Arrays.toString(input));
//        splitTasks = new ArrayBlockingQueue<>(input.length/2);
//        mergeTasks = new ArrayBlockingQueue<>(input.length/2);

//        exec.submit(new SplitTask(input, splitTasks, mergeTasks, result));
        BlockingQueue<int[]> queue = new LinkedBlockingQueue<>();
        splitter.submit(new SplitTask(input.length, input, splitter, queue));
        merger.submit(new MergeTask(input.length, merger, result, queue));

        synchronized (ConcurrentMergeSort.class) {
            while (result.get() == null) {
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }
//        System.out.println("result " + Arrays.toString(result.get()));
        splitter.shutdown();
        merger.shutdownNow();
    }
}

class SplitTask implements Runnable {
    int size;
    int[] array;
    //    ArrayBlockingQueue<SplitTask> splitTasks;
//    ArrayBlockingQueue<MergeTask> mergeTasks;
    ExecutorService splitter;
    ExecutorService merger;
    AtomicReference<int[]> result;
    BlockingQueue<int[]> mergeCandidates;

//    public SplitTask(int[] array, ArrayBlockingQueue<SplitTask> splitTasks, ArrayBlockingQueue<MergeTask> mergeTasks,  AtomicReference<int[]> result) {
//        this.array = array;
//        this.splitTasks = splitTasks;
//        this.mergeTasks = mergeTasks;
//        this.result = result;
//    }


    public SplitTask(int size, int[] array, ExecutorService splitter, BlockingQueue<int[]> mergeCandidates) {
        this.size = size;
        this.array = array;
        this.splitter = splitter;
        this.mergeCandidates = mergeCandidates;
    }

    @Override
    public void run() {
        splitter.submit(() -> split(array));

    }

    public void split(int[] array) {
        if (array.length == 1) {
//            System.out.println("adding to q:" + Arrays.toString(array));
            mergeCandidates.add(array);
            return;
        }

        int middle = array.length / 2;
        int[] first = new int[middle];
        int[] second = new int[array.length - middle];
        System.arraycopy(array, 0, first, 0, first.length);
        System.arraycopy(array, middle, second, 0, second.length);

//        System.out.println("split: " + Arrays.toString(first) + " " + Arrays.toString(second));
        splitter.submit(new SplitTask(size, first, splitter, mergeCandidates));
        splitter.submit(new SplitTask(size, second, splitter, mergeCandidates));
    }
}

class MergeTask implements Runnable {
    int size;
    ExecutorService merger;
    AtomicReference<int[]> result;
    BlockingQueue<int[]> queue;

    public MergeTask(int size, ExecutorService merger, AtomicReference<int[]> result, BlockingQueue<int[]> queue) {
        this.size = size;
        this.merger = merger;
        this.result = result;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            merger.submit(() -> {
                int[] first = null;
                int[] second = null;
                try {
                    synchronized (queue) {
//                        System.out.println("waiting first: " + Thread.currentThread().getName());
                        first = queue.take();
//                        System.out.println("first from q:" + Arrays.toString(first));
//                        System.out.println("waiting second: " + Thread.currentThread().getName());
                        second = queue.take();
//                        System.out.println("second from q:" + Arrays.toString(second));
                    }

                    mergeSorted(first, second);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    void mergeSorted(int[] first, int[] second) {
        int[] result = new int[first.length + second.length];
        int i = 0, j = 0, k = 0;

        while (i < first.length && j < second.length) {
            if (first[i] <= second[j]) {
                result[k++] = first[i++];
            } else {
                result[k++] = second[j++];
            }
        }

        if (i < first.length) {
            for (; i < first.length; i++) {
                result[k++] = first[i];
            }
        } else if (j < second.length) {
            for (; j < second.length; j++) {
                result[k++] = second[j];
            }
        }

        if (result.length == size) {
            this.result.set(result);
//            System.out.println("finished:" + Arrays.toString(result));
            return;
        }

        try {
            queue.put(result);
//            System.out.println("merged to q:" + Arrays.toString(result));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

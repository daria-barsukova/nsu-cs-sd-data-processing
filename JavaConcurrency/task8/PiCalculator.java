package task8;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;



public class PiCalculator implements Runnable {
    private static final int THREAD_COUNT = 4;
    private final int startIndex;
    private final int iterationsPerThread;
    private final AtomicReference<Double> pi;
    private final CyclicBarrier barrier;

    public PiCalculator(int startIndex, int iterationsPerThread, AtomicReference<Double> pi, CyclicBarrier barrier) {
        this.startIndex = startIndex;
        this.iterationsPerThread = iterationsPerThread;
        this.pi = pi;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        double localSum = 0.0;

        // Calculate partial sum for this thread
        for (int j = 0; j < iterationsPerThread; j++) {
            int termIndex = calculateTermIndex(j);
            localSum += calculateLeibnizTerm(termIndex);

            // Barrier synchronization to wait for other threads
            if (!awaitBarrier()) {
                break;
            }
        }

        // Update the shared Pi value atomically
        double finalLocalSum = localSum;
        pi.updateAndGet(currentValue -> currentValue + finalLocalSum);
    }

    // Calculates the term index for this thread's portion of the work
    private int calculateTermIndex(int iteration) {
        return (iteration * THREAD_COUNT) + startIndex;
    }

    // Calculates the term in the Leibniz series
    private double calculateLeibnizTerm(int n) {
        return Math.pow(-1, n) / (2 * n + 1);
    }

    // Awaits the barrier and handles exceptions, returns false if interrupted
    private boolean awaitBarrier() {
        try {
            barrier.await();
            return true;
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
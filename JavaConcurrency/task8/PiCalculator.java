package task8;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

public class PiCalculator implements Runnable {
    private static final int THREAD = 4;
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

        // calculate partial sum for this thread
        for (int j = 0; j < iterationsPerThread; j++) {
            int termIndex = calculateTermIndex(j);
            localSum += calculateLeibnizTerm(termIndex);

            // barrier synchronization to wait for other threads
            if (!awaitBarrier()) {
                break;
            }
        }

        // update shared Pi value atomically
        double finalLocalSum = localSum;
        pi.updateAndGet(currentValue -> currentValue + finalLocalSum);
    }

    // calculate term index for this thread's portion of work
    private int calculateTermIndex(int iteration) {
        return (iteration * THREAD) + startIndex;
    }

    // calculate term in Leibniz series
    private double calculateLeibnizTerm(int n) {
        return Math.pow(-1, n) / (2 * n + 1);
    }

    // awaits barrier and handles exceptions, returns false if interrupted
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

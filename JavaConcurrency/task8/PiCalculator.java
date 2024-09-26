package task8;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

public class PiCalculator implements Runnable {
    private static final int THREAD = 4;
    private final int startIndex;
    private final AtomicReference<Double> pi;
    private final CyclicBarrier barrier;
    private int iterationCount = 0;

    public PiCalculator(int startIndex, AtomicReference<Double> pi, CyclicBarrier barrier) {
        this.startIndex = startIndex;
        this.pi = pi;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        double localSum = 0.0;
        int iteration = 0;

        while (!Thread.currentThread().isInterrupted()) {
            int termIndex = calculateTermIndex(iteration);
            localSum += calculateLeibnizTerm(termIndex);
            iterationCount++;

            // barrier synchronization to wait for other threads
            if (!awaitBarrier()) {
                break;
            }

            iteration++;
        }

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

    public int getIterationCount() {
        return iterationCount;
    }
}

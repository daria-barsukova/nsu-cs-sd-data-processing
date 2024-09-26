package task8;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    private static final int THREAD = 4;
    private static final AtomicReference<Double> pi = new AtomicReference<>(0.0);
    private static final PiCalculator[] calculators = new PiCalculator[THREAD];
    private static final Thread[] threads = new Thread[THREAD];

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD);

        setupShutdownHook();

        startThreads(barrier);
    }

    private static void startThreads(CyclicBarrier barrier) {
        for (int i = 0; i < THREAD; i++) {
            calculators[i] = new PiCalculator(i, pi, barrier);
            threads[i] = new Thread(calculators[i]);
            threads[i].start();
        }
    }

    private static void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(Main::handleShutdown));
    }

    private static void handleShutdown() {
        System.out.println("Received SIGINT. Stopping threads...");
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to join thread", e);
            }
        }

        for (int i = 0; i < THREAD; i++) {
            System.out.println("Thread " + i + " completed iterations: " + calculators[i].getIterationCount());
        }

        double newPi = pi.get() * 4;
        System.out.println("Pi: " + newPi);
    }
}

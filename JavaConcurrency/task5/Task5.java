package task5;


import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Task5 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Child thread is working");
                    Thread.sleep(100); // pause to reduce console load
                }
            } catch (InterruptedException e) {
                System.out.println("Child thread was interrupted");
            } finally {
                System.out.println("Child thread is finishing");
            }
        };

        executor.submit(task); // starting task

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted");
        }

        executor.shutdownNow(); // interrupts running tasks

        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("Task did not terminate in given time");
            }
        } catch (InterruptedException e) {
            System.out.println("Termination was interrupted");
        }

        System.out.println("Main thread finished");
    }
}

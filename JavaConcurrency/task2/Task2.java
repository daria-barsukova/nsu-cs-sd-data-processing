package task2;


public class Task2 {

    public static void main(String[] args) {
        Thread childThread = new Thread(() -> printMessages("from new thread"));
        childThread.start();

        try {
            childThread.join();
        } catch (InterruptedException e) {
            System.out.println("Child thread was interrupted");
            Thread.currentThread().interrupt(); // restoring interrupt flag
        }

        printMessages("from main thread");
    }

    private static void printMessages(String source) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Message #" + i + " " + source);
        }
    }
}

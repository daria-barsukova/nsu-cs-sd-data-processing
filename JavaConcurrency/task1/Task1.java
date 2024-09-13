package task1;


public class Task1 {

    public static void main(String[] args) {
        Thread newThread = new Thread(() -> printMessages("from new thread"));
        newThread.start();
        printMessages("from main thread");
    }

    private static void printMessages(String source) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Message #" + i + " " + source);
        }
    }
}

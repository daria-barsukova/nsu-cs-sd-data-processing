package task10;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task10 {

    public static void main(String[] args) {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        Lock lock3 = new ReentrantLock();

        lock1.lock(); // main thread starts first
        Thread thread = new Thread(new PrintLine("from new thread", lock1, lock2, lock3));
        thread.start();

        for (int i = 1; i <= 10; ++i) {
            lock3.lock(); // main thread locks to print
            System.out.println("Message #" + i + " from main thread");
            lock1.unlock(); // allow child thread to continue
            lock2.lock();   // main thread locks to wait for child thread
            lock3.unlock(); // unlock main thread
            lock1.lock();   // lock for synchronization
            lock2.unlock(); // allow child thread to finish its turn
        }
    }

    static class PrintLine implements Runnable {
        private final String source;
        private final Lock lock1;
        private final Lock lock2;
        private final Lock lock3;

        public PrintLine(String source, Lock lock1, Lock lock2, Lock lock3) {
            this.source = source;
            this.lock1 = lock1;
            this.lock2 = lock2;
            this.lock3 = lock3;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; ++i) {
                lock2.lock();  // child thread waits for its turn
                lock1.lock();  // synchronization with main thread
                System.out.println("Message #" + i + " " + source);
                lock2.unlock(); // allow parent thread to continue
                lock3.lock();   // block to ensure proper synchronization
                lock1.unlock(); // allow main thread to continue
                lock3.unlock(); // complete turn
            }
        }
    }
}

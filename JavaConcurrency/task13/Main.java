package task13;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int COUNT = 5;

    public static void main(String[] args) {
        Lock[] forks = new ReentrantLock[COUNT];

        // initialize locks for each fork
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new ReentrantLock();
        }

        // shared lock and condition for synchronizing fork access
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        // create and start philosopher threads
        List<Thread> philosophers = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i + 1) % forks.length];
            philosophers.add(new Thread(new Philosopher(i + 1, leftFork, rightFork, lock, condition)));
        }
        philosophers.forEach(Thread::start);
    }
}

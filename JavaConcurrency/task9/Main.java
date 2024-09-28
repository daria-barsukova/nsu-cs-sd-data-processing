package task9;


import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int COUNT = 5;

    public static void main(String[] args) {
        List<Thread> philosophers = new ArrayList<>();
        Object[] forks = new Object[COUNT];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < COUNT; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            if (i == COUNT - 1) {
                philosophers.add(new Thread(new Philosopher(i + 1, rightFork, leftFork)));
            } else {
                philosophers.add(new Thread(new Philosopher(i + 1, leftFork, rightFork)));
            }
        }

        philosophers.forEach(Thread::start);
    }
}

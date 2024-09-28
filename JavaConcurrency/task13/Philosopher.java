package task13;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;
    private final Lock forks;
    private final Condition condition;

    private static final int MAX_EATING_TIME = 2000;
    private static final int MAX_THINKING_TIME = 200;
    private static final int ACTION_TIME = 100;

    public Philosopher(int id, Lock leftFork, Lock rightFork, Lock forks, Condition condition) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.forks = forks;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();

                forks.lock();
                try {
                    if (!leftFork.tryLock()) {
                        condition.await();
                        continue;
                    }
                    pickUp("left fork");

                    if (!rightFork.tryLock()) {
                        leftFork.unlock();
                        putDown("left fork, waiting for both forks");
                        condition.await();
                        continue;
                    }
                    pickUp("right fork");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    forks.unlock();
                }

                eat();

                forks.lock();
                try {
                    putDown("right fork");
                    rightFork.unlock();

                    putDown("left fork");
                    leftFork.unlock();

                    condition.signalAll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    forks.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void pickUp(String fork) throws InterruptedException {
        System.out.println("Philosopher " + id + " picked up " + fork);
        Thread.sleep((long) (Math.random() * ACTION_TIME));
    }

    private void putDown(String fork) throws InterruptedException {
        System.out.println("Philosopher " + id + " put down " + fork);
        Thread.sleep((long) (Math.random() * ACTION_TIME));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep((long) (Math.random() * MAX_EATING_TIME));
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep((long) (Math.random() * MAX_THINKING_TIME));
    }
}

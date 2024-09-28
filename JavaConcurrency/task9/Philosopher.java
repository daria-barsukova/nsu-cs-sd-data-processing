package task9;


public class Philosopher implements Runnable {
    private final int id;
    private final Object leftFork;
    private final Object rightFork;

    private static final int MAX_EATING_TIME = 2000;
    private static final int MAX_THINKING_TIME = 200;
    private static final int ACTION_TIME = 100;

    public Philosopher(int id, Object leftFork, Object rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                synchronized (leftFork) {
                    pickUp("left fork");
                    synchronized (rightFork) {
                        pickUp("right fork");
                        eat();
                        putDown("right fork");
                    }
                    putDown("left fork");
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

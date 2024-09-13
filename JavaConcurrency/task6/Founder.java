package task6;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public final class Founder {
    private final List<Thread> workers;
    private final CyclicBarrier barrier;
    private final Company company;

    public Founder(final Company company) {
        this.company = company;
        int companyCount = company.getDepartmentsCount();

        // initialize CyclicBarrier for main thread
        barrier = new CyclicBarrier(companyCount + 1);

        this.workers = new ArrayList<>(companyCount);
        for (int i = 0; i < companyCount; i++) {
            int departmentIndex = i;
            workers.add(new Thread(() -> {
                company.getFreeDepartment(departmentIndex).performCalculations();
                try {
                    barrier.await(); // worker waits at barrier
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.err.println("Error: " + e.getMessage() + " in " + Thread.currentThread().getName());
                }
            }));
        }
    }

    public void start() throws BrokenBarrierException, InterruptedException {
        for (Thread worker : workers) {
            worker.start();
        }

        barrier.await();
        company.showCollaborativeResult();
    }

    public static void main(String[] args) {
        Company company = new Company(10);  // create company with 10 departments
        Founder founder = new Founder(company);

        try {
            founder.start();
        } catch (BrokenBarrierException | InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}

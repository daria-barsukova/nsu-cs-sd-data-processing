package task7;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect input arguments");
            System.exit(1);
        }

        int iterations = Integer.parseInt(args[0]);
        int threads = Integer.parseInt(args[1]);

        try (ExecutorService executor = Executors.newFixedThreadPool(threads)) {
            List<Future<Double>> futures = submitTasks(executor, threads, iterations);
            double pi = calculatePi(futures);
            System.out.println("Result: " + pi);
        }
    }

    private static List<Future<Double>> submitTasks(ExecutorService executor, int threads, int iterations) {
        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            futures.add(executor.submit(new PiCalculator(i, threads, iterations)));
        }
        return futures;
    }

    private static double calculatePi(List<Future<Double>> futures) {
        double pi = futures.stream().mapToDouble(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).sum();
        return pi * 4;
    }
}

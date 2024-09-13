package task7;


import java.util.concurrent.Callable;

public class PiCalculator implements Callable<Double> {
    private final int startIndex;
    private final int stepSize;
    private final int totalIterations;

    public PiCalculator(int startIndex, int stepSize, int totalIterations) {
        this.startIndex = startIndex;
        this.stepSize = stepSize;
        this.totalIterations = totalIterations;
    }

    @Override
    public Double call() {
        double sum = 0.0;
        for (int i = startIndex; i < totalIterations; i += stepSize) {
            sum += calculateLeibnizTerm(i);
        }
        return sum;
    }

    private double calculateLeibnizTerm(int termIndex) {
        return Math.pow(-1, termIndex) / (2 * termIndex + 1);
    }
}

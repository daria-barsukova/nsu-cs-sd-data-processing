package task3;


import java.util.Arrays;
import java.util.List;

public class Task3 {

    public static void main(String[] args) {
        List<List<String>> sequences = Arrays.asList(
                Arrays.asList("Task A -- 1", "Task A -- 2", "Task A -- 3"),
                Arrays.asList("Task B -- 1", "Task B -- 2", "Task B -- 3"),
                Arrays.asList("Task C -- 1", "Task C -- 2", "Task C -- 3"),
                Arrays.asList("Task D -- 1", "Task D -- 2", "Task D -- 3")
        );

        sequences.stream()
                .map(sequence -> new Thread(() -> sequence.forEach(System.out::println)))
                .forEach(Thread::start);
    }
}

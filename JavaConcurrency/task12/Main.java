package task12;


import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        Scanner input = new Scanner(System.in);
        Thread sortThread = new Thread(new SortList(list));
        sortThread.start();

        while (true) {
            String nextLine = input.nextLine();
            if (nextLine.isEmpty()) {
                printList(list);
            } else {
                addLinesToList(list, nextLine);
            }
        }
    }

    // helper method to split long lines and add them to list
    private static void addLinesToList(LinkedList<String> list, String inputLine) {
        String[] lines = inputLine.split("(?<=\\G.{80})");
        synchronized (list) {
            for (String line : lines) {
                list.addFirst(line);
            }
        }
    }

    // helper method to print current list
    private static void printList(LinkedList<String> list) {
        synchronized (list) {
            list.forEach(System.out::println);
        }
    }
}

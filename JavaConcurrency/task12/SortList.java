package task12;


import java.util.List;
import java.util.concurrent.TimeUnit;

class SortList implements Runnable {
    private final List<String> list;

    public SortList(List<String> list) {
        this.list = list;
    }

    private void bubbleSort() {
        synchronized (list) {
            boolean sorted = false;
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                        String temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                        sorted = false;
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                bubbleSort();
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sorting thread interrupted.");
        }
    }
}

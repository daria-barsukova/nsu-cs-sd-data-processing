package task14;


import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        // semaphore array for synchronizing production process
        Semaphore[] semaphores = new Semaphore[4];
        for (int i = 0; i < semaphores.length; i++) {
            semaphores[i] = new Semaphore(0); // initially locked
        }

        String[] partNames = {"DetailA", "DetailB", "DetailC", "Module", "Widget"};
        Thread[] productionThreads = new Thread[partNames.length];

        for (int i = 0; i < partNames.length; i++) {
            productionThreads[i] = new Thread(new ProductionLine(partNames[i], semaphores));
            productionThreads[i].start();
        }
    }
}

package task14;


import java.util.concurrent.Semaphore;

class ProductionLine implements Runnable {
    private final String partName;
    private final Semaphore semaphoreDetailA;
    private final Semaphore semaphoreDetailB;
    private final Semaphore semaphoreDetailC;
    private final Semaphore semaphoreModule;

    public ProductionLine(String partName, Semaphore[] semaphores) {
        this.partName = partName;
        this.semaphoreDetailA = semaphores[0];
        this.semaphoreDetailB = semaphores[1];
        this.semaphoreDetailC = semaphores[2];
        this.semaphoreModule = semaphores[3];
    }

    @Override
    public void run() {
        try {
            while (true) {
                switch (partName) {
                    case "DetailA" -> produceDetail("Detail A", 1000, semaphoreDetailA);
                    case "DetailB" -> produceDetail("Detail B", 2000, semaphoreDetailB);
                    case "DetailC" -> produceDetail("Detail C", 3000, semaphoreDetailC);
                    case "Module" -> assembleModule();
                    case "Widget" -> assembleWidget();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
            System.err.println("Production thread interrupted: " + e.getMessage());
        }
    }

    private void produceDetail(String detailName, int productionTime, Semaphore semaphore) throws InterruptedException {
        System.out.println("Producing " + detailName);
        Thread.sleep(productionTime); // simulate production time
        System.out.println(detailName + " has been produced");
        semaphore.release(); // signal completion
    }

    private void assembleModule() throws InterruptedException {
        System.out.println("Assembling module...");
        semaphoreDetailA.acquire(); // wait for Detail A
        semaphoreDetailB.acquire(); // wait for Detail B
        System.out.println("Module has been assembled");
        semaphoreModule.release(); // signal module is ready
    }

    private void assembleWidget() throws InterruptedException {
        System.out.println("Assembling widget...");
        semaphoreDetailC.acquire(); // wait for Detail C
        semaphoreModule.acquire(); // wait for Module
        System.out.println("Widget has been assembled");
    }
}

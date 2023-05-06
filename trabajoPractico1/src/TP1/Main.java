package TP1;

// Clase principal
public class Main {
    // Se indica la cantidad de hilos para cada proceso
    private static final int numberOfLoaders = 2;
    private static final int numberOfImprovers = 3;
    private static final int numberOfResizers = 3;
    private static final int numberOfClones = 2;
    private static final int targetAmountOfData = 100;

    public static void main(String[] args) {
        // Se inicia el contador de tiempo
        long start = System.currentTimeMillis();
        // Se limpia el archivo del Log
        Log.clearFile();
        // Se instancian los contenedores
        InitContainer initContainer = new InitContainer(targetAmountOfData);
        FinalContainer finalContainer = new FinalContainer(targetAmountOfData);
        // Se crean arreglos con las diferentes instancias
        Loader[] loaders = new Loader[numberOfLoaders];
        Cloner[] cloners = new Cloner[numberOfClones];
        Resizer[] resizers = new Resizer[numberOfResizers];
        Improver[] improvers = new Improver[numberOfImprovers];
        // Se crean arreglos de hilos
        Thread[] loadersThreads = new Thread[numberOfLoaders];
        Thread[] improversThreads = new Thread[numberOfImprovers];
        Thread[] resizerThreads = new Thread[numberOfResizers];
        Thread[] clonersThreads = new Thread[numberOfClones];
        // Se inicia el Log
        TP1.Log log = new TP1.Log(targetAmountOfData, initContainer, finalContainer, loaders, improvers, resizers,
                cloners, loadersThreads, improversThreads, resizerThreads, clonersThreads);
        // Se crean los hilos
        for (int i = 0; i < numberOfLoaders; i++) {
            loaders[i] = new Loader(initContainer, "TP1.Loader " + i);
            loadersThreads[i] = new Thread(loaders[i]);
            loadersThreads[i].setName(loaders[i].getName() + " (Thread ID: " + loadersThreads[i].getId() + ")");
        }
        for (int i = 0; i < numberOfImprovers; i++) {
            improvers[i] = new Improver(initContainer, "TP1.Improver " + i, numberOfImprovers, targetAmountOfData);
            improversThreads[i] = new Thread(improvers[i]);
            improversThreads[i].setName(improvers[i].getName() + " (Thread ID: " + improversThreads[i].getId() + ")");
        }
        for (int i = 0; i < numberOfResizers; i++) {
            resizers[i] = new Resizer(initContainer, "TP1.Resizer " + i , targetAmountOfData);
            resizerThreads[i] = new Thread(resizers[i]);
            resizerThreads[i].setName(resizers[i].getName() + " (Thread ID: " + resizerThreads[i].getId() + ")");
        }
        for (int i = 0; i < numberOfClones; i++) {
            cloners[i] = new Cloner(initContainer, finalContainer, "TP1.Cloner " + i);
            clonersThreads[i] = new Thread(cloners[i]);
            clonersThreads[i].setName(cloners[i].getName() + " (Thread ID: " + clonersThreads[i].getId() + ")");
        }
        // Se les hace el start() a cada hilo
        for (Thread loadersThread : loadersThreads) {
            loadersThread.start();
        }
        for (Thread improverThread : improversThreads) {
            improverThread.start();
        }
        for (Thread resizersThread : resizerThreads) {
            resizersThread.start();
        }
        for (Thread clonerThread : clonersThreads) {
            clonerThread.start();
        }
        // El Log empieza a tomar datos
        log.start();
        // Se le hace join() a los hilos para esperar a que mueran y continuar con el main
        try {
            for (Thread waiting : loadersThreads) {
                waiting.join();
            }
            for (Thread waiting : improversThreads) {
                waiting.join();
            }
            for (Thread waiting : resizerThreads) {
                waiting.join();
            }
            for (Thread waiting : clonersThreads) {
                waiting.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Se le pide la interrupción al Log
        log.interrupt();
        // Se finaliza la cuenta del cronómetro iniciado
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        // Se imprime información de la ejecución
        // La información más completa aparece en el Log estadístico
        System.out.println("\nExecution Complete!");
        System.out.println("\nThread's states:");
        for (Thread loaderThread : loadersThreads) {
            System.out.println(loaderThread.getName() + ": " + loaderThread.getState().name());
        }
        for (Thread improverThread : improversThreads) {
            System.out.println(improverThread.getName() + ": " + improverThread.getState().name());
        }
        for (Thread resizerThread : resizerThreads) {
            System.out.println(resizerThread.getName() + ": " + resizerThread.getState().name());
        }
        for (Thread clonerThread : clonersThreads) {
            System.out.println(clonerThread.getName() + ": " + clonerThread.getState().name());
        }
        int totalLoadedImages = 0;
        int totalImprovements = 0;
        int totalResizing = 0;
        int totalClonedImages = 0;
        for (Loader load : loaders) {
            totalLoadedImages += load.getLoadedImages();
        }
        for (Improver improver : improvers) {
            totalImprovements += improver.getTotalImagesImprovedByThread();
        }
        for (Resizer resizer : resizers) {
            totalResizing += resizer.getTotalImagesResized();
        }
        for (Cloner cloner : cloners) {
            totalClonedImages += cloner.getClonedImages();
        }
        System.out.println("\nPerformance stats:");
        System.out.println("Elapsed Time: " + (float) (timeElapsed / 1000.00) + " seconds");
        System.out.println("Final InitContainer size: " + initContainer.getSize());
        System.out.println("Final FinalContainer size: " + finalContainer.getSize());
        System.out.println("Total Loaded Images: " + totalLoadedImages);
        System.out.println("Total improvements: " + totalImprovements);
        System.out.println("Total resizing: " + totalResizing);
        System.out.println("Total cloned Images " + totalClonedImages);
    }
}
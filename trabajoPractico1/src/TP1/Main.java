package TP1;


import java.util.concurrent.TimeUnit;

public class Main {
    private static final int numberOfLoaders = 2;
    private static final int numberOfImprovers = 3;
    private static final int numberOfResizers = 3;
    private static final int numberOfClones = 2;
    private static final int numberOfExecutions = 1;
    private static final int targetAmountOfData = 100;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

      TP1.Log.clearFile();


    //    for (int j = 0; j < numberOfExecutions; j++) {
            InitContainer initContainer = new InitContainer(targetAmountOfData);
            FinalContainer finalContainer = new FinalContainer();

            Loader[] loaders = new Loader[numberOfLoaders];
            Cloner[] cloners = new Cloner[numberOfClones];
            Resizer[] resizers = new Resizer[numberOfResizers];
            Improver[] improvers = new Improver[numberOfImprovers];

            Thread[] loadersThreads = new Thread[numberOfLoaders];
            Thread[] improversThreads = new Thread[numberOfImprovers];
            Thread[] resizerThreads = new Thread[numberOfResizers];
            Thread[] clonersThreads = new Thread[numberOfClones];

            TP1.Log log = new TP1.Log(initContainer, finalContainer, loaders, improvers, resizers, cloners , loadersThreads, improversThreads, resizerThreads);

        for (int i = 0; i < numberOfLoaders; i++) {
            loaders[i] = new Loader(initContainer, "TP1.Loader " + i);
            loadersThreads[i] = new Thread(loaders[i]);
            loadersThreads[i].setName(loaders[i].getName() + " (Thread ID: " + loadersThreads[i].getId() + ")");
        }

        for (int i = 0; i < numberOfImprovers; i++) {
                improvers[i] = new Improver(initContainer, "TP1.Improver " + i,numberOfImprovers);
                improversThreads[i] = new Thread(improvers[i]);
                improversThreads[i].setName(improvers[i].getName() + " (Thread ID: " + improversThreads[i].getId() + ")");
            }

        for (int i = 0; i < numberOfResizers; i++) {
            resizers[i] = new Resizer(initContainer, "TP1.Resizer " + i);
            resizerThreads[i] = new Thread(resizers[i]);
            resizerThreads[i].setName(resizers[i].getName() + " (Thread ID: " + resizerThreads[i].getId() + ")");
        }


            for (int i = 0; i < numberOfClones; i++) {
                cloners[i] = new Cloner(initContainer, finalContainer , "TP1.Cloner " + i);
                clonersThreads[i] = new Thread(cloners[i]);
                clonersThreads[i].setName(cloners[i].getName() + " (Thread ID: " + clonersThreads[i].getId() + ")");
            }


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
            log.start();

            try {
                for (Thread  waitting :  loadersThreads) {
                    waitting.join();
                }
                for (Thread  waitting :  improversThreads) {
                    waitting.join();
                }
                for (Thread  waitting :  resizerThreads) {
                    waitting.join();
                }
                for (Thread  waitting : clonersThreads) {
                    waitting.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.interrupt();

    long finish = System.currentTimeMillis();
    long timeElapsed = finish - start;
    System.out.println("Tiempo: " + (float)(timeElapsed/1000.00) + " segundos");

        }

   // }
}
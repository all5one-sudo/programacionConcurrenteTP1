package TP1;

import TP1.FinalContainer;
import TP1.InitContainer;
import TP1.Loader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Log extends Thread {

    private final Date initTime;
    private final InitContainer initContainer;
    private final FinalContainer finalContainer;

    private final Loader[] loaders;
    private final Improver[] improvers;
    private final Resizer[] resizers;

    private final Cloner[] cloners;

    private final Thread[] loadersThreads;
    private final Thread[] improversThreads;
    private final Thread[] resizersThreads;

    private final Thread[] clonersThreads;

    public static void clearFile() {
        try {
            PrintWriter pw_log = new PrintWriter(".\\data\\log.txt");
            pw_log.print("");
            pw_log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Log(InitContainer initContainer,
               FinalContainer finalContainer,
               Loader[] loaders,
               Improver[] improvers,
               Resizer[] resizers,
               Cloner[] cloners,
               Thread[] loadersThreads,
               Thread[] improversThreads,
               Thread[] resizersThreads ,
               Thread[] clonersThreads) {
        this.initContainer = initContainer;
        this.finalContainer = finalContainer;
        this.improvers = improvers;
        this.loaders = loaders;
        this.resizers = resizers;
        this.cloners = cloners;
        this.improversThreads = improversThreads;
        this.resizersThreads = resizersThreads;
        this.clonersThreads = clonersThreads;
        this.loadersThreads = loadersThreads;

        initTime = new Date();
    }

    @Override
    public void run() {
        while(true) {
            try {
                writeLog();
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                writeLog();
                break;
            }
        }
    }

    private void writeLog() {
        try {
            PrintWriter pw_log = new PrintWriter(new FileWriter(".\\data\\log.txt", true));

            pw_log.print("*-------------------------------------------------------------------------------*\n");
            pw_log.printf("Execution time: %f [Seg]\n", (float)(new Date().getTime() - initTime.getTime()) / 1000);
            pw_log.printf("InitBuffer size: %d\n", initContainer.getSize());
            pw_log.printf("finalContainer size: %d\n", finalContainer.getSize());
            pw_log.print("*-------------------------------------------------------------------------------*\n\n");

            Loader[] loadersCopy = loaders;
            Improver[] improversCopy = improvers;
            Resizer[] resizersCopy = resizers;
            Cloner[] clonersCopy = cloners;

            int totalImageLoad = 0;
            for (Loader load: loadersCopy) {
               // totalDataLost += loader.getDataLost();
                totalImageLoad += load.getImageLoad();
            }

            pw_log.println("      Total loaders:");
            pw_log.printf("       Loaded images: %d\n", totalImageLoad);
            pw_log.printf("       Loaders: ");
            for (Loader loader: loadersCopy) {
                pw_log.printf("   %s:\n", loader.getName());
            }


            pw_log.println("   Improve Totals:");
            pw_log.printf("       data revised: %d\n", totalDataRevised);
            pw_log.printf("       data copied: %d\n\n", totalDataMoved);

            for (Resizer resizer: resizersCopy) {
                pw_log.printf("   %s:\n", resizer.getName());
                pw_log.printf("      resized images: %d\n", resizer.getTotalImagesResized());
                pw_log.printf("      data copied: %d\n", reviewer.getDataMoved());

                pw_log.printf("      responsibility percentage in copied data: %f %%\n\n", 100 * (float) reviewer.getDataMoved() / totalDataMoved);
            }

            int totalDataConsumed = 0;
            for (Consumer consumer: consumersCopy) {
                totalDataConsumed += consumer.getDataConsumed();
            }

            pw_log.println("   Consumers Totals:");
            pw_log.printf("       data taken: %d\n\n", totalDataConsumed);

            for (Consumer consumer: consumersCopy) {
                pw_log.printf("   %s:\n", consumer.getName());
                pw_log.printf("      data taken: %d\n", consumer.getDataConsumed());

                pw_log.printf("      responsibility percentage in taken data: %f %%\n\n", 100 * (float) consumer.getDataConsumed() / totalDataConsumed);
            }

            pw_log.println("   Threads State:");

            for (Thread loaderThread: loadersThreads) {
                pw_log.printf("      %s: %s\n", loaderThread.getName(), loaderThread.getState().name());
            }

            pw_log.println();

            for (Thread consumerThread: consumersThreads) {
                pw_log.printf("      %s: %s\n", consumerThread.getName(), consumerThread.getState().name());
            }

            pw_log.println();

            for (Thread reviewerThread: reviewersThreads) {
                pw_log.printf("      %s: %s\n", reviewerThread.getName(), reviewerThread.getState().name());
            }

            pw_log.println();

            pw_log.print("*-------------------------------------------------------------------------------*\n\n");

            pw_log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

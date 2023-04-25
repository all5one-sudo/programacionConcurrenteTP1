package TP1;
/*
El sistema debe contar con un LOG con fines estadísticos, el cual registre cada 500
milisegundos en un archivo:
- Cantidad de imágenes insertadas en el contenedor.
- Cantidad de imágenes mejoradas completamente.
- Cantidad de imágenes ajustadas.
- Cantidad de imágenes que han finalizado el último proceso.
- El estado de cada hilo del sistema.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Log extends Thread {
    private final int targetAmountOfData;
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
            PrintWriter pw_log = new PrintWriter(".//Estadistica.txt");
            pw_log.print("");
            pw_log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Log(int targetAmountOfData, InitContainer initContainer,
            FinalContainer finalContainer,
            Loader[] loaders,
            Improver[] improvers,
            Resizer[] resizers,
            Cloner[] cloners,
            Thread[] loadersThreads,
            Thread[] improversThreads,
            Thread[] resizersThreads,
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
        this.targetAmountOfData = targetAmountOfData;
        initTime = new Date();
    }

    @Override
    public void run() {
        while (finalContainer.getSize() <= targetAmountOfData) {
            try {
                writeLog();
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                writeLog();
                break;
            }
        }
    }

    private void writeLog() {
        try {
            PrintWriter pw_log = new PrintWriter(new FileWriter(".//Estadistica.txt", true));
            pw_log.print("*-------------------------------------------------------------------------------*\n");
            pw_log.printf("Execution time: %.3f [Seg]\n", (float) (new Date().getTime() - initTime.getTime()) / 1000);
            pw_log.printf("InitContainer size at this moment: %d\n", initContainer.getSize());
            pw_log.printf("InitContainer size: %d\n", initContainer.getAmountOfImages());
            pw_log.printf("finalContainer size: %d\n", finalContainer.getSize());
            pw_log.print("*-------------------------------------------------------------------------------*\n\n");
            Loader[] loadersCopy = loaders;
            Improver[] improversCopy = improvers;
            Resizer[] resizersCopy = resizers;
            Cloner[] clonersCopy = cloners;

            /////////////////// LOADERS ///////////////////////////////

            int totalImageLoad = 0;
            for (Loader load : loadersCopy) {
                totalImageLoad += load.getImageLoad();
            }
            pw_log.println("   Total loaders:\n");
            pw_log.printf("      Loaded images: %d\n", totalImageLoad);
            pw_log.println("");
            pw_log.printf("      Loaders: \n");
            for (Loader loader : loadersCopy) {
                pw_log.printf("         %s:\n", loader.getName());
                pw_log.printf("            loaded images: %d\n", loader.getImageLoad());
            }
            pw_log.println("");

            /**
             * ////////// IMPROVERS //////////
             */

            int totalImageImproved = 0;
            for (Improver improver : improversCopy) {
                totalImageImproved += improver.getTotalImagesImprovedByThread();
            }
            pw_log.println("   Total Improvers:\n");
            pw_log.printf("      Improved images: %d\n", totalImageImproved);
            pw_log.println("");
            pw_log.printf("      Improvers: \n");
            for (Improver improver : improversCopy) {
                pw_log.printf("         %s:\n", improver.getName());
                pw_log.printf("            improved images: %d\n", improver.getTotalImagesImprovedByThread());
            }
            pw_log.println("");

            /**
             * ////////// RESIZERS //////////
             */

            int totalImageResized = 0;
            for (Resizer resizer : resizersCopy) {
                totalImageResized += resizer.getTotalImagesResized();

            }
            pw_log.println("   Total Resizers:");
            pw_log.println("");
            pw_log.printf("      Resized images: %d\n", totalImageResized);
            pw_log.println("");
            pw_log.printf("      Resizers: \n");
            for (Resizer resizer : resizersCopy) {
                pw_log.printf("         %s:\n", resizer.getName());
                pw_log.printf("            resized images: %d\n", resizer.getTotalImagesResized());
                pw_log.printf("            responsibility percentage in copied data over target data: %.2f %%\n",
                        100 * (float) resizer.getTotalImagesResized() / totalImageLoad);
            }
            pw_log.println("");

            /////////////////// CLONER ///////////////////////////////

            int totalImagesCloned = 0;
            pw_log.println("   Total Cloners:\n");
            for (Cloner cloner : clonersCopy) {
                totalImagesCloned += cloner.getImageCloned();
            }
            pw_log.printf("      Cloned images: %d\n", totalImagesCloned);
            pw_log.println("");
            pw_log.printf("      Cloners: \n");
            for (Cloner cloner : clonersCopy) {
                pw_log.printf("         %s:\n", cloner.getName());
                pw_log.printf("            cloned images: %d\n", cloner.getImageCloned());
                pw_log.printf("            responsibility percentage in taken data over target data: %.2f %%\n",
                        100 * (float) cloner.getImageCloned() / totalImageLoad);
            }
            pw_log.println("");

            /////////////////// Estados de los Hilos ///////////////////////////////

            pw_log.println("   Threads State:\n");

            for (Thread loaderThread : loadersThreads) {
                pw_log.printf("      %s: %s\n", loaderThread.getName(), loaderThread.getState().name());
            }

            pw_log.println();

            for (Thread improverThread : improversThreads) {
                pw_log.printf("      %s: %s\n", improverThread.getName(), improverThread.getState().name());
            }

            pw_log.println();

            for (Thread resizerThread : resizersThreads) {
                pw_log.printf("      %s: %s\n", resizerThread.getName(), resizerThread.getState().name());
            }

            pw_log.println();

            for (Thread clonerThread : clonersThreads) {
                pw_log.printf("      %s: %s\n", clonerThread.getName(), clonerThread.getState().name());
            }

            pw_log.println();
            pw_log.print("*-------------------------------------------------------------------------------*\n\n");
            pw_log.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

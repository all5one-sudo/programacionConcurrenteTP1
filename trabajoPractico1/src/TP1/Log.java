package TP1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

// Clase que crea el Log estadístico para la ejecución del programa
public class Log extends Thread {
    
    private final int targetAmountOfData; // Objetivo de imágenes
    private final Date initTime; // Fecha inicial
    private final InitContainer initContainer; // Contenedor inicial
    private final FinalContainer finalContainer; // Contenedor final

    // Hilos en arreglos
    private final Loader[] loaders;
    private final Improver[] improvers;
    private final Resizer[] resizers;

    private final Cloner[] cloners;

    private final Thread[] loadersThreads;
    private final Thread[] improversThreads;
    private final Thread[] resizersThreads;

    private final Thread[] clonersThreads;

    // Método que crea un archivo txt limpio
    public static void clearFile() {
        try {
            PrintWriter pw_log = new PrintWriter(".//Estadistica.txt");
            pw_log.print("");
            pw_log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructor
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

    // Sobreescritura del método run()
    @Override
    public void run() {
        while (finalContainer.getSize() <= targetAmountOfData) {
            try {
                writeLog();
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                writeLog();// ?
                break;
            }
        }
    }

    // Se escribe el Log
    private void writeLog() {
        try {
            PrintWriter pw_log = new PrintWriter(new FileWriter(".//Estadistica.txt", true));
            pw_log.print("*-------------------------------------------------------------------------------*\n");
            pw_log.printf("Execution time: %.3f [Seg]\n", (float) (new Date().getTime() - initTime.getTime()) / 1000);
            pw_log.printf("InitContainer size at this moment: %d\n", initContainer.getSize());
            pw_log.printf("InitContainer size: %d\n", initContainer.getAmountOfImages());
            pw_log.printf("FinalContainer size: %d\n", finalContainer.getSize());
            pw_log.print("*-------------------------------------------------------------------------------*\n\n");
            Loader[] loadersCopy = loaders;
            Improver[] improversCopy = improvers;
            Resizer[] resizersCopy = resizers;
            Cloner[] clonersCopy = cloners;

            /////////////////// LOADERS ///////////////////////////////

            int totalLoadedImages = 0;
            for (Loader load : loadersCopy) {
                totalLoadedImages += load.getLoadedImages();
            }
            pw_log.println("   Total loaders:\n");
            pw_log.printf("      Loaded images: %d\n", totalLoadedImages);
            pw_log.println("");
            pw_log.printf("      Loaders: \n");
            for (Loader loader : loadersCopy) {
                pw_log.printf("         %s:\n", loader.getName());
                pw_log.printf("            loaded images: %d\n", loader.getLoadedImages());
            }
            pw_log.println("");

            ////////////////// IMPROVERS ////////////////////////////

            int totalImprovedImages = 0;
            for (Improver improver : improversCopy) {
                totalImprovedImages += improver.getTotalImagesImprovedByThread();
            }
            pw_log.println("   Total Improvers:\n");
            pw_log.printf("      Improved images: %d\n", totalImprovedImages);
            pw_log.println("");
            pw_log.printf("      Improvers: \n");
            for (Improver improver : improversCopy) {
                pw_log.printf("         %s:\n", improver.getName());
                pw_log.printf("            improved images: %d\n", improver.getTotalImagesImprovedByThread());
            }
            pw_log.println("");

            ///////////////// RESIZERS ////////////////////////////////

            int totalResizedImages = 0;
            for (Resizer resizer : resizersCopy) {
                totalResizedImages += resizer.getTotalImagesResized();
            }
            pw_log.println("   Total Resizers:");
            pw_log.println("");
            pw_log.printf("      Resized images: %d\n", totalResizedImages);
            pw_log.println("");
            pw_log.printf("      Resizers: \n");
            for (Resizer resizer : resizersCopy) {
                pw_log.printf("         %s:\n", resizer.getName());
                pw_log.printf("            resized images: %d\n", resizer.getTotalImagesResized());
                pw_log.printf("            responsibility percentage in copied data over target data: %.2f %%\n",
                        100 * (float) resizer.getTotalImagesResized() / totalLoadedImages);
            }
            pw_log.println("");

            /////////////////// CLONERS ///////////////////////////////

            int totalClonedImages = 0;
            pw_log.println("   Total Cloners:\n");
            for (Cloner cloner : clonersCopy) {
                totalClonedImages += cloner.getClonedImages();
            }
            pw_log.printf("      Cloned images: %d\n", totalClonedImages);
            pw_log.println("");
            pw_log.printf("      Cloners: \n");
            for (Cloner cloner : clonersCopy) {
                pw_log.printf("         %s:\n", cloner.getName());
                pw_log.printf("            cloned images: %d\n", cloner.getClonedImages());
                pw_log.printf("            responsibility percentage in taken data over target data: %.2f %%\n",
                        100 * (float) cloner.getClonedImages() / totalLoadedImages);
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

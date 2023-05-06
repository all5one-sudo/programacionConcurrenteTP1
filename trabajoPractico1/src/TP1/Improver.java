package TP1;

import java.util.concurrent.TimeUnit;

// Clase que se encarga de mejorar las imágenes
public class Improver implements Runnable {

    private final InitContainer initContainer; // Contenedor inicial

    private static boolean finishImprove = false; // Booleano para indicar si se completó la mejora

    private int totalImagesImprovedByThread; // Cantidad de imágenes mejoradas por el hilo

    private final String name; // Nombre

    private static int totalImprovedImages = 0; // Imágenes mejoradas por esta clase

    private final int totalThreadsImprovements;

    private Image lastImprovedImage; // Última imagen mejorada

    private final int targetAmountOfData; // Cantidad de imágenes objetivo

    public Improver(InitContainer initContainer, String name, int totalThreadsImprovements, int targetAmountOfData) {
        this.initContainer = initContainer;
        this.name = name;
        this.totalThreadsImprovements = totalThreadsImprovements;
        totalImagesImprovedByThread = 0;
        lastImprovedImage = null;
        this.targetAmountOfData=targetAmountOfData;

    }

    // Sobreescritura del método run()
    @Override
    public void run() {
        while (!finishImprove) {
            try {
                lastImprovedImage = initContainer.getImage();
                if (lastImprovedImage != null) {
                    if (!lastImprovedImage.isImprovedByThread(this)) {
                        System.out.println("Improved image: " + lastImprovedImage.getId() + ", thread: "
                                + Thread.currentThread().getName());
                        lastImprovedImage.improveByThread(this);
                        TimeUnit.MILLISECONDS.sleep(100);
                        totalImagesImprovedByThread++;
                        increaseTotalImprovedImages();
                        if(getTotalImprovedImages() == targetAmountOfData*totalThreadsImprovements){
                            finishImprove=true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getter del name()
    public String getName() {
        return name;
    }

    // Getter de la cantidad de imágenes mejoradas por el proceso (clase)
    public static int getTotalImprovedImages() {
        return totalImprovedImages;
    }

    // Método que incrementa la cantidad de imágenes mejoradas por el proceso (clase)
    public synchronized static void increaseTotalImprovedImages(){
        totalImprovedImages++;
    }

    // Getter de la cantidad de mejoras hechas
    public int getTotalThreadsImprovements() {
        return totalThreadsImprovements;
    }

    // Getter de la cantidad de imágenes mejoradas por el hilo
    public int getTotalImagesImprovedByThread() {
        return totalImagesImprovedByThread;
    }
}

package TP1;

import java.util.concurrent.TimeUnit;

// Clase encargada de reescalar las imágenes
public class Resizer implements Runnable {

    private final InitContainer initContainer; // Contenedor inicial

    private final String name; // Nombre del resizer

    private Image lastImageResized; // Última imagen reescalada

    private int totalImagesResized; // Cantidad de imágenes reescaladas por el hilo

    private static int totalResizedImages = 0; // Cantidad de imágenes reescaladas por el proceso

    private static boolean finishResized = false; // Flag que indica si el proceso de reescalado terminó

    private final int targetAmountOfData; // Cantidad objetivo de imágenes

    // Método que reescala las imágenes
    public Resizer(InitContainer initContainer, String name, int targetAmountOfData) {
        this.initContainer = initContainer;
        this.name = name;
        lastImageResized = null;
        totalImagesResized = 0;
        this.targetAmountOfData = targetAmountOfData;
    }

    // Getter de cantidad de imágenes reescaladas
    public int getTotalImagesResized() {
        return totalImagesResized;
    }

    // Se sobreescribe el run()
    @Override
    public void run() {
        while (!finishResized) {
            try {
                lastImageResized = initContainer.getImage();
                if (lastImageResized != null) {
                    if (lastImageResized.getAmIImproved()) {
                        if (lastImageResized.resize()) {
                            System.out.println("Image: " + getlastImageResized().getId() + " resized by: "
                                    + Thread.currentThread().getName());
                            increaseImageResizer();
                            TimeUnit.MILLISECONDS.sleep(100);
                            increaseTotalResizedImages();
                            if (totalResizedImages == targetAmountOfData) {
                                finishResized = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getter del String name
    public String getName() {
        return name;
    }

    // Incrementador de imágenes reescaladas por el hilo
    public void increaseImageResizer() {
        totalImagesResized++;
    }

    // Incrementador de imágenes reescaladas por el proceso
    public synchronized static void increaseTotalResizedImages() {
        totalResizedImages++;
    }

    // Getter de la última imagen reescalada
    public Image getlastImageResized() {
        return lastImageResized;
    }

}
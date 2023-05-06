package TP1;

import java.util.concurrent.TimeUnit;

// Clase que clona las imágenes y las carga al contenedor final
public class Cloner implements Runnable {

    private final InitContainer initContainer; // Contenedor inicial

    private final FinalContainer finalContainer; // Contenedor final

    private final String name; // Nombre del cloner

    private Image lastClonedImage; // Última imagen clonada

    private int clonedImages; // Cantidad de imágenes clonadas

    // Constructor
    public Cloner(InitContainer initContainer, FinalContainer finalContainer, String name) {
        this.initContainer = initContainer;
        this.finalContainer = finalContainer;
        this.name = name;
        lastClonedImage = null;
        clonedImages = 0;
    }

    // Sobreescritura del método run()
    @Override
    public void run() {
        while (initContainer.getSize() > 0 || initContainer.isLoadNotCompleted()) {
            try {
                lastClonedImage = initContainer.getImage();
                if (lastClonedImage != null) {
                    if (lastClonedImage.isResized()) {
                        if (!lastClonedImage.amIDeletedFromInitContainer()) {
                            if (lastClonedImage.tryCloneToFinalContainer()) {
                                if (finalContainer.Clone(initContainer.copyAndDelete(lastClonedImage), this)) {
                                    lastClonedImage.setClonedToFinalContainer(true);
                                    increaseImageClone();
                                    TimeUnit.MILLISECONDS.sleep(50);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getter del campo name
    public String getName() {
        return name;
    }

    // Getter de la cantidad de imágenes clonadas
    public int getClonedImages() {
        return clonedImages;
    }

    // Método que incrementa la cantidad de imágenes clonadas
    public void increaseImageClone() {
        clonedImages++;
    }

}

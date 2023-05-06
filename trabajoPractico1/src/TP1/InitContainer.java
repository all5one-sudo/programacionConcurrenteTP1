package TP1;

import java.util.Random;

// Contenedor inicial del proceso
public class InitContainer extends Container {
    private boolean loadCompleted; // Boolean que indica si la carga de imágenes está completa
    private final int targetAmountOfImages; // Cantidad objetivo de imágenes
    private int amountOfImages; // Cantidad actual de imágenes

    private final Object keyLoad = new Object(); // Llave para sincronismo en la carga

    public InitContainer(int targetAmountOfImages) {
        loadCompleted = false;
        this.targetAmountOfImages = targetAmountOfImages;
        this.amountOfImages = 0;
    }

    // Método de carga de imágenes
    public boolean load(Image image, Loader loader, int amount) throws FullContainerException {
        synchronized (keyLoad) {
            if (!loadCompleted) {
                container.addLast(image);
                amountOfImages++;
                System.out.println("Loaded image: " + image.getId());
                if (amountOfImages == targetAmountOfImages) {
                    loadCompleted = true;
                    loader.setLoadedImages(amount + 1);
                }
                else if (amountOfImages > targetAmountOfImages) {
                    throw new FullContainerException("Contenedor Excedido");
                }
            }
            return loadCompleted;
        }
    }

    // Método que retorna una imagen aleatoria del contenedor
    public synchronized Image getImage() {
        if (container.size() > 0) {
            int aux = new Random().nextInt(container.size());
            return container.get(aux);
        } else {
            return null;
        }
    }

    // Getter que permite ver si la carga no está completa
    public boolean isLoadNotCompleted() {
        return !loadCompleted;
    }

    // Método para copiar y eliminar imagen de este contenedor
    public synchronized Image copyAndDelete(Image image) {
            if (container.size() > 0 && image.amIDeletedFromInitContainer()) {
                Image forClone = new Image(image.getImprovements(), image.getAmIResized(), image.getId(), true,
                        image.getAmIImproved());
                this.container.remove(image);
                System.out.printf("Imagen copiada y borrada del contenedor inicial: " + forClone.getId() + "\n");
                return forClone;
            } else {
                return null;
            }
    }

    // Getter de la cantidad de imágenes en el contenedor
    public int getAmountOfImages() {
        return amountOfImages;
    }
}

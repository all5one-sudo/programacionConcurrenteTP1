package TP1;

// Clase contenedor final
public class FinalContainer extends Container {

    private boolean cloneCompleted; // Boolean que representa la compleción de la carga

    private final int targetAmountOfImages; // Cantidad objetivo de imágenes para guardar en el contenedor

    private int amountOfImages; // Cantidad de imágenes

    // Constructor
    public FinalContainer(int targetAmountOfImages) {
        this.amountOfImages = 0;
        this.targetAmountOfImages = targetAmountOfImages;
        cloneCompleted = false;
    }

    // Método que clona las imágenes del contenedor inicial al final
    public synchronized boolean Clone(Image image, Cloner cloner) throws FullContainerException {
        try {
            if (!cloneCompleted && image != null && image.isResized()) {
                this.container.addLast(image);
                amountOfImages++;
                System.out.printf("[FinalContainer (Size: %d)] %s Image cloned <ID: %d \n",
                        this.container.size(),
                        Thread.currentThread().getName(), image.getId());
                if (amountOfImages == targetAmountOfImages) {
                    cloneCompleted = true;
                    cloner.increaseImageClone();
                } else if (amountOfImages > targetAmountOfImages) {
                    throw new FullContainerException("Contenedor Excedido");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !cloneCompleted;
    }
}
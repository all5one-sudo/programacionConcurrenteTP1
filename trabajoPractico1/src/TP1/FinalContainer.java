package TP1;

public class FinalContainer extends Container {

    private boolean cloneCompleted;
    private int targetAmountOfImages; // cantidad Max de imagenes a trabajar
    private int amountOfImages; // cantidad de imagenes actual

    public FinalContainer(int targetAmountOfImages) {
        this.amountOfImages = 0;
        this.targetAmountOfImages = targetAmountOfImages;
        cloneCompleted = false;

    }

    public boolean Clone(Image image, Cloner cloner, int cantidad) throws InterruptedException {
        lock.lock();
        try {
            if (!cloneCompleted && image != null && image.isResized()) { // agregamos esto pq el return null de
                                                                         // DopyandDelete nos perjudicaba
                this.container.addLast(image);
                amountOfImages++;
                System.out.printf("[FinalContainer (Size: %d)] %s Image cloned <ID: %d \n", this.container.size(),
                        Thread.currentThread().getName(), image.getId());
                if (amountOfImages == targetAmountOfImages) {
                    cloneCompleted = true;
                    cloner.increaseImageClone();
                    throw new InterruptedException("Contenedor final lleno");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Imagen a clonar nula");
        } finally {
            lock.unlock();
        }
        return !cloneCompleted;
    }
}

package TP1;

public class FinalContainer extends Container {

    private boolean cloneCompleted;
    private int targetAmountOfImages;   //cantidad Max de imagenes a trabajar
    private int amountOfImages;      //cantidad de imagenes actual



    public FinalContainer(int targetAmountOfImages) {
        this.amountOfImages = 0;
        this.targetAmountOfImages = targetAmountOfImages;
        cloneCompleted = false;


    }


    public boolean Clone(Image image, Cloner cloner,int cantidad) throws InterruptedException {
        lock.writeLock().lock();

        try {

            if(!cloneCompleted && image != null && image.isResized()) {  // agregamos esto pq el return null de DopyandDelete nos perjudicaba

                this.container.addLast(image);

                amountOfImages++;

                System.out.printf("[FinalContainer (Size: %d)] %s Image clone <ID: %d \n", this.container.size(), Thread.currentThread().getName(), image.getId());

                if(amountOfImages == targetAmountOfImages) {
                    System.out.println("LEle entre"+ amountOfImages);
                    cloneCompleted = true;
                    cloner.increaseImageClone();
                }


            }

        }
        catch (NullPointerException e) {
            System.out.println("Imagen a clonar nula!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        finally {
            lock.writeLock().unlock();
        }

        return  !cloneCompleted;

    }


    public boolean isNotReviewCompleted() {
        lock.readLock().lock();

        try {
            return !cloneCompleted;
        } finally {
            lock.readLock().unlock();
        }
    }


}







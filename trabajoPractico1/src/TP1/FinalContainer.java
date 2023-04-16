package TP1;

public class FinalContainer extends Container {

    private boolean cloneCompleted;
    private int targetAmountOfImages;   //cantidad Max de imagenes a trabajar
    private int amountOfImages;      //cantidad de imagenes actual


    public FinalContainer() {
        cloneCompleted = false;
    }


    public void Clone(Image image) throws InterruptedException {
        lock.writeLock().lock();

        try {

            if(!cloneCompleted) {
                this.container.addLast(image);

                amountOfImages++;

                if(amountOfImages == targetAmountOfImages) {
                    cloneCompleted = true;
                }

                System.out.printf("[FinalContainer (Size: %d)] %s Image clone <ID: %d - Value: %s>\n", this.container.size(), Thread.currentThread().getName(), image.getId(), image.getValue());

            }
        } finally {
            lock.writeLock().unlock();
        }
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







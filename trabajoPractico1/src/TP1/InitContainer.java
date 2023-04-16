package TP1;

import java.util.Random;

public class InitContainer extends  Container {
    private boolean loadCompleted;
    private int targetAmountOfImages;   //cantidad Max de imagenes a crear
    private int amountOfImages;      //cantidad de imagenes actual

    public InitContainer(int targetAmountOfImages) {
        loadCompleted = false ;
        this.targetAmountOfImages = targetAmountOfImages;
        this.amountOfImages = 0;
    }

    public void load(Image image) throws InterruptedException, FullContainerException {
        lock.writeLock().lock();
        try {
            if (!loadCompleted) {
                container.addLast(image);
                amountOfImages++;
                System.out.printf("[InitContainer (Size: %d)] %s image load <ID>: %d \n", this.container.size(), Thread.currentThread().getName(), image.getId());
                if(amountOfImages == targetAmountOfImages) {
                    loadCompleted = true;
                }
            }
            else {
                throw new FullContainerException("Full container");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Image getImage (Image last) throws InterruptedException {
        lock.readLock().lock();

        try {
            if (container.size() > 0) {
                try {
                    return container.get(container.indexOf(last)+1);  //SACAMOS EL +1
                } catch (IndexOutOfBoundsException e) {
                    return container.get(new Random().nextInt(container.size()));
                }
            } else {
                return null;
            }
        } finally {
            lock.readLock().unlock();
        }

    }

    public void remove(Image image) {
        lock.writeLock().lock();
        try {
            if(this.container.remove(image)) {
                System.out.printf("[Initcontainer (Size: %d)] %s data removed <ID: %d - Value: %s>\n", this.container.size() , Thread.currentThread().getName(), image.getId());

            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isNotLoadCompleted() {
        lock.readLock().lock();     //

        try {
            return !loadCompleted;
        } finally {
            lock.readLock().unlock();
        }
    }

    public Image CopyAndDeleted(Image image) throws InterruptedException{
        lock.writeLock().lock();

        try {
            if (container.size() > 0) {


                Image forClone = new Image(image.getImprovements(),image.getAmIResized(),image.getId(), true,image.getAmIImproved());

                this.container.remove(image);

                System.out.printf("[InitContainer (Size: %d)] %s Image removed and Cloned <ID: %d \n", this.container.size(), Thread.currentThread().getName(),image.getId());

                return forClone;
            } else {
                return null;
            }
        }
        finally {
            lock.writeLock().unlock();
        }
    }


}














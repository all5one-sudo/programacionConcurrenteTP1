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
                System.out.printf("[InitContainer (Size: %d)] %s image load <ID: %d - Imagen: %s>\n", this.container.size(), Thread.currentThread().getName(), image.getId());
                if(amountOfImages == targetAmountOfImages) {
                    loadCompleted = true;
                }
            }
            else {
                throw new FullContainerException("Full container");
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public Image getImage (Image last) throws InterruptedException {
        lock.readLock().lock();
        try {
            if (container.size() > 0) {
                try {
                    return container.get(container.indexOf(last) + 1);
                }
                catch (IndexOutOfBoundsException e) {
                    return container.get(new Random().nextInt(container.size())); //SACA IMAGEN RANDOM DEL CONTAINER
                }
            }
            else {
                return null;
            }
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public void remove(Image image) {
        lock.writeLock().lock();
        try {
            if(this.container.remove(image)) {
                System.out.printf("[InitBuffer (Size: %d)] %s data removed <ID: %d - Value: %s>\n", this.container.size() , Thread.currentThread().getName(), image.getId());

            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isNotLoadCompleted() {
        lock.readLock().lock();

        try {
            return !loadCompleted;
        } finally {
            lock.readLock().unlock();
        }
    }

    public Image poll() throws InterruptedException{
        lock.writeLock().lock();

        try {

            if (container.size() > 0) {
                Image value = this.container.poll();

                System.out.printf("[InitContainer (Size: %d)] %s Image removed and Cloned <ID: %d - Value: %s>\n", this.container.size(), Thread.currentThread().getName(), value.getId());

                return value;
            } else {
                return null;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }


}














package TP1;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Image {

    private final ReadWriteLock lock;
    private final List<Improver> improvements;
    private final int id;
    private static int generator = 0;
    private static final Object key = new Object();
    private boolean resized;
    private boolean clonedToFinalContainer;

    private boolean iamImproved;

    private static int newId() {
        synchronized (key) {
            return generator++;
        }
    }

    public Image() {
        improvements = new ArrayList<>();
        resized = false;
        id = newId();
        lock = new ReentrantReadWriteLock(false); //  no hay fairness  ; no es necesario que tenga false
        clonedToFinalContainer = false;
        iamImproved=false;
    }

    public ReadWriteLock getLock() {
        return lock;
    }

    public List<Improver> getImprovements() {
        return improvements;
    }

    public static int getGenerator() {
        return generator;
    }

    public static void setGenerator(int generator) {
        Image.generator = generator;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public void setClonedToFinalContainer(boolean clonedToFinalContainer) {
        this.clonedToFinalContainer = clonedToFinalContainer;
    }

    public boolean isIamImproved() {
        return iamImproved;
    }

    public void setIamImproved(boolean iamImproved) {
        this.iamImproved = iamImproved;
    }

    public Image(List<Improver> improvements, boolean resized, int id, boolean clonedToFinalContainer, boolean iamImproved) {      //es solamente para el Clone
        this.improvements = improvements;
        this.resized = resized;
        this.id = id;
        lock = new ReentrantReadWriteLock(false); //  no hay fairness  ; no es necesario que tenga false
        this.clonedToFinalContainer = clonedToFinalContainer;
        this.iamImproved = iamImproved;
    }

    public void improve(Improver improver) {
        lock.writeLock().lock();
        try {
            improvements.add(improver);
            System.out.printf("[InitContainer] %s: improved image quality <ID: %d\n", Thread.currentThread().getName(), id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isImproved(Improver improver) {
        lock.readLock().lock();
        try {
            return improvements.contains(improver);
        } finally {
            lock.readLock().unlock();
        }
    }


    public int getNumberOfImprover() {
        lock.readLock().lock();
        try {
            return improvements.size();
        }
        finally {
            lock.readLock().unlock();
        }
    }


    public void resize() {
        lock.writeLock().lock();
        try {
            this.resized = true;
         //   System.out.printf("[InitContainer] %s: resize image quality <ID: %d - Image: %s>\n", Thread.currentThread().getName(), id);
        } finally {
            lock.writeLock().unlock();
        }
    }



    public boolean isClonedToFinalContainer() {
        lock.readLock().lock();
        try {
            return clonedToFinalContainer;
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public boolean tryClonedToFinalContainer() {
        lock.writeLock().lock();
        try {
            if(!clonedToFinalContainer) {
                clonedToFinalContainer = true;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getId() {
        return id;
    }


    public void setIamImprove(){
       iamImproved=true;

    }

    public boolean isResized(){
         return resized;
    }

    public boolean getAmIImproved(){
        return iamImproved;
    }

    public boolean getAmIResized(){
        return resized;
    }
}















    

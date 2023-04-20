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

    private boolean IamDeletefromInitContainer;

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
        IamDeletefromInitContainer=false;
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

    public boolean isIamDeletefromInitContainer() {
        return IamDeletefromInitContainer;
    }

    public boolean improve(Improver improver) {
        lock.writeLock().lock();
        try {
            improvements.add(improver);

            if ( improvements.size() == improver.getTotalThreadsImprovements()) {
                this.setIamImprove();
               return true;
            }
            else{return false;}

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


    public boolean resize() {
        lock.writeLock().lock();
        try {
            if(!isResized()) {
                resized = true;

                return true;
            } else {
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void setIamDeletefromInitContainer() {
        IamDeletefromInitContainer = false;
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
        lock.readLock().lock();
        try {
            return resized;
        }
        finally {
            lock.readLock().unlock();
        }


    }

    public boolean tryCloneToFinalContainer(){
        lock.writeLock().lock();
        try {
            if(!IamDeletefromInitContainer){
                IamDeletefromInitContainer=true;

                return true;
            }else {
                return false;
            }

        }finally {
            lock.writeLock().unlock();        }

    }




    public boolean getAmIImproved(){
        return iamImproved;
    }

    public boolean getAmIResized(){
        return resized;
    }
}















    

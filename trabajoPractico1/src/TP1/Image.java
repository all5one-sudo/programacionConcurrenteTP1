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

    private static int newId() {
        synchronized (key) {
            return generator++;
        }
    }

    public Image() {
        resized = false;
        id = newId();
        lock = new ReentrantReadWriteLock(false); //  no hay fairness  ; no es necesario que tenga false
        clonedToFinalContainer = false;
        improvements = null;
    }

    public void improve(Improver improver) {
        lock.writeLock().lock();
        try {
            improvements.add(improver);
            System.out.printf("[InitContainer] %s: improved image quality <ID: %d - Image: %s>\n", Thread.currentThread().getName(), id);
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
    
} //LO DEJAMOS AQUI















    

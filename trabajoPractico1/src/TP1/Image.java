package TP1;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Image {

    private final Lock lock;
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
        lock = new ReentrantLock(false); // no hay fairness, no es necesario que tenga false
        clonedToFinalContainer = false;
        iamImproved = false;
        IamDeletefromInitContainer = false;
    }

    public Lock getLock() {
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

    public Image(List<Improver> improvements, boolean resized, int id, boolean clonedToFinalContainer,
            boolean iamImproved) { // es solamente para el Clone
        this.improvements = improvements;
        this.resized = resized;
        this.id = id;
        lock = new ReentrantLock(false); // no hay fairness ; no es necesario que tenga false
        this.clonedToFinalContainer = clonedToFinalContainer;
        this.iamImproved = iamImproved;
    }

    public boolean isIamDeletefromInitContainer() {
        return IamDeletefromInitContainer;
    }

    public boolean improve(Improver improver) {
        System.out.println("El hilo improver "+ Thread.currentThread().getName() + "quiere hacer el improve");
        lock.lock();
        try {
            improvements.add(improver);

            if (improvements.size() == improver.getTotalThreadsImprovements()) {
                this.setIamImprove();
                return true;
            } else {
                return false;
            }

        } finally {
            lock.unlock();
        }
    }

    public boolean isImproved(Improver improver) {
        return improvements.contains(improver);
    }

    public boolean resize() {
        lock.lock();
        try {
            if (!isResized()) {
                resized = true;

                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getId() {
        return id;
    }

    public void setIamImprove() {
        iamImproved = true;

    }

    public boolean isResized() {

        return resized;

    }

    public boolean tryCloneToFinalContainer() {
        lock.lock();
        try {
            if (!IamDeletefromInitContainer) {
                IamDeletefromInitContainer = true;

                return true;
            } else {
                return false;
            }

        } finally {
            lock.unlock();
        }

    }

    public boolean getAmIImproved() {
        return iamImproved;
    }

    public boolean getAmIResized() {
        return resized;
    }
}

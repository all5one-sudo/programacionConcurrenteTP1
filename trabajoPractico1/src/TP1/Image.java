package TP1;

import java.util.ArrayList;
import java.util.List;

public class Image {

    private final List<Improver> improvements;
    private final int id;
    private static int generator = 0;
    private static final Object key = new Object();
    private boolean resized;
    private boolean clonedToFinalContainer;

    private static final Object keyImprove = new Object();
    private static final Object keyResize = new Object();
    private static final Object keyClone = new Object();

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
        clonedToFinalContainer = false;
        iamImproved = false;
        IamDeletefromInitContainer = false;
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
            boolean iamImproved) {
        this.improvements = improvements;
        this.resized = resized;
        this.id = id;
        this.clonedToFinalContainer = clonedToFinalContainer;
        this.iamImproved = iamImproved;
    }

    public boolean isIamDeletefromInitContainer() {
        return IamDeletefromInitContainer;
    }

    public boolean improve(Improver improver) {

        synchronized (keyImprove) {
            improvements.add(improver);
            if (improvements.size() == improver.getTotalThreadsImprovements()) {
                this.setIamImprove();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isImproved(Improver improver) {
        return improvements.contains(improver);
    }

    public boolean resize() {
        synchronized (keyResize) {
            if (!isResized()) {
                resized = true;
                return true;
            } else {
                return false;
            }
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
        synchronized (keyClone) {
            if (!IamDeletefromInitContainer) {
                IamDeletefromInitContainer = true;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean getAmIImproved() {
        return iamImproved;
    }

    public boolean getAmIResized() {
        return resized;
    }
}

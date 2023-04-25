package TP1;

import java.util.concurrent.TimeUnit;

public class Improver implements Runnable {

    private final InitContainer initContainer;

    private int totalImprovements;

    private int totalImagesImprovedByThread;

    private final String name;

    private final int totalThreadsImprovements;

    private Image lastImageImprove;

    public Improver(InitContainer initContainer, String name, int totalThreadsImprovements) {
        this.initContainer = initContainer;
        totalImprovements = 0;
        this.name = name;
        this.totalThreadsImprovements = totalThreadsImprovements;
        totalImagesImprovedByThread = 0;
        lastImageImprove = null;
    }

    @Override
    public void run() {
        while (initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {
            try {
                lastImageImprove = initContainer.getImage(lastImageImprove);
                if (lastImageImprove != null) {
                    if (!lastImageImprove.isImproved(this)) {
                        System.out.println("Improved image: " + lastImageImprove.getId() + ", thread: "
                                + Thread.currentThread().getName());
                        increaseImageImprover();
                        lastImageImprove.improve(this);
                        TimeUnit.MILLISECONDS.sleep(100);
                        totalImagesImprovedByThread++;
                    }
                }
            } catch (NullPointerException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getTotalThreadsImprovements() {
        return totalThreadsImprovements;
    }

    public int getTotalImagesImprovedByThread() {
        return totalImagesImprovedByThread;
    }

    public void increaseImageImprover() {
        totalImprovements++;
    }
}

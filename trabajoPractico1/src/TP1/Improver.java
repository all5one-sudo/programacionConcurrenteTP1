package TP1;

import java.util.concurrent.TimeUnit;

public class Improver implements Runnable {

    private final InitContainer initContainer;

    private int totalImprovements;

    private final String name;

    private final  int totalThreadsImprovements;

    private Image lastImageImprove;

    public Improver(InitContainer initContainer, String name , int totalThreadsImprovements) {
        this.initContainer = initContainer;
        totalImprovements=0;
        this.name = name;
        this.totalThreadsImprovements= totalThreadsImprovements;

        lastImageImprove= null;
    }



    @Override
    public void run() {
        while(initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {
            try {
                lastImageImprove = initContainer.getImage( lastImageImprove );

                if ( lastImageImprove  != null) {
                    if (! lastImageImprove .isImproved(this)) {
                         lastImageImprove .improve(this);

                        totalImprovements++;

                        if ( lastImageImprove.getNumberOfImprover() == totalThreadsImprovements) {
                            lastImageImprove.setIamImprove();
                        }
                    }
                    TimeUnit.MILLISECONDS.sleep(2);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    public InitContainer getInitContainer() {
        return initContainer;
    }

    public int getTotalImprovements() {
        return totalImprovements;
    }

    public void setTotalImprovements(int totalImprovements) {
        this.totalImprovements = totalImprovements;
    }

    public String getName() {
        return name;
    }

    public int getTotalThreadsImprovements() {
        return totalThreadsImprovements;
    }

    public Image getLastImageImprove() {
        return lastImageImprove;
    }

    public void setLastImageImprove(Image lastImageImprove) {
        this.lastImageImprove = lastImageImprove;
    }
}







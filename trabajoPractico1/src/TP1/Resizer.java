package TP1;

import java.util.concurrent.TimeUnit;

public class Resizer implements Runnable {

    private final InitContainer initContainer;

    private final String name;

    private Image lastImageResized;

    private int totalImagesResized;

    public Resizer(InitContainer initContainer, String name) {
        this.initContainer = initContainer;
        this.name = name;
        lastImageResized= null;
        totalImagesResized=0;
        lastImageResized= null;
    }


    public Image getLastImageResized() {
        return lastImageResized;
    }

    public void setLastImageResized(Image lastImageResized) {
        this.lastImageResized = lastImageResized;
    }

    public int getTotalImagesResized() {
        return totalImagesResized;
    }

    public void setTotalImagesResized(int totalImagesResized) {
        this.totalImagesResized = totalImagesResized;
    }

    @Override
    public void run() {
        while(initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {  //ver idea de esteban
            try {
                lastImageResized = initContainer.getImage(lastImageResized);

                if ( lastImageResized  != null) {

                    if(lastImageResized.getAmIImproved()) {

                        if (!lastImageResized.isResized()) {
                            lastImageResized.resize();

                            totalImagesResized++;
                            TimeUnit.MILLISECONDS.sleep(2);
                        }
                    }
            }
            }catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }



    public InitContainer getInitContainer() {
        return initContainer;
    }

    public void setTotalImprovements(int totalImprovements) {
        this.totalImagesResized = totalImprovements;
    }

    public String getName() {
        return name;
    }


    public Image getlastImageResized() {
        return lastImageResized;
    }




}
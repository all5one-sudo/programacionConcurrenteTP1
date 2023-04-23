package TP1;

import java.util.concurrent.TimeUnit;

public class Improver implements Runnable {

    private final InitContainer initContainer;

    private int totalImprovements;

    private  int totalImagesImprovedByThread;

    private final String name;

    private final  int totalThreadsImprovements;

    private Image lastImageImprove;


    public Improver(InitContainer initContainer, String name , int totalThreadsImprovements) {
        this.initContainer = initContainer;
        totalImprovements=0;
        this.name = name;
        this.totalThreadsImprovements= totalThreadsImprovements;
        totalImagesImprovedByThread =0;
        lastImageImprove= null;
    }



    @Override
    public void run() {
        while(initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {
            try {
                lastImageImprove = initContainer.getImage( lastImageImprove );
                if ( lastImageImprove  != null) {
                    if (! lastImageImprove .isImproved(this)) {
                       increaseImageImprover();
                        if ( lastImageImprove.improve(this)) {
                            System.out.println("Imagen improved: " + lastImageImprove.getId() +" por hilo: " + Thread.currentThread().getName());
                            totalImagesImprovedByThread++;
                        }

                    }
                    TimeUnit.MILLISECONDS.sleep(50);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(NullPointerException e){
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


    public void increaseImageImprover(){

            totalImprovements++;

    }
}







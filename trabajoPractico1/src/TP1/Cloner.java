package TP1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cloner implements Runnable {

    private final InitContainer initContainer;

    private final FinalContainer finalContainer;


    private final  String name;

    private Image lastImageClone;

    private int imageCloned;

    public Cloner(InitContainer initContainer, FinalContainer finalContainer, String name) {

        this.initContainer = initContainer;
        this.finalContainer = finalContainer;
        this.name = name;
        lastImageClone=null;
        imageCloned =0;

    }

    @Override
    public void run() {
        while(initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {
            try {
                  lastImageClone = initContainer.getImage(lastImageClone);

                if ( lastImageClone  != null) {

                    if(lastImageClone.isResized())
                    {
                        if(!lastImageClone.isIamDeletefromInitContainer())
                        {
                            if(lastImageClone.tryCloneToFinalContainer()){
                            if(finalContainer.Clone(initContainer.CopyAndDeleted( lastImageClone),this,imageCloned)) {
                                increaseImageClone();
                                TimeUnit.MILLISECONDS.sleep(50);
                            }

                        }
                        }
}
                    }
                }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;

            }catch (NullPointerException | IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }



    public String getName() {
        return name;
    }


    public int getImageCloned() {
        return imageCloned;
    }

    public void increaseImageClone() {
        imageCloned++;
    }

}



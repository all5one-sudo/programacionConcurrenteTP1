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
    //  lastImageClone= initContainer.container.get(new Random().nextInt(initContainer.container.size()));
        lastImageClone=null;

    }

    @Override
    public void run() {
        while(initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {  //ver idea de esteban
            try {
                  lastImageClone = initContainer.getImage(lastImageClone);

                if ( lastImageClone  != null) {

                    if(lastImageClone.getAmIResized()) {

                           finalContainer.Clone(initContainer.CopyAndDeleted( lastImageClone));

                            imageCloned++;
                            TimeUnit.MILLISECONDS.sleep(2);
                        }
                    }
                }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public InitContainer getInitContainer() {
        return initContainer;
    }

    public FinalContainer getFinalContainer() {
        return finalContainer;
    }

    public String getName() {
        return name;
    }

    public Image getLastImageClone() {
        return lastImageClone;
    }

    public void setLastImageClone(Image lastImageClone) {
        this.lastImageClone = lastImageClone;
    }

    public int getImageCloned() {
        return imageCloned;
    }

    public void setImageCloned(int imageCloned) {
        this.imageCloned = imageCloned;
    }
}



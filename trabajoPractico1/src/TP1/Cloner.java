package TP1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cloner implements Runnable {

    private final InitContainer initContainer;

    private final FinalContainer finalContainer;

    private static final Object LLAVE = new Object();

    private final  String name;

    private Image lastImageClone;

    private int imageCloned;

    public Cloner(InitContainer initContainer, FinalContainer finalContainer, String name) {

        this.initContainer = initContainer;
        this.finalContainer = finalContainer;
        this.name = name;
    //  lastImageClone= initContainer.container.get(new Random().nextInt(initContainer.container.size()));
        lastImageClone=null;
        imageCloned =0;

    }

    @Override
    public void run() {
        while(initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {  //ver idea de esteban
            try {
                  lastImageClone = initContainer.getImage(lastImageClone);

                if ( lastImageClone  != null) {

                    if(lastImageClone.isResized())
                    {
                        if(!lastImageClone.isIamDeletefromInitContainer())
                        {
                            if(lastImageClone.tryCloneToFinalContainer()){
                            if(finalContainer.Clone(initContainer.CopyAndDeleted( lastImageClone),this,imageCloned)) {
                                System.out.println("Bandera"+ lastImageClone.getId()+ this.getName());

                                increaseImageClone();
                                TimeUnit.MILLISECONDS.sleep(10);
                            }
                            else{
                                break;
                            }
                        }
                        }


}
                    }
                }
            catch (InterruptedException e) {
                e.printStackTrace();

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
    public void increaseImageClone(){

       synchronized (LLAVE){
           imageCloned++;

       }

    }
}



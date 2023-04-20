package TP1;

import java.util.concurrent.TimeUnit;
public class Loader implements Runnable {

    private int imageLoad;

    private final InitContainer initContainer;
    private static final Object LLAVE = new Object();
    private final String name;

    public Loader(InitContainer initContainer, String name) {
        this.initContainer = initContainer;
        this.name = name;
        imageLoad = 0;

    }

    @Override
    public void run() {
        while (initContainer.isNotLoadCompleted()) {
            try {
               if(!initContainer.load(new Image(),this,imageLoad))
                {    System.out.println("ENTREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESADASDASDSASDASDASSAD" );
                    increaseImageLoad();
                    TimeUnit.MILLISECONDS.sleep(15);
                }
               else
               {
                   System.out.println("ESTA LLENOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
               }

            } catch (InterruptedException e) {
                e.printStackTrace();}
         //   } catch (FullContainerException e) {
         //       throw new RuntimeException(e);
         //   }
        }

    }

    public InitContainer getInitContainer() {
        return initContainer;
    }

    public void setImageLoad(int imageLoad) {
        this.imageLoad = imageLoad;
    }

    public String getName() {
        return name;
    }

    public int getImageLoad() {
        return imageLoad;
    }

    public void increaseImageLoad(){
        synchronized (LLAVE){
            imageLoad++;
        }
    }

}



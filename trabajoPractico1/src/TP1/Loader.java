package TP1;

import java.util.concurrent.TimeUnit;
public class Loader implements Runnable {

    private int imageLoad;

    private final InitContainer initContainer;

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
                {
                    increaseImageLoad();
                    TimeUnit.MILLISECONDS.sleep(50);
                }

            } catch (Exception e) {
                e.printStackTrace();
            break;}
        }

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
            imageLoad++;
    }

}



package TP1;

import java.util.Random;

public class InitContainer extends  Container {
    private boolean loadCompleted;
    private int targetAmountOfImages;   //cantidad Max de imagenes a crear
    private int amountOfImages;      //cantidad de imagenes actual

    public InitContainer(int targetAmountOfImages) {
        loadCompleted = false ;
        this.targetAmountOfImages = targetAmountOfImages;
        this.amountOfImages = 0;
    }

    public boolean load(Image image, Loader loader,int cantidad) throws Exception{
        lock.lock();
        try {
            if (!loadCompleted) {
                container.addLast(image);
                amountOfImages++;
                System.out.println("imagen cargada: "+ image.getId());
                if(amountOfImages == targetAmountOfImages) {
                    loadCompleted = true;
                     loader.setImageLoad(cantidad+1);
                     throw new Exception("contenedor lleno");
                }
            }

        } finally {
            lock.unlock();
        }
        return loadCompleted;
    }

    public Image getImage (Image last) throws InterruptedException {
            if (container.size() > 0) {
                    int aux= new Random().nextInt(container.size());
                    return container.get(aux);
            } else {
                return null;
            }
    }



    public boolean isNotLoadCompleted() {

            return !loadCompleted;

    }

    public Image CopyAndDeleted(Image image) throws InterruptedException{
        lock.lock();

        try {
            if (container.size() > 0 && image.isIamDeletefromInitContainer()) {


                Image forClone = new Image(image.getImprovements(),image.getAmIResized(),image.getId(), true,image.getAmIImproved());

                this.container.remove(image);

               System.out.printf("Imagen copiada y borrada del contenedor inicial: " + forClone.getId());

                return forClone;
            } else {
                return null;
            }
        }
        finally {
            lock.unlock();
        }
    }



    public int getAmountOfImages() {
        return amountOfImages;
    }
}














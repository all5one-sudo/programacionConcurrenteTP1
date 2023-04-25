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
        lastImageResized = null;
        totalImagesResized = 0;
        lastImageResized = null;
    }

    public int getTotalImagesResized() {
        return totalImagesResized;
    }

    @Override
    public void run() {
        while (initContainer.getSize() > 0 || initContainer.isNotLoadCompleted()) {
            try {
                lastImageResized = initContainer.getImage(lastImageResized);

                if (lastImageResized != null) {

                    if (lastImageResized.getAmIImproved()) {

                        if (lastImageResized.resize()) {
                            System.out.println("Image: " + lastImageResized.getId() + "resized by: "
                            + Thread.currentThread().getName());
                            increaseImageResizer();
                            TimeUnit.MILLISECONDS.sleep(100);
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Container data deleted, trying again...");
            } catch (InterruptedException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public InitContainer getInitContainer() {
        return initContainer;
    }

    public String getName() {
        return name;
    }

    public void increaseImageResizer() {
        totalImagesResized++;
    }

    public Image getlastImageResized() {
        return lastImageResized;
    }

}
package TP1;

import java.util.concurrent.TimeUnit;

// Clase que se encarga de cargar las imágenes en el contenedor inicial
public class Loader implements Runnable {

    private int loadedImages; // Cantidad de imágenes cargadas

    private final InitContainer initContainer; // Contenedor inicial

    private final String name; // Nombre

    public Loader(InitContainer initContainer, String name) {
        this.initContainer = initContainer;
        this.name = name;
        loadedImages = 0;
    }

    // Sobreescritura del método run()
    @Override
    public void run() {
        while (initContainer.isLoadNotCompleted()) {
            try {
                if (!initContainer.load(new Image(), this, loadedImages)) {
                    increaseImageLoad();
                    TimeUnit.MILLISECONDS.sleep(50);
                }
            }
            // Se deja este catch por si por alguna razón aparece alguna excepción, de todos modos no aparecen
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Setter de cantidad de imágenes cargadas
    public void setLoadedImages(int loadedImages) {
        this.loadedImages = loadedImages;
    }

    // Getter del name del Loader
    public String getName() {
        return name;
    }

    // Getter de la cantidad de imágenes cargadas
    public int getLoadedImages() {
        return loadedImages;
    }

    // Método para incrementar la cantidad de imágenes cargadas
    public void increaseImageLoad() {
        loadedImages++;
    }

}

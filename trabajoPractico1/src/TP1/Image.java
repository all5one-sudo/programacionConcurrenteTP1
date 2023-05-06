package TP1;

import java.util.ArrayList;
import java.util.List;

public class Image {

    private final List<Improver> improvements; // Lista de los hilos improver que mejoraron la imagen
    private final int id; // Identificador de la imagen
    private static int generator = 0; // Generador para el nombre
    private static final Object key = new Object(); // Llave
    private boolean resized; // Boolean que indica si la imagen se reescaló
    private boolean clonedToFinalContainer; // Booleano que indica si la imagen fue clonada al contenedor final

    private final Object keyImprove = new Object(); // Llave para el sincronismo del improve
    private final Object keyResize = new Object(); // Llave para el sincronismo de resize
    private final Object keyClone = new Object(); // Llave para el sincronismo del clonado

    private boolean iAmDeletedFromInitContainer; // Booleano para indicar si la imagen fue eliminada del contenedor inicial

    private boolean iAmImproved; // Booleano para verificar si la imagen está mejorada

    // Se genera un nuevo id, se usa el generador de la clase (static)
    private static int newId() {
        synchronized (key) {
            return generator++;
        }
    }

    public Image() {
        improvements = new ArrayList<>();
        resized = false;
        id = newId();
        clonedToFinalContainer = false;
        iAmImproved = false;
        iAmDeletedFromInitContainer = false;
    }

    // Método Getter de la lista de hilos que mejoraron la imagen
    public List<Improver> getImprovements() {
        return improvements;
    }

    // Setter del campo resized
    public void setResized(boolean resized) {
        this.resized = resized;
    }

    // Setter del campo clonedToFinalContainer
    public void setClonedToFinalContainer(boolean clonedToFinalContainer) {
        this.clonedToFinalContainer = clonedToFinalContainer;
    }

    // Constructor
    public Image(List<Improver> improvements, boolean resized, int id, boolean clonedToFinalContainer,
            boolean iamImproved) {
        this.improvements = improvements;
        this.resized = resized;
        this.id = id;
        this.clonedToFinalContainer = clonedToFinalContainer;
        this.iAmImproved = iamImproved;
    }

    // Getter del campo iAmDeletedFromInitContainer
    public boolean amIDeletedFromInitContainer() {
        return iAmDeletedFromInitContainer;
    }

    // Método para mejorar la imagen por un hilo Improver
    public void improveByThread(Improver improver) {
        synchronized (keyImprove) {
            improvements.add(improver);
            if (improvements.size() == improver.getTotalThreadsImprovements()) {
                this.setIamImprove();
            }
        }
    }

    // Getter que indica si la imagen fue mejorada por un cierto hilo Improver que recibe como parámetro
    public boolean isImprovedByThread(Improver improver) {
        return improvements.contains(improver);
    }

    // Método que reescala la imagen
    public boolean resize() {
        synchronized (keyResize) {
            if (!isResized()) {
                setResized(true);
                return true;
            } else {
                return false;
            }
        }
    }

    // Getter del id de la imagen
    public int getId() {
        return id;
    }

    // Setter del improve
    public void setIamImprove() {
        iAmImproved = true;
    }

    // Getter que indica si la imagen fue reescalada
    public boolean isResized() {
        return resized;
    }

    // Método que intenta clonar la imagen al contenedor inicial
    public boolean tryCloneToFinalContainer() {
        synchronized (keyClone) {
            if (!iAmDeletedFromInitContainer) {
                iAmDeletedFromInitContainer = true;
                return true;
            } else {
                return false;
            }
        }
    }

    // Getter que indica si la imagen fue mejorada
    public boolean getAmIImproved() {
        return iAmImproved;
    }

    // Getter que indica si la imagen fue reescalada
    public boolean getAmIResized() {
        return resized;
    }
}

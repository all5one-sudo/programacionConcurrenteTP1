package TP1;

import java.util.LinkedList;

// Clase abstracta contenedor
public abstract class Container {

    protected LinkedList<Image> container;

    protected int targetAmountOfImages;

    public Container() {
        container = new LinkedList<>();
    }

    public int getSize() {
        return container.size();
    }
}
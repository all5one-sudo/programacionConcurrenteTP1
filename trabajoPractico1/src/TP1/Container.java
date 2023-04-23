package TP1;

import java.util.LinkedList;
import java.util.concurrent.locks.*;

public abstract class Container {
    protected Lock lock;
    protected LinkedList<Image> container;
    protected int targetAmountOfImages;

    public Container() {
        container = new LinkedList<>();
        lock = new ReentrantLock(false);
    }

    public int getSize() {
        return container.size();
    }
}
package TP1;

import java.util.LinkedList;
import java.util.concurrent.locks.*;
    public abstract class Container {
        protected ReadWriteLock lock;
        protected LinkedList<Image> container;
        protected int targetAmountOfImages;
        public Container() {
            container = new LinkedList<>();
            lock = new ReentrantReadWriteLock(false);
        }
        public int getSize() {
            lock.readLock().lock();

            try {
                return container.size();
            } finally {
                lock.readLock().unlock();
            }
        }
}

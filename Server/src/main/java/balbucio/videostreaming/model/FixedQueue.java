package balbucio.videostreaming.model;

import lombok.Getter;

import java.util.LinkedList;

public class FixedQueue<E> {
    private final int maxSize;
    @Getter
    private final LinkedList<E> queue = new LinkedList<>();

    public FixedQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(E element) {
        if (queue.size() >= maxSize) {
            queue.poll(); // Remove o elemento mais antigo
        }
        queue.offer(element);
    }

}

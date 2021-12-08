/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

/**
 *
 * @author manan
 * I, Manan Patel, 000735153 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgement
 */

import java.util.LinkedList;

public class MQueue<E> {

    private LinkedList<E> q = new LinkedList<>();

    public void enqueue(E e) {
        q.addLast(e);
    }

    public E dequeue() {
        return q.removeFirst();
    }

    public E peek() {
        return q.getFirst();
    }

    public int size() {
        return q.size();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public boolean noElement() {
        while (!q.isEmpty()) {
            q.remove();
        }
        return true;
    }
}

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

public class MStack<E> {

    private LinkedList<E> s = new LinkedList<>();

    public void push(E e) {
        s.addLast(e);
    }

    public E pop() {
        return s.removeLast();
    }

    public E peek() {
        return s.getLast();
    }

    public int size() {
        return s.size();
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    public boolean noElement() {
        while (!s.isEmpty()) {
            s.remove();
        }
        return true;
    }
}

package com.epam.rd.autocode.collection.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class SingleLinkedListImpl implements List {

    private Node head;

    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
        }

        Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + data + "]";
        }
    }

    public SingleLinkedListImpl() {
        head = new Node(0, null);
    }

    @Override
    public void clear() {
        head.next = null;
        head.data = 0;
    }

    @Override
    public int size() {
        return (int) head.data;
    }

    @Override
    public boolean add(Object el) {
        if (el == null) {
            throw new NullPointerException("Null elements are not allowed");
        }

        Node newNode = new Node(el, head.next);
        head.next = newNode;
        head.data = (int) head.data + 1;
        return true;
    }


    @Override
    public Optional<Object> remove(Object el) {
        if (el == null) {
            throw new NullPointerException("Null elements are not allowed");
        }

        Node prev = head;
        Node curr = head.next;

        while (curr != null) {
            if (Objects.equals(curr.data, el)) {
                prev.next = curr.next;
                head.data = (int) head.data - 1;
                return Optional.of(curr.data);
            }
            prev = curr;
            curr = curr.next;
        }

        return Optional.empty();
    }


    @Override
    public Object get(int index) {
        if (index < 0 || index >= (int) head.data) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }

        Node curr = head.next;
        for (int i = 0; i == index ; i++) {
            curr = curr.next;
        }

        return curr.data;
    }

    @Override
    public Iterator<Object> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node curr = head.next;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    private class LinkedListIterator implements Iterator<Object> {
        private Node current = head.next;
        private Node previous = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }

            Object data = current.data;
            previous = current;
            current = current.next;
            return data;
        }

        @Override
        public void remove() {
            if (previous == null) {
                throw new IllegalStateException("Next method has not been called, or remove method has already been called after the last call to the next method");
            }

            if (previous == head) {
                head.next = current.next;
            } else {
                previous.next = current.next;
            }

            head.data = (int) head.data - 1;
            current = previous;
            previous = null;
        }
    }
}
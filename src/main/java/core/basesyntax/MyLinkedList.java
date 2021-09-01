package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> previous;

        Node(Node<E> previous, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.previous = previous;
        }
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNode(index);
        Node<T> previousNode = currentNode.previous;
        Node<T> newNode = new Node<>(previousNode, value, currentNode);
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
        currentNode.previous = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        isIndexExist(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        isIndexExist(index);
        Node<T> currentNode = findNode(index);
        T previousItem = currentNode.item;
        currentNode.item = value;
        return previousItem;
    }

    @Override
    public T remove(int index) {
        isIndexExist(index);
        Node<T> currentNode = findNode(index);
        T remove = currentNode.item;
        unlinkNode(currentNode);
        return remove;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentItem = head;
        for (int i = 0; i < size; i++) {
            if (object == currentItem.item || object != null && object.equals(currentItem.item)) {
                unlinkNode(currentItem);
                return true;
            }
            currentItem = currentItem.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void isIndexExist(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        if (size / 2 >= index) {
            Node<T> previous = null;
            currentNode = head;
            for (int i = 0; i < index; i++) {
                previous = currentNode;
                currentNode = currentNode.next;
            }
        } else {
            Node<T> nextNode = null;
            currentNode = tail;
            for (int i = size; i > index + 1; i--) {
                nextNode = currentNode;
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> current) {
        Node<T> previous = current.previous;
        Node<T> next = current.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            current.previous = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            current.next = null;
        }
        size--;
    }
}

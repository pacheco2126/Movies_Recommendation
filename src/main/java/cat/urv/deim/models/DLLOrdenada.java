package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;



public class DLLOrdenada<E extends Comparable<E>> implements ILlista<E> {
    
        private Node<E> head;
        private Node<E> tail;
        private int size;
    
        private static class Node<E> {
            E element;
            Node<E> prev;
            Node<E> next;
    
            Node(E element, Node<E> prev, Node<E> next) {
                this.element = element;
                this.prev = prev;
                this.next = next;
            }
        }

    @Override
    public void inserir(E e) {
        Node<E> newNode = new Node<>(e, null, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            Node<E> currentNode = head;
            while (currentNode != null) {
                if (e.compareTo(currentNode.element) < 0) {
                    if (currentNode == head) {
                        newNode.next = currentNode;
                        currentNode.prev = newNode;
                        head = newNode;
                    } else {
                        newNode.prev = currentNode.prev;
                        newNode.next = currentNode;
                        currentNode.prev.next = newNode;
                        currentNode.prev = newNode;
                    }
                    size++;
                    return;
                }
                currentNode = currentNode.next;
            }
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }


    @Override
    public void esborrar(E e) throws ElementNoTrobat {
        Node<E> currentNode = head;
        while (currentNode != null) {
            if (currentNode.element.equals(e)) {
                if (currentNode == head && currentNode == tail) {
                    head = null;
                    tail = null;
                } else if (currentNode == head) {
                    head = currentNode.next;
                    head.prev = null;
                } else if (currentNode == tail) {
                    tail = currentNode.prev;
                    tail.next = null;
                } else {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                }
                size--;
                return;
            }
            currentNode = currentNode.next;
        }
        throw new ElementNoTrobat("Element not found in the list.");
}

    @Override
    public boolean buscar(E e) {
        Node<E> currentNode = head;
        while (currentNode != null) {
            if (currentNode.element.equals(e)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;    }

    @Override
    public boolean esBuida() {
        return size == 0;

    }

    @Override
    public int longitud() {
        return size;
    }

    @Override
    public Object[] elements() {
        Object[] result = new Object[size];
        Node<E> currentNode = head;
        int index = 0;
        while (currentNode != null) {
            result[index] = currentNode.element;
            currentNode = currentNode.next;
            index++;
        }
        return result;    }

        @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> currentNode = head;
        while (currentNode != null) {
            sb.append(currentNode.element.toString());
            currentNode = currentNode.next;
            if (currentNode != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}

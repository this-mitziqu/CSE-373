package deques;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node front;
    private Node end;

    public LinkedDeque() {
        front = null;
        end = null;
        size = 0;
    }

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        Node(T value) {
            this.item = value;
        }
    }

    public void addFirst(T item) {
        if (size == 0){
            Node temp = new Node(item);
            front = temp;
            end = temp;
        }else {
           Node temp = front;
           front = new Node(item);
           front.next = temp;
           temp.prev = front;
        }
        size += 1;
    }

    public void addLast(T item) {
        if (size == 0){
            Node temp = new Node(item);
            front = temp;
            end = temp;
        } else {
            Node temp = end;
            end = new Node(item);
            temp.next = end;
            end.prev = temp;
        }
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = front.item;
        if (size == 1){
            front = null;
            end = null;
        } else {
            front = front.next;
            front.prev = null;
        }
            size -= 1;
            return temp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = end.item;
        if (size == 1) {
            front = null;
            end = null;
        }else {
            end = end.prev;
            end.next = null;
        }
        size -= 1;
        return temp;
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }
        //index == 0 && size == 0
        if (size == 0){
           return null;
        }
        Node temp = front;
        for (int i = 0; i < index; i++){
           temp = temp.next;
        }
        return temp.item;
    }

    public int size() {
        return size;
    }
}

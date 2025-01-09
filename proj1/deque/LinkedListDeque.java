package deque;
class Node<E> {
    E data;
    Node<E> next;
    Node<E> prev;

    Node(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
class LinkedListDeque<E> {
    private Node<E> head;
    private Node<E> tail;
    private Node<E> prev;
    private int size = 0;
    public  LinkedListDeque() {
        this.head = null;
        this.tail = null;
        this.prev = null;
    }

    public boolean isEmpty() {
        return this.head == null;
    }
    // Add from begin (push) .
    public void addFirst (E e) {
        size++;
        Node<E> newNode = new Node<E>(e);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }else{
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }
    // Add from end (enqueue)  .
    public void addLast (E e) {
        size++;
        Node<E> newNode = new Node<E>(e);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }else{
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
    }
    // Remove from begin (dequeue)
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }else {
            Node<E> first = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            size--;
            return first.data;
        }
    }
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }else if(head == tail) { // last element in the dq
            Node<E> last = tail;
            head = null;
            tail = null;
            size--;
            return last.data;
        }
        else{
            Node<E> last = tail;
            tail = tail.prev;
            tail.next = null;
            size--;
            return last.data;
        }
    }
    public int size(){
        return this.size;
    }
    //
    public E iterativeGet(int index) {
        if(index >= size || index < 0) {
            return null ;
        }else {
            Node<E> node = head;
            for(int i = 0; i < index; i++) {
                node = node.next;
            }
            return node.data ;
        }
    }

    public E getRecursiveHelper (Node<E> current , int index) {
        if(index == 0) {
            return current.data ;
        }
        Node<E> node = current.next;
        return getRecursiveHelper(node, index - 1);
    }

    public E getRecursive(int index) {
        if(index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(head , index);
    }

    public void printDeque() {
        Node<E> node = head;
        while(node != null) {
            System.out.println(node.data);
            node = node.next;
        }
    }

}
package deque;
class ArrayDeque<E> {
    private int head;
    private int tail;
    private int length;
    private int arraySize;
    private E[] data;
    public ArrayDeque() {
        this.head = -1 ;
        this.tail = -1 ;
        this.length = 8;
        this.arraySize = 0 ;
        this.data = (E[]) new Object[length];
    }
    public boolean isEmpty() {
        return this.arraySize == 0;
    }
    public int size() {
        return this.arraySize;
    }
    public void push(E e) {
        // Empty
        if(head == -1) {
            head = tail = length;
            data[tail] = data[head] = e;
            length++;
        }
        // Full
        if(arraySize == length) {
            length = arraySize * 2;
            E[] newData = (E[]) new Object[arraySize];
            System.arraycopy(data, 0, newData, 0, arraySize);
            data = newData;
            data[head] = e;
        }
        // else
    }

}
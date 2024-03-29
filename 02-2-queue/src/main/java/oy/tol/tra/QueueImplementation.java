package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E> {
    private Object[] itemArray;
    private int capacity;
    private int currentSize = 0;
    private int head = 0;
    private int tail = -1;
    private static final int DEFAULT_STACK_SIZE = 10;

    public QueueImplementation() throws QueueAllocationException {
        this(DEFAULT_STACK_SIZE);
    }

    public QueueImplementation(int capacity) throws QueueAllocationException {
        if (capacity < 2) {
            throw new QueueAllocationException("Capacity is too small!");
        }
        this.capacity = capacity;
        itemArray = new Object[capacity];
        head = 0;
        tail = -1;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if (element == null) {
            throw new NullPointerException();
        }
        if (currentSize == capacity) {
            resizeArray(capacity * 2 + 1);
        }
        tail = (tail + 1) % capacity;
        itemArray[tail] = element;
        currentSize++;
    }

    private void resizeArray(int newCapacity) {
        Object[] tmp = new Object[newCapacity];
        int indexOfItemArray = head;
        int indexOfTmp = 0;
        int loopTime = currentSize;
        while (loopTime-- > 0) {
            tmp[indexOfTmp++] = itemArray[indexOfItemArray];
            itemArray[indexOfItemArray] = null; // Set the removed element to null
            indexOfItemArray = (indexOfItemArray + 1) % capacity;
        }
        head = 0;
        tail = indexOfTmp - 1;
        itemArray = tmp;
        capacity = newCapacity;
    }

    @Override
    public E dequeue() throws QueueIsEmptyException {
        E returnE = element();
        itemArray[head] = null; // Set the removed element to null
        head = (head + 1) % capacity;
        currentSize--;
        return returnE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E element() throws QueueIsEmptyException {
        if (isEmpty()) {
            throw new QueueIsEmptyException("Queue is empty");
        }
        return (E) itemArray[head];
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void clear() {
        head = 0;
        tail = -1;
        currentSize = 0;
        itemArray = new Object[capacity]; // Reset the internal storage
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        int indexOfItemArray = head;
        int loopTime = currentSize;
        while (loopTime-- > 0) {
            builder.append(itemArray[indexOfItemArray].toString());
            indexOfItemArray = (indexOfItemArray + 1) % capacity;
            if (loopTime != 0) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
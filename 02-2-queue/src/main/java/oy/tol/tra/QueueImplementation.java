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
            throw new QueueAllocationException("Cannot allocate room for the internal array");
        }
        this.capacity = capacity;
        itemArray = new Object[capacity];
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if (currentSize == capacity) {
            resizeArray();
        }

        if (element == null) {
            throw new NullPointerException();
        }

        tail = (tail + 1) % capacity;
        itemArray[tail] = element;
        currentSize++;
    }

    private void resizeArray() {
        Object[] tmp = new Object[capacity * 2 + 1];
        if (tail >= head) {
            System.arraycopy(itemArray, head, tmp, 0, currentSize);
        } else {
            System.arraycopy(itemArray, head, tmp, 0, capacity - head);
            System.arraycopy(itemArray, 0, tmp, capacity - head, tail + 1);
        }
        head = 0;
        tail = currentSize - 1;
        itemArray = tmp;
        capacity = capacity * 2 + 1;
    }

    @Override
    public E dequeue() throws QueueIsEmptyException {
        E returnE = element();
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
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = head; i != tail; i = (i + 1) % capacity) {
            builder.append(itemArray[i]);
            if ((i + 1) % capacity != (tail + 1) % capacity) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
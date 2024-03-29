package oy.tol.tra;

public class StackImplementation<E> implements StackInterface<E> {

   private Object[] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;

   public StackImplementation() throws StackAllocationException {
      capacity = DEFAULT_STACK_SIZE;
      itemArray = new Object[DEFAULT_STACK_SIZE];
   }

   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2) {
         throw new StackAllocationException("Cannot allocate room for the internal array");
      }
      this.capacity = capacity;
      itemArray = new Object[capacity];
   }

   @Override
   public int capacity() {
      return this.capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null) {
         throw new NullPointerException("Element cannot be null");
      }

      if (size() == capacity()) {
         Object[] tmp = new Object[this.capacity * 2 + 1];

         for (int i = 0; i < itemArray.length; i++) {
            tmp[i] = itemArray[i];
         }
         itemArray = tmp;
         capacity = capacity * 2 + 1;
      }
      itemArray[++currentIndex] = element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (isEmpty()) {
         throw new StackIsEmptyException("Stack is empty");
      }
      E poppedElement = (E) itemArray[currentIndex];
      itemArray[currentIndex--] = null; // Remove the element from internal storage
      return poppedElement;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (isEmpty()) {
         throw new StackIsEmptyException("Stack is empty");
      }
      return (E) itemArray[currentIndex];
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      currentIndex = -1;
   }

   @Override
   public boolean isEmpty() {
      return currentIndex == -1;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      for (int index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}
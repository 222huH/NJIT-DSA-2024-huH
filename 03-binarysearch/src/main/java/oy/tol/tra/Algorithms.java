package oy.tol.tra;

public class Algorithms {

public static <E> void reverse(E[] arr) {
    int start = 0;
    int end = arr.length - 1;
    while (start < end) {
        swap(arr, start, end);
        start++;
        end--;
    }
}
private static <E> void swap(E[] arr, int i, int j) {
    E temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

public static <E extends Comparable<E>> int binarySearch(E key, E[] arr, int start, int end) {
    if (end >= start) {
        int mid = start + (end - start) / 2;

        if (arr[mid].compareTo(key) == 0) {
            return mid; 
        } else if (arr[mid].compareTo(key) > 0) {
            return binarySearch(key, arr, start, mid - 1); 
        } else {
            return binarySearch(key, arr, mid + 1, end); 
        }
    }

    return -1;
}
}
package oy.tol.tra;
import java.util.Comparator;
import java.util.function.Predicate;


public class Algorithms {
    public static <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    public static <T> void reverse(T[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            swap(array, left, right);
            left++;
            right--;
        }
    }

    public static <T extends Comparable<T>> int binarySearch(T value, T[] array, int fromIndex, int toIndex) {
        while (fromIndex <= toIndex) {
            int mid = fromIndex + (toIndex - fromIndex) / 2;
            int cmp = value.compareTo(array[mid]);
            if (cmp > 0) {
                fromIndex = mid + 1;
            } else if (cmp < 0) {
                toIndex = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static <E extends Comparable<E>> void fastSort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static <E extends Comparable<E>> void quickSort(E[] array, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot - 1);
        quickSort(array, pivot + 1, end);
    }

    private static <E extends Comparable<E>> int partition(E[] array, int begin, int end) {
        E pivot = array[begin];
        int left = begin;
        int right = end;
        while (left != right) {
            while (left < right && array[right].compareTo(pivot) > 0) {
                right--;
            }
            while (left < right && array[left].compareTo(pivot) <= 0) {
                left++;
            }
            if (left < right) {
                swap(array, left, right);
            }
        }
        array[begin] = array[left];
        array[left] = pivot;
        return left;
    }

    private static <T> void swap(T[] array, int index1, int index2) {
        T tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
    public static <T> int partitionByRule(T[] arr, int n, Predicate<T> rule) {
        int i = 0;
        for (; i < n; i++) {
            if (rule.test(arr[i])) {
                break;
            }
        }
        if (i >= n) {
            return n;
        }
        int j = i + 1;
        while (j != n) {
            if (!rule.test(arr[j])) {
                swap(arr, i, j);
                i++;
            }
            j++;
        }
        return i;
    }
    public static <T> void sortWithComparator(T[] array, Comparator<T> comparator) {
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }}
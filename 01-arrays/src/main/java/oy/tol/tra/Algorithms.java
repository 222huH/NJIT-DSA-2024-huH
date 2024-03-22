package oy.tol.tra;

public class Algorithms {

    public static <T extends Comparable<T>> void sort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static <T> void reverse(T[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            T temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    public static <T>void swap(T[] array,int a,int b){
        T tmp=array[a];
        array[a]=array[b];
        array[b]=tmp;
    }
}
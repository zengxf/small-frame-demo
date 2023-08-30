package test.algorithm.array;

import java.util.Arrays;

/**
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestQuickSort {

    public static void main(String[] args) {
        int[] arr = {99, 88, 77, 66, 55, 44, 67, 22, 11};
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        // quickSort1( arr );
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end >= array.length || start > end)
            return;
        int smallIndex = partition(array, start, end);
        if (smallIndex > start)
            quickSort(array, start, smallIndex - 1);
        if (smallIndex < end)
            quickSort(array, smallIndex + 1, end);
    }

    public static int partition(int[] array, int start, int end) {
        // int pivot = (int) ( start + Math.random() * ( end - start + 1 ) );
        int pivot = start + (end - start) / 2;
        int smallIndex = start - 1;
        swap(array, pivot, end);
        for (int i = start; i <= end; i++)
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex)
                    swap(array, i, smallIndex);
            }
        return smallIndex;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /// ------------------
    /// ------------------
    /// ------------------

    public static void quickSort1(int[] arr) {
        qsort1(arr, 0, arr.length - 1);
    }

    private static void qsort1(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition1(arr, low, high); //
            qsort1(arr, low, pivot - 1); //
            qsort1(arr, pivot + 1, high); //
        }
    }

    private static int partition1(int[] arr, int low, int high) {
        int pivot = arr[low]; //
        while (low < high) {
            while (low < high && arr[high] >= pivot)
                --high;
            arr[low] = arr[high]; //
            while (low < high && arr[low] <= pivot)
                ++low;
            arr[high] = arr[low]; //
        }
        arr[low] = pivot;
        return low;
    }

}

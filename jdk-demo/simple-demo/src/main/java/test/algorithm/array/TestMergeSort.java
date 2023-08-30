package test.algorithm.array;

import java.util.Arrays;

/**
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestMergeSort {

    public static void main(String[] args) {
        int[] arr = {23, 54, 2, 43, 65, 3};
        System.out.println(Arrays.toString(arr));
        int[] sorted = mergeSort(arr);
        System.out.println(Arrays.toString(sorted));
    }

    public static int[] mergeSort(int[] array) {
        if (array.length < 2)
            return array;
        int mid = array.length / 2; // len >= 2
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(mergeSort(left), mergeSort(right)); //
    }

    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, li = 0, ri = 0; index < result.length; index++) {
            if (li >= left.length) //
                result[index] = right[ri++];
            else if (ri >= right.length) //
                result[index] = left[li++];
            else if (left[li] > right[ri])
                result[index] = right[ri++];
            else
                result[index] = left[li++];
        }
        return result;
    }

    // ----------------------
    // ----------------------

    public static int[] mergeSort1(int[] arr) {
        int[] temp = new int[arr.length];
        internalMergeSort(arr, temp, 0, arr.length - 1);
        return temp;
    }

    private static void internalMergeSort(int[] a, int[] b, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            internalMergeSort(a, b, left, middle); //
            internalMergeSort(a, b, middle + 1, right); //
            mergeSortedArray(a, b, left, middle, right); //
        }
    }

    private static void mergeSortedArray(int arr[], int temp[], int left, int middle, int right) {
        int i = left;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= middle) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (i = 0; i < k; ++i) {
            arr[left + i] = temp[i];
        }
    }

}

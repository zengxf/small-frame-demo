package test.algorithm.array;

import java.util.Arrays;

/**
 * <p>
 * Created by zengxf on 2018-03-07
 */
public class TestSelectionSort {

    public static void main(String[] args) {
        int[] arr = {23, 54, 2, 43, 65, 3};
        System.out.println(Arrays.toString(arr));
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void selectionSortA(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int minValue = a[i];
            int minIndex = i; //
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < minValue) {//
                    minValue = a[j];
                    minIndex = j; //
                }
            }
            if (minIndex != i) {
                a[minIndex] = a[i];
                a[i] = minValue;
            }
        }
    }

    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) //
                    minIndex = j; //
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

}

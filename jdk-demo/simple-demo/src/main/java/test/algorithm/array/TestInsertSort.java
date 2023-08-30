package test.algorithm.array;

import java.util.Arrays;

/**
 * <p>
 * Created by zengxf on 2018-03-07
 */
public class TestInsertSort {

    public static void main(String[] args) {
        int[] arr = {23, 54, 2, 43, 65, 3};
        System.out.println(Arrays.toString(arr));
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int currentNum = array[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && currentNum < array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            array[preIndex + 1] = currentNum;
        }
    }

    public static void insertSortA(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int currentNumber = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] > currentNumber) { //
                numbers[j + 1] = numbers[j]; //
                j--;
            }
            numbers[j + 1] = currentNumber;
        }
    }

}

package com.convenient.excel;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MegeSort {

    private static final AtomicInteger COUNT = new AtomicInteger();

    public static void mergeSort(Integer[] a, Integer[] temp, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, temp, left, center);
            mergeSort(a, temp, center + 1, right);
            sort(a, temp, left, center + 1, right);
        }
    }

    /**
     * int leftEnd = rightPos - 1;
     * int tmpPos = leftPos;
     * int numElements = rightEnd - leftPos + 1;
     * // Main loop
     * while (leftPos <= leftEnd && rightPos <= rightEnd)
     * if (a[leftPos].compareTo(a[rightPos]) <= 0)
     * tmpArray[tmpPos++] = a[leftPos++];
     * else
     * tmpArray[tmpPos++] = a[rightPos++];
     * <p>
     * while (leftPos <= leftEnd)    // Copy rest of first half
     * tmpArray[tmpPos++] = a[leftPos++];
     * <p>
     * while (rightPos <= rightEnd)  // Copy rest of right half
     * tmpArray[tmpPos++] = a[rightPos++];
     * <p>
     * // Copy tmpArray back
     * for (int i = 0; i < numElements; i++, rightEnd--)
     * a[rightEnd] = tmpArray[rightEnd];
     *
     * @param a
     * @param temp
     * @param leftPos
     * @param rightPos
     * @param rightEnd
     */
    private static void sort(Integer[] a, Integer[] temp, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;//为什么加1
        while (leftPos <= leftEnd && rightPos <= rightEnd)
            if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                temp[tmpPos++] = a[leftPos++];
            } else {
                temp[tmpPos++] = a[rightPos++];
            }
        while (leftPos <= leftEnd)
            temp[tmpPos++] = a[leftPos++];

        while (rightPos <= rightEnd)
            temp[tmpPos++] = a[rightPos++];
        for (int i = 0; i < numElements; i++, rightEnd--)
            a[rightEnd] = temp[rightEnd];
    }

    private static void merge(Integer[] a, Integer[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        // Main loop
        while (leftPos <= leftEnd && rightPos <= rightEnd)
            if (a[leftPos].compareTo(a[rightPos]) <= 0)
                tmpArray[tmpPos++] = a[leftPos++];
            else
                tmpArray[tmpPos++] = a[rightPos++];

        while (leftPos <= leftEnd)    // Copy rest of first half
            tmpArray[tmpPos++] = a[leftPos++];

        while (rightPos <= rightEnd)  // Copy rest of right half
            tmpArray[tmpPos++] = a[rightPos++];

        // Copy tmpArray back
        for (int i = 0; i < numElements; i++, rightEnd--)
            a[rightEnd] = tmpArray[rightEnd];
    }

    public static void main(String[] args) {
        Integer[] integers = {56, 32, 65, 526, 73438, 34, 78, 3232, 8};
        mergeSort(integers);
        Arrays.stream(integers).forEach(System.out::println);
    }

    public static void mergeSort(Integer[] a) {
        Integer[] temp = new Integer[a.length];
        mergeSort(a, temp, 0, a.length - 1);
    }
}

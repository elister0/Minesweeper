public class Sorting {

    public static void main(String[] args) {
        int n = 10; // Starting length of array
        
        while (true) {
            // Generate two identical random double arrays of length n
            double[] array1 = new double[n];
            for (int i = 0; i < n; i++) {
                array1[i] = Math.random();
            }

            // Manually copy array1 to array2
            double[] array2 = new double[array1.length];
            for (int i = 0; i < array1.length; i++) {
                array2[i] = array1[i];
            }

            System.out.println("Sorting arrays of length: " + n);

            // Time and sort with bubble sort
            try {
                long bubbleStartTime = System.nanoTime();
                bubbleSort(array1);
                long bubbleEndTime = System.nanoTime();
                long bubbleDuration = (bubbleEndTime - bubbleStartTime) / 1_000_000;

                if (bubbleDuration > 20000) {
                    System.out.println("Bubble sort took longer than 20 seconds, stopping.");
                    break;
                }
                System.out.println("Bubble sort time: " + bubbleDuration + " ms");

            } catch (OutOfMemoryError e) {
                System.out.println("Out of memory during bubble sort, stopping.");
                break;
            }

            // Time and sort with merge sort
            try {
                long mergeStartTime = System.nanoTime();
                mergeSort(array2);
                long mergeEndTime = System.nanoTime();
                long mergeDuration = (mergeEndTime - mergeStartTime) / 1000000;

                if (mergeDuration > 20000) {
                    System.out.println("Merge sort took longer than 20 seconds, stopping.");
                    break;
                }
                System.out.println("Merge sort time: " + mergeDuration + " ms");

            } catch (OutOfMemoryError e) {
                System.out.println("Out of memory during merge sort, stopping.");
                break;
            }

            n *= 10; // Increase n by a factor of 10
        }
    }

    
    public static void bubbleSort(double[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    double temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    
    public static void mergeSort(double[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            double[] left = new double[mid];
            double[] right = new double[array.length - mid];

            for (int i = 0; i < mid; i++) {
                left[i] = array[i];
            }
            for (int i = mid; i < array.length; i++) {
                right[i - mid] = array[i];
            }

            mergeSort(left);
            mergeSort(right);

            merge(array, left, right);
        }
    }

    private static void merge(double[] array, double[] left, double[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}

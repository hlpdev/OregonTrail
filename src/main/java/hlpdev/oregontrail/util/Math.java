package hlpdev.oregontrail.util;

import java.util.ArrayList;
import java.util.List;

public class Math {
    /**
     * Finds the average value of a given array of double values
     * @param values An array of doubles to find the average of
     * @return A double value representing the average
     */
    public static double average(double[] values) {
        double total = 0;
        for (double value : values) {
            total += value;
        }

        return total / values.length;
    }

    /**
     * Finds the average value of a given array of integer values
     * @param values An array of integers to find the average of
     * @return An integer representing the average
     */
    public static int average(int[] values) {
        List<Integer> integerValues = new ArrayList<Integer>();
        for (int value : values) {
            integerValues.add(value);
        }

        return average(integerValues.toArray(new Integer[0]));
    }

    /**
     * Same as above, just with the Integer object
     * @param values An array of integers to find the average of
     * @return An integer representing the average
     */
    public static int average(Integer[] values) {
        int total = 0;
        for (int value : values) {
            total += value;
        }

        return total / values.length;
    }
}

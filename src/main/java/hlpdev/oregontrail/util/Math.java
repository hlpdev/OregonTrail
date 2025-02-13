package hlpdev.oregontrail.util;

import java.util.ArrayList;
import java.util.List;

public class Math {
    public static double average(double[] values) {
        double total = 0;
        for (double value : values) {
            total += value;
        }

        return total / values.length;
    }

    public static int average(int[] values) {
        List<Integer> integerValues = new ArrayList<Integer>();
        for (int value : values) {
            integerValues.add(value);
        }

        return average(integerValues.toArray(new Integer[0]));
    }

    public static int average(Integer[] values) {
        int total = 0;
        for (int value : values) {
            total += value;
        }

        return total / values.length;
    }
}

package net.juliantexo.magicprogression;

public class MathHelper {

    public static double mapNumber(double value, double rangeMin, double rangeMax, double resultMin, double resultMax) {
        return (value - rangeMin) / (rangeMax - rangeMin) * (resultMax - resultMin) + resultMin;
    }
}

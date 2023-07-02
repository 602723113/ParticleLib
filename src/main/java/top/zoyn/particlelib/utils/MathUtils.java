package top.zoyn.particlelib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathUtils {

    private static final Random RANDOM = new Random();

    public static Random getRandom() {
        return RANDOM;
    }

    public static double getRandomDouble() {
        return RANDOM.nextDouble();
    }

    public static double getUniformRandom(double a, double b) {
        return a + (b - a) * RANDOM.nextDouble();
    }

    /**
     * 根据给定的起始和终止点创建一个等间距的数字范围
     * <p>
     * 类似 NumPy 的 arange 方法
     * @param start 起始值
     * @param stop 结束值
     * @param step 步进长度
     * @return 所有数字的列表
     */
    public static List<Double> arange(double start, double stop, double step) {
        List<Double> data = new ArrayList<>();
        for (double i = start; i < stop; i += step) {
            data.add(i);
        }
        return data;
    }
}

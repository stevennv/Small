package steven.small.utils;

import java.util.Random;

/**
 * Created by Admin on 10/25/2017.
 */

public class Utils {
    public static Integer RandomNumberRange(int max, int min, int block) {
        Random rand = new Random();
        int randomNum;

        do {
            randomNum = rand.nextInt((max - min) + 1) + min;
        } while (randomNum == block && (max != block && block != min));

        return randomNum;
    }
}
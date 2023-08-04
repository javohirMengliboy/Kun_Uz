package com.example.util;

import java.util.Random;

public class RandomUtil {
    private static Random random = new Random();
    public static String getRandomSmsCode(){
        return String.valueOf(random.nextInt(10000,99999));
    }
}

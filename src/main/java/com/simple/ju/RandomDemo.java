package com.simple.ju;

import java.util.Random;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class RandomDemo {

    public static void main(String[] args) {
        var random = new Random().nextInt();
        System.out.println(random);

        var random2 = new Random().nextInt();
        System.out.println(random2);

        // private static final AtomicLong seedUniquifier
        //         = new AtomicLong(8682522807148012L);
    }
}

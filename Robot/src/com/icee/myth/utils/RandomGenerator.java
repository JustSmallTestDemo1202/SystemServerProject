/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.utils;

import java.util.Random;

/**
 *
 * @author lidonglin
 */
public class RandomGenerator {
    public final static RandomGenerator INSTANCE = new RandomGenerator();

    public final Random generator = new Random();

    private RandomGenerator() {
    }

//    public static RandomGenerator INSTANCE {
//      return INSTANCE;
//    }
}

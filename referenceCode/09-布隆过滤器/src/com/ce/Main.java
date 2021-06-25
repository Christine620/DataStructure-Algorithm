package com.ce;

/**
 * ClassName:    Main
 * Package:    com.ce
 * Description:
 * Datetime:    2021/4/13   15:27
 * Author:   XXXXX@XX.com
 */
public class Main {
    public static void main(String[] args) {
        BloomFilter<Integer> bf = new BloomFilter<>(1_0000_0000,0.01);
        for (int i = 1; i < 100_0000 ; i++) {
            bf.put(i);
        }
        int count = 0;
        for (int i = 100_0001; i < 200_0000 ; i++) {
            //System.out.println(bf.contains(i));
            //Asserts.test(bf.contains(i));
            if (bf.contains(i)){
                count++;
            }
        }
        System.out.println(count);


    }
}

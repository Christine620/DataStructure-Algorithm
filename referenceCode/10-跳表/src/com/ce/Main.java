package com.ce;

/**
 * ClassName:    Main
 * Package:    com.ce
 * Description:
 * Datetime:    2021/4/14   20:15
 * Author:   XXXXX@XX.com
 */
public class Main {

    public static void main(String[] args) {
        SkipList<Integer, Integer> list = new SkipList<>();
        test(list,30,10);
        //test(list,100,10);
        //test(list,30,10);
        //test(list,20,5);
    }

    public static void test(SkipList<Integer, Integer> list, int count, int delta) {
        for (int i = 0; i < count; i++) {
            list.put(i, i + delta);
        }
        System.out.println(list);
        for (int i = 0; i < count; i++) {
            Asserts.test(list.get(i) == i + delta);
        }
        Asserts.test(list.size() == count);
        for (int i = 0; i < count; i++) {
            Asserts.test(list.remove(i) == i + delta);
        }
        Asserts.test(list.size() == 0);
    }


}

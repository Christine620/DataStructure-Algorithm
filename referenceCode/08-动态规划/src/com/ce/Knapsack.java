package com.ce;

/**
 * ClassName:    Knapsack
 * Package:    com.ce
 * Description: 0-1背包问题
 * Datetime:    2021/4/7   15:43
 * Author:   XXXXX@XX.com
 */
public class Knapsack {
    public static void main(String[] args) {

        int[] values = {6,3,5,4,6};
        int[] weight = {2,2,6,5,4};
        int capacity = 10;
        System.out.println(maxValueExactly(values, weight, capacity));

    }

    /**
     * 背包问题的拓展 -- 恰好装满背包容量capacity时的最大价值
     * @param values
     * @param weights
     * @param capacity
     * @return 返回-1代表没法刚好凑到capacity这个容量，返回别的值，代表凑到capacity这个容量所能装进背包里的最大值
     */
    static int maxValueExactly(int[] values,int[] weights, int capacity){
        if (values == null || values.length == 0) return 0;
        if (weights == null || values.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity+1];
        for (int j = 1; j < capacity ; j++) {
            dp[j] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= values.length ; i++) {
            for (int j = capacity; j >= weights[i-1] ; j--) {
                dp[j] = Math.max(dp[j-weights[i-1]]+values[i-1],dp[j]);
            }
        }
        return dp[capacity] < 0 ? -1 : dp[capacity];
    }

    /**
     * 对maxValue2的dp一维数组进一步优化
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    static int maxValue(int[] values,int[] weights, int capacity){
        if (values == null || values.length == 0) return 0;
        if (weights == null || values.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity+1];
        for (int i = 1; i <= values.length ; i++) {
            for (int j = capacity; j >= weights[i-1] ; j--) {
                dp[j] = Math.max(dp[j-weights[i-1]]+values[i-1],dp[j]);
            }
        }
        return dp[capacity];
    }


    /**
     * 对maxValue1的优化，dp采用一维数组
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    static int maxValue2(int[] values,int[] weights, int capacity){
        if (values == null || values.length == 0) return 0;
        if (weights == null || values.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity+1];
        for (int i = 1; i <= values.length ; i++) {
            for (int j = capacity; j >= 1 ; j--) {
                if (j < weights[i-1]){
                    dp[j] = dp[j];
                }else {
                    dp[j] = Math.max(dp[j-weights[i-1]]+values[i-1],dp[j]);
                }
            }
        }
        return dp[capacity];
    }

    /**
     *
     * @param values 货物价值
     * @param weights 货物重量
     * @param capacity 背包容量
     * @return 背包能承受的最大价值
     */
    static int maxValue1(int[] values,int[] weights, int capacity){
        if (values == null || values.length == 0) return 0;
        if (weights == null || values.length == 0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[][] dp = new int[values.length+1][capacity+1];

        for (int i = 1; i <= values.length; i++) {//物品个数
            for (int j = 1; j <= capacity ; j++) {//背包容量
                if (j < weights[i-1]){
                    dp[i][j] = dp[i-1][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-weights[i-1]]+values[i-1]);
                }
            }
        }

        return dp[values.length][capacity];
    }
}

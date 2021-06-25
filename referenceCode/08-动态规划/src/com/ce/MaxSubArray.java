package com.ce;

/**
 * ClassName:    MaxSubArray
 * Package:    com.ce
 * Description:练习二：最大连续子序列和
 * Datetime:    2021/3/31   16:23
 * Author:   XXXXX@XX.com
 */
public class MaxSubArray {
    public static void main(String[] args) {
        System.out.println(MaxSubArray2(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    static int MaxSubArray2(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int dp = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(dp <= 0) dp = nums[i];
            else  dp += nums[i];
            max = Math.max(dp,max);
        }
        return max;
    }

    static int MaxSubArray1(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < dp.length ; i++) {
            if(dp[i-1]<=0){
                dp[i] = nums[i];
            }else {
                dp[i] = dp[i-1]+nums[i];
            }
            if (dp[i] > max) max = dp[i];
        }
        return max;
    }
}

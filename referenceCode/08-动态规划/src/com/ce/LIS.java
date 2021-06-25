package com.ce;

/**
 * ClassName:    LIS
 * Package:    com.ce
 * Description: 练习3最长上升子序列
 * Datetime:    2021/3/31   17:18
 * Author:   XXXXX@XX.com
 */
public class LIS {
    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    /**
     * 利用二分搜索优化一下lengthOfLIS2
     * @param nums
     * @return
     */
    static int lengthOfLIS(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        //牌堆的数量
        int len = 0;
        //牌顶的数组
        int[] top = new int[nums.length];
        //遍历所有的牌
        for (int num : nums){
            int begin = 0;
            int end = len;
            while (begin < end){
                int mid = (begin + end) >> 1;
                if(num <= top[mid]){
                    end = mid;
                }else {
                    begin = mid + 1;
                }
            }
            top[begin] = num;
            //检查是否需要新建一个牌堆
            if (begin==len) len++;
        }
        return len;
    }


    /**
     * 牌顶牌堆的做法
     * @param nums
     * @return
     */
    static int lengthOfLIS2(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        //牌堆的数量
        int len = 0;
        //牌顶的数组
        int[] top = new int[nums.length];
        //遍历所有的牌
        for (int num : nums){
            int i = 0;
            while (i<len){
                if(top[i] >= num){
                    top[i] = num;
                    break;
                }
                i++;
            }
            if (i==len){//新建一个牌堆
                top[i] = num;
                len++;
            }

        }
        return len;
    }

    /**
     * 动态规划的做法
     * @param nums
     * @return
     */
    static int lengthOfLIS1(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxLength = dp[0];
        for (int i = 1; i < dp.length  ; i++) {
            dp[i] = 1;
            for (int j = 0; j < i ; j++) {
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            maxLength = Math.max(dp[i],maxLength);
        }
        return maxLength;
    }
}

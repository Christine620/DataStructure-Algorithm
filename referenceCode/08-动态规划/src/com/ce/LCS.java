package com.ce;

/**
 * ClassName:    LCS
 * Package:    com.ce
 * Description: 最长公共子序列
 * Datetime:    2021/4/1   16:39
 * Author:   XXXXX@XX.com
 */
public class LCS {
    public static void main(String[] args) {
        System.out.println(lcs(new int[]{1, 4, 5, 9, 10},
                new int[]{1, 4, 9, 10}));

    }

    /**
     * lcs4的优化，dp一维数组的长度用nums1和nums2中长度较小的那个，进一步优化空间
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs(int[] nums1,int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;

        int[] rowsNums, colsNums;
        if (nums1.length > nums2.length){
            colsNums = nums2;
            rowsNums = nums1;
        }else {
            colsNums = nums1;
            rowsNums = nums2;
        }
        int[] dp = new int[colsNums.length + 1];

        for (int i = 1; i <= rowsNums.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colsNums.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if(rowsNums[i-1] == colsNums[j-1]){
                    dp[j] =leftTop + 1;
                }else {
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
            }

        }
        return dp[colsNums.length];

    }

    /**
     * lcs3的优化，dp数组从滚动数组优化成一维数组，降低空间复杂度
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs4(int[] nums1,int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[] dp = new int[nums2.length+1];
        for (int i = 1; i <= nums1.length ; i++) {
            int cur = 0;
            for (int j = 1; j <= nums2.length ; j++) {
                int leftTop = cur;
                cur = dp[j];
                if(nums1[i-1]==nums2[j-1]){
                    dp[j] =leftTop + 1;
                }else {
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
            }

        }
        return dp[nums2.length];

    }
    /**
     * lcs2的优化，dp数组使用滚动数组，降低空间复杂度
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs3(int[] nums1,int[] nums2){
        if(nums1 == null || nums1.length == 0) return 0;
        if(nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[2][nums2.length+1];
        for (int i = 1; i <= nums1.length ; i++) {
            int row = i & 1;
            int prevRow = (i - 1)&1;
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i-1] == nums2[j-1]){
                    dp[row][j] = dp[prevRow][j-1] + 1;
                }else {
                    dp[row][j] = Math.max(dp[prevRow][j],dp[row][j-1]);
                }
            }
        }

        return dp[(nums1.length)&1][nums2.length];
    }

    /**|
     * 求nums1的前i个元素和nums2的前j个元素的最长公共子序列（动态规划实现）
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs2(int[] nums1,int[] nums2){
        if(nums1 == null || nums1.length == 0) return 0;
        if(nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        for (int i = 1; i <= nums1.length ; i++) {
            for (int j = 1; j <= nums2.length ; j++) {
                if(nums1[i-1] == nums2[j-1]){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else {
                    dp[i][j] =  Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];

    }

    static int lcs1(int[] nums1, int[] nums2){
        if(nums1 == null || nums1.length == 0) return 0;
        if(nums2 == null || nums2.length == 0) return 0;
        return lcs1(nums1,nums1.length,nums2,nums2.length);
    }

    /**
     *  求nums1的前i个元素和nums2的前j个元素的最长公共子序列（递归实现）
     * @param nums1
     * @param i
     * @param nums2
     * @param j
     * @return
     */
    static int lcs1(int[] nums1, int i, int[] nums2, int j){
        if( i == 0 || j == 0) return 0;
        if (nums1[i-1] == nums2[j-1]){//即 nums1的第i个元素和nums2的第j个元素是相等的dp(i,j) = dp(i-1,j-1) + 1
            return lcs1(nums1,i-1,nums2,j-1)+1;
        }else {//nums1的第i个元素和nums2的第j个元素不相等 dp(i,j) = Max{dp(i-1,j),dp(i,j-1)}
            return Math.max(lcs1(nums1,i-1,nums2,j), lcs1(nums1,i,nums2,j-1));
        }
    }
}

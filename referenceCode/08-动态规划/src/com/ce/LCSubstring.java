package com.ce;

/**
 * ClassName:    LCSubstring
 * Package:    com.ce
 * Description: 最长公共子串
 * Datetime:    2021/4/7   14:39
 * Author:   XXXXX@XX.com
 */
public class LCSubstring {
    public static void main(String[] args) {
        System.out.println(LCSubstring("ABDCBA", "ABBA"));
    }

    /**
     * 对LCSubstring2的优化，采用一维数组dp[],但是不用leftTop，而是从右往左遍历
     * @param str1
     * @param str2
     * @return
     */
    static int LCSubstring(String str1, String str2){
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if(chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;
        char[] rowChars = chars1;
        char[] colChars = chars2;
        if (chars1.length < chars2.length){
            colChars = chars1;
            rowChars = chars2;
        }
        int[] dp = new int[colChars.length+1];
        int max = 0;
        for (int row = 1; row <= rowChars.length ; row++) {
            for (int col = colChars.length; col >= 1 ; col--) {
                if (rowChars[row-1] == colChars[col-1]){
                    dp[col] = dp[col-1] + 1;
                }else {
                    dp[col] = 0;
                }
                max = Math.max(max,dp[col]);
            }
        }
        return max;

    }

    /**
     * 对LCSubstring1的优化，采用一维数组dp[]
     * @param str1
     * @param str2
     * @return
     */
    static int LCSubstring2(String str1, String str2){
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if(chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;
        char[] rowChars = chars1;
        char[] colChars = chars2;
        if (chars1.length < chars2.length){
            colChars = chars1;
            rowChars = chars2;
        }
        int[] dp = new int[colChars.length+1];
        int max = 0;
        for (int row = 1; row <= rowChars.length ; row++) {
            int cur = 0;
            for (int col = 1; col <= colChars.length ; col++) {
                int leftTop = cur;
                cur = dp[col];
                if (rowChars[row-1] == colChars[col-1]){
                    dp[col] = leftTop + 1;
                }else {
                    dp[col] = 0;
                }
                max = Math.max(max,dp[col]);
            }
        }
        return max;

    }

    static int LCSubstring1(String str1, String str2){
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if(chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;

        int[][] dp = new int[chars1.length+1][chars2.length+1];
        int max = 0;
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length ; j++) {
                if (chars1[i-1]==chars2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else {
                    dp[i][j] = 0;
                }
                max = Math.max(dp[i][j],max);
            }
        }

        return max;
    }
}

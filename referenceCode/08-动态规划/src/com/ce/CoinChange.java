package com.ce;

/**
 * ClassName:    CoinChange
 * Package:    com.ce
 * Description: 练习一：找零钱
 * Datetime:    2021/3/29   13:12
 * Author:   XXXXX@XX.com
 */
public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coins5(7,new int[]{1,5,10,25}));
    }

    /**
     * 通用类型的找零钱，零钱n,还有硬币的面额都是参数可以传的
     * @param n
     * @param faces
     */
    static int coins5(int n, int[] faces){
        if (n<1 || faces == null || faces.length == 0) return -1;
        int[] dp = new  int[n+1];
        for (int i = 1; i <=n ; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces){
                if (i < face) continue;//钱数小于面额数或者dp[i-face]是-1,例如i = 6,虽然大于面额5，但是他的上一个凑的方案dp[6-5]=dp[1]是-1，所以也不行
                if(dp[i-face]< 0 || dp[i-face] >= min) continue;
                min = dp[i-face];
            }
            if (min == Integer.MAX_VALUE){//所要找的钱数小于所有面值的硬币
                dp[i] = -1;
            }else {
                dp[i] = min + 1;
            }

        }
        return dp[n];
    }

    /**
     * 找硬币的具体方案，不光输出所需的最少硬币，还要把具体用了哪些面额的硬币输出出来
     * @param n
     * @return
     */
    static int coins4(int n){
        if (n < 1) return -1;//代表输入的金额不合理
        int[] dp = new int[n+1];
        // faces[i]是凑够i块钱时，最后选择的那枚硬币的面值
        int[] faces = new int[dp.length];
        for (int i = 1; i < dp.length ; i++) {
            int min = Integer.MAX_VALUE;
            if(i >= 1 && dp[i-1]<min){
                min = dp[i-1];
                faces[i] = 1;//即代表找i块钱时，是在找i-1块钱所需要的硬币面值再加一个面值为1 的硬币
            }
            if(i >= 5 && dp[i-5]<min){
                min = dp[i-5];
                faces[i] = 5;//即代表找i块钱时，是在找i-5块钱所需要的硬币面值再加一个面值为5 的硬币
            }
            if(i >= 20 && dp[i-20]<min){
                min = dp[i-20];
                faces[i] = 20;//即代表找i块钱时，是在找i-20块钱所需要的硬币面值再加一个面值为20 的硬币
            }
            if(i >= 25 && dp[i-25]<min){
                min = dp[i-25];
                faces[i] = 25;//即代表找i块钱时，是在找i-25块钱所需要的硬币面值再加一个面值为25的硬币
            }

            dp[i] = min + 1;
            print(faces,i);

        }

        System.out.println("-----------------------------------------------");
        return dp[n];
    }

    static void print(int[] faces,int n){
        System.out.print("["+ n + "] = ");
        while (n>0){
            System.out.print(faces[n]+" ");
            n -= faces[n];
        }
        System.out.println();
    }

    /**
     * 递推(自底向上的调用)
     * @param n
     * @return
     */
    static int coins3(int n){
        if (n < 1) return -1;//代表输入的金额不合理
        int[] dp = new int[n+1];
        for (int i = 1; i < dp.length ; i++) {
            int min = dp[i-1];

            if( i>= 5) min = Math.min(dp[i-5],min);
            if( i>= 20) min = Math.min(dp[i-20],min);
            if( i>= 25) min = Math.min(dp[i-25],min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /**
     * 记忆化搜索(自顶下下调用，将之前算过的子问题的解存储到dp数组中)
     * @param n 代表要找的钱数
     * @return
     */
    static int coins2(int n){
        if ( n < 1) return -1;
        // coins2(16) = 4,代表找16元的钱最少要4个硬币，因此希望dp数组这样存储：dp[16] = 4
        // 因此有多少钱，希望开辟多大的数组，又希望索引想对应，因此dp开辟的大小为n+1
        int[] dp = new int[n+1];
        int[] faces = {1,5,20,25};
        for (int face : faces){
            if(n < face) break;
            dp[face] = 1;
        }

        return coins2(n,dp);
    }

    static int coins2(int n, int[] dp){

        if(n<1) return Integer.MAX_VALUE;
        if (dp[n] == 0){ //即dp[n]目前还没有值，需要计算
            int min1 = Math.min(coins2(n-25,dp),coins2(n-20,dp));
            int min2 = Math.min(coins2(n-5,dp), coins2(n-1,dp));
            dp[n] = Math.min(min1,min2)+1;
        }
        return dp[n];
    }

    /**
     * 暴力递归(自顶向下调用，出现了重叠子问题)
     * @param n 代表要找的钱数
     * @return
     */
    static int coins1(int n){
        if(n <= 0) return Integer.MAX_VALUE;
        if (n==25 || n==20 || n==5 || n==1) return 1;

        int min1 = Math.min(coins1(n-25), coins1(n-20));
        int min2 = Math.min(coins1(n-5), coins1(n-1));
        return Math.min(min1,min2) + 1;
    }
}

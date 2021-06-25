package com.ce;

/**
 * ClassName:    main
 * Package:    com.ce
 * Description:
 * Datetime:    2021/3/28   17:22
 * Author:   XXXXX@XX.com
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums1 = {-2, -1};
        System.out.println(maxSubarray(nums1));
    }


    static int maxSubarray(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        return maxSubarray(nums,0,nums.length);
    }

    /**
     * 求解 [begin,end)中最大子序列的和
     * @param nums
     * @param begin
     * @param end
     * @return
     */
    static int maxSubarray(int[] nums,int begin, int end){//[begin,end)
        if(end-begin < 2){//不足两个元素，即区间只剩下一个元素
            return nums[begin];
        }
        int mid = (begin+end) >> 1;

        // 考虑最大子序列mid左边占一部分，mid右边占一部分
        int leftMax = Integer.MIN_VALUE;
        int leftSum = 0;
        for (int i = mid-1; i >= begin ; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax,leftSum);
        }

        int rightmax= Integer.MIN_VALUE;
        int rightSum = 0;
        for (int i = mid; i < end ; i++) {
            rightSum += nums[i];
            rightmax = Math.max(rightmax,rightSum);
        }

        return Math.max(leftMax+rightmax, //最大子序列左边占一部分，右边占一部分的情况
                    Math.max(
                    maxSubarray(nums,begin,mid),// 左边区间[begin,mid)求出的最大的连续子序列和
                    maxSubarray(nums,mid,end))// 右边区间[mid,end)求出的最大的连续子序列和)
                );
    }

    static int maxSubarray2(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length ; end++) {
                //[begin,end]
                sum += nums[end];
                max = Math.max(max,sum);
            }
        }
        return max;

    }

    /**
     * 求数组nums的最大子数组的和
     * @param nums
     * @return
     */
    static int maxSubarray1(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length ; end++) {
                //[begin,end]
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max,sum);
            }
        }
        return max;
    }
}

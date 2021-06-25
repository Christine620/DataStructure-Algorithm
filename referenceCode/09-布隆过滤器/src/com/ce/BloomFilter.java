package com.ce;

/**
 * ClassName:    BloomFilter
 * Package:    com.ce
 * Description: 布隆过滤器的实现
 * Datetime:    2021/4/13   15:26
 * Author:   XXXXX@XX.com
 */
public class BloomFilter<T> {
    /**
     * 二进制向量的长度m(一共有多少个二进制位)
     */
    private int bitSize;
    /**
     * 哈希函数的个数
     */
    private int hashFunSize;

    /**
     * 二进制向量，long类型是8个字节，代表64位，这里是个long数组，可以代表更多位数，也可使用int型数组，数组的长度取决于bitSize
     * 例如 bitsize=20，bits数组长度为1，因为1个long类型的数就可以表示
     */
    private long[]  bits;

    /**
     *
     * @param n 数据规模
     * @param p 误判率,取值范围(0,1)
     */
    public BloomFilter(int n, double p) {
        if(n <= 0 || p <= 0 || p >= 1){
            throw new IllegalArgumentException(" wrong n or p");
        }
        double ln2 = Math.log(2);
        // 求出二进制向量的长度
        bitSize = (int)(-(n * Math.log(p)/(ln2*ln2)));
        // 求出哈希函数的个数
        hashFunSize = (int)(bitSize * (ln2) / n);

        //System.out.println(bitSize);
        //System.out.println(hashFunSize);
        //bits数组的长度
        bits = new long[(bitSize + Long.SIZE -1) / Long.SIZE];
    }

    /**
     * 添加元素
     * @param value
     */
    public boolean put(T value){
        nullCheck(value);


        // 利用value生成两个整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        boolean result = false;
        for (int i = 0; i < hashFunSize ; i++) {
            // step1 利用哈希函数生成索引index
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0){
                combinedHash = ~ combinedHash;
            }
            int index = combinedHash % bitSize;
            // System.out.println(index);
            // step2 在对应的索引位置的二进制数组的值设置为1(按位或，任何数和1或就是1，和0或就是他本身，因此只需要将要或的值的index位的元素置为1 --> 1左移index)
            if (set(index)) result = true;

        }
        return result;
    }

    /**
     * 判断一个元素是否存在
     * @return
     */
    public boolean contains(T value){
        nullCheck(value);
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;
        for (int i = 0; i < hashFunSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0){
                combinedHash = ~ combinedHash;
            }
            int index = combinedHash % bitSize;
            //查询index位置的2进制位是否为0
            if(!get(index)) return false;
        }
        // 来到这里就是代表所有2进制位数为1
        return true;
    }

    /**
     * 设置index位置的2进制位为1
     * @param index
     */
    private boolean set(int index){
        // 找到对应的long值
        long value = bits[index / Long.SIZE];
        int bitValue = 1 << (index % Long.SIZE);
        boolean result = (value & bitValue) == 0;
        bits[index/Long.SIZE] = value | bitValue;
        return result;
    }

    /**
     * 查看index位置的2进制位是否为1
     * @param index
     * @return true代表1，false代表0
     */
    private boolean get(int index){
        long value = bits[index/Long.SIZE];
         value =  value & (index%Long.SIZE);
         return value != 0;
    }

    /**
     * 检查元素是否为空
     */
    private void nullCheck(T value ){
        if(value == null){
            throw new IllegalArgumentException("Value must not be null.");
        }
    }
}

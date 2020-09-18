package com.convenient.excel;

import java.util.HashMap;
import java.util.Map;

class Solution {


    /**
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int first = 1;
        int second = 2;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int result = solution.climbStairs(6);
        System.out.println(result);
    }

}
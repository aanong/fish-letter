package com.aihaibara.leedcode;

import java.util.Arrays;
import java.util.Comparator;

public class Solution {
    private class LargerNumberComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            System.out.println(a);
            System.out.println(b);
            String order1 = a + b;
            String order2 = b + a;
            System.out.println(order2.compareTo(order1));
            return order2.compareTo(order1);
        }
    }

    public String largestNumber(int[] nums) {
        // Get input integers as strings.
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }

        // Sort strings according to custom comparator.
        Arrays.sort(asStrs, new LargerNumberComparator());

        // If, after being sorted, the largest number is `0`, the entire number
        // is zero.
        if (asStrs[0].equals("0")) {
            return "0";
        }

        // Build largest number from sorted array.
        String largestNumberStr = new String();
        for (String numAsStr : asStrs) {
            largestNumberStr += numAsStr;
        }

        return largestNumberStr;
    }

    public static void main(String[] args) {
        int[] num = {12,9,13,46,13};
        Solution solution = new Solution();
        String s = solution.largestNumber(num);
        System.out.println(s);
    }



}

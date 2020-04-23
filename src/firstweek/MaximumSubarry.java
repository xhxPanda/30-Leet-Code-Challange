package firstweek;

public class MaximumSubarry {
    public int maxSubArray(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }
        int[] dp = new int[length];
        dp[0] = nums[0];
        for (int i = 1; i < length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
        }
        int result = dp[0];
        for (int i = 1; i < length; i++) {
            result = Math.max(result, dp[i]);
        }

        return result;
    }
}

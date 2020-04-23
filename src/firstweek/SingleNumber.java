package firstweek;

public class SingleNumber {

    public int solution(int[] nums) {
        // 利用位运算
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }

}

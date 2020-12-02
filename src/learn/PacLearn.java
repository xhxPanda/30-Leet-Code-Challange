package learn;

public class PacLearn {

    int capacity = 12;
    int weight[] = {2, 3, 4, 5, 6};
    int value[] = {1, 4, 3, 6, 8};
    int result[] = {-1, -1, -1, -1, -1};

    public int handle(int num, int c) {
        int temp = handle(num - 1, c);

        if (weight[num] > c) {
            result[num] = 0;
            return temp;
        }
        int temp2 = value[num] + handle(num - 1, c - weight[num]);
        if (temp > temp2) {
            result[num] = 0;
            return temp;
        }
        result[num] = 1;
        return temp2;
    }

    /**
     *
     * @param num 物品的个数
     * @param c 袋子容积
     */
    private void handle2(int num, int c) {
        int dp[][] = new int[num + 1][c];

        for (int i = 1; i <= num; i++) {
            for (int cw = 1; cw <= c;cw++) {
                if(weight[i-1] <= cw) {
                    dp[i][cw] = Math.max(value[i-1]+dp[i-1][cw - weight[i-1]], dp[i-1][cw]);
                } else {
                    dp[i][cw] = dp[i-1][cw];
                }
            }
        }
        System.out.println("袋子能装的最大价值:" + dp[num][c]);
    }
}

package learn;

import java.util.ArrayList;

/**
 * 求解背包问题：
 * 给定 n 个背包，其重量分别为 w1,w2,……,wn, 价值分别为 v1,v2,……,vn
 * 要放入总承重为 totalWeight 的箱子中，
 * 求可放入箱子的背包价值总和的最大值。
 * <p>
 * NOTE: 使用动态规划法求解 背包问题
 * 设 前 n 个背包，总承重为 j 的最优值为 v[n,j], 最优解背包组成为 b[n];
 * 求解最优值：
 * 1. 若 j < wn, 则 ： v[n,j] = v[n-1,j];
 * 2. 若  j >= wn, 则：v[n,j] = max{v[n-1,j], vn + v[n-1,j-wn]}。
 * <p>
 * 求解最优背包组成：
 * 1. 若 v[n,j] > v[n-1,j] 则 背包 n 被选择放入 b[n],
 * 2. 接着求解前 n-1 个背包放入 j-wn 的总承重中，
 * 于是应当判断 v[n-1, j-wn] VS v[n-2,j-wn], 决定 背包 n-1 是否被选择。
 * 3. 依次逆推，直至总承重为零。
 * <p>
 * 重点： 掌握使用动态规划法求解问题的分析方法和实现思想。
 * 分析方法： 问题实例 P(n) 的最优解S(n) 蕴含 问题实例 P(n-1) 的最优解S(n-1);
 * 在S(n-1)的基础上构造 S(n)
 * 实现思想： 自底向上的迭代求解 和 基于记忆功能的自顶向下递归
 */
public class KnapsackProblem {
    /**
     * 指定背包
     */
    private Knapsack[] bags;

    /**
     * 总承重
     */
    private int totalWeight;

    /**
     * 给定背包数量
     */
    private int n;

    /**
     * 前 n 个背包，总承重为 totalWeight 的最优值矩阵
     */
    private int[][] bestValues;

    /**
     * 前 n 个背包，总承重为 totalWeight 的最优值
     */
    private int bestValue;

    /**
     * 前 n 个背包，总承重为 totalWeight 的最优解的物品组成
     */
    private ArrayList<Knapsack> bestSolution;

    public KnapsackProblem(Knapsack[] bags, int totalWeight) {
        this.bags = bags;
        this.totalWeight = totalWeight;
        this.n = bags.length;
        if (bestValues == null) {
            bestValues = new int[n + 1][totalWeight + 1];
        }
    }

    public void solve() {
        for (int j = 0; j < totalWeight; j++) {
            for (int i = 0; i <= n; i++) {
                if(i == 0 || j == 0) {
                    bestValues[i][j] = 0;
                } else {
                    // 如果第i个背包不大于总承重，则最优解要么是包含第i个背包的最优解
                    // 要么是不包含第i个背包的最优解，区两者最大值，这里采用了分类讨论
                    int iweight = bags[i-1].getWeight();
                    int ivalue = bags[i-1].getValue();
                    bestValues[i][j] = Math.max(bestValues[i][j], ivalue + bestValues[i-1][j-iweight]);
                }
            }
        }

        // 求解背包组成
        if (bestSolution == null) {
            bestSolution = new ArrayList<>();
        }
        int tempWeight = totalWeight;
        for (int i = n; i >= 1;i++) {
            if (bestValues[i][tempWeight] > bestValues[i-1][tempWeight]) {
                bestSolution.add(bags[i-1]);
                tempWeight -= bags[i-1].getWeight();
            }
            if(tempWeight == 0) {
                break;
            }
        }
    }
}

class Knapsack {
    /**
     * 背包重量
     */
    private int weight;

    /**
     * 背包物品价值
     */
    private int value;

    /***
     * 构造器
     */
    public Knapsack(int weight, int value) {
        this.value = value;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}
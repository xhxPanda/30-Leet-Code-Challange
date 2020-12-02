package graphlearn.mtslearn;

import java.util.ArrayList;

/**
 * 最小生成树学习
 */
public class MTSLearn {

    /**
     * @param num    顶点数
     * @param weight 权值
     */
    public void primLearn(int num, float[][] weight) {
        float[] lowcost = new float[num + 1]; // 到新集合的最小权

        int[] closest = new int[num + 1]; // 代表与s集合相连的最小权边的点

        boolean[] s = new boolean[num + 1];

        s[1] = true;// 把第一个点放入s集合

        for (int i = 2; i <= num; i++) {
            lowcost[i] = weight[1][i];
            closest[i] = 1;
            s[i] = false;
        }

        for (int i = 1; i <= num; i++) {
            float min = Float.MAX_VALUE;
            int j = 1;
            for (int k = 2; k <= num; k++) {
                if ((lowcost[k] < min) && (!s[k])) { // 根据最小权加入新点
                    min = lowcost[k];
                    j = k;
                }
            }

            s[j] = true;

            for (int k = 2; k <= num; k++) {
                if ((weight[j][k] < lowcost[k]) && !s[k]) {
                    lowcost[k] = weight[j][k];
                    closest[k] = j;
                }
            }
        }
    }

    int n = 5;
    int max = 100;
    int[] parent = new int[max];

    public void kruskalLearn() {
        ArrayList<Edge> edges = new ArrayList<>(0);
        ArrayList<Edge> targer = new ArrayList<>();

        float theMax = Float.MAX_VALUE;

        int i = 0;
        Edge temp = null;
        while (i < n - 1 && edges.size() > 0) {
            double min = Double.MIN_VALUE;
            for (int j = 0; j < edges.size(); j++) {
                Edge tt = edges.get(j);
                if (tt.weight < min) {
                    temp = tt;
                }
            }
            // 构建一棵树
            int jj = parent[temp.start];
            int kk = parent[temp.end];

            if (jj != kk) {
                i++;                    // 以end作为下一条边的star继续寻找下一个
                targer.add(temp);       // 将找到的边放入目标集合中
                union(jj, kk);
            }
            edges.remove(temp); // 把临时边删除
        }
        for (int k = 0; k < targer.size(); k++) {
            Edge e = targer.get(k);
        }
    }

    public void union(int j, int k) {
        for (int i = 1; i <= n; i++) {
            if (parent[i] == j) {
                parent[i] = k;
            }
        }
    }

}

class Edge {
    public int start;
    public int end;
    public double weight;
}

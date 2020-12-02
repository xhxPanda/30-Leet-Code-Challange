package learn;

import java.util.LinkedList;
import java.util.List;

public class CutVerEdge {
    private boolean[] marked;

    /**
     * low数组的下标表示顶点的编号
     * 数组中的值表示DFS中该顶点不通过父顶点能访问到的祖先顶点中最小的顺序值
     */
    private int[] low;
    /**
     * dfn数组表示顶点的编号
     * 数组中的值表示该顶点在深度优先遍历中的遍历顺序
     */
    private int[] dfn;
    /**
     * 下标表示顶点的编号，
     * 数组中的值表示该顶点的父顶点编号
     * 它主要用于更新low值时排除父顶点
     */
    private int[] parent;

    /**
     * 是否割点
     */
    private boolean[] cutVer;

    /**
     * 存储割点集的容器
     */
    private List<Integer> listV;

    /**
     * 存储割边的容器，容器中存储的是数组
     */
    private List<int[]> listE;

    private UndirectedGraph ug;
    private int visitOrder; // 时间戳变量

    public CutVerEdge(UndirectedGraph ug){

        this.ug = ug;

        marked = new boolean[ug.getVtxNum()];

        low = new int[ug.getVtxNum()];
        dfn = new int[ug.getVtxNum()];
        parent = new int[ug.getVtxNum()];

        cutVer = new boolean[ug.getVtxNum()];

        listV = new LinkedList<Integer>();
        listE = new LinkedList<int[]>();

        /*调用深度优先遍历，求解各个顶点的dfn值和low值*/
        dfs();
    }

    private void dfs() {
        int childTree = 0;
        marked[0] = true;
        visitOrder = 1;
        parent[0] = -1;

        for (Edge e : ug.getAdj()[0]) {
            int current = e.getTo();
            if(!marked[current]) {
                marked[current] = true;
                parent[current] = 0;
                dfs0(current);
                // 根顶点相连的边是否为桥
                if(low[current] > dfn[0]){
                    listE.add(new int[]{0, current});
                }
                childTree++;
            }
        }
        if (childTree >= 2) { // 根顶点是割点的条件
            cutVer[0] = true;
        }
    }

    /**
     * 除了根节点的其它情况
     * @param current
     */
    private void dfs0(int current) {
        dfn[current] = low[current] = ++visitOrder;
        for (Edge e : ug.getAdj()[current]) {
            int next = e.getTo();
            if(!marked[next]) {
                marked[next] = true;
                parent[next] = current;
                dfs0(next);
                low[current] = Math.min(low[current], low[next]);

                if(low[next] >= dfn[current]) {
                    cutVer[current] = true;

                    // 判断桥
                    if(low[next] > dfn[current]) {
                        listE.add(new int[]{current, next});
                    }
                }
            } else if(parent[current] != next && dfn[next]<dfn[current]) {
                low[current] = Math.min(low[current], dfn[next]);
            }
        }
    }
}

/*定义无向图*/
class UndirectedGraph{
    private int vtxNum;/*顶点数量*/
    private int edgeNum;/*边数量*/

    /*邻接表*/
    private LinkedList<Edge>[] adj;

    /*返回顶点个数*/
    public int getVtxNum(){
        return vtxNum;
    }

    /*返回边的数量*/
    public int getEdgeNum(){
        return edgeNum;
    }

    public LinkedList<Edge>[] getAdj() {
        return adj;
    }
}

class Edge{
    /*边起始顶点*/
    private final int from;

    /*边终结顶点*/
    private final int to;

    public Edge(int from, int to){
        this.from = from;
        this.to= to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}



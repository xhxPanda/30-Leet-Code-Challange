package learn;

public class EightQueen {
    private int[][] qipan = new int[8][8];
    private int count = 0;
    private int step = 1;
    private int vis[][] = new int[3][20]; // 三种情况对角线、行有无被占用

    public EightQueen() {
        //初始化棋盘
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                qipan[i][j] = 0;
            }
        }
        for (int i = 0; i < vis.length; i++) {
            for (int j = 0; j < vis[i].length; j++) {
                vis[i][j] = 0;
            }
        }
        move(0);
        //从第0个位置开始放第一个皇后，没得说，只要求出皇后的位置，和摆放的数量即可。
        System.out.println("count = " + count);
    }

    public void move(int x) {
        int next_x = x + 1;
        if (step > 8) {
            count++;
        } else {
            for (int i = 0; i < 8; i++) {
                if (vis[0][1] == 0 && vis[1][x+i] == 0 && vis[2][x-i+8] == 0) {
                    qipan[x][i] = step;
                    step++;
                    vis[0][i] = 1;
                    vis[i][x+i]=1;
                    vis[2][x-i+8]=1;
                    move(next_x);
                    qipan[x][i] = 0;
                    vis[0][i] = 0;
                    vis[i][x+i]=0;
                    vis[2][x-i+8]=0;
                    step--;
                }
            }
        }
    }
}

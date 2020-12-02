package learn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSPAntLearn {
    private Ant[] ants; // 蚂蚁
    private int antNum; // 蚂蚁数量
    private int cityNum; // 城市数量
    private int MAX_GEN; // 运行代数
    private float[][] pheromone; // 信息素矩阵
    private int[][] distance; // 距离矩阵
    private int bestLength; // 最佳长度
    private int[] bestTour; // 最佳路径
    // 三个参数
    private float alpha;
    private float beta;
    private float rho;

    public void slove() {
        for (int g = 0; g < MAX_GEN; g++) {
            // antNum只蚂蚁
            for (int i = 0; i < antNum; i++) {
                for (int j = 1; j < cityNum; j++) {
                    ants[i].selectNextCity(pheromone);
                }
                // 把这只蚂蚁的起始城市加入其禁忌表中
                ants[i].getTabu().add(ants[i].getFirstCity());
                // 查看这只蚂蚁行走路径距离是否比当前距离优秀
                if (ants[i].getTourLength() < bestLength) {
                    // 比当前优秀则拷贝优秀TSP路径
                    bestLength = ants[i].getTourLength();
                    for (int k = 0; k < cityNum + 1; k++) {
                        bestTour[k] = ants[i].getTabu().get(k);
                    }
                }
                // 更新这只蚂蚁的信息数变化矩阵，对称矩阵
                for (int j = 0; j < cityNum; j++) {
                    ants[i].getDelta()[ants[i].getTabu().get(j)][ants[i]
                            .getTabu().get(j + 1)] = (float) (1. / ants[i]
                            .getTourLength());
                    ants[i].getDelta()[ants[i].getTabu().get(j + 1)][ants[i]
                            .getTabu().get(j)] = (float) (1. / ants[i]
                            .getTourLength());
                }
            }
            // 更新信息素
            updatePheromone();
            // 重新初始化蚂蚁
            for (int i = 0; i < antNum; i++) {
                ants[i].init(distance, alpha, beta);
            }
        }
    }

    // 更新信息素
    private void updatePheromone() {
        // 信息素挥发
        for (int i = 0; i < cityNum; i++)
            for (int j = 0; j < cityNum; j++)
                pheromone[i][j] = pheromone[i][j] * (1 - rho);
        // 信息素更新
        for (int i = 0; i < cityNum; i++) {
            for (int j = 0; j < cityNum; j++) {
                for (int k = 0; k < antNum; k++) {
                    pheromone[i][j] += ants[k].getDelta()[i][j];
                }
            }
        }
    }

}

class Ant implements Cloneable {
    private List<Integer> tabu; // 禁忌表
    private List<Integer> allowedCities; // 允许搜索的城市
    private float[][] delta; // 信息数变化矩阵
    private int[][] distance; // 距离矩阵
    private float alpha;
    private float beta;

    private int tourLength; // 路径长度
    private int cityNum; // 城市数量
    private int firstCity;
    private int currentCity; // 当前城市

    public Ant() {
        cityNum = 30;
        tourLength = 0;
    }

    public void init(int[][] distance, float a, float b) {
        alpha = a;
        beta = b;
        allowedCities = new ArrayList<>(cityNum);
        tabu = new ArrayList<>(cityNum);
        this.distance = distance;
        delta = new float[cityNum][cityNum];
        for (int i = 0; i < cityNum; i++) {
            allowedCities.add(i);
            for (int j = 0; j < cityNum; j++) {
                delta[i][j] = 0;
            }
        }

        // 随机挑选一个城市作为初始城市
        Random random = new Random();
        firstCity = random.nextInt(cityNum);
        allowedCities.remove(firstCity);

        // 将初始城市添加到禁忌表
        tabu.add(firstCity);
        currentCity = firstCity;
    }

    public void selectNextCity(float[][] pheromone) {
        float[] p = new float[cityNum];
        float sum = 0;

        for (int i : allowedCities) {
            sum += Math.pow(pheromone[currentCity][i], alpha)
                    * Math.pow(1.0 / distance[currentCity][i], beta);
        }
        // 计算概率矩阵
        for (int i = 0; i < cityNum; i++) {
            boolean flag = false;

            for (int j : allowedCities) {
                if (i == j) {
                    p[i] = (float) (Math.pow(pheromone[currentCity][i], alpha)
                            * Math.pow(1.0 / distance[currentCity][i], beta)) / sum;
                    flag = true;
                    break;
                }
            }
            if (flag = false) {
                p[i] = 0;
            }
        }

        // 随机下一个城市
        Random random = new Random();
        int selectP = random.nextInt(allowedCities.size());

        int chooseCity = allowedCities.get(selectP);
        tabu.add(chooseCity);
        currentCity = chooseCity;
    }

    private int calculateTourLength() {
        int len = 0;

        for (int i = 0; i < cityNum; i++) {
            len += distance[this.tabu.get(i)][this.tabu.get(i + 1)];
        }
        return len;
    }

    public List<Integer> getTabu() {
        return tabu;
    }

    public void setTabu(List<Integer> tabu) {
        this.tabu = tabu;
    }

    public List<Integer> getAllowedCities() {
        return allowedCities;
    }

    public void setAllowedCities(List<Integer> allowedCities) {
        this.allowedCities = allowedCities;
    }

    public float[][] getDelta() {
        return delta;
    }

    public void setDelta(float[][] delta) {
        this.delta = delta;
    }

    public int[][] getDistance() {
        return distance;
    }

    public void setDistance(int[][] distance) {
        this.distance = distance;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getBeta() {
        return beta;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }

    public int getTourLength() {
        return tourLength;
    }

    public void setTourLength(int tourLength) {
        this.tourLength = tourLength;
    }

    public int getCityNum() {
        return cityNum;
    }

    public void setCityNum(int cityNum) {
        this.cityNum = cityNum;
    }

    public int getFirstCity() {
        return firstCity;
    }

    public void setFirstCity(int firstCity) {
        this.firstCity = firstCity;
    }

    public int getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(int currentCity) {
        this.currentCity = currentCity;
    }

}

package graphlearn.graphcolor;

import java.util.*;

public class GraphColorLearn {
    /**
     * 给点涂色的过程。
     *
     * @param rowDataMap 点阵数据。这个数据已经有值了。不用再取获取。其中的 key 就代表点，
     *                   value 是个 Set，代表和 key 里面的点相连的其他点。
     * @return 返回的结果也是个 map，里面的 key 代表点，value 代表点的颜色。
     */
    private Map<Integer, Integer> process(Map<Integer, Set<Integer>> rowDataMap) {
        //着色情况
        Map<Integer, Integer> resultMap = new HashMap<>();
        int color = 0;


        return resultMap;
    }

    /**
     * @param rowDataMap 点阵
     * @param resultMap  结果
     * @param color      颜色
     */
    public void loop(Map<Integer, Set<Integer>> rowDataMap, Map<Integer, Integer> resultMap, int color) {
        while (rowDataMap.size() > 0) {
            // 把未着色的节点列表U按照各节点未着色的临界点的数目从打到小排序
            PointModel[] sortU = sortU(rowDataMap);
            List<PointModel> pointModels = new ArrayList<>(0);

            for (int i = 0; i < sortU.length; i++) {
                PointModel pm = sortU[i];

                if(check(pm, pointModels, rowDataMap)) {
                    continue;
                } else {
                    pointModels.add(pm);
                    // 上色
                    resultMap.put(pm.getKey(), color);
                }
            }
            remove(rowDataMap, pointModels);
            color = color + 1;
            loop(rowDataMap, resultMap, color);
        }
    }

    /**
     * 移除
     * @param rowDataMap
     * @param paintPoint
     */
    private Map<Integer, Set<Integer>>  remove(Map<Integer, Set<Integer>> rowDataMap, List<PointModel> paintPoint) {
        if(paintPoint.size()>0){
            for(int i=0; i<paintPoint.size(); i++){
                rowDataMap.remove(paintPoint.get(i).getKey());
            }
        }
        return rowDataMap;
    }

    /**
     * 判断是否相邻
     *
     * @param pm
     * @param pointModels
     * @param rowDataMap
     * @return
     */
    private boolean check(PointModel pm, List<PointModel> pointModels, Map<Integer, Set<Integer>> rowDataMap) {
        int key = pm.getKey();
        // 获取key所有相邻接节点
        Set<Integer> set = rowDataMap.get(key);
        for (int i = 0; i< pointModels.size(); i++) {
            if(set.contains(pointModels.get(i).getKey())) {
                return true;
            }
        }
        return false;
    }

    private PointModel[] sortU(Map<Integer, Set<Integer>> rowDataMap) {
        Set<Integer> u = rowDataMap.keySet();
        int index = 0;
        PointModel[] pms = new PointModel[rowDataMap.size()];

        for (Integer key : u) {
            Set<Integer> values = rowDataMap.get(key);
            PointModel pointModel = new PointModel();
            pointModel.setKey(key);
            pointModel.setPointSize(values.size());
            pms[index] = pointModel;
            index = index + 1;
        }
        quick_sort(pms, 0, pms.length - 1);

        return pms;
    }

    //快速排序
    private void quick_sort(PointModel[] pms, int l, int r) {
        if (l < r) {
            int i = l, j = r;
            PointModel x = pms[l];
            while (i < j) {
                while (i < j && pms[j].getPointSize() <= x.getPointSize()) { // 从右向左找第一个小于x的数
                    j--;
                }
                if (i < j) {
                    pms[i++] = pms[j];
                }

                while (i < j && pms[i].getPointSize() > x.getPointSize()) { // 从左向右找第一个大于等于x的数
                    i++;
                }
                if (i < j) {
                    pms[j--] = pms[i];
                }
            }
            pms[i] = x;
            quick_sort(pms, l, i - 1); // 递归调用
            quick_sort(pms, i + 1, r);
        }
    }
}

class PointModel {
    /**
     *
     */
    private Integer key;
    /**
     * 邻接节点数目
     */
    private Integer pointSize;


    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getPointSize() {
        return pointSize;
    }

    public void setPointSize(Integer pointSize) {
        this.pointSize = pointSize;
    }
}

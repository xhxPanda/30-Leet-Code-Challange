package firstweek;

import java.util.HashSet;
import java.util.Set;

public class CountingElements {
    public int countElements(int[] arr) {
        Set<Integer> set = new HashSet<>(arr.length);
        for(int i=0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        int result = 0;
        for(int i=0; i < arr.length; i++) {
            int a = arr[i] + 1;
            if(set.contains(a)){
                result += 1;
            }
        }
        return result;
    }
}

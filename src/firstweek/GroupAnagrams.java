package firstweek;

import java.util.*;

public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> msl = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            char[] sc = strs[i].toCharArray();
            Arrays.sort(sc);
            String ns = new String(sc);
            msl.putIfAbsent(ns, new ArrayList<String>());
            msl.get(ns).add(strs[i]);
        }

        return new ArrayList(msl.values());
    }
}

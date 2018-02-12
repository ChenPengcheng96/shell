package main;

import java.util.HashMap;
import java.util.Map;

public class ReDirection {
    public static Map<String, Integer> getRdMap() {
        Map<String, Integer> rdMap = new HashMap<>();
        rdMap.put(">", 1);
        rdMap.put(">>", 2);
        return rdMap;
    }
}

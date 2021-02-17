package backend.web.util;

import java.util.Comparator;
import java.util.Map;

public class MapComparator implements Comparator<Map<String, String>> {
    private final String key;

    public MapComparator(String key) {
        this.key = key;
    }

    public int compare(Map<String, String> first, Map<String, String> second) {
        String firstValue = first.get(key);
        String secondValue = second.get(key);
        return firstValue.compareTo(secondValue);
    }
}

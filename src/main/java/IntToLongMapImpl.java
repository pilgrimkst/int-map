import java.util.HashMap;
import java.util.Map;

public class IntToLongMapImpl implements IntToLongMap {
    private final Map<Integer, Long> map = new HashMap<Integer, Long>();

    public long get(int key) {
        Long aLong = map.get(key);
        return checkNotNull(aLong, NULL_VALUE);
    }

    public long put(int key, long value) {
        Long aLong = map.put(key, value);
        return checkNotNull(aLong, NULL_VALUE);
    }

    private long checkNotNull(Long aLong, int defaultValue) {
        return aLong != null ? aLong : defaultValue;
    }

    public int size() {
        return map.size();
    }
}

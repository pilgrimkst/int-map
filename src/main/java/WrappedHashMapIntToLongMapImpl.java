import java.util.HashMap;
import java.util.Map;

public class WrappedHashMapIntToLongMapImpl implements IntToLongMap {
    private final Map<Integer, Long> map = new HashMap<Integer, Long>();

    public long get(int key) {
        Long aLong = map.get(key);
        return CommonUtils.checkNotNull(aLong, DEFAULT_VALUE);
    }

    public long put(int key, long value) {
        Long aLong = map.put(key, value);
        return CommonUtils.checkNotNull(aLong, DEFAULT_VALUE);
    }

    public int size() {
        return map.size();
    }
}

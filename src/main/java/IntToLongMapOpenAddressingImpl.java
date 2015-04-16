import java.util.Arrays;

public class IntToLongMapOpenAddressingImpl implements IntToLongMap {
    public static final int KEY_NOT_FOUND = Integer.MIN_VALUE + 1;
    public static final int INITIAL_CAPACITY = 32;
    public static final double MAX_LOAD_FACTOR = 0.5;
    private int[] keys = new int[INITIAL_CAPACITY];
    private long[] values = new long[INITIAL_CAPACITY];

    private int size = 0;

    public IntToLongMapOpenAddressingImpl() {
        fillWithDefaultValues(keys, values);
    }

    public long get(int key) {
        int index = findMatchingKeyIndex(k -> k == key, key, keys);
        if (index != KEY_NOT_FOUND && keys[index] != UNDEFINED_KEY) {
            return values[index];
        }

        return IntToLongMap.UNDEFINED_VALUE;
    }

    public long put(int key, long value) {
        if (isReballanceRequired()) {
            increaseBucketSizeTo(keys.length * 2);
        }

        return putInternal(key, value, keys, values);
    }

    private void fillWithDefaultValues(int[] keys, long[] values) {
        Arrays.fill(keys, UNDEFINED_KEY);
        Arrays.fill(values, UNDEFINED_VALUE);
    }

    private long putInternal(int key, long value, int[] keys, long[] values) {
        int index = findMatchingKeyIndex(k -> k == key || k == UNDEFINED_KEY, key, keys);

        if (keys[index] == UNDEFINED_KEY) {
            size++;
        }

        return writeValue(index, key, value, keys, values);
    }

    private long writeValue(int index, int key, long value, int[] keys, long[] values) {
        long oldValue = values[index];
        keys[index] = key;
        values[index] = value;
        return oldValue;
    }

    private boolean isReballanceRequired() {
        return size / keys.length >= MAX_LOAD_FACTOR;
    }

    protected void increaseBucketSizeTo(int newSize) {
        int[] newKeys = new int[newSize];
        long[] newValues = new long[newSize];
        fillWithDefaultValues(newKeys, newValues);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != UNDEFINED_KEY) {
                putInternal(keys[i], values[i], newKeys, newValues);
            }
        }

        keys = newKeys;
        values = newValues;
    }

    public int size() {
        return size;
    }

    private int getKeyHash(int key, int[] keys) {
        return key % keys.length;
    }

    private int findMatchingKeyIndex(KeyPredicate predicate, int key, int[] keys) {
        int start = getKeyHash(key, keys);
        for (int i = start; i < keys.length; i++) {
            if (predicate.matches(keys[i])) return i;
        }

        for (int i = 0; i < start; i++) {
            if (predicate.matches(keys[i])) return i;
        }

        return KEY_NOT_FOUND;
    }

    private interface KeyPredicate {
        boolean matches(int k);
    }

}

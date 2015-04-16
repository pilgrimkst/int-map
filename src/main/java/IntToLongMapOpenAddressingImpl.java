import java.util.Arrays;

public class IntToLongMapOpenAddressingImpl implements IntToLongMap {
    public static final int NON_EXISTENT_INDEX = Integer.MIN_VALUE + 1;
    public static final int INITIAL_CAPACITY = 16;
    public static final double MAX_LOAD_FACTOR = 0.75;
    private int[] keys = new int[INITIAL_CAPACITY];
    private long[] values = new long[INITIAL_CAPACITY];

    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    public IntToLongMapOpenAddressingImpl() {
        fillWithDefaultValues(keys, values);
    }

    private void fillWithDefaultValues(int[] keys, long[] values) {
        Arrays.fill(keys, DEFAULT_KEY);
        Arrays.fill(values, DEFAULT_VALUE);
    }

    public long get(int key) {
        int keyIndex = getIndexForExistingKey(key, keys);
        if (indexFound(keyIndex) && isExistingKey(keyIndex)) {
            return values[keyIndex];
        }

        return IntToLongMap.DEFAULT_VALUE;
    }

    private boolean indexFound(int keyIndex) {
        return NON_EXISTENT_INDEX != keyIndex;
    }

    private boolean isExistingKey(int keyIndex) {
        return keys[keyIndex] != DEFAULT_KEY;
    }

    public long put(int key, long value) {
        if (isReballanceRequired()) {
            increaseBucketSizeTo(capacity * 2);
        }

        return putInternal(key, value, keys, values);
    }

    private long putInternal(int key, long value, int[] keys, long[] values) {
        int index = findIndexForKey(key, keys);
        int k = keys[index];
        if (k == DEFAULT_KEY) {
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

    private int findIndexForKey(int key, int[] keys) {
        return findMatchingKeyIndex(k -> k == key || k == DEFAULT_KEY, getKeyHash(key, keys), keys);
    }

    private boolean isReballanceRequired() {
        return size / keys.length >= MAX_LOAD_FACTOR;
    }

    protected void increaseBucketSizeTo(int newSize) {
        int[] newKeys = new int[newSize];
        long[] newValues = new long[newSize];
        fillWithDefaultValues(newKeys, newValues);
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != DEFAULT_KEY) {
                putInternal(keys[i], values[i], newKeys, newValues);
            }
        }

        keys = newKeys;
        values = newValues;
        capacity = newSize;
    }

    public int size() {
        return size;
    }

    private int getIndexForExistingKey(int key, int[] keys) {
        int start = getKeyHash(key, keys);
        return findMatchingKeyIndex(k -> k == key, start, keys);
    }

    private int getKeyHash(int key, int[] keys) {
        return key % keys.length;
    }

    private int findMatchingKeyIndex(KeyPredicate predicate, int start, int[] keys) {
        for (int i = start; i < keys.length; i++) {
            if (predicate.matches(keys[i])) return i;
        }

        for (int i = 0; i < start; i++) {
            if (predicate.matches(keys[i])) return i;
        }

        return NON_EXISTENT_INDEX;
    }

    private interface KeyPredicate {
        boolean matches(int k);
    }

}

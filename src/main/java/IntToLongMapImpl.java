import java.util.HashSet;
import java.util.Set;

public class IntToLongMapImpl implements IntToLongMap {
    public static final int INITIAL_CAPACITY = 16;
    public static final double MAX_LOAD_FACTOR = 2.0;
    public Bucket[] buckets = new Bucket[INITIAL_CAPACITY];
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    public long get(int key) {
        int bucketNumber = getBucketIndexForKey(key, capacity);
        Bucket bucket = buckets[bucketNumber];
        if (bucket != null) {
            IntToLongEntry entry = bucket.entryByKey(key);
            if (entry != null) return entry.getValue();
        }

        return IntToLongMap.DEFAULT_VALUE;
    }

    public long put(int key, long value) {

        if (isReballanceRequired()) {
            increaseBucketSizeTo(capacity * 2);
        }

        return putInternal(key, value, buckets);
    }

    private long putInternal(int key, long value, Bucket[] buckets) {
        int index = getBucketIndexForKey(key, buckets.length);

        Bucket b = buckets[index];

        if (b == null) {
            buckets[index] = new Bucket(new IntToLongEntry(key, value));
            size++;
            return IntToLongMap.DEFAULT_VALUE;
        } else {
            long set = b.set(key, value);
            if (set == IntToLongMap.DEFAULT_VALUE) size++;
            return set;
        }
    }

    private boolean isReballanceRequired() {
        return size / buckets.length >= MAX_LOAD_FACTOR;
    }

    protected void increaseBucketSizeTo(int newBucketSize) {
        Bucket[] newBuckets = new Bucket[newBucketSize];
        Set<IntToLongEntry> entrys = entrySet();

        for (IntToLongEntry entry : entrys) {
            putInternal(entry.getKey(), entry.getValue(), newBuckets);
        }

        buckets = newBuckets;
        capacity = newBuckets.length;
        size = entrys.size();

    }

    public int size() {
        return size;
    }

    private Set<IntToLongEntry> entrySet() {
        Set<IntToLongEntry> entrys = new HashSet<>();
        for (Bucket e : buckets) {
            entrys.addAll(e.entrySet());
        }
        return entrys;
    }

    private int getBucketIndexForKey(int key, int capacity) {
        return key % capacity;
    }


}

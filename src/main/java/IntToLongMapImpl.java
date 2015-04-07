import com.sun.istack.internal.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class IntToLongMapImpl implements IntToLongMap {
    public static final int INITIAL_CAPACITY = 10;
    private static final double MAX_LOAD_FACTOR = 2.0;
    private static final IntToLongEntry EMPTY_ENTRY = new IntToLongEntry(IntToLongMap.DEFAULT_KEY, IntToLongMap.DEFAULT_VALUE);
    public IntToLongEntry[] buckets = new IntToLongEntry[INITIAL_CAPACITY];
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    public long get(int key) {
        return entryByKey(key, buckets)
                .orElse(EMPTY_ENTRY)
                .getValue();
    }

    public long put(int key, long value) {
        int index = getBucketIndexForKey(key, capacity);
        IntToLongEntry bucket = buckets[index];
        if (bucket == null) {
            buckets[index] = new IntToLongEntry(key, value);
//            rebalanceBucketsIfRequired();
            size++;
            return EMPTY_ENTRY.getValue();
        } else {
            Optional<IntToLongEntry> entry = entryByKey(bucket, key);
            if (entry.isPresent()) {
                IntToLongEntry existingEntry = entry.get();
                long oldVal = existingEntry.getValue();
                existingEntry.setValue(value);
                return oldVal;
            } else {
                IntToLongEntry newEntry = new IntToLongEntry(key, value);
                IntToLongEntry lastEntryInBucket = findLastEntryInBucket(bucket);
                lastEntryInBucket.setNext(newEntry);
                size++;
                return EMPTY_ENTRY.getValue();
            }
        }
    }

    private void rebalanceBucketsIfRequired() {
        if (size / buckets.length >= MAX_LOAD_FACTOR) {
            increaseBucketSizeTo(capacity * 2);
        }
    }

    private void increaseBucketSizeTo(int newBucketSize) {
        IntToLongEntry[] newBuckets = new IntToLongEntry[newBucketSize];
        Set<IntToLongEntry> entrys = entrySet();

    }

    private Set<IntToLongEntry> entrySet() {
        Set<IntToLongEntry> entrys = new HashSet<>();
        for (IntToLongEntry e : buckets) {
            do {
                entrys.add(e);
            } while (e.hasNext());
        }
        return entrys;
    }

    private IntToLongEntry findLastEntryInBucket(IntToLongEntry bucket) {
        return !bucket.hasNext() ? bucket : findLastEntryInBucket(bucket.getNext());
    }

    public int size() {
        return size;
    }

    private Optional<IntToLongEntry> entryByKey(int key, IntToLongEntry[] buckets) {
        IntToLongEntry entry = getBucketByKey(key, buckets);
        return entryByKey(entry, key);
    }

    private Optional<IntToLongEntry> entryByKey(IntToLongEntry startEntry, int key) {
        if (startEntry == null) return Optional.empty();
        return startEntry.getKey() == key ? Optional.of(startEntry) : entryByKey(startEntry.next, key);
    }

    @Nullable
    private IntToLongEntry getBucketByKey(int key, IntToLongEntry[] buckets) {
        int bucketNumber = getBucketIndexForKey(key, capacity);
        return buckets[bucketNumber];
    }

    private int getBucketIndexForKey(int key, int capacity) {
        return key % capacity;
    }

    private static class IntToLongEntry {
        private final int key;
        private long value;
        private IntToLongEntry next;

        public IntToLongEntry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public IntToLongEntry getNext() {
            return next;
        }

        public void setNext(IntToLongEntry next) {
            this.next = next;
        }

        public boolean hasNext() {
            return next != null;
        }

        public int getKey() {
            return key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
}

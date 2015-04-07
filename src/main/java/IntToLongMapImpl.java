import com.sun.istack.internal.Nullable;

import java.util.Optional;

public class IntToLongMapImpl implements IntToLongMap {
    public static final int INITIAL_CAPACITY = 10;
    private static final IntToLongEntry EMPTY_ENTRY = new IntToLongEntry(IntToLongMap.DEFAULT_KEY, IntToLongMap.DEFAULT_VALUE);
    public IntToLongEntry[] buckets = new IntToLongEntry[INITIAL_CAPACITY];
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    public long get(int key) {
        return entryByKey(key)
                .orElse(EMPTY_ENTRY)
                .getValue();
    }

    public long put(int key, long value) {
        int index = getBucketIndexForKey(key);

        IntToLongEntry bucket = buckets[index];
        if (bucket == null) {
            buckets[index] = new IntToLongEntry(key, value);
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

    private IntToLongEntry findLastEntryInBucket(IntToLongEntry bucket) {
        return !bucket.hasNext() ? bucket : findLastEntryInBucket(bucket.getNext());
    }

    public int size() {
        return size;
    }

    private Optional<IntToLongEntry> entryByKey(int key) {
        IntToLongEntry entry = getBucketByKey(key);
        return entryByKey(entry, key);
    }

    private Optional<IntToLongEntry> entryByKey(IntToLongEntry startEntry, int key) {
        if (startEntry != null) {
            do {
                if (startEntry.getKey() == key) {
                    return Optional.of(startEntry);
                }
                startEntry = startEntry.next;
            } while (startEntry.hasNext());
        }

        return Optional.empty();
    }

    @Nullable
    private IntToLongEntry getBucketByKey(int key) {
        int bucketNumber = getBucketIndexForKey(key);
        return buckets[bucketNumber];
    }

    private int getBucketIndexForKey(int key) {
        return key % capacity;
    }

    private static class IntToLongEntry {
        private int key;
        private long value;
        private IntToLongEntry next;

        public IntToLongEntry() {
        }

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

        public void setKey(int key) {
            this.key = key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
}

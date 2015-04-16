import java.util.HashSet;
import java.util.Set;

public class Bucket {
    private IntToLongEntry firstNode;

    public Bucket(IntToLongEntry firstNode) {
        this.firstNode = firstNode;
    }


    public long set(int key, long value) {
        IntToLongEntry entry = entryByKey(key);
        if (entry != null) {
            long oldVal = entry.getValue();
            entry.setValue(value);
            return oldVal;
        } else {
            IntToLongEntry newEntry = new IntToLongEntry(key, value);
            IntToLongEntry lastEntryInBucket = getLastEntry();
            lastEntryInBucket.setNext(newEntry);
            return IntToLongMap.UNDEFINED_VALUE;
        }
    }

    public IntToLongEntry entryByKey(int key) {
        IntToLongEntry node = firstNode;
        while (node != null) {
            if (key == node.getKey()) {
                return node;
            } else {
                node = node.next();
            }
        }

        return null;
    }

    public Set<IntToLongEntry> entrySet() {
        Set<IntToLongEntry> entrys = new HashSet<>();
        IntToLongEntry entry = firstNode;
        while (entry != null) {
            entrys.add(entry);
            entry = entry.next();
        }
        return entrys;
    }

    private IntToLongEntry getLastEntry() {
        IntToLongEntry node = firstNode;
        for (; ; ) {
            if (node.hasNext()) {
                node = node.next();
            } else {
                return node;
            }
        }
    }
}

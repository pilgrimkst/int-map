public class IntToLongEntry {

    private final int key;
    private long value;
    private IntToLongEntry next;

    public IntToLongEntry(int key, long value) {
        this.key = key;
        this.value = value;
    }

    public IntToLongEntry next() {
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


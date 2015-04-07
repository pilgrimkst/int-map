public interface IntToLongMap {

    int NULL_VALUE = Integer.MIN_VALUE;

    long get(int key);

    long put(int key, long value);

    int size();
}

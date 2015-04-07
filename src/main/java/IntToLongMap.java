public interface IntToLongMap {

    int DEFAULT_VALUE = Integer.MIN_VALUE;
    int DEFAULT_KEY = Integer.MIN_VALUE;

    long get(int key);

    long put(int key, long value);

    int size();
}

public interface IntToLongMap {

    int UNDEFINED_VALUE = Integer.MIN_VALUE;
    int UNDEFINED_KEY = Integer.MIN_VALUE;

    long get(int key);

    long put(int key, long value);

    int size();
}

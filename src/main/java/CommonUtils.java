public class CommonUtils {

    public static long checkNotNull(Long value, long defaultValue) {
        return value != null ? value : defaultValue;
    }

}

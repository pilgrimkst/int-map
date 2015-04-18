import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class IntToLongMapTest {

    public static void fillMap(IntToLongMap map, int from, int to) {
        for (int i = from; i < to; i++) {
            map.put(i, i);
        }
    }

    @Test
    public void getShouldRetrieveExistingElement() throws Exception {
        IntToLongMap map = initMap();
        map.put(0, 1l);
        assertEquals(1l, map.get(0));
    }

    @Test
    public void getShouldReturnDefaultValueOnMissingKey() throws Exception {
        IntToLongMap map = initMap();
        assertEquals(0, map.size());
        assertEquals(IntToLongMap.UNDEFINED_KEY, map.get(0));
    }

    @Test
    public void putShouldPutValuesAccordingToKeys() throws Exception {
        IntToLongMap map = initMap();
        assertEquals(0, map.size());
        map.put(0, 1l);
        map.put(1, 2l);
        assertEquals(2, map.size());
        assertEquals(1l, map.get(0));
        assertEquals(2l, map.get(1));
    }

    @Test
    public void putShouldReturnOldValue() throws Exception {
        IntToLongMap map = initMap();
        assertEquals(0, map.size());
        map.put(0, 1l);
        assertEquals(1l, map.put(0, 2l));
        assertEquals(2l, map.get(0));
    }

    @Test
    public void putShouldReturnDefaultValueIfValueIsNotSet() throws Exception {
        IntToLongMap map = initMap();
        assertEquals(0, map.size());
        assertEquals(IntToLongMap.UNDEFINED_KEY, map.put(0, 1l));
    }

    @Test
    public void putShouldReplaceElementsWithSameKeys() throws Exception {
        IntToLongMap map = initMap();
        assertEquals(0, map.size());

        map.put(0, 1l);
        assertEquals(1, map.size());

        map.put(0, 2l);
        assertEquals(1, map.size());
        assertEquals(2l, map.get(0));

    }

    @Test
    public void randomAccessingTest() throws Exception {
        Map<Integer, Long> testValues = getRandomMap(100000);
        IntToLongMap map = initMap();
        copyToTestMap(map, testValues);
        assertMapsEqual(map, testValues);
    }

    private void assertMapsEqual(IntToLongMap map, Map<Integer, Long> testValues) {
        assertEquals(testValues.size(), map.size());
        testValues.entrySet().forEach(e -> assertEquals("for key" + e.getKey(), map.get(e.getKey()), (long) e.getValue()));
    }

    private void copyToTestMap(IntToLongMap map, Map<Integer, Long> testValues) {
        for (Map.Entry<Integer, Long> entry : testValues.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    private HashMap<Integer, Long> getRandomMap(int n) {
        Random r = new Random();
        HashMap<Integer, Long> integerLongHashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            integerLongHashMap.put(r.nextInt(1000000), (long) r.nextInt(1000000));
        }
        return integerLongHashMap;
    }

    private IntToLongMap initMap() {
        return new IntToLongMapOpenAddressingImpl();
    }
}
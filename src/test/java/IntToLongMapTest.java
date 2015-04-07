import org.junit.Test;

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
        assertEquals(IntToLongMap.DEFAULT_KEY, map.get(0));
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
        assertEquals(IntToLongMap.DEFAULT_KEY, map.put(0, 1l));
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
    public void allValuesRemainAccessibleOnIncreasedCapacity() throws Exception {
        IntToLongMap map = initMap();
        map.put(0, 1000l);
        map.put(Integer.MAX_VALUE, 2000l);

        fillMap(map, 1, 1000);

        assertEquals(1000l, map.get(0));
        assertEquals(2000l, map.get(Integer.MAX_VALUE));

        for (int i = 1; i < 1000; i++) {
            assertEquals(i, map.get(i));
        }
    }

    private IntToLongMap initMap() {
        return new IntToLongMapImpl();
    }
}
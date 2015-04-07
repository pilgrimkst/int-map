import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IntToLongMapImplTest {

    @Test
    public void increaseBucketSizeToShouldBeExecutedOnLoadFactor2() throws Exception {
        IntToLongMapImpl map = new IntToLongMapImpl();
        map = Mockito.spy(map);
        int totalValues = 65;
        IntToLongMapTest.fillMap(map, 0, totalValues);
        assertEquals(totalValues, map.size());
        verify(map, times(totalValues / (IntToLongMapImpl.INITIAL_CAPACITY * 2))).increaseBucketSizeTo(anyInt());
    }
}
package decisionmakertool.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilClassTest {

    @Test
    public void polygonArea() {
        Integer []valuePoints = {1,5,4,2,3,1,4,5,3};
        double expected = 26.99;
        Assert.assertEquals(expected,UtilClass.getPolygonArea(valuePoints),0.5);
    }
}

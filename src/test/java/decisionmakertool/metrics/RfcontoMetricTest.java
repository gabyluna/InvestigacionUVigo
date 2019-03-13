package decisionmakertool.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RfcontoMetricTest {
    private QualityMetrics qualityMetrics;
    private int inputNumber1;
    private int inputNumber2;
    private int inputNumber3;
    private int expectedResult;

    @Before
    public void initialize() {
        qualityMetrics = new QualityMetrics();
    }

    public RfcontoMetricTest(int inputNumber1, int inputNumber2, int inputNumber3, int expectedResult) {
        this.inputNumber1 = inputNumber1;
        this.inputNumber2 = inputNumber2;
        this.inputNumber3 = inputNumber3;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection possibleResults() {
        return Arrays.asList(new Object[][] {
                { 20, 150, 50, 1 },
                { 100, 150, 2750, 2 },
                { 100, 110,3450, 3 },
                { 60, 30,2860, 4 },
                { 50, 10,2080, 5 }
        });
    }

    @Test
    public void rfcontoMetric() {
        System.out.println("Parameterized Number expected is : " + expectedResult);
        Assert.assertEquals(expectedResult,qualityMetrics.rfcontoMetric(inputNumber1,inputNumber2, inputNumber3));
    }
}

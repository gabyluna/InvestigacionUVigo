package decisionmakertool.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class NomontoMetricTest {
    private QualityMetrics qualityMetrics;
    private int inputNumber1;
    private int inputNumber2;
    private int expectedResult;

    @Before
    public void initialize() {
        qualityMetrics = new QualityMetrics();
    }

    public NomontoMetricTest(int inputNumber1, int inputNumber2, int expectedResult) {
        this.inputNumber1 = inputNumber1;
        this.inputNumber2 = inputNumber2;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection possibleResults() {
        return Arrays.asList(new Object[][] {
                { 16, 92, 1 },
                { 150, 2400, 2 },
                { 102, 2042, 3 },
                { 85, 3125, 4 },
                { 50, 3500, 5 }
        });
    }

    @Test
    public void nomontoMetric() {
        System.out.println("Parameterized Number expected is : " + expectedResult);
        Assert.assertEquals(expectedResult,qualityMetrics.nomontoMetric(inputNumber1,inputNumber2));
    }
}

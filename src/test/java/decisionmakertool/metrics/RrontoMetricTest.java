package decisionmakertool.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RrontoMetricTest {

    private QualityMetrics qualityMetrics;
    private int inputNumber1;
    private int inputNumber2;
    private int expectedResult;

    @Before
    public void initialize() {
        qualityMetrics = new QualityMetrics();
    }

    public RrontoMetricTest(int inputNumber1, int inputNumber2, int expectedResult) {
        this.inputNumber1 = inputNumber1;
        this.inputNumber2 = inputNumber2;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection possibleResults() {
        return Arrays.asList(new Object[][] {
                { 20, 150, 1 },
                { 100, 150, 2 },
                { 500, 600, 3 },
                { 1000, 250, 4 },
                { 1150, 250, 5 }
        });
    }

    @Test
    public void rrontoMetric() {
        System.out.println("Parameterized Number expected is : " + expectedResult);
        Assert.assertEquals(expectedResult,qualityMetrics.rrontoMetric(inputNumber1,inputNumber2));
    }

}
package decisionmakertool.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CboontoMetricTest {
    private QualityMetrics qualityMetrics;
    private int inputNumber1;
    private int inputNumber2;
    private int inputNumber3;
    private int expectedResult;

    @Before
    public void initialize() {
        qualityMetrics = new QualityMetrics();
    }

    public CboontoMetricTest(int inputNumber1, int inputNumber2, int inputNumber3, int expectedResult) {
        this.inputNumber1 = inputNumber1;
        this.inputNumber2 = inputNumber2;
        this.inputNumber3 = inputNumber3;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection possibleResults() {
        return Arrays.asList(new Object[][] {
                { 20, 150, 50, 1 },
                { 1400, 250, 50, 2 },
                { 750, 220, 70, 3 },
                { 600, 220, 20, 4 },
                { 100, 130, 30, 5 }
        });
    }

    @Test
    public void cboontoMetric() {
        System.out.println("Parameterized Number expected is : " + expectedResult);
        Assert.assertEquals(expectedResult,qualityMetrics.cboontoMetric(inputNumber1,inputNumber2,inputNumber3));
    }
}

package decisionmakertool.metrics;

import metrics.qualitymetrics.QualityMetricEnum;
import metrics.qualitymetrics.QualityMetricFactory;
import metrics.qualitymetrics.QualityMetricsStrategy;
import model.MetricOntologyModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RrontoMetricTest {

    private QualityMetricFactory qualityMetricFactory;
    private MetricOntologyModel metricOntologyModel;
    private int expectedResult;

    @Before
    public void initialize() {
        qualityMetricFactory = new QualityMetricFactory();
    }

    public RrontoMetricTest(MetricOntologyModel metricOntologyModel, int expectedResult) {
        this.metricOntologyModel = metricOntologyModel;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection possibleResults() {
        MetricOntologyModel metricOntologyModel = new MetricOntologyModel("ontology",92,81,510,16,819,1,1,1);
        return Arrays.asList(new Object[][] {
                { metricOntologyModel, 1 }
        });
    }

    @Test
    public void rrontoMetric() {
        QualityMetricsStrategy qualityMetricsStrategy;
        qualityMetricsStrategy = qualityMetricFactory.getQualityMetric(QualityMetricEnum.RROnto.getType());
        System.out.println("Parameterized Number expected is : " + expectedResult);
        Assert.assertEquals(1, qualityMetricsStrategy.calculateQualityMetric(metricOntologyModel));
    }

}
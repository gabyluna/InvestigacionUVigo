package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.metrics.qualitymetrics.Range;
import decisionmakertool.model.MetricOntologyModel;

public class RFCOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float rfconto = (float) (metricOntologyModel.getNumProperties() + metricOntologyModel.getNumSuperclasses()) /
                metricOntologyModel.getNumClasses();

        Range range1 = new Range(8,12);
        Range range2 = new Range(6,8);
        Range range3 = new Range(3,6);
        Range range4 = new Range(0,3);

        return ComputeQualityMetrics.getTotalValue(rfconto, range1, range2, range3, range4);
    }


}

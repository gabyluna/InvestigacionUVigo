package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.metrics.qualitymetrics.Range;
import decisionmakertool.model.MetricOntologyModel;

public class CBOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float cboonto = (float) metricOntologyModel.getNumSuperclasses() /
                (metricOntologyModel.getNumClasses() - metricOntologyModel.getRelationsThing());
        Range range1 = new Range(6,8);
        Range range2 = new Range(4,6);
        Range range3 = new Range(2,4);
        Range range4 = new Range(0,2);

        return ComputeQualityMetrics.getTotalValue(cboonto, range1, range2, range3, range4);
    }
}

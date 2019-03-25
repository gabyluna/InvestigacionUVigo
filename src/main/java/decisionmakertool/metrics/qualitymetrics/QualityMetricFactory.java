package decisionmakertool.metrics.qualitymetrics;

import decisionmakertool.metrics.qualitymetrics.impl.*;

public class QualityMetricFactory {

    public  QualityMetricsStrategy getQualityMetric(int numQualityMetric){
        QualityMetricsStrategy qualityMetricsStrategy = null;
        switch (numQualityMetric) {
            case 1:
                qualityMetricsStrategy = new RRontoMetric();
                break;
            case 2:
                qualityMetricsStrategy = new INROntoMetric();
                break;
            case 3:
                qualityMetricsStrategy = new ANOntoMetric();
                break;
            case 4:
                qualityMetricsStrategy = new CROntoMetric();
                break;
            case 5:
                qualityMetricsStrategy = new NOMOntoMetric();
                break;
            case 6:
                qualityMetricsStrategy = new RFCOntoMetric();
                break;
            case 7:
                qualityMetricsStrategy = new CBOntoMetric();
                break;
            case 8:
                qualityMetricsStrategy = new LCOMOntoMetric();
                break;
            case 9:
                qualityMetricsStrategy = new RCOntoMetric();
                break;

        }
        return qualityMetricsStrategy;
    }
}

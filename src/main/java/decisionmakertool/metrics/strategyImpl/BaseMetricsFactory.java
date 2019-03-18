package decisionmakertool.metrics.strategyImpl;

public class BaseMetricsFactory {

    public  BaseMetricsStrategy getBaseMetric(BaseMetric baseMetricTag){
        BaseMetricsStrategy baseMetricsStrategy = null ;

        switch(baseMetricTag) {
            case ANNOTATIONS:
                baseMetricsStrategy =  new NumAnnotation();
                break;
            case INSTANCES:
                baseMetricsStrategy = new NumInstances();
                break;
            case SUBCLASSES:
                baseMetricsStrategy = new NumSubClasses();
                break;
            case SUPERCLASSES:
                baseMetricsStrategy = new NumSuperClasses();
                break;
            case PROPERTIES:
                baseMetricsStrategy = new NumProperties();
                break;
            case RELATIONS_THING:
                baseMetricsStrategy = new NumRelationsOfThing();
                break;
            case CLASS_WITH_INDIVIDUALS:
                baseMetricsStrategy = new NumClassesWithIndividuals();
                break;
            case CLASSES:
                baseMetricsStrategy = new NumClasses();
                break;


        }
        return baseMetricsStrategy;
    }

}

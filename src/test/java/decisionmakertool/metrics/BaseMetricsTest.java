package decisionmakertool.metrics;

import decisionmakertool.metrics.strategyImpl.*;
import decisionmakertool.owl.LoadOntologyClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

public class BaseMetricsTest {
    private OWLOntology ontologyActual;
    private BaseMetricsStrategy baseMetricsStrategy;
    private BaseMetricsFactory baseMetricsFactory;

    @Before
    public void initialize() {
        LoadOntologyClass loadOntology = new LoadOntologyClass("C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl");
        ontologyActual = loadOntology.getOntology();
        baseMetricsFactory = new BaseMetricsFactory();
    }

    @Test
    public void getNumSuperClasses(){
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.SUPERCLASSES);
        Assert.assertEquals(1141,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumClasses() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.CLASSES);
        Assert.assertEquals(923,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumSubClasses() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.SUBCLASSES);
        Assert.assertEquals(1124,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumInstances() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.INSTANCES);
        Assert.assertEquals(370,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumProperties() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.PROPERTIES);
        Assert.assertEquals(48,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumAnnotation() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.ANNOTATIONS);
        Assert.assertEquals(4020,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumRelationsOfThing() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.RELATIONS_THING);
        Assert.assertEquals(922,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

    @Test
    public void getNumClassesWithIndividuals() {
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.CLASS_WITH_INDIVIDUALS);
        Assert.assertEquals(1,baseMetricsStrategy.calculateMetric(ontologyActual));
    }

}
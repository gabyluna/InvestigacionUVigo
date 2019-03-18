package decisionmakertool.metrics;

import decisionmakertool.owl.LoadOntologyClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

public class BaseMetricsTest {
    private OWLOntology ontologyActual;
    private BaseMetricsContext baseMetrics;

    @Before
    public void initialize() {
        LoadOntologyClass loadOntology = new LoadOntologyClass("C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl");
        ontologyActual = loadOntology.getOntology();
        baseMetrics = new BaseMetricsContext();
    }

    @Test
    public void getNumSuperClasses(){
        baseMetrics.setBaseMetricsStrategy(new NumSuperClasses());
        Assert.assertEquals(1141,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumClasses() {
        baseMetrics.setBaseMetricsStrategy(new NumClasses());
        Assert.assertEquals(923,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumSubClasses() {
        baseMetrics.setBaseMetricsStrategy(new NumSubClasses());
        Assert.assertEquals(1124,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumInstances() {
        baseMetrics.setBaseMetricsStrategy(new NumInstances());
        Assert.assertEquals(370,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumProperties() {
        baseMetrics.setBaseMetricsStrategy(new NumProperties());
        Assert.assertEquals(48,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumAnnotation() {
        baseMetrics.setBaseMetricsStrategy(new NumAnnotation());
        Assert.assertEquals(4020,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumRelationsOfThing() {
        baseMetrics.setBaseMetricsStrategy(new NumRelationsOfThing());
        Assert.assertEquals(922,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

    @Test
    public void getNumClassesWithIndividuals() {
        baseMetrics.setBaseMetricsStrategy(new NumClassesWithIndividuals());
        Assert.assertEquals(1,baseMetrics.getBaseMetricStrategy(ontologyActual));
    }

}
package decisionmakertool.metrics;

import decisionmakertool.owl.LoadOntologyClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

public class BaseMetricsTest {
    private OWLOntology ontologyActual;
    private BaseMetrics baseMetrics;

    @Before
    public void initialize() {
        LoadOntologyClass loadOntology = new LoadOntologyClass();
        loadOntology.loadOntology("C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl");
        ontologyActual = loadOntology.getOntology();
        baseMetrics = new BaseMetrics();
    }

    @Test
    public void getNumSuperClasses(){
        Assert.assertEquals(50,baseMetrics.getNumSuperClasses(ontologyActual));
    }

    @Test
    public void getNumClasses() {
        Assert.assertEquals(923,baseMetrics.getNumClasses(ontologyActual));
    }

    @Test
    public void getNumSubClasses() {
        Assert.assertEquals(1124,baseMetrics.getNumSubClasses(ontologyActual));
    }

    @Test
    public void getNumInstances() {
        Assert.assertEquals(370,baseMetrics.getNumInstances(ontologyActual));
    }

    @Test
    public void getNumProperties() {
        Assert.assertEquals(48,baseMetrics.getNumProperties(ontologyActual));
    }

    @Test
    public void getNumAnnotation() {
        Assert.assertEquals(4020,baseMetrics.getNumAnnotation(ontologyActual));
    }

    @Test
    public void getNumRelationsOfThing() {
        Assert.assertEquals(922,baseMetrics.getNumRelationsOfThing(ontologyActual));
    }

    @Test
    public void getNumClassesWithIndividuals() {
        Assert.assertEquals(1,baseMetrics.getNumClassesWithIndividuals(ontologyActual));
    }
}
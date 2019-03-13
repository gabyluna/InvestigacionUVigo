package decisionmakertool.metrics;

import decisionmakertool.owl.LoadOntologyClass;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SmellErrorsTest {
    private SmellErrors smellErrors;
    private String path;

    @Before
    public void initialize() {
       path = "C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl";
       smellErrors = new SmellErrors();
    }

    @Test
    public void circularityErrors() {
       Assert.assertNotNull(smellErrors.circularityErrors(path));
    }

    @Test
    public void partitionErrors() {
        Assert.assertNotNull(smellErrors.partitionErrors(path));
    }

    @Test
    public void semanticErrors() {
        Assert.assertNotNull(smellErrors.semanticErrors(path));
    }

    @Test
    public void incompletenessErrors() {
        Assert.assertNotNull(smellErrors.incompletenessErrors(path));
    }

    @Test
    public void getElementsSmellErrors() {

    }
}
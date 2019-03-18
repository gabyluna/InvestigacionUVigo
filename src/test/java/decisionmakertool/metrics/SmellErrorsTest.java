package decisionmakertool.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SmellErrorsTest {
    private CircularityErrors circularityErrors;
    private PartitionErrors partitionErrors;
    private SemanticErrors semanticErrors;
    private IncompletenessErrors incompletenessErrors;
    private String path;

    @Before
    public void initialize() {
       path = "C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl";
       circularityErrors = new CircularityErrors();
       partitionErrors = new PartitionErrors();
       semanticErrors = new SemanticErrors();
       incompletenessErrors = new IncompletenessErrors();
    }

    @Test
    public void circularityErrors() {
       Assert.assertNotNull(circularityErrors.getListSmellErrors(path));
    }

    @Test
    public void partitionErrors() {

        Assert.assertNotNull(partitionErrors.getListSmellErrors(path));
    }

    @Test
    public void semanticErrors() {
        Assert.assertNotNull(semanticErrors.getListSmellErrors(path));
    }

    @Test
    public void incompletenessErrors() {
        Assert.assertNotNull(incompletenessErrors.getListSmellErrors(path));
    }

    @Test
    public void getElementsSmellErrors() {

    }
}
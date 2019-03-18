package decisionmakertool.owl;

import decisionmakertool.metrics.QualityMetrics;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

public class LoadOntologyClassTest {
    private LoadOntologyClass loadOntologyClass;
    private String path;

    @Before
    public void initialize() {
        path = "C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl";
        loadOntologyClass = new LoadOntologyClass(path);
    }

    @Test
    public void validationConsistency() {
        boolean condition = false;
        int lengthAnswerValidation = loadOntologyClass.validationConsistency(path).length();
        if(lengthAnswerValidation > 0){
            condition = true;
        }
        Assert.assertTrue(condition);
    }

}
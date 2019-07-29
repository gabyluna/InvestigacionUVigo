package decisionmakertool.owl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OntologyUtilTest {
    private OntologyUtil loadOntologyClass;
    private String path;

    @Before
    public void initialize() {
        path = "C:/Users/Gaby/Desktop/Vbox/OntoFinales/ontoFinal.owl";
        loadOntologyClass = new OntologyUtil(path);
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
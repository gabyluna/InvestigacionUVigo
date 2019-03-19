package decisionmakertool.metrics.templateimpl;

import decisionmakertool.metrics.templateimpl.impl.CircularityErrors;
import decisionmakertool.metrics.templateimpl.impl.IncompletenessErrors;
import decisionmakertool.metrics.templateimpl.impl.PartitionErrors;
import decisionmakertool.metrics.templateimpl.impl.SemanticErrors;

public class SmellErrorFactory {

    public static SmellErrorTemplate getSmellError(SmellError smellErrorTag){
        SmellErrorTemplate smellErrorTemplate = null;

        switch(smellErrorTag) {
            case CIRCULARITY:
                smellErrorTemplate =  new CircularityErrors();
                break;
            case INCOMPLETENESS:
                smellErrorTemplate = new IncompletenessErrors();
                break;
            case PARTITION:
                smellErrorTemplate = new PartitionErrors();
                break;
            case SEMANTIC:
                smellErrorTemplate = new SemanticErrors();
                break;
        }

        return smellErrorTemplate;
    }

}

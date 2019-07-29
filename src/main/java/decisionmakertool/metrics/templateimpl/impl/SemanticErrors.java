package decisionmakertool.metrics.templateimpl.impl;

import decisionmakertool.metrics.templateimpl.SmellErrorTemplate;
import drontoapi.except.UnexpectedErrorException;
import drontoapi.pitfallmanager.Pitfall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SemanticErrors extends SmellErrorTemplate {

    private static final Integer []arrayPitfallsSemantic = {1,2,7,12,19,20,22,30,32};

    public List<Pitfall> getListSmellErrors(String path){
        List<Pitfall> listResult = new ArrayList<>();

        try {
            listResult = SmellErrorTemplate.getPitfallsSelector(path, arrayPitfallsSemantic);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SemanticErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }
}

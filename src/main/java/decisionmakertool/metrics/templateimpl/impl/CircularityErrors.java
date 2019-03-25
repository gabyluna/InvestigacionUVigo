package decisionmakertool.metrics.templateimpl.impl;

import decisionmakertool.metrics.templateimpl.SmellErrorTemplate;
import ionelvirgilpop.drontoapi.except.UnexpectedErrorException;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CircularityErrors extends SmellErrorTemplate {

    private static final Integer []arrayPitfallsCircularity = {6};

    public  List<Pitfall> getListSmellErrors(String path){
        List<Pitfall> listResult = new ArrayList<>();

        try {
            listResult = SmellErrorTemplate.getPitfallsSelector(path, arrayPitfallsCircularity);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(CircularityErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

}
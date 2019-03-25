package decisionmakertool.metrics.templateimpl;

import decisionmakertool.model.MetricOntologyModel;
import ionelvirgilpop.drontoapi.except.UnexpectedErrorException;
import ionelvirgilpop.drontoapi.pitfallmanager.AffectedElement;
import ionelvirgilpop.drontoapi.pitfallmanager.IPitfallManager;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import ionelvirgilpop.drontoapi.pitfallmanager.PitfallManager;
import ionelvirgilpop.drontoapi.service.IWebService;
import ionelvirgilpop.drontoapi.service.WebService;
import ionelvirgilpop.drontoapi.util.PitfallSelector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SmellErrorTemplate {

    private static IWebService webService = new WebService();

    public  abstract List<Pitfall> getListSmellErrors(String path);

    public static List<Pitfall> getPitfallsSelector(String path, Integer[] arrayPitfallsSelector) throws UnexpectedErrorException, IOException {
        webService.setOntologyFile(path);
        PitfallSelector pitfallSelector = new PitfallSelector();

        for(Integer pitfall: arrayPitfallsSelector){
            pitfallSelector.selectPitfall(pitfall);
        }
        webService.setPitfallSelector(pitfallSelector);
        IPitfallManager manager = new PitfallManager(webService.getResponse());

        return manager.getPitfalls();
    }

    public static List<AffectedElement> getElementsSmellErrors(String path, Pitfall pitfall) {
        List<AffectedElement> listResult = new ArrayList<>();

        try {
            webService.setOntologyFile(path);
            IPitfallManager manager = new PitfallManager(webService.getResponse());
            listResult =  manager.getAffectedElements(pitfall);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SmellErrorTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    public List<MetricOntologyModel> redundandyErrors(String path, List<String> lista) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO To change body of generated methods, choose Tools | Templates.
    }

}

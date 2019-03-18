package decisionmakertool.metrics;

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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SmellErrorTemplate {
    private static IWebService webService = new WebService();

    private  static void getResultPitfalls(String path, List<Pitfall> listResult, PitfallSelector pitfallSelector) throws UnexpectedErrorException, IOException {
        webService.setOntologyFile(path);
        webService.setPitfallSelector(pitfallSelector);
        IPitfallManager manager = new PitfallManager(webService.getResponse());
        Iterator<Pitfall> iterator = manager.getPitfalls().iterator();
        while (iterator.hasNext()) {
            Pitfall pitfall = iterator.next();
            listResult.add(pitfall);
        }
    }

    public static void loadPitfallSelector(String path, List<Pitfall> listResult, Integer[] arrayPitfalls) throws UnexpectedErrorException, IOException {
        PitfallSelector pitfallSelector = new PitfallSelector();
        for (int i = 0; i < arrayPitfalls.length; i++) {
            pitfallSelector.selectPitfall(arrayPitfalls[i]);
        }
        getResultPitfalls(path, listResult, pitfallSelector);
    }

    public  List<AffectedElement> getElementsSmellErrors(String path, Pitfall pitfall) {
        List<AffectedElement> listResult = new ArrayList<>();
        try {
            webService.setOntologyFile(path);
            IPitfallManager manager = new PitfallManager(webService.getResponse());

            Iterator<AffectedElement> elementsIterator = manager.getAffectedElements(pitfall).iterator();
            while (elementsIterator.hasNext()) {
                AffectedElement affectedElement = elementsIterator.next();
                listResult.add(affectedElement);
            }

        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SmellErrorTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    public void redundandyErrors(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  abstract List<Pitfall> getListSmellErrors(String path);
}

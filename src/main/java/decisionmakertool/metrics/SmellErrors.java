/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.metrics;

import decisionmakertool.service.SmellErrorsInterface;
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

/**
 *
 * @author Gaby
 */
public class SmellErrors implements SmellErrorsInterface {

    private IWebService webService = new WebService();
    public static final Integer arrayPitfallsCircularity[] = {6};
    public static final Integer arrayPitfallsPartition[] = {10};
    public static final Integer arrayPitfallsSemantic[] = {1,2,7,12,19,20,22,30,32};
    public static final  Integer arrayPitfallsIncompleteness[] = {4,8,11,13};

    @Override
    public List<Pitfall> circularityErrors(String path) {
        List<Pitfall> listResult = new ArrayList<>();
        try {

            System.out.println("circularity");
            loadPitfallSelector(path, listResult, arrayPitfallsCircularity);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SmellErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    private void loadPitfallSelector(String path, List<Pitfall> listResult, Integer[] arrayPitfallsCircularity) throws UnexpectedErrorException, IOException {
        PitfallSelector pitfallSelector = new PitfallSelector();
        for (int i = 0; i < arrayPitfallsCircularity.length; i++) {
            pitfallSelector.selectPitfall(arrayPitfallsCircularity[i]);
        }
        getResultPitfalls(path, listResult, pitfallSelector);
    }

    @Override
    public List<Pitfall> partitionErrors(String path) {
        List<Pitfall> listResult = new ArrayList<>();
        try {

            System.out.println("partition");
            loadPitfallSelector(path, listResult, arrayPitfallsPartition);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SmellErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    @Override
    public List<Pitfall> semanticErrors(String path) {
        List<Pitfall> listResult = new ArrayList<>();
        try {

            System.out.println("semanticErrors");
            loadPitfallSelector(path, listResult, arrayPitfallsSemantic);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SmellErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    @Override
    public List<Pitfall> incompletenessErrors(String path){
        List<Pitfall> listResult = new ArrayList<>();
        try {

            System.out.println("incompletenessErrors");
            loadPitfallSelector(path, listResult, arrayPitfallsSemantic);
        } catch (UnexpectedErrorException | IOException ex) {
            Logger.getLogger(SmellErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    @Override
    public void redundandyErrors(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AffectedElement> getElementsSmellErrors(String path, Pitfall pitfall) {
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
            Logger.getLogger(SmellErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listResult;
    }

    private void getResultPitfalls(String path, List<Pitfall> listResult, PitfallSelector pitfallSelector) throws UnexpectedErrorException, IOException {
        webService.setOntologyFile(path);
        webService.setPitfallSelector(pitfallSelector);
        IPitfallManager manager = new PitfallManager(webService.getResponse());
        Iterator<Pitfall> iterator = manager.getPitfalls().iterator();
        while (iterator.hasNext()) {
            Pitfall pitfall = iterator.next();
            listResult.add(pitfall);
        }
    }
}

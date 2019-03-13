/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ionelvirgilpop.drontoapi.except.UnexpectedErrorException;
import ionelvirgilpop.drontoapi.pitfallmanager.AffectedElement;
import ionelvirgilpop.drontoapi.pitfallmanager.IPitfallManager;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import ionelvirgilpop.drontoapi.pitfallmanager.PitfallManager;
import ionelvirgilpop.drontoapi.pitfallmanager.Report;
import ionelvirgilpop.drontoapi.service.IWebService;
import ionelvirgilpop.drontoapi.service.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ionel Virgil Pop
 *
 */
public class ReportExample {

    public ReportExample() {

    }

    public static List<Pitfall> getPifalls(String path) {
        List<Pitfall> listPitfalls = new ArrayList<Pitfall>();
        IWebService webService = new WebService();
        try {

            //Set the ontology that will be evaluated
            //webService.setOntologyIRI("http://purl.org/goodrelations/v1");
            webService.setOntologyFile(path);

            //You may select only some pitfalls using the PitfallSelector
            //PitfallSelector pitfallSelector = new PitfallSelector();
            //webService.setPitfallSelector(pitfallSelector);
            //Create a model of the response to manage it
            IPitfallManager manager = new PitfallManager(webService.getResponse());

            //Generate a report to standard output
            //manager.generateReport(System.out, Report.PITFALL_REPORT);
            //manager.generateReport(System.out, Report.CSV_REPORT);
            //manager.generateReport(System.out, Report.AFFECTED_ELEMENTS_REPORT);
            listPitfalls = manager.getPitfalls();

            //It is also possible to generate a report in a file
            //manager.generateReport(new FileOutputStream(new File("c:/tmp/csv_report.csv")), Report.CSV_REPORT);
            //manager.generateReport(new FileOutputStream(new File("c:/tmp/affected_elements_report.txt")), Report.AFFECTED_ELEMENTS_REPORT);			
            //manager.generateReport(new FileOutputStream(new File("c:/tmp/pitfall_report.txt")), Report.PITFALL_REPORT);
        } catch (IOException | UnexpectedErrorException e) {
            e.printStackTrace();
        }

        return listPitfalls;
    }

    public static List<AffectedElement> getAffectedElements(String path, Pitfall pitfall) {
        List<AffectedElement> listAffectedElements = new ArrayList<AffectedElement>();
        IWebService webService = new WebService();
        try {

            //Set the ontology that will be evaluated
            //webService.setOntologyIRI("http://purl.org/goodrelations/v1");
            webService.setOntologyFile(path);

            //You may select only some pitfalls using the PitfallSelector
            //PitfallSelector pitfallSelector = new PitfallSelector();
            //webService.setPitfallSelector(pitfallSelector);
            //Create a model of the response to manage it
            IPitfallManager manager = new PitfallManager(webService.getResponse());

            //Generate a report to standard output
            //manager.generateReport(System.out, Report.PITFALL_REPORT);
            //manager.generateReport(System.out, Report.CSV_REPORT);
            //manager.generateReport(System.out, Report.AFFECTED_ELEMENTS_REPORT);
            listAffectedElements = manager.getAffectedElements(pitfall);

            for (AffectedElement p : listAffectedElements) {
                System.out.println("Code:" + p.toString());

            }
            //It is also possible to generate a report in a file
            //manager.generateReport(new FileOutputStream(new File("c:/tmp/csv_report.csv")), Report.CSV_REPORT);
            //manager.generateReport(new FileOutputStream(new File("c:/tmp/affected_elements_report.txt")), Report.AFFECTED_ELEMENTS_REPORT);			
            //manager.generateReport(new FileOutputStream(new File("c:/tmp/pitfall_report.txt")), Report.PITFALL_REPORT);

        } catch (IOException | UnexpectedErrorException e) {
            e.printStackTrace();
        }

        return listAffectedElements;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ReportExample reportEx = new ReportExample();
        //reportEx.evaluate();
    }

}

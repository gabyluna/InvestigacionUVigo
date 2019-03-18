/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.owl;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owl.explanation.api.Explanation;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owl.explanation.api.ExplanationGenerator;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;

public class LoadOntologyClass {

    private OWLOntology ontology;
    private OWLOntologyManager manager;
    private OWLReasonerFactory factory = null;
    private OWLReasoner reasoner;
    private OWLDataFactory dataFactory = null;

    public LoadOntologyClass() {

    }

    public LoadOntologyClass(String path) {
        try {
            File file = new File(path);
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(file);
            factory = new JFactFactory();
            reasoner = this.factory.createReasoner(ontology);
            dataFactory = manager.getOWLDataFactory();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(LoadOntologyClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String validationConsistency(String path) {
        String answer = "";
        File file = new File(path);

        try {
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(file);
            manager = ontology.getOWLOntologyManager();
            dataFactory = manager.getOWLDataFactory();
            factory = new JFactFactory();
            reasoner = factory.createReasoner(ontology);

            if (reasoner.isConsistent()) {
                int unsatisfiableClasses = 0;
                unsatisfiableClasses = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size();
                if (unsatisfiableClasses > 0) {
                    answer = "Merged ontology FAILED satisfiability test. Unsatisfiable classes detected: "
                            + reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size();
                }
                answer = "Merged ontology PASSED the consistency test";
            }
            else
            {
                answer = "Merged ontology FAILED the consistency test, please review the Axioms or debug using Protege";
                answer += getAnswerExplanations(answer);
            }
        } catch (OWLOntologyCreationException | InconsistentOntologyException ex) {
            Logger.getLogger(LoadOntologyClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }

    private String getAnswerExplanations(String answer) {
        ExplanationGenerator<OWLAxiom> explainInconsistency = new InconsistentOntologyExplanationGeneratorFactory(factory,
                1000L).createExplanationGenerator(ontology);
        // Ask for an explanation of `Thing subclass of Nothing` - this axiom is entailed in any inconsistent ontology
        Set<Explanation<OWLAxiom>> explanations = explainInconsistency.getExplanations(dataFactory.getOWLSubClassOfAxiom(dataFactory
                .getOWLThing(), dataFactory.getOWLNothing()));
        int cont = 1;

        for (Explanation<OWLAxiom> e : explanations) {
            answer += "Explain " + cont + "\n";
            answer += "Axioms causing the inconsistency:\n";

            for (OWLAxiom axiom : e.getAxioms()) {
                answer += "Axiom: " + axiom.getSignature() + "\n";
            }

            answer += "------------------" + "\n";
            cont++;
        }
        return answer;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public OWLReasonerFactory getFactory() {
        return factory;
    }

    public void setFactory(OWLReasonerFactory factory) {
        this.factory = factory;
    }

}

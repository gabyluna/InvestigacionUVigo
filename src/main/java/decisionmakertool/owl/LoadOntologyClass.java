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
import org.semanticweb.owlapi.model.IRI;
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
import org.semanticweb.owlapi.model.OWLEntity;
import uk.ac.manchester.cs.jfact.JFactFactory;

/**
 *
 * @author Gaby
 */
public class LoadOntologyClass {

    private OWLOntology ontology;
    private OWLOntologyManager manager;
    private IRI documentIRI;
    private OWLReasonerFactory factory = null;
    private OWLReasoner reasoner;
    private OWLDataFactory dataFactory = null;

    public LoadOntologyClass() {
    }

    public void loadOntology(String path) {
        try {
            File file = new File(path);
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(file);
            documentIRI = manager.getOntologyDocumentIRI(ontology);
            factory = new JFactFactory();
            reasoner = this.factory.createReasoner(ontology);
            dataFactory = manager.getOWLDataFactory();
            //reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);

        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(LoadOntologyClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String validationConsistency(String path) {
        String answer = "";
        File file = new File(path);
        try {
            //BlackBoxOWLDebugger blackBoxOWLDebugger = new BlackBoxOWLDebugger(manager, ontology, factory);
            // blackBoxOWLDebugger.
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(file);
            manager = ontology.getOWLOntologyManager();

            //System.out.println("Loaded ontology: " + ontology);
            dataFactory = manager.getOWLDataFactory();
            factory = new JFactFactory();

            reasoner = factory.createReasoner(ontology);
            // reasoner =  factory.createNonBufferingReasoner(ontology);
            if (reasoner.isConsistent()) {

                if (reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size() > 0) {
                    answer = "Merged ontology FAILED satisfiability test. Unsatisfiable classes detected: " + reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size();
                }
                answer = "Merged ontology PASSED the consistency test";
                System.out.println("Answer:" + answer);

            } else {

                answer = "Merged ontology FAILED the consistency test, please review the Axioms or debug using Protege";
                System.out.println("Answer:" + answer);

                long lo = 1000L;
                ExplanationGenerator<OWLAxiom> explainInconsistency = new InconsistentOntologyExplanationGeneratorFactory(factory,
                        1000L).createExplanationGenerator(ontology);
                // Ask for an explanation of `Thing subclass of Nothing` - this axiom is entailed in any inconsistent ontology
                Set<Explanation<OWLAxiom>> explanations = explainInconsistency.getExplanations(dataFactory.getOWLSubClassOfAxiom(dataFactory
                        .getOWLThing(), dataFactory.getOWLNothing()));

                //System.out.println("TestExplanation.main() " + explanations);
                int cont = 1;
                for (Explanation<OWLAxiom> e : explanations) {
                    System.out.println("------------------");
                    System.out.println("Explain " + cont);
                    System.out.println("Axioms causing the inconsistency: ");
                    answer += "Explain " + cont + "\n";
                    answer += "Axioms causing the inconsistency:\n";

                    //System.out.println(e.getAxioms());
                    for (OWLAxiom axiom : e.getAxioms()) {
                        //System.out.println("class:"+axiom.getClass().getSimpleName());
                        //System.out.println("axiomType:"+axiom.getAxiomType());
                        //System.out.println("dataProperties:"+axiom.getDataPropertiesInSignature());
                        //System.out.println("axiomwithout annottaton:"+axiom.getAxiomWithoutAnnotations());

                        System.out.println("axiom signature:" + axiom.getSignature());
                        answer += "Axiom: " + axiom.getSignature() + "\n";

                        //SSystem.out.println("axiom individuals:"+axiom.getIndividualsInSignature());
                    }
                    answer += "------------------" + "\n";
                    System.out.println("------------------");
                    cont++;
                }

                //  ExplanationGeneratorFactory<OWLAxiom> genFac = ExplanationManager.createExplanationGeneratorFactory(factory);
// Now create the actual explanation generator for our ontology
                //ExplanationGenerator<OWLAxiom> explainInconsistency = ExplanationManager.createExplanationGenerator(ontology);
                //System.out.println("TestExplanation.main() " + explanations);
// Ask for an explanation of `Thing subclass of Nothing` - this axiom is entailed in any inconsistent ontology
                // Now we can get explanations for the inconsistency 
                /* Set<Set<OWLAxiom>> explanations=multExplanator.getExplanations(dataFactory.getOWLThing());

        // Let us print them. Each explanation is one possible set of axioms that cause the 
        // unsatisfiability. 
        for (Set<OWLAxiom> explanation : explanations) {
            System.out.println("------------------");
            System.out.println("Axioms causing the inconsistency: ");
            for (OWLAxiom causingAxiom : explanation) {
                System.out.println(causingAxiom);
            }
            System.out.println("------------------");
        }*/
                ///   gen = genFac.createExplanationGenerator(ontology);
            }

        } catch (OWLOntologyCreationException | InconsistentOntologyException ex) {
            Logger.getLogger(LoadOntologyClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answer;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public OWLOntologyManager getManager() {
        return manager;
    }

    public void setManager(OWLOntologyManager manager) {
        this.manager = manager;
    }

    public OWLReasoner getReasoner() {
        return reasoner;
    }

    public void setReasoner(OWLReasoner reasoner) {
        this.reasoner = reasoner;
    }

    public OWLDataFactory getDataFactory() {
        return dataFactory;
    }

    public void setDataFactory(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public IRI getDocumentIRI() {
        return documentIRI;
    }

    public void setDocumentIRI(IRI documentIRI) {
        this.documentIRI = documentIRI;
    }

    public OWLReasonerFactory getFactory() {
        return factory;
    }

    public void setFactory(OWLReasonerFactory factory) {
        this.factory = factory;
    }

}

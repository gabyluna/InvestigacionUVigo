package decisionmakertool.service.impl;

import decisionmakertool.entities.QuickFixModel;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.service.QuickFixInterface;
import decisionmakertool.util.Util;
import ionelvirgilpop.drontoapi.pitfallmanager.AffectedElement;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.util.*;
import java.util.stream.Collectors;

public class RemoveSimilarElements implements QuickFixInterface {
    @Override
    public Set<OWLAxiom> validateOntology(OWLOntology ontology, List<AffectedElement> listAffectedElements) {
        Set<OWLAxiom> axiomsToChange =  new HashSet<>();
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        List<AffectedElement> listAffectedElemenstToRemove = new ArrayList<>();
        int sizeList = listAffectedElements.size();

        for(int i = 0; i < sizeList; i++){
            if(!listAffectedElements.get(i).getElementSet().isEmpty()){
                String wordAux1 =  Util.getWord(listAffectedElements.get(i).getURI());
                for(int j=(sizeList-1);j >= 0 ; j--){
                    String wordAux2 =  Util.getWord(listAffectedElements.get(j).getURI());
                    int distance = levenshteinDistance.apply(wordAux1,wordAux2);

                    if(distance > 0 && distance < 3) {
                        listAffectedElemenstToRemove.add(listAffectedElements.get(j));
                    }
                }
            }

        }

        listAffectedElemenstToRemove = listAffectedElemenstToRemove.stream().collect(Collectors.toCollection(()->
                new TreeSet<>(Comparator.comparing(AffectedElement::getURI)))).stream().collect(Collectors.toList());

        for ( OWLAxiom axiom : ontology.getAxioms ()) {
            for(AffectedElement affectedElement: listAffectedElemenstToRemove){
                if (axiom.getSignature().toString().contains(affectedElement.getURI())) {
                    axiomsToChange.add(axiom);
                }
            }
        }

        return  axiomsToChange;
    }

    @Override
    public List<String> getListChangesToApply(Set<OWLAxiom> axiomsToChange) {
        List<String> listChanges = new ArrayList<>();
        for ( OWLAxiom axiom : axiomsToChange) {
            listChanges.add("Axiom to change:" + axiom);
        }
        return listChanges;
    }


    @Override
    public OWLOntology getOntologyResult(OWLOntology ontology, OWLOntologyManager manager, Set<OWLAxiom> axiomsToRemove) {
        manager.removeAxioms(ontology, axiomsToRemove);
        return ontology;
    }

   /* @Override
    public List<String> getListChangesToApply(QuickFixModel quickFixModel) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        List<AffectedElement> listAffectedElemenstToRemove = new ArrayList<>();
        int sizeList = quickFixModel.getListAffectedElements().size();

        for(int i = 0; i < sizeList; i++){
            if(quickFixModel.getListAffectedElements().get(i).getSelected()){
                String wordAux1 =  Util.getWord(quickFixModel.getListAffectedElements().get(i).getURI());
                for(int j=(sizeList-1);j >= 0 ; j--){
                    String wordAux2 =  Util.getWord(quickFixModel.getListAffectedElements().get(j).getURI());
                    int distance = levenshteinDistance.apply(wordAux1,wordAux2);

                    if(distance > 0 && distance < 3) {
                        listAffectedElemenstToRemove.add(quickFixModel.getListAffectedElements().get(j));
                    }
                }
            }

        }

        listAffectedElemenstToRemove = listAffectedElemenstToRemove.stream().collect(Collectors.toCollection(()->
                new TreeSet<>(Comparator.comparing(AffectedElement::getURI)))).stream().collect(Collectors.toList());
        for(AffectedElement element:listAffectedElemenstToRemove){
            try {
                return  quickFixModel.getOntologyUtil().removeAxioms(element.getURI(), quickFixModel.getPath());
            } catch (OWLOntologyStorageException e) {
                e.printStackTrace();
            }
        }
        return null;
    }*/


}

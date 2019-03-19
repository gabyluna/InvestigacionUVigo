package decisionmakertool.model;

public class MetricOntologyModel {

    private String nameOntology;
    private int numClasses;
    private int numSubclassOf;
    private int numInstances;
    private int numProperties;
    private int numAnnotations;
    private int numSuperclasses;
    private int relationsThing;
    private int numClassWithIndividuals;

    public   MetricOntologyModel(String nameOntology){
        this.nameOntology = nameOntology;
    }

    public   MetricOntologyModel(String nameOntology, int numClasses, int numSubclassOf, int numInstances,
                                   int numProperties, int numAnnotations, int numSuperclasses, int relationsThing,
                                   int numClassWithIndividuals){

        this.nameOntology = nameOntology;
        this.numClasses = numClasses;
        this.numSubclassOf = numSubclassOf;
        this.numInstances = numInstances;
        this.numProperties = numProperties;
        this.numAnnotations = numAnnotations;
        this.numSuperclasses = numSuperclasses;
        this.relationsThing = relationsThing;
        this.numClassWithIndividuals = numClassWithIndividuals;

    }

    public int getNumClasses() {
        return numClasses;
    }

    public void setNumClasses(int numClasses) {
        this.numClasses = numClasses;
    }

    public int getNumSubclassOf() {
        return numSubclassOf;
    }

    public void setNumSubclassOf(int numSubclassOf) {
        this.numSubclassOf = numSubclassOf;
    }

    public int getNumInstances() {
        return numInstances;
    }

    public void setNumInstances(int numInstances) {
        this.numInstances = numInstances;
    }

    public int getNumProperties() {
        return numProperties;
    }

    public void setNumProperties(int numProperties) {
        this.numProperties = numProperties;
    }

    public int getNumAnnotations() {
        return numAnnotations;
    }

    public void setNumAnnotations(int numAnnotations) {
        this.numAnnotations = numAnnotations;
    }

    public int getNumSuperclasses() {
        return numSuperclasses;
    }

    public void setNumSuperclasses(int numSuperclasses) {
        this.numSuperclasses = numSuperclasses;
    }

    public int getRelationsThing() {
        return relationsThing;
    }

    public void setRelationsThing(int relationsThing) {
        this.relationsThing = relationsThing;
    }

    public String getNameOntology() {
        return nameOntology;
    }

    public void setNameOntology(String nameOntology) {
        this.nameOntology = nameOntology;
    }

    public int getNumClassWithIndividuals() {
        return numClassWithIndividuals;
    }

    public void setNumClassWithIndividuals(int numClassWithIndividuals) {
        this.numClassWithIndividuals = numClassWithIndividuals;
    }

}

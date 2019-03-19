package decisionmakertool.model;

public class MetricOntologyBuilder {
    private String nameOntology;
    private int numClasses;
    private int numSubclassOf;
    private int numInstances;
    private int numProperties;
    private int numAnnotations;
    private int numSuperclasses;
    private int relationsThing;
    private int numClassWithIndividuals;

    public  MetricOntologyBuilder(String nameOntology){
        this.nameOntology = nameOntology;
    }

    public  MetricOntologyBuilder(String nameOntology, int numClasses, int numSubclassOf, int numInstances,
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

    public MetricOntologyBuilder setNameOntology(String nameOntology) {
       this.nameOntology = nameOntology;
       return this;
    }

    public MetricOntologyBuilder setNumClasses(int numClasses){
        this.numClasses = numClasses;
        return this;
    }

    public MetricOntologyBuilder setNumSubclassOf(int numSubclassOf){
        this.numSubclassOf = numSubclassOf;
        return this;
    }

    public MetricOntologyBuilder setNumInstances(int numInstances){
        this.numInstances = numInstances;
        return this;

    }

    public MetricOntologyBuilder setNumProperties(int numProperties){
        this.numProperties = numProperties;
        return this;
    }

    public MetricOntologyBuilder setNumAnnotations(int numAnnotations){
        this.numAnnotations = numAnnotations;
        return this;
    }

    public MetricOntologyBuilder setNumSuperClasses(int numSuperClasses){
        this.numSuperclasses = numSuperClasses;
        return this;
    }

    public MetricOntologyBuilder setRelationsThing(int relationsThing){
        this.relationsThing = relationsThing;
        return this;
    }

    public MetricOntologyBuilder setNumClassWithIndividuals(int numClassWithIndividuals){
        this.numClassWithIndividuals = numClassWithIndividuals;
        return this;
    }

    public MetricOntologyModel build(){
        return new MetricOntologyModel(nameOntology,numClasses,numSubclassOf,numInstances,numProperties,numAnnotations,
                numSuperclasses,relationsThing,numClassWithIndividuals);
    }
}

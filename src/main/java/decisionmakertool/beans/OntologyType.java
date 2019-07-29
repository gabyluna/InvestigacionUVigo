package decisionmakertool.beans;

public enum  OntologyType {

    BASE("B"),
    AUTOMATIC("A");

    private final String type;

    OntologyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}


package decisionmakertool.metrics.qualitymetrics;

public class Range {
    private int min;
    private int max;

    public Range(int min,int max){
        this.min = min;
        this.max = max;
    }

    public boolean includes(float value){
        return value > min && value <= max;
    }

}

package com.brewed.core.rest.predict;

public class PredictResponse {
    private String label;
    private PredictResult result;

    public PredictResponse(String label, PredictResult result) {
        this.label = label;
        this.result = result;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PredictResult getResult() {
        return result;
    }

    public void setResult(PredictResult result) {
        this.result = result;
    }
}

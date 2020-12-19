package pl.uek.mm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestData {

    @JsonProperty("request")
    private InputDataModel inputData;

    public RequestData() {
    }

    public InputDataModel getInputData() {
        return inputData;
    }

    public void setInputData(InputDataModel inputData) {
        this.inputData = inputData;
    }

}

package pl.uek.mm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseModel {

    @JsonProperty("response")
    private ResultDataModel resultData;

    public ResponseModel() {
    }

    public ResultDataModel getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataModel resultData) {
        this.resultData = resultData;
    }

}

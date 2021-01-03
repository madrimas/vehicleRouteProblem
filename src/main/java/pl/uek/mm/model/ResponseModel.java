package pl.uek.mm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ResponseModel {

    @JsonProperty("response")
    private ResultDataModel resultData;

}

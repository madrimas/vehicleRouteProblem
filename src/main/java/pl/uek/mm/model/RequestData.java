package pl.uek.mm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RequestData {

	@JsonProperty("parameters")
	private InputParametersModel inputParameters;

    @JsonProperty("data")
    private InputDataModel inputData;

}

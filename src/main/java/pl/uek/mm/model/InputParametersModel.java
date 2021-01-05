package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class InputParametersModel {

	private Integer tabuDelay;
	private Integer tabuRandomBound;
	private Integer maxIterations;
	private Integer maxIterationsWithoutImprovement;

}

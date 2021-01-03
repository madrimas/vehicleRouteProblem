package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ResultDataModel {

	private double cost;
	private long executionTime;
	private List<Path> paths;

}

package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PathCity {

	private String name;
	private double latitude;
	private double longitude;
	private int demand;

}

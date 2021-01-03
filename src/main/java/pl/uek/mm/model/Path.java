package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Path {

	List<PathCity> pathCities = new ArrayList<>();

}

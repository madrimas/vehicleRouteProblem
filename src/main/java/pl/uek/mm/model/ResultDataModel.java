package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ResultDataModel {

    private List<List<Path>> paths;
    private long executionTime;

}

package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class City {

    private String name;
    private double longitude;
    private double latitude;
    private int demand;

}

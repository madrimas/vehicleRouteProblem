package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class InputDataModel {

    private int noOfCars;
    private int carCapacity;
    private int noOfCities;
    private List<City> cities;

}

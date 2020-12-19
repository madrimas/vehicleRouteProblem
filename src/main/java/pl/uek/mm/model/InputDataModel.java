package pl.uek.mm.model;

import java.util.List;

public class InputDataModel {

    private int noOfCars;
    private int carCapacity;
    private int noOfCities;
    private List<City> cities;

    public InputDataModel() {
    }

    public int getNoOfCars() {
        return noOfCars;
    }

    public void setNoOfCars(int noOfCars) {
        this.noOfCars = noOfCars;
    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        this.carCapacity = carCapacity;
    }

    public int getNoOfCities() {
        return noOfCities;
    }

    public void setNoOfCities(int noOfCities) {
        this.noOfCities = noOfCities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}

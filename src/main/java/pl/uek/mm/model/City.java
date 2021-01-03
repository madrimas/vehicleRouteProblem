package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class City {

	public static final int DEPOT_LOCATION_ID = 0;

    private String name;
	private int id;
    private double longitude;
    private double latitude;
    private int demand;

	/**
	 * depot city constructor
	 */
	public City(String name, double latitude, double longitude) {
		this.name = name;
		this.id = DEPOT_LOCATION_ID;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * consumer city constructor
	 */
	public City(String name, double latitude, double longitude, int demand) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.demand = demand;
	}

	/**
	 * consumer city constructor
	 */
	public City(String name, int id, double latitude, double longitude, int demand) {
		this.name = name;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.demand = demand;
	}

}

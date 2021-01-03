package pl.uek.mm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Vehicle {

	private int id;
	private List<City> route = new ArrayList<>();
	private int capacity;
	private int load;

	public Vehicle(int id, int capacity) {
		this.id = id;
		this.capacity = capacity;
		this.load = 0;
	}

	public void visitCustomer(City city) {
		route.add(city);
		load += city.getDemand();
	}

	/**
	 * check capacity violation
	 */
	public boolean checkIfFits(int demand) {
		return ((collectLoad() + demand <= capacity));
	}

	private int collectLoad() {
		int currentLoad = 0;
		for (City city : route) {
			currentLoad += city.getDemand();
		}

		load = currentLoad;

		return load;
	}

}

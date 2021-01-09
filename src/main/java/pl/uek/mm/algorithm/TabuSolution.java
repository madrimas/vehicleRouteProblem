package pl.uek.mm.algorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.uek.mm.model.City;
import pl.uek.mm.model.InputDataModel;
import pl.uek.mm.model.InputParametersModel;
import pl.uek.mm.model.Vehicle;
import pl.uek.mm.util.GeoUtil;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class TabuSolution {

	private int tabuDelay;
	private int tabuRandomBound;
	private int maxIterations;
	private int maxIterationsWithoutImprovement;

	private int vehiclesAmount;
	private int customersAmount;
	private int vehicleCapacity;
	private List<City> cities;

	private double[][] distanceMatrix;

	private double cost;
	private List<Vehicle> vehicles;
	private double bestSolutionCost;
	private List<Vehicle> vehiclesForBestSolution;

	private int[][] tabuMatrix;
	private int tabuMatrixDimension;

	double bestNeighbourCost = (float) Double.MAX_VALUE;
	private int swapIndexFrom = -1;
	private int swapIndexTo = -1;
	private int swapRouteFrom = -1;
	private int swapRouteTo = -1;

	public TabuSolution(InputParametersModel parameters, InputDataModel input) {
		tabuDelay = parameters.getTabuDelay();
		tabuRandomBound = parameters.getTabuRandomBound();
		maxIterations = parameters.getMaxIterations();
		maxIterationsWithoutImprovement = parameters.getMaxIterationsWithoutImprovement();

		this.vehiclesAmount = input.getNoOfCars();
		this.customersAmount = input.getNoOfCities();
		this.vehicleCapacity = input.getCarCapacity();
		this.cities = input.getCities();

		this.distanceMatrix = new double[customersAmount + 1][customersAmount + 1];

		this.cost = 0.0;
		this.bestSolutionCost = 0.0;

		this.vehicles = new ArrayList<>();
		this.vehiclesForBestSolution = new ArrayList<>();

		this.tabuMatrixDimension = cities.size() + 1;
		this.tabuMatrix = new int[tabuMatrixDimension][tabuMatrixDimension];

		computeDistanceMatrix();
		createVehicles();
	}

	/**
	 * distance matrix is symmetrical to the first diagonal (O(n/2))
	 */
	private void computeDistanceMatrix() {
		for (int i = 0; i <= customersAmount; i++) {
			for (int j = i + 1; j <= customersAmount; j++) {

				double distance = GeoUtil.distance(
						cities.get(i).getLatitude(),
						cities.get(j).getLatitude(),
						cities.get(i).getLongitude(),
						cities.get(j).getLongitude()
				);
				distance = Math.round(distance * 100.0) / 100.0;

				distanceMatrix[i][j] = distance;
				distanceMatrix[j][i] = distance;
			}
		}
	}

	private void createVehicles() {
		for (int i = 0; i < vehiclesAmount; i++) {
			vehicles.add(new Vehicle(i + 1, vehicleCapacity));
			vehiclesForBestSolution.add(new Vehicle(i + 1, vehicleCapacity));
		}
	}

	public void initSolution() throws Exception {
//		int availableVehicleIndex = 0;
//		for (int i = 1; i <= customersAmount; i++) {
//			City city = cities.get(i);
//			int currentCityDemand = city.getDemand();
//			Vehicle currentVehicle = vehicles.get(availableVehicleIndex);
//
//			if (!currentVehicle.checkIfFits(currentCityDemand)){
//				if (availableVehicleIndex >= (vehiclesAmount - 1)) {
//					throw new Exception("Not enough vehicles");
//				}
//
//				availableVehicleIndex += 1;
//				currentVehicle = vehicles.get(availableVehicleIndex);
//			}
//
//			currentVehicle.visitCustomer(city);
//		}

		vehicles.get(0).visitCustomer(cities.get(26));
		vehicles.get(0).visitCustomer(cities.get(16));
		vehicles.get(0).visitCustomer(cities.get(1));

		vehicles.get(1).visitCustomer(cities.get(2));
		vehicles.get(1).visitCustomer(cities.get(6));
		vehicles.get(1).visitCustomer(cities.get(28));
		vehicles.get(1).visitCustomer(cities.get(24));
		vehicles.get(1).visitCustomer(cities.get(22));
		vehicles.get(1).visitCustomer(cities.get(17));
		vehicles.get(1).visitCustomer(cities.get(5));
		vehicles.get(1).visitCustomer(cities.get(4));
		vehicles.get(1).visitCustomer(cities.get(14));
		vehicles.get(1).visitCustomer(cities.get(13));

		vehicles.get(2).visitCustomer(cities.get(8));
		vehicles.get(2).visitCustomer(cities.get(3));

		vehicles.get(3).visitCustomer(cities.get(27));
		vehicles.get(3).visitCustomer(cities.get(15));
		vehicles.get(3).visitCustomer(cities.get(29));
		vehicles.get(3).visitCustomer(cities.get(11));
		vehicles.get(3).visitCustomer(cities.get(10));
		vehicles.get(3).visitCustomer(cities.get(20));
		vehicles.get(3).visitCustomer(cities.get(7));
		vehicles.get(3).visitCustomer(cities.get(25));

		vehicles.get(4).visitCustomer(cities.get(9));
		vehicles.get(4).visitCustomer(cities.get(19));
		vehicles.get(4).visitCustomer(cities.get(18));
		vehicles.get(4).visitCustomer(cities.get(12));
		vehicles.get(4).visitCustomer(cities.get(30));
		vehicles.get(4).visitCustomer(cities.get(21));
		vehicles.get(4).visitCustomer(cities.get(23));

		City depotCity = cities.get(0);
		for (Vehicle vehicle : vehicles) {
			List<City> currentRoute = vehicle.getRoute();
			currentRoute.add(0, depotCity);
			currentRoute.add(depotCity);

			for (int i = 1; i < currentRoute.size(); i++) {
				City previousCity = currentRoute.get(i - 1);
				City currentCity = currentRoute.get(i);

				cost += distanceMatrix[previousCity.getId()][currentCity.getId()];
			}

		}
	}

	public void executeTabuSearch() {
		bestSolutionCost = cost;
		int iterationsWithoutImprovementCounter = 0;

		for (int iteration = 0; iteration < maxIterations; iteration++) {
			List<City> routeFrom;
			List<City> routeTo;
			bestNeighbourCost = (float) Double.MAX_VALUE;

			findBestNeighbour();

			routeFrom = vehicles.get(swapRouteFrom).getRoute();
			routeTo = vehicles.get(swapRouteTo).getRoute();
			City swapCity = routeFrom.get(swapIndexFrom);

			decreaseTabu();
			addTabu(routeFrom, routeTo, swapCity);
			changeRoute(routeFrom, routeTo, swapCity);

			cost += bestNeighbourCost;
			if (cost < bestSolutionCost) {
				saveBestSolution();
				iterationsWithoutImprovementCounter = 0;
			} else {
				iterationsWithoutImprovementCounter++;
			}

			if (iterationsWithoutImprovementCounter >= maxIterationsWithoutImprovement) {
				break;
			}
		}

//		vehicles = vehiclesForBestSolution;
//		cost = Math.round(bestSolutionCost * 100.0) / 100.0;
	}

	private void findBestNeighbour() {
		List<City> routeFrom;
		List<City> routeTo;
		for (int vehicleFromIndex = 0; vehicleFromIndex < vehicles.size(); vehicleFromIndex++) {
			routeFrom = vehicles.get(vehicleFromIndex).getRoute();
			for (int routeFromIndex = 1; routeFromIndex < (routeFrom.size() - 1); routeFromIndex++) {
				for (int vehicleToIndex = 0; vehicleToIndex < vehicles.size(); vehicleToIndex++) {
					routeTo = vehicles.get(vehicleToIndex).getRoute();
					for (int routeToIndex = 0; routeToIndex < (routeTo.size() - 1); routeToIndex++) {

						if (isTabuMove(routeFrom, routeTo, routeFromIndex, routeToIndex)) {
							break;
						}

						int movingDemand = routeFrom.get(routeFromIndex).getDemand();
						if (isChangingRouteAvailable(vehicleFromIndex, vehicleToIndex, movingDemand)
								&& isCostChange(vehicleFromIndex, vehicleToIndex, routeFromIndex, routeToIndex)) {
							double neighbourCost = calculateNeighbourCost(routeFrom, routeTo, routeFromIndex, routeToIndex);
							if (neighbourCost < bestNeighbourCost) {
								changeBestNeighbour(neighbourCost, vehicleFromIndex, vehicleToIndex, routeFromIndex, routeToIndex);
							}
						}
					}
				}
			}
		}
	}

	private boolean isTabuMove(List<City> routeFrom, List<City> routeTo, int routeFromIndex, int routeToIndex) {
		return (tabuMatrix[routeFrom.get(routeFromIndex - 1).getId()][routeFrom.get(routeFromIndex + 1).getId()] != 0)
				|| (tabuMatrix[routeTo.get(routeToIndex).getId()][routeFrom.get(routeFromIndex).getId()] != 0)
				|| (tabuMatrix[routeFrom.get(routeFromIndex).getId()][routeTo.get(routeToIndex + 1).getId()] != 0);
	}

	private boolean isChangingRouteAvailable(int vehicleFromIndex, int vehicleToIndex, int movingDemand) {
		return isTheSameVehicle(vehicleFromIndex, vehicleToIndex) || vehicles.get(vehicleToIndex).checkIfFits(movingDemand);
	}

	private boolean isTheSameVehicle(int vehicleFromIndex, int vehicleToIndex) {
		return vehicleFromIndex == vehicleToIndex;
	}

	private boolean isCostChange(int vehicleFromIndex, int vehicleToIndex, int routeFromIndex, int routeToIndex) {
		return !(isTheSameVehicle(vehicleFromIndex, vehicleToIndex) && ((routeFromIndex == routeToIndex) || (routeToIndex == routeFromIndex - 1)));
	}

	private double calculateNeighbourCost(List<City> routeFrom, List<City> routeTo, int routeFromIndex, int routeToIndex) {
		double oldCost1 = distanceMatrix[routeFrom.get(routeFromIndex - 1).getId()][routeFrom.get(routeFromIndex).getId()];
		double oldCost2 = distanceMatrix[routeFrom.get(routeFromIndex).getId()][routeFrom.get(routeFromIndex + 1).getId()];
		double oldCost3 = distanceMatrix[routeTo.get(routeToIndex).getId()][routeTo.get(routeToIndex + 1).getId()];

		double newCost1 = distanceMatrix[routeFrom.get(routeFromIndex - 1).getId()][routeFrom.get(routeFromIndex + 1).getId()];
		double newCost2 = distanceMatrix[routeTo.get(routeToIndex).getId()][routeFrom.get(routeFromIndex).getId()];
		double newCost3 = distanceMatrix[routeFrom.get(routeFromIndex).getId()][routeTo.get(routeToIndex + 1).getId()];

		return newCost1 + newCost2 + newCost3 - oldCost1 - oldCost2 - oldCost3;
	}

	private void changeBestNeighbour(double neighbourCost, int vehicleFromIndex, int vehicleToIndex, int routeFromIndex, int routeToIndex) {
		bestNeighbourCost = neighbourCost;
		swapRouteFrom = vehicleFromIndex;
		swapRouteTo = vehicleToIndex;
		swapIndexFrom = routeFromIndex;
		swapIndexTo = routeToIndex;
	}

	private void decreaseTabu() {
		for (int i = 0; i < tabuMatrixDimension; i++) {
			for (int j = 0; j < tabuMatrixDimension; j++) {
				if (tabuMatrix[i][j] > 0) {
					tabuMatrix[i][j]--;
				}
			}
		}
	}

	private void addTabu(List<City> routeFrom, List<City> routeTo, City swapCity) {
		int cityBeforeId = routeFrom.get(swapIndexFrom - 1).getId();
		int cityAfterId = routeFrom.get(swapIndexFrom + 1).getId();
		int newCityId = routeTo.get(swapIndexTo).getId();
		int newNextCityId = routeTo.get(swapIndexTo + 1).getId();

		int swapCityId = swapCity.getId();
		tabuMatrix[cityBeforeId][swapCityId] = tabuDelay + (int) (Math.random() * tabuRandomBound);
		tabuMatrix[swapCityId][cityAfterId] = tabuDelay + (int) (Math.random() * tabuRandomBound);
		tabuMatrix[newCityId][newNextCityId] = tabuDelay + (int) (Math.random() * tabuRandomBound);
	}

	private void changeRoute(List<City> routeFrom, List<City> routeTo, City swapCity) {
		routeFrom.remove(swapIndexFrom);
		addNewRoute(routeTo, swapCity);
		vehicles.get(swapRouteFrom).setRoute(routeFrom);
		vehicles.get(swapRouteTo).setRoute(routeTo);
	}

	private void addNewRoute(List<City> routeTo, City swapCity) {
		if (swapRouteFrom == swapRouteTo) {
			if (swapIndexFrom < swapIndexTo) {
				routeTo.add(swapIndexTo, swapCity);
			} else {
				routeTo.add(swapIndexTo + 1, swapCity);
			}
		} else {
			routeTo.add(swapIndexTo + 1, swapCity);
		}
	}

	private void saveBestSolution() {
		bestSolutionCost = cost;
		for (int i = 0; i < vehiclesAmount; i++) {
			vehiclesForBestSolution.get(i).getRoute().clear();
			if (!vehicles.get(i).getRoute().isEmpty()) {
				int routeSize = vehicles.get(i).getRoute().size();
				for (int k = 0; k < routeSize; k++) {
					City city = vehicles.get(i).getRoute().get(k);
					vehiclesForBestSolution.get(i).getRoute().add(city);
				}
			}
		}
	}

}

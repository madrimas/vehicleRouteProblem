package pl.uek.mm.service;

import org.springframework.stereotype.Service;
import pl.uek.mm.algorithm.TabuSolution;
import pl.uek.mm.model.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {

    public ResponseModel doCalculations(RequestData request) throws Exception {
        Instant executionStart = Instant.now();

//	    TabuSolution solution = new TabuSolution(createMockInput());
	    TabuSolution solution = new TabuSolution(
	    		prepareInputParameters(request.getInputParameters()),
			    prepareInputData(request.getInputData())
	    );
	    solution.initSolution();
	    solution.executeTabuSearch();

        ResponseModel model = new ResponseModel();

//	    model.setResultData(createMockResponse());
	    model.setResultData(createResponse(solution));

        model.getResultData().setExecutionTime(Duration.between(executionStart, Instant.now()).toMillis());
        return  model;
    }

	private InputParametersModel prepareInputParameters(InputParametersModel inputParameters) {
		InputParametersModel parameters = new InputParametersModel();

		Integer tabuDelay = inputParameters.getTabuDelay();
		parameters.setTabuDelay(tabuDelay != null ? tabuDelay : 10);

		Integer tabuRandomBound = inputParameters.getTabuRandomBound();
		parameters.setTabuRandomBound(tabuRandomBound != null ? tabuRandomBound : 1);

		Integer maxIterations = inputParameters.getMaxIterations();
		parameters.setMaxIterations(maxIterations != null ? maxIterations : 100000);

		Integer maxIterationsWithoutImprovement = inputParameters.getMaxIterationsWithoutImprovement();
		parameters.setMaxIterationsWithoutImprovement(maxIterationsWithoutImprovement != null ? maxIterationsWithoutImprovement : 10000);

		return parameters;
	}

	private InputDataModel prepareInputData(InputDataModel inputData) {
		int idCounter = 0;
		for (City city : inputData.getCities()) {
			city.setId(idCounter);
			idCounter++;
		}

		return inputData;
	}

	private ResultDataModel createResponse(TabuSolution solution) {
		List<Path> paths = new ArrayList<>();
		for (Vehicle vehicle : solution.getVehicles()) {
			Path path = new Path();
			for (City city : vehicle.getRoute()) {
				PathCity pathCity = new PathCity();
				pathCity.setName(city.getName());
				pathCity.setLatitude(city.getLatitude());
				pathCity.setLongitude(city.getLongitude());
				pathCity.setDemand(city.getDemand());
				path.getPathCities().add(pathCity);
			}
			paths.add(path);
		}

		ResultDataModel result = new ResultDataModel();
		result.setCost(solution.getCost());
		result.setPaths(paths);
		return result;
	}

	private ResultDataModel createMockResponse() {
        ResultDataModel result = new ResultDataModel();
        List<Path> paths = new ArrayList<>();

        Path cracowPath = new Path();
        Path brzeskoPath = new Path();

        PathCity cracow = new PathCity();
		cracow.setLatitude(19.11);
		cracow.setLongitude(15.11);
		cracow.setName("Cracow");

		PathCity brzesko = new PathCity();
		brzesko.setName("Brzesko");
		brzesko.setLatitude(19.11);
		brzesko.setLongitude(15.11);

		cracowPath.getPathCities().add(cracow);
		cracowPath.getPathCities().add(brzesko);
		brzeskoPath.getPathCities().add(cracow);
		brzeskoPath.getPathCities().add(brzesko);

        paths.add(cracowPath);
        paths.add(brzeskoPath);

        result.setPaths(paths);

       return result;
    }

	private InputDataModel createMockInput() {
		InputDataModel input = new InputDataModel();

		int customersAmount = 30;
		int vehiclesAmount = 5;
		int vehicleCapacity = 1000;

		double depotLatitude = 50.0638240635848;
		double depotLongitude = 19.9450372973592;

		List<Integer> demands = List.of(
				500, 50, 400, 200, 100, 40, 200, 300, 30, 60,
				50, 60, 160, 100, 120, 300, 100, 200, 100, 60,
				200, 150, 60, 50, 70, 200, 90, 40, 200, 300);

		List<Double> latitude = List.of(
				53.133763808683, 49.8228931507415, 50.1346925170808, 54.3681868752769, 54.5261256129553,
				50.292884459293, 49.8385799795983, 50.2483514377451, 50.872438125039, 49.6825311354067,
				49.426603045457, 51.2494638321565, 51.7597054582131, 54.0360626134552, 49.4772173217487,
				53.7815529251133, 52.4153351294928, 51.4168078115061, 51.3991805522491, 50.0415733058072,
				50.6815748754491, 53.4238859866752, 50.3085914366627, 50.8300872396905, 50.0117837939957,
				52.2269667125479, 49.9823650173841, 51.1028550955785, 49.2913229074502, 50.720507802313);

		List<Double> longitude = List.of(
				23.1571057303185, 19.0572725638193, 19.4014550408773, 18.6213698922573, 18.539695724899,
				18.6623182427468, 20.9645880884179, 19.0308809187921, 20.6204468294804, 21.7670346911926,
				20.9536428018337, 22.5645054358963, 19.4534561236313, 19.0367679305274, 20.0291639218743,
				20.479787211229, 16.9155608267419, 21.969341588227, 21.1505172073571, 21.9943494976893,
				21.7418333646162, 14.5566418008483, 21.0757528251235, 15.5094768025357, 20.9896079879687,
				21.0062584735009, 20.0600140047875, 17.0172657109586, 19.9567084969829, 23.2567520827492);

		List<City> cities = new ArrayList<>();
		City depot = new City("Kraków", depotLatitude, depotLongitude);
		cities.add(depot);
		for (int i = 1; i <= customersAmount; i++) { //Id 0 is reserved for depot
			cities.add(new City("XXX_" + i,
					i,
					latitude.get(i - 1),
					longitude.get(i - 1),
					demands.get(i - 1)
			));
		}


		input.setNoOfCars(vehiclesAmount);
		input.setCarCapacity(vehicleCapacity);
		input.setNoOfCities(customersAmount);
		input.setCities(cities);

		return input;
	}

}

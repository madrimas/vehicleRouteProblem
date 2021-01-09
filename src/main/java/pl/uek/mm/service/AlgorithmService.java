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

	    TabuSolution solution = new TabuSolution(
	    		prepareInputParameters(request.getInputParameters()),
			    prepareInputData(request.getInputData())
	    );
	    solution.initSolution();
	    solution.executeTabuSearch();

        ResponseModel model = new ResponseModel();

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

}

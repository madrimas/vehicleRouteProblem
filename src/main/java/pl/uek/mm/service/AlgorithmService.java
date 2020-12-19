package pl.uek.mm.service;

import org.springframework.stereotype.Service;
import pl.uek.mm.model.InputDataModel;
import pl.uek.mm.model.Path;
import pl.uek.mm.model.ResponseModel;
import pl.uek.mm.model.ResultDataModel;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {

    public ResponseModel doCalculations(InputDataModel inputData) {
        Instant executionStart = Instant.now();
        ResponseModel model = new ResponseModel();

        createMockResponse(model);

        model.getResultData().setExecutionTime(Duration.between(executionStart, Instant.now()).toMillis());
        return  model;
    }

    private void createMockResponse(ResponseModel model) {
        ResultDataModel result = new ResultDataModel();
        List<List<Path>> paths = new ArrayList<>();

        List<Path> firstPathList = new ArrayList<>();
        List<Path> secondPathList = new ArrayList<>();

        Path cracowPath = new Path();
        cracowPath.setLatitude(19.11);
        cracowPath.setLongitude(15.11);
        cracowPath.setName("Cracow");

        firstPathList.add(cracowPath);
        secondPathList.add(cracowPath);

        Path brzeskoPath = new Path();
        brzeskoPath.setName("Brzesko");
        brzeskoPath.setLatitude(19.11);
        brzeskoPath.setLongitude(15.11);

        firstPathList.add(brzeskoPath);
        secondPathList.add(brzeskoPath);

        paths.add(firstPathList);
        paths.add(secondPathList);

        result.setPaths(paths);

        model.setResultData(result);
    }

}

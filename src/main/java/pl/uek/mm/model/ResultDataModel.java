package pl.uek.mm.model;

import java.util.List;

public class ResultDataModel {

    private List<List<Path>> paths;
    private long executionTime;

    public ResultDataModel() {
    }

    public List<List<Path>> getPaths() {
        return paths;
    }

    public void setPaths(List<List<Path>> paths) {
        this.paths = paths;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

}

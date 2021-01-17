package pl.harazin.bagsoptimiser.model;

import java.util.Arrays;
import java.util.List;

public class Response {
    public List<List<Product>> result;
    public float avgTime;
    public float[] times;

    public Response(List<List<Product>> result, float[] measurements) {
        this.result = result;
        this.times = measurements;
        float sum = 0;
        for (float measurement : measurements) {
            sum += measurement;
        }
        this.avgTime = sum / measurements.length;
    }
}

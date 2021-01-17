package pl.harazin.bagsoptimiser.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Response {
    private List<List<Product>> result;
    private float avgTime;
    private float[] times;
    private int[] bagUsage;
    private int avgBagUsage;


    public Response(List<List<Product>> result, float[] measurements, int[] bagUsage) {
        this.result = result;
        this.times = measurements;
        this.bagUsage = bagUsage;
        float sum = 0;
        int bags = 0;
        for (float measurement : measurements) {
            sum += measurement;
        }
        for (int bag : bagUsage) {
            bags += bag;
        }
        this.avgTime = sum / measurements.length;
        this.avgBagUsage = bags / bagUsage.length;
    }
}

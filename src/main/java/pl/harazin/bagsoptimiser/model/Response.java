package pl.harazin.bagsoptimiser.model;

import java.util.List;

public class Response {
    public List<List<Product>> result;
    public float timeElapsedMs;

    public Response(List<List<Product>> result, float timeElapsed) {
        this.result = result;
        this.timeElapsedMs = timeElapsed;
    }
}

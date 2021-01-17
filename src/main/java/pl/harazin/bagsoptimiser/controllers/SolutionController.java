package pl.harazin.bagsoptimiser.controllers;


import com.mongodb.lang.Nullable;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.model.Response;
import pl.harazin.bagsoptimiser.services.*;
import pl.harazin.bagsoptimiser.services.genetic.Genetic;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SolutionController {

    private BruteForce bruteForce;
    private Dynamic dynamic;
    private Genetic genetic;
    private Greedy greedy;

    @RequestMapping(value = {"/solution/bruteForce", "/solution/bruteForce/{iterations}"}, method = RequestMethod.POST)
    public Response getBruteForce(@RequestBody List<Product> inputList, @PathVariable Optional<Integer> iterations) {
        return getResponse(inputList, bruteForce, iterations);
    }

    @RequestMapping(value = {"/solution/dynamic", "/solution/dynamic/{iterations}"}, method = RequestMethod.POST)
    public Response getDynamic(@RequestBody List<Product> inputList, @PathVariable Optional<Integer> iterations) {
        return getResponse(inputList, dynamic, iterations);
    }

    @RequestMapping(value = {"/solution/genetic", "/solution/genetic/{iterations}"}, method = RequestMethod.POST)
    public Response getGenetic(@RequestBody List<Product> inputList, @PathVariable Optional<Integer> iterations) {
        return getResponse(inputList, genetic, iterations);
    }

    @RequestMapping(value = {"/solution/greedy", "/solution/greedy/{iterations}"}, method = RequestMethod.POST)
    public Response getGreedy(@RequestBody List<Product> inputList, @PathVariable Optional<Integer> iterations) {
        return getResponse(inputList, greedy, iterations);
    }

    private Response getResponse(List<Product> inputList, Algorithm solver, Optional<Integer> iterations) {
        int numOfIterations = iterations.isPresent() && iterations.get() > 0 ? iterations.get() : 1;
        float[] iterationsTimes = new float[numOfIterations];
        List<List<Product>> solution = null;
        for (int i = 0; i < numOfIterations; i++) {
            Date startTime = new Date();
            solution = solver.solution(inputList);
            Date endTime = new Date();
            iterationsTimes[i] = endTime.getTime() - startTime.getTime();
            if (numOfIterations - i > 1) {
                solution = null;
            }
        }
        return new Response(solution, iterationsTimes);
    }
}

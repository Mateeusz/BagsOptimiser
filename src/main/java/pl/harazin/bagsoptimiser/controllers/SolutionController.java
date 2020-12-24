package pl.harazin.bagsoptimiser.controllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.model.Response;
import pl.harazin.bagsoptimiser.services.*;

import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SolutionController {

    private BruteForce bruteForce;
    private Dynamic dynamic;
    private Genetic genetic;
    private Greedy greedy;

    @RequestMapping(value = "/solution/bruteForce", method = RequestMethod.POST)
    public Response getBruteForce(@RequestBody List<Product> inputList) {
        return getResponse(inputList, bruteForce);
    }

    @RequestMapping(value = "/solution/dynamic", method = RequestMethod.POST)
    public Response getDynamic(@RequestBody List<Product> inputList) {
        return getResponse(inputList, dynamic);
    }

    @RequestMapping(value = "/solution/genetic", method = RequestMethod.POST)
    public Response getGenetic(@RequestBody List<Product> inputList) {
        return getResponse(inputList, genetic);
    }

    @RequestMapping(value = "/solution/greedy", method = RequestMethod.POST)
    public Response getGreedy(@RequestBody List<Product> inputList) {
        return getResponse(inputList, greedy);
    }

    private Response getResponse(List<Product> inputList, Algorithm solver)
    {
        Date startTime = new Date();
        List<List<Product>> solution = solver.solution(inputList);
        Date endTime = new Date();
        float timeElapsed = endTime.getTime() - startTime.getTime();
        return new Response(solution, timeElapsed);
    }
}

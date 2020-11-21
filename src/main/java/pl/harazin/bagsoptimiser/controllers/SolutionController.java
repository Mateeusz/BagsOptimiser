package pl.harazin.bagsoptimiser.controllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.services.BruteForce;
import pl.harazin.bagsoptimiser.services.Dynamic;
import pl.harazin.bagsoptimiser.services.Genetic;
import pl.harazin.bagsoptimiser.services.Greedy;

import java.util.List;


@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SolutionController {

    private BruteForce bruteForce;
    private Dynamic dynamic;
    private Genetic genetic;
    private Greedy greedy;

    @RequestMapping(value = "/solution/bruteForce", method = RequestMethod.POST)
    public List<List<Product>> getBruteForce(@RequestBody List<Product> inputList) {

        return bruteForce.solution(inputList);
    }

    @RequestMapping(value = "/solution/dynamic", method = RequestMethod.GET)
    public String getDynamic() {

        return "dynamic.solution()";
    }

    @RequestMapping(value = "/solution/genetic", method = RequestMethod.GET)
    public String getGenetic() {

        return "genetic.solution()";
    }

    @RequestMapping(value = "/solution/greedy", method = RequestMethod.GET)
    public String getGreedy() {

        return "greedy.solution()";
    }
}

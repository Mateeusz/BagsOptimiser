package pl.harazin.bagsoptimiser.services;

import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class Dynamic implements Algorithm {

    @Override
    public List<List<Product>> solution(List<Product> inputProducts) {
        List<List<Product>> result = new ArrayList<>();

        return result;
    }
}

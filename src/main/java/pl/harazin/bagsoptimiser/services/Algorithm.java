package pl.harazin.bagsoptimiser.services;

import pl.harazin.bagsoptimiser.model.Product;

import java.util.List;

public interface Algorithm {

    int BAG_WEIGHT = 10000;
    int BAG_CAPACITY = 10000;

    List<List<Product>> solution(List<Product> inputProducts);

}

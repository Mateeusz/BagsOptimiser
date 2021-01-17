package pl.harazin.bagsoptimiser.services;

import pl.harazin.bagsoptimiser.model.Product;

import java.util.List;

public interface Algorithm {

    int BAG_WEIGHT = 15;
    int BAG_CAPACITY = 150;

    List<List<Product>> solution(List<Product> inputProducts);

}

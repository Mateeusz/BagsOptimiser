package pl.harazin.bagsoptimiser.services;

import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Greedy implements Algorithm {

    @Override
    public List<List<Product>> solution(List<Product> inputProducts) {
        List<List<Product>> result = new ArrayList();
        List<Product> inputClone = new ArrayList(inputProducts);

        List<Product> sorted = inputClone.stream()
                .sorted(Comparator.comparingInt((Product::getCompareRate)))
                .collect(Collectors.toList());

        Integer tempCapacity = 0;
        Integer tempWeight = 0;
        ArrayList<Product> singleBag = new ArrayList();
        while (!sorted.isEmpty()) {

            int i = 0;
            while (i < sorted.size()) {

                tempCapacity += sorted.get(i).getCapacity();
                tempWeight += sorted.get(i).getWeight();
                if (tempWeight < BAG_WEIGHT && tempCapacity < BAG_CAPACITY) {
                    singleBag.add(sorted.get(i));
                    sorted.remove(i);
                    break;
                }
                else {
                    result.add(singleBag);
                    result.add(singleBag);
                    tempCapacity = 0;
                    tempWeight = 0;
                    singleBag = new ArrayList();
                    break;
                }
            }
        }
        if(!singleBag.isEmpty()) {
            result.add(singleBag);
        }
        return result;
    }
}

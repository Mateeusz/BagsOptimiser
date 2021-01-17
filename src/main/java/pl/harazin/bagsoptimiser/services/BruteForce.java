package pl.harazin.bagsoptimiser.services;

import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class BruteForce implements Algorithm {

    @Override
    public List<List<Product>> solution(List<Product> inputProducts) {
        List<List<Product>> result = new ArrayList<>();

        List<Product> inputClone = new ArrayList(inputProducts);
        while (inputClone.size() > 0) {
            result.add(getProductsFromBag(inputClone));
        }

        return result;
    }

    private List<Product> getProductsFromBag(List<Product> inputProducts) {
        List<Product> bestList = new ArrayList<>();
        bestList = compute(inputProducts, bestList);

        for (int i=0; i<inputProducts.size(); i++) {
            for (Product product : bestList) {
                if(inputProducts.get(i).equals(product)) {
                    inputProducts.remove(i);
                }
            }
        }

        return bestList;
    }

    private List<Product> compute(List<Product> inputProducts, List<Product> bestList) {
        int bestWeight = 0;
        int bestCapacity = 0;

        int allMasks = (1<<inputProducts.size());

        for (int i=1; i<allMasks; i++) {
            List<Product> currentBestList = new ArrayList<>();
            int currentBestWeight = 0;
            int currentBestCapacity = 0;

            for (int j=0; j<inputProducts.size(); j++) {

                if ((i & (1<<j)) > 0) {
                    currentBestWeight += inputProducts.get(j).getWeight();
                    currentBestCapacity += inputProducts.get(j).getCapacity();
                    currentBestList.add(inputProducts.get(j));
                }
            }

            if(currentBestWeight <= BAG_WEIGHT && currentBestCapacity <= BAG_CAPACITY) {
                if(currentBestWeight > bestWeight && currentBestCapacity > bestCapacity) {
                    bestWeight = currentBestWeight;
                    bestCapacity = currentBestCapacity;
                    bestList = currentBestList;
                }
            }
        }
        return bestList;
    }
}

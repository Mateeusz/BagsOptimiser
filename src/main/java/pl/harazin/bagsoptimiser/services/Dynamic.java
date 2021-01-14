package pl.harazin.bagsoptimiser.services;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Dynamic implements Algorithm {

    private class DpItem {
        public int val = 0;
        public List<Integer> productIndices = new ArrayList<>();

        public DpItem() {}

        public DpItem(DpItem itemToClone) {
            this.val = itemToClone.val;
            this.productIndices = new ArrayList<>(itemToClone.productIndices);
        }

        public DpItem(DpItem itemToClone, int additionalItem) {
            this(itemToClone);
            this.val++;
            this.productIndices.add(additionalItem);
        }
    }

    @Override
    public List<List<Product>> solution(List<Product> inputProducts) {
        List<List<Product>> result = new ArrayList<>();

        while (!inputProducts.isEmpty()) {
            result.add(this.getProductsForBag(inputProducts));
        }

        return result;
    }

    private TreeSet<Integer> getSums(List<Integer> values, int starting, int maxSum) {
        if (values.size() == starting) {
            return new TreeSet();
        }

        Set recursiveSet = getSums(values, starting + 1, maxSum);

        Set newSet = new HashSet();
        int value = values.get(starting);
        for (Object object : recursiveSet) {
            int element = (int) object;
            if (element <= maxSum) {
                newSet.add(element);
            }
            if (element + value <= maxSum) {
                newSet.add(element + value);
            }
        }

        if (value <= maxSum) {
            newSet.add(value);
        }
        newSet.add(0);

        return new TreeSet(newSet);
    }

    private List<Product> getProductsForBag(List<Product> input) {
        int inputSize = input.size();
        List<Product> productsForBag = new ArrayList<>();

        List<Integer> allWeights = input.stream().map(x -> x.getWeight()).collect(Collectors.toList());
        TreeSet<Integer> possibleWeightSumsSet = getSums(allWeights,0, BAG_WEIGHT);
        Integer[] possibleWeightSums = possibleWeightSumsSet.toArray(new Integer[possibleWeightSumsSet.size()]);
        int maxWeight = Collections.max(possibleWeightSumsSet);

        List<Integer> allVolumes = input.stream().map(x -> x.getCapacity()).collect(Collectors.toList());
        TreeSet<Integer> possibleVolumeSumsSet = getSums(allVolumes, 0, BAG_CAPACITY);
        Integer[] possibleVolumeSums = possibleVolumeSumsSet.toArray(new Integer[possibleVolumeSumsSet.size()]);
        int maxVolume = Collections.max(possibleVolumeSumsSet);

        DpItem[][][] dpArray = new DpItem[inputSize + 1][maxWeight + 1][maxVolume + 1];

        for (int i = 0; i <= inputSize; i++) {
            for (int j = 0; j <= maxWeight; j++) {
                Arrays.fill(dpArray[i][0], new DpItem());
                Arrays.fill(dpArray[0][j], new DpItem());
                dpArray[i][j][0] = new DpItem();
            }
        }

        for (int i = 1; i <= inputSize; i++) {
            int weight = input.get(i - 1).getWeight();
            int volume = input.get(i - 1).getCapacity();
            for (int j = 1; j <= possibleWeightSums.length ; j++) {
                int w = possibleWeightSums[j - 1];
                for (int k = 1; k <= possibleVolumeSums.length; k++) {
                    int v = possibleVolumeSums[k - 1];
                    if (w < weight || v < volume || dpArray[i-1][w][v].val > dpArray[i-1][w-weight][v-i].val + 1) {
                        dpArray[i][w][v] = dpArray[i-1][w][v];
                    } else {
                        dpArray[i][w][v] = new DpItem(dpArray[i-1][w-weight][v-i], i - 1);
                    }
                    DpItem currentItem = dpArray[i][w][v];
                    if (j != possibleWeightSums.length) {
                        for (int l = w ; l < possibleWeightSums[j] ; l++) {
                            dpArray[i][l][v] = currentItem;
                            if (k != possibleVolumeSums.length) {
                                for (int m = v ; m < possibleVolumeSums[k] ; m++) {
                                    dpArray[i][l][m] = currentItem;
                                }
                            }
                        }
                    }
                }
            }
        }

        List<Integer> productIndices = dpArray[inputSize][maxWeight][maxVolume].productIndices;
        Collections.sort(productIndices, Collections.reverseOrder());
        for (int index : productIndices) {
            productsForBag.add(input.get(index));
            input.remove(index);
        }

        return productsForBag;
    }
}

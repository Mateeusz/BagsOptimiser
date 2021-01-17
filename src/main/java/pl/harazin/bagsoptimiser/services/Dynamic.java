package pl.harazin.bagsoptimiser.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Dynamic implements Algorithm {

    private class DpItem {
        private int val = 0;
        private List<Integer> productIndices = new ArrayList<>();

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
        List<List<Product>> result = new ArrayList();
        List<Product> inputClone = new ArrayList(inputProducts);

        while (!inputClone.isEmpty()) {
            result.add(this.getProductsForBag(inputClone));
        }

        return result;
    }

    private TreeSet<Integer> getSums(List<Integer> values, int starting, int maxSum) {
        if (values.size() == starting) {
            return new TreeSet();
        }

        Set<Integer> recursiveSet = getSums(values, starting + 1, maxSum);

        Set<Integer> newSet = new HashSet();
        int value = values.get(starting);
        for (Integer number : recursiveSet) {
            int element = number;
            if (element <= maxSum) {
                newSet.add(element);
                if (element + value <= maxSum) {
                    newSet.add(element + value);
                }
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

        DpItem[][][] dpArray = new DpItem[inputSize + 1][BAG_WEIGHT + 1][BAG_CAPACITY + 1];

        for (int i = 0; i <= inputSize; i++) {
            for (int j = 0; j <= BAG_WEIGHT; j++) {
                Arrays.fill(dpArray[i][0], new DpItem());
                Arrays.fill(dpArray[0][j], new DpItem());
                dpArray[i][j][0] = new DpItem();
            }
        }

        for (int i = 1; i <= inputSize; i++) {
            int weight = input.get(i - 1).getWeight();
            int volume = input.get(i - 1).getCapacity();
            for (int w = 1; w <= BAG_WEIGHT ; w++) {
                for (int v = 1; v <= BAG_CAPACITY; v++) {
                    if (w < weight || v < volume)
                        dpArray[i][w][v] = dpArray[i - 1][w][v];
                    else if (dpArray[i-1][w][v].val > dpArray[i-1][w-weight][v-volume].val + 1)
                        dpArray[i][w][v] = dpArray[i - 1][w][v];
                    else
                        dpArray[i][w][v] = new DpItem(dpArray[i-1][w-weight][v-volume], i - 1);
                }
            }
        }

        List<Integer> productIndices = dpArray[inputSize][BAG_WEIGHT][BAG_CAPACITY].productIndices;
        Collections.sort(productIndices, Collections.reverseOrder());
        for (int index : productIndices) {
            productsForBag.add(input.get(index));
            input.remove(index);
        }

        return productsForBag;
    }
}

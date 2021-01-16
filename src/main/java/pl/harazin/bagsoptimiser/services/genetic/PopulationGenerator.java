package pl.harazin.bagsoptimiser.services.genetic;

import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.services.Algorithm;

import java.io.Console;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class PopulationGenerator {

    public static List<List<List<Product>>> generate(List<Product> inputList) {

        Random rand = new Random();

        List<List<List<Product>>> population = new ArrayList<>();

        for (int i = 0; i < Genetic.POPULATION; i++) {
            List<Product> productList = new ArrayList<>(inputList);
            List<Product> bag = new ArrayList<>();
            List<List<Product>> individual = new ArrayList<>();

            int weight = 0;
            int capacity = 0;

            while (!productList.isEmpty()) {

                Product randomProduct = productList.get(rand.nextInt(productList.size()));

                weight += randomProduct.getWeight();
                capacity += randomProduct.getCapacity();
                if (weight <= Algorithm.BAG_WEIGHT && capacity <= Algorithm.BAG_CAPACITY) {
                    bag.add(randomProduct);
                    productList.remove(randomProduct);
                } else {
                    weight = 0;
                    capacity = 0;
                    individual.add(new ArrayList<>(bag));
                    bag.clear();
                }
            }
            population.add(individual);
        }

        return population;
    }
}



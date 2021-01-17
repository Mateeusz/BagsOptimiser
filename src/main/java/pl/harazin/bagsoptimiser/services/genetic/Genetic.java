package pl.harazin.bagsoptimiser.services.genetic;

import lombok.val;
import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.services.Algorithm;

import java.util.*;

@Service
public class Genetic implements Algorithm {
    public static int POPULATION = 100;
    public static int REPRODUCTIVE_POPULATION = 30;
    public static int REPRODUCTIONS = 1000;
    public static int GENERATIONS = 10;
    public static double MUTATION_PROBABILITY = 0.001;
    public static double CROSSOVER_PROBABILITY = 0.9;

    @Override
    public List<List<Product>> solution(List<Product> inputProducts) {
        List<Product> inputClone = new ArrayList(inputProducts);
        List<List<List<Product>>> population = PopulationGenerator.generate(inputClone);

        return runAlgorithm(population, inputClone);
    }

    private List<List<Product>> runAlgorithm(List<List<List<Product>>> population, List<Product> inputProducts) {
        Random rand = new Random();
        sortPopulation(population);
        for (int i = 0; i < GENERATIONS; i++) {
            List<List<List<Product>>> reproductivePopulation = new ArrayList(population.subList(0, REPRODUCTIVE_POPULATION - 1));
            //reproduction
            for (int j = 0; j < REPRODUCTIONS; j++) {
                if (rand.nextDouble() <= CROSSOVER_PROBABILITY) {
                    val parent1 = reproductivePopulation.get(rand.nextInt(reproductivePopulation.size()));
                    val parent2 = reproductivePopulation.get(rand.nextInt(reproductivePopulation.size()));
                    population.addAll(onePointCrossover(parent1, parent2, inputProducts));
                }
            }
            //mutation
            for (List<List<Product>> ind : population) {
                if (rand.nextDouble() <= MUTATION_PROBABILITY) {
                    List<Product> randomBag1 = ind.get(rand.nextInt(ind.size()));
                    List<Product> randomBag2 = ind.get(rand.nextInt(ind.size()));

                    Product product1 = randomBag1.get(rand.nextInt(randomBag1.size()));
                    Product product2 = randomBag2.get(rand.nextInt(randomBag2.size()));

                    randomBag2.add(product1);
                    randomBag1.add(product2);
                    randomBag2.remove(product2);
                    randomBag1.remove(product1);
                }
            }

            sortPopulation(population);
            population = checkPopulation(population);
            if (population.size() > POPULATION)
                population = population.subList(0, POPULATION);
        }

        return population.get(0);
    }

    private List<List<List<Product>>> checkPopulation(List<List<List<Product>>> population) {
        List<List<List<Product>>> newPopulation = new ArrayList(population);
        for (List<List<Product>> ind : population) {
            for (List<Product> bag : ind) {
                int weight = 0;
                int capacity = 0;

                for (Product p : bag) {
                    weight += p.getWeight();
                    capacity += p.getCapacity();
                }

                if (weight > Algorithm.BAG_WEIGHT || capacity > Algorithm.BAG_CAPACITY) {
                    newPopulation.remove(ind);
                    break;
                }
            }
        }
        return newPopulation;
    }

    private List<List<List<Product>>> onePointCrossover(List<List<Product>> parent1, List<List<Product>> parent2, List<Product> inputProducts) {
        Random rand = new Random();
        int biggerBagSize = Math.max(parent1.size(), parent2.size());
        int cutPoint = rand.nextInt(biggerBagSize);

        int[] set1 = generateSet(inputProducts.size(), parent1);
        int[] set2 = generateSet(inputProducts.size(), parent2);

        for (int i = cutPoint; i < set1.length; i++) {
            int temp = set1[i];
            set1[i] = set2[i];
            set2[i] = temp;
        }

        List<List<Product>> child1 = setToList(set1, inputProducts);
        List<List<Product>> child2 = setToList(set1, inputProducts);

        List<List<List<Product>>> children = new ArrayList();
        children.add(child1);
        children.add(child2);

        return children;
    }

    private List<List<Product>> setToList(int[] set, List<Product> inputProducts) {
        Map<Integer, List<Product>> map = new HashMap();

        for (int i = 0; i < set.length - 1; i++) {
            if (!map.containsKey(set[i])) {
                List<Product> list = new ArrayList();
                list.add(inputProducts.get(i + 1));
                map.put(set[i], list);
            } else {
                map.get(set[i]).add(inputProducts.get(i + 1));
            }
        }
        return new ArrayList(map.values());
    }

    private int[] generateSet(int productAmount, List<List<Product>> individual) {
        int[] set = new int[productAmount];
        int bagNumber = 0;
        for (List<Product> bag : individual) {

            for (Product product : bag) {
                set[Integer.parseInt(product.getId()) - 1] = bagNumber;
            }

            bagNumber++;
        }
        return set;
    }

    private static <T> void sortPopulation(List<List<T>> superList) {
        Collections.sort(superList, new Comparator<List<T>>() {
            @Override
            public int compare(List<T> o1, List<T> o2) {
                return Integer.compare(o1.size(), o2.size());
            }
        });
    }
}

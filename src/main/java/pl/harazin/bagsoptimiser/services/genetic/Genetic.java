package pl.harazin.bagsoptimiser.services.genetic;

import org.springframework.stereotype.Service;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.services.Algorithm;

import java.util.BitSet;
import java.util.List;

@Service
public class Genetic implements Algorithm {

    public static int POPULATION = 100;
    public static int GENERATIONS = 10;
    public static double MUTATION_PROBABILITY;
    public static double MUTATION_INCREMENT;
    public static double MAX_MUTATION_PROBABILITY;
    public static int NEGATIVE_FITNESS = -100;
    public static int OPTIMUM; // populated automatically
    public static boolean OPTIMUM_FOUND = false;
    public static int SHUFFLE_TOLERANCE = 10;
    public static double SHUFFLE_PROBABILITY;
    public static int GBEST;
    public static int MAX_TIME = 60 * 1000;

    @Override
    public List<List<Product>> solution(List<Product> inputProducts) {

        List<List<List<Product>>> population = PopulationGenerator.generate(inputProducts);


        MUTATION_PROBABILITY = (double) 1 / (double) inputProducts.size();
        MUTATION_INCREMENT = (double) 1 / (double) inputProducts.size();
        MAX_MUTATION_PROBABILITY = (double) 20 / (double) inputProducts.size();
        SHUFFLE_PROBABILITY = (double) 20 / (double) inputProducts.size();

        return null;

    }

    private List<Product> runAlgorithm(List population) {

        return null;
    }

    private int fitness(BitSet individual) {
        return 0;
    }

}

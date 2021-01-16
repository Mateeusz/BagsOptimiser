package pl.harazin.bagsoptimiser.services.genetic;

import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.services.Algorithm;

import java.io.Console;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class PopulationGenerator {

    public static List<BitSet> generate(List<Product> inputList) {

        List<BitSet> population = new ArrayList<BitSet>();

        for(int i = 0; i < Genetic.POPULATION; i++)
        {
           BitSet individual =  new BitSet(inputList.size());

           int weight = 0;
           int capacity = 0;
           int size = 0;
           int it = 0;
           Random rand = new Random();

           while ((weight < Algorithm.BAG_WEIGHT && capacity < Algorithm.BAG_CAPACITY) || size == inputList.size() || it < Genetic.MAX_POPULATION_IT){

               int randomInt = rand.nextInt(inputList.size());
               Product randomProduct = inputList.get(randomInt);
               if(!individual.get(randomInt)) {
                   weight += randomProduct.getWeight();
                   capacity += randomProduct.getCapacity();
                   if(weight < Algorithm.BAG_WEIGHT && capacity < Algorithm.BAG_CAPACITY){
                       size++;
                       individual.set(randomInt, true);
                   }
               }
               it++;
           }
            System.out.println(size);
            System.out.println(individual.toString());
         population.add(individual);
        }


        return population;
    }
}

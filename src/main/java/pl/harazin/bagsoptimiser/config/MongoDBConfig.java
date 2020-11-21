package pl.harazin.bagsoptimiser.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.repository.ProductRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories(basePackageClasses = ProductRepository.class)
@Configuration
public class MongoDBConfig {

    private static final String RESOURCE_PATH = "src/main/resources/InputData.json";

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {

        Gson gson = new Gson();
        List<Product> products = new ArrayList<>();
        try {
            JsonReader reader = new JsonReader(new FileReader(RESOURCE_PATH));
            products = gson.fromJson(reader, new TypeToken<List<Product>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Product> finalProducts = products;
        return strings -> {
            for (Product product : finalProducts) {
                productRepository.save(product);
            }
        };
    }

}
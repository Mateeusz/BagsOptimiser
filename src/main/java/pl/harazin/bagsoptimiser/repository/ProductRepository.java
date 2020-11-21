package pl.harazin.bagsoptimiser.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.harazin.bagsoptimiser.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}

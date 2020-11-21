package pl.harazin.bagsoptimiser.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.harazin.bagsoptimiser.model.Product;
import pl.harazin.bagsoptimiser.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Optional<Product> getById(@PathVariable String id) {
        return productRepository.findById(id);
    }
}

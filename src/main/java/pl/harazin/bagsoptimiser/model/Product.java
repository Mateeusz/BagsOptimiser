package pl.harazin.bagsoptimiser.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Product {

    @Id
    private int id;
    private String name;
    private Integer weight;
    private Integer capacity;

    private Integer compareRate;

    public Product(int id, String name, Integer weight, Integer capacity) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.capacity = capacity;
        this.compareRate = weight*capacity;
    }
}


package com.example.product_api.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;

@RestController
@RequestMapping("/products") //route de l'api
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAll() {
        //fonction findAll de JPARepository
        return repository.findAll();
    }

    @GetMapping("/{id}") //méthode get, route /products/id
    public Product getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }
    
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product existing = repository.findById(id).orElseThrow();
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        return repository.save(existing);
}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
@PostMapping("/{id}/duplicate")
public Product duplicateById(@PathVariable Long id) {
    Product existing = repository.findById(id).orElseThrow();
    Product duplicatedProduct = new Product();
    duplicatedProduct.setName(existing.getName());
    duplicatedProduct.setPrice(existing.getPrice());
    return repository.save(duplicatedProduct);
}

@PostMapping("/bundle/{id}")
public Product createBundle(@RequestBody Long id) {
    List<Product> bundle = new ArrayList<>();
    Product existing = repository.findById(id).orElseThrow();
    bundle.add(existing);
    return repository.saveAll(bundle);

}
}

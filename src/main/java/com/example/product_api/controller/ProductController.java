package com.example.product_api.controller;
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
    Product existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    Product duplicatedProduct = new Product();
    duplicatedProduct.setName(existing.getName());
    duplicatedProduct.setPrice(existing.getPrice());
    return repository.save(duplicatedProduct);
}

@PostMapping("/bundle")
    public Product createBundle(@RequestBody List<Long> sourceIds) {
        // Récupère tous les produits correspondant aux ids reçus
        List<Product> sources = repository.findAllById(sourceIds);
        if (sources.size() != sourceIds.size()) {
            throw new RuntimeException("Certains produits sources n'ont pas été trouvés");
        }
        if (hasRecursion(sources, sources)) {
            throw new RuntimeException("Boucle détectée dans les produits sources");
        }

        // Construit le nom du bundle en concaténant les noms des produits sources avec un " + "
        StringBuilder bundleName = new StringBuilder();
        double totalPrice = 0;

        for (Product p : sources) {
            if (bundleName.length() > 0) {
                bundleName.append(" + ");
            }
            bundleName.append(p.getName());
            totalPrice += p.getPrice();
        }
        Product bundle = new Product();
        bundle.setName(bundleName.toString());
        bundle.setPrice(totalPrice);
        bundle.setSources(sources);

        // Sauvegarde le bundle en base et le retourne
        return repository.save(bundle);
    }
    private boolean hasRecursion(List<Product> productsToCheck, List<Product> originalSources) {
        for (Product product : productsToCheck) {
            List<Product> sources = product.getSources();

            // Si ce produit a des sources associées
            if (sources != null && !sources.isEmpty()) {
                for (Product source : sources) {
                    // Si une source fait partie des produits originaux, on a une boucle
                    if (originalSources.contains(source)) {
                        return true;
                    }
                   
                    if (hasRecursion(List.of(source), originalSources)) {
                        return true;
                    }
                }
            }
        }
        return false; 
    }
}


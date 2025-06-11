package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.example.product_api.ProductApiApplication;
import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//ajout de la classe car mauvaise archtecture
@SpringBootTest(classes = ProductApiApplication.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ProductRepository repository;

    // mockmvc simule les requetes http
    @Autowired
    private MockMvc mockMvc;

    // transforme les objets en json
    @Autowired
    private ObjectMapper objectMapper;

    // avant chaque test, on vide la base pour rester propre
    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testReadProduct() throws Exception {
        Product product = repository.save(new Product(null, "Ecran", 150));

        mockMvc.perform(get("/products/" + product.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ecran"));
    }

    @Test
    void testCreateProduct() throws Exception {
        // création d'un faux produit pour tester
        Product product = new Product(null, "Clavier", 50);

        // on performe la méthode POST
        mockMvc.perform(post("/products")
                // on indique le type du contenu json
                .contentType(MediaType.APPLICATION_JSON)
                // transforme le contenu en json
                .content(objectMapper.writeValueAsString(product)))
                // on attend un statut http 200
                .andExpect(status().isOk())
                // vérifie que le champ id existe dans la réponse
                .andExpect(jsonPath("$.id").exists())
                // vérifie que name = Clavier
                .andExpect(jsonPath("$.name").value("Clavier"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = repository.save(new Product(null, "Souris", 200));

        // On modifie ses propriétés
        product.setName("Sac");
        product.setPrice(300);

        mockMvc.perform(put("/products/"
                + product.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Sac"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = repository.save(new Product(null, "machin", 2000));

        mockMvc.perform(delete("/products/" + product.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist()).andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void testDuplicateProduct() throws Exception {
        Product product = repository.save(new Product(null, "truc", 300));

        mockMvc.perform(post("/products/" + product.getId() + "/duplicate").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.name").value("truc"))
                .andExpect(jsonPath("$.price").value(300));
    }

    @Test
    void testCreateBundle() throws Exception {
        Product p1 = repository.save(new Product(null, "p1", 10));
        Product p2 = repository.save(new Product(null, "p2", 20));
        Product p3 = repository.save(new Product(null, "p3", 30));

        // Liste des IDs à envoyer dans le body
        List<Long> ids = List.of(p1.getId(), p2.getId(), p3.getId());

        mockMvc.perform(post("/products/bundle").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.name").value("p1 + p2 + p3")) // Nom concaténé
                .andExpect(jsonPath("$.price").value(60.0)); // Total: 10 + 20 + 30
    }

}

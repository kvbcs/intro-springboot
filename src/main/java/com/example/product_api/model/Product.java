package com.example.product_api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity //indique que c'est une classe JPA pour communiquer avec Hibernate (SQL intégré)

public class Product {
@Id //indique que le champ id est une clé primaire
@GeneratedValue(strategy = GenerationType.IDENTITY) //GeneratedValue : Génère auto l'id par la base de données
//GenerationType.IDENTITY signifie que la BDD auto incrémente

//Variables
private Long id;
private String name;
private double price;

@ManyToMany 
    @JoinTable( 
        name = "product_sources", 
        joinColumns = @JoinColumn(name = "product_id"), 
        inverseJoinColumns = @JoinColumn(name = "source_id") 
    ) 
private List<Product> sources = new ArrayList<>();
    
public List<Product> getSources() {
    return sources;
}

public void setSources(List<Product> sources) {
    this.sources = sources;
}


//Getters et setters
//comme les variables sont private, pour les utiliser ailleurs il faut faire des getters et setters

//Pour un get, on retourne la variable
public Long getId() {
    return id;
}

//Pour un set, on dit que le paramètre est égale à la valeur donnée
public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public double getPrice() {
    return price;
}

public void setPrice(double price) {
    this.price = price;
}
}

package com.example.product_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_api.model.Product;

//instanciation d'une interface qui hérite de JpaRepository qui contient déjà des CRUD
//Product est le type d'entité fait model
//Long : le type de l'id
public interface ProductRepository extends JpaRepository<Product, Long>{

}

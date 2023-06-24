package com.magnus.cafe.dao;

import com.magnus.cafe.POJO.Product;
import com.magnus.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    List<ProductWrapper> getAllProduct();

}

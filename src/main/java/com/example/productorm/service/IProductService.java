package com.example.productorm.service;

import com.example.productorm.model.Product;

import java.util.List;

public interface IProductService {
    public List<Product> findAll();
    public Product findById(int id);
    public void save(Product product);
    public void delete(int id);

}

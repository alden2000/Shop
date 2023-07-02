package com.prodavnica.shop2.products.service;

import com.prodavnica.shop2.products.entity.Product;
import com.prodavnica.shop2.products.entity.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    JdbcTemplate template;

    @Autowired
    ProductRepository productRepository;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

//    public Iterable<Product> getPageProducts(Integer pageSize, Integer offset) {
//        return productRepository.findAll(PageRequest.of(offset, pageSize));
//    }
}

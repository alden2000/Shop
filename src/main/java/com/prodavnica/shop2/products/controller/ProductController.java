package com.prodavnica.shop2.products.controller;

import com.prodavnica.shop2.products.entity.Product;
import com.prodavnica.shop2.products.entity.ProductRepository;
import com.prodavnica.shop2.products.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public ProductController() {
    }

    @GetMapping("/products")
    public String getProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("proizvodi", products);
        return "products";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        Product product = new Product();
        model.addAttribute("proizvod", product);
        return "add_product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product){
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/productPage")
    public String getProductPage(@RequestParam(defaultValue = "1") int page, @RequestParam(name = "dropDownList", defaultValue = "3") int pageSize, Model model) throws IOException {
        Document document = Jsoup.connect("http://localhost:8080/productPage").get();
        List<String> options = new ArrayList<>();
        options.add("3");
        options.add("5");
        options.add("10");
        options.add("15");
        model.addAttribute("options", options);
        Element element = document.getElementById("options");
        pageSize= Integer.parseInt(element.toString());
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<Product> productPage=productRepository.findAll(pageable);
        model.addAttribute("productPage", productPage);
        int totalPages = productPage.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers= IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "productPage";
    }
}

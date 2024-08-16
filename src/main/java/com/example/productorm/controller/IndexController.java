package com.example.productorm.controller;


import com.example.productorm.model.Product;
import com.example.productorm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private IProductService productService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }
}
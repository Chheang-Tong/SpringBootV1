package com.example.demo.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        p.setId(null);
        return ResponseEntity.ok(repo.save(p));
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody Product p) {
        Product cur = repo.findById(id).orElseThrow();
        cur.setSku(p.getSku());
        cur.setName(p.getName());
        cur.setDescription(p.getDescription());
        cur.setPrice(p.getPrice());
        cur.setStock(p.getStock());
        cur.setImageUrl(p.getImageUrl());
        return repo.save(cur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

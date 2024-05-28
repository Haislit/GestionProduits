package com.example.gestionproduitsj2ee.controllers;

import com.example.gestionproduitsj2ee.entities.Produit;
import com.example.gestionproduitsj2ee.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestProduitController {
    @Autowired
    ProduitService produitService;
    @GetMapping("/produits/{produit_id}")
    public ResponseEntity<Produit> getProduits (@RequestParam Long produit_id){
        Produit produit = produitService.getProduitId(produit_id);
        return new ResponseEntity<>(produit,HttpStatus.OK);
    }
    @PostMapping("/produits")
    public ResponseEntity<String> addProduits (@RequestBody Produit produit){
        produitService.addProduit(produit);
        return new ResponseEntity<>("Produit ajoutee",HttpStatus.CREATED);
    }
}

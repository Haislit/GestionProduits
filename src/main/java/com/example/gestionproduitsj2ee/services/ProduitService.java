package com.example.gestionproduitsj2ee.services;

import com.example.gestionproduitsj2ee.entities.Produit;
import com.example.gestionproduitsj2ee.repositories.ProduitRepository;
import org.springframework.stereotype.Service;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    public void addProduit(Produit produit){
        produitRepository.save(produit);
    }
    public Produit getProduitId(Long id){
        return produitRepository.findById(id).orElseThrow(()-> new RuntimeException("Error"));
    }
}

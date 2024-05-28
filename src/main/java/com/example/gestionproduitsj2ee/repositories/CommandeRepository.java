package com.example.gestionproduitsj2ee.repositories;

import com.example.gestionproduitsj2ee.entities.Commande;
import com.example.gestionproduitsj2ee.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    Page<Commande> findByStatutContains(String searchStatus, PageRequest of);
}

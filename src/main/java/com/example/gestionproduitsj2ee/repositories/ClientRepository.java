package com.example.gestionproduitsj2ee.repositories;

import com.example.gestionproduitsj2ee.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByNomContains(String searchName, PageRequest of);
}


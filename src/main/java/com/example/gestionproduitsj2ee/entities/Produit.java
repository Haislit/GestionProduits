package com.example.gestionproduitsj2ee.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private float prix;

    private int quantite;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Categorie categorie;

}

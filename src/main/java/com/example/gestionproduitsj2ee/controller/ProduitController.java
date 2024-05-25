package com.example.gestionproduitsj2ee.controller;

import com.example.gestionproduitsj2ee.entities.Categorie;
import com.example.gestionproduitsj2ee.entities.Produit;
import com.example.gestionproduitsj2ee.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/index")
    public String allProduits(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "3") int size,
                              @RequestParam(name = "search", defaultValue = "") String searchName) {
        Page<Produit> pageProduits = produitRepository.findByNomContains(searchName, PageRequest.of(page, size));
        int[] pages = new int[pageProduits.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pageProduits", pageProduits.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        return "produits";
    }

    @GetMapping("/create")
    public String createProduit(Model model) {
        Produit produit = new Produit();
        model.addAttribute("produit", produit);
        model.addAttribute("categories", Categorie.values());
        return "formAddProduit";
    }

    @PostMapping("/save")
    public String saveProduit(Produit produit,
                              @RequestParam(name = "currentPage", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "3") int size,
                              @RequestParam(name = "searchName", defaultValue = "") String search) {
        produitRepository.save(produit);
        return "redirect:/index?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/")
    public String homePage() {
        return "menu";
    }

    @GetMapping("/delete")
    public String deleteProduit(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "page") int page,
                                @RequestParam(name = "size") int size,
                                @RequestParam(name = "search") String search) {
        produitRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/edit")
    public String editProduit(Model model,
                              @RequestParam(name = "id") Long id,
                              @RequestParam(name = "page") int page,
                              @RequestParam(name = "size") int size,
                              @RequestParam(name = "search") String search) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        model.addAttribute("categories", Categorie.values());
        model.addAttribute("produit", produit);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", search);
        return "formEditProduit";
    }
}


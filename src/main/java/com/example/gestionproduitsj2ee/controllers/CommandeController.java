package com.example.gestionproduitsj2ee.controllers;

import com.example.gestionproduitsj2ee.entities.Commande;
import com.example.gestionproduitsj2ee.repositories.ClientRepository;
import com.example.gestionproduitsj2ee.repositories.CommandeRepository;
import com.example.gestionproduitsj2ee.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/index")
    public String allCommandes(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "3") int size,
                               @RequestParam(name = "search", defaultValue = "") String searchStatus) {
        Page<Commande> pageCommandes = commandeRepository.findByStatutContains(searchStatus, PageRequest.of(page, size));
        int[] pages = new int[pageCommandes.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pageCommandes", pageCommandes.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchStatus", searchStatus);
        return "commandes";
    }

    @GetMapping("/create")
    public String createCommande(Model model) {
        Commande commande = new Commande();
        model.addAttribute("commande", commande);
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("produits", produitRepository.findAll());
        return "formAddCommande";
    }

    @PostMapping("/save")
    public String saveCommande(Commande commande,
                               @RequestParam(name = "currentPage", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "3") int size,
                               @RequestParam(name = "searchStatus", defaultValue = "") String search) {
        commandeRepository.save(commande);
        return "redirect:/commandes/index?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/")
    public String homePage() {
        return "menu";
    }

    @GetMapping("/delete")
    public String deleteCommande(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "page") int page,
                                 @RequestParam(name = "size") int size,
                                 @RequestParam(name = "search") String search) {
        commandeRepository.deleteById(id);
        return "redirect:/commandes/index?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/edit")
    public String editCommande(Model model,
                               @RequestParam(name = "id") Long id,
                               @RequestParam(name = "page") int page,
                               @RequestParam(name = "size") int size,
                               @RequestParam(name = "search") String search) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande not found"));
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("produits", produitRepository.findAll());
        model.addAttribute("commande", commande);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchStatus", search);
        return "formEditCommande";
    }
}





package com.example.gestionproduitsj2ee.controllers;

import com.example.gestionproduitsj2ee.entities.Client;
import com.example.gestionproduitsj2ee.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public String allClients(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "3") int size,
                             @RequestParam(name = "search", defaultValue = "") String searchName) {
        Page<Client> pageClients = clientRepository.findByNomContains(searchName, PageRequest.of(page, size));
        int[] pages = new int[pageClients.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pageClients", pageClients.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        return "clients";
    }

    @GetMapping("/clients/create")
    public String createClient(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "formAddClient";
    }

    @PostMapping("/clients/save")
    public String saveClient(Client client,
                             @RequestParam(name = "currentPage", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "3") int size,
                             @RequestParam(name = "searchName", defaultValue = "") String search) {
        clientRepository.save(client);
        return "redirect:/clients?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/clients/delete")
    public String deleteClient(@RequestParam(name = "id") Long id,
                               @RequestParam(name = "page") int page,
                               @RequestParam(name = "size") int size,
                               @RequestParam(name = "search") String search) {
        clientRepository.deleteById(id);
        return "redirect:/clients?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/clients/edit")
    public String editClient(Model model,
                             @RequestParam(name = "id") Long id,
                             @RequestParam(name = "page") int page,
                             @RequestParam(name = "size") int size,
                             @RequestParam(name = "search") String search) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", search);
        return "formEditClient";
    }
}


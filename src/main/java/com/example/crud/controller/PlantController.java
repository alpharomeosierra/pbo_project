package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.crud.Model.Plant;
import com.example.crud.repository.PlantRepository;

@Controller
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;
    
    @GetMapping("/")
    public String index(Model model) {
        try {
            model.addAttribute("plants", plantRepository.findAll());
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Terjadi kesalahan saat mengambil data tanaman");
            return "error";
        }
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("plant", new Plant());
        return "add";
    }
    
    @PostMapping("/add")
    public String addPlant(@ModelAttribute Plant plant, RedirectAttributes redirectAttributes) {
        try {
            if (plant.getName() == null || plant.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Nama tanaman tidak boleh kosong");
                return "redirect:/add";
            }
            plantRepository.save(plant);
            redirectAttributes.addFlashAttribute("success", "Tanaman berhasil ditambahkan");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menambahkan tanaman");
            return "redirect:/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID tanaman tidak valid: " + id));
            model.addAttribute("plant", plant);
            return "edit";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Terjadi kesalahan saat mengambil data tanaman");
            return "redirect:/";
        }
    }
    
    @PostMapping("/edit/{id}")
    public String updatePlant(@PathVariable Long id, @ModelAttribute Plant plant, RedirectAttributes redirectAttributes) {
        try {
            if (plant.getName() == null || plant.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Nama tanaman tidak boleh kosong");
                return "redirect:/edit/" + id;
            }
            if (!plantRepository.existsById(id)) {
                redirectAttributes.addFlashAttribute("error", "ID tanaman tidak valid: " + id);
                return "redirect:/";
            }
            plant.setId(id);
            plantRepository.save(plant);
            redirectAttributes.addFlashAttribute("success", "Tanaman berhasil diperbarui");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal memperbarui tanaman");
            return "redirect:/edit/" + id;
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deletePlant(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (!plantRepository.existsById(id)) {
                redirectAttributes.addFlashAttribute("error", "ID tanaman tidak valid: " + id);
                return "redirect:/";
            }
            plantRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Tanaman berhasil dihapus");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menghapus tanaman");
            return "redirect:/";
        }
    }
}
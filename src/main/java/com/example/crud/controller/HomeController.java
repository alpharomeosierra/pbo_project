package com.example.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Tampilkan halaman home tanpa mengambil data dari database
    @GetMapping("/")
    public String index() {
        return "index"; // Mengarahkan ke template index.html
    }
}

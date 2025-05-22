package com.example.crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Home {

    @Id
    private Long id;

    private String nama;

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}

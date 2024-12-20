package com.buyme.buymeEcom.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "brands")
public class Brand  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @Column(nullable = true, length = 128)
    private String logo;

    @ManyToMany
    @JoinTable(
            name = "brands_categories",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Brand() {

    }

    public Brand(String name) {
        this.name = name;
        this.logo = "brand-logo.png";
    }

    public Brand(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Brand(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", categories=" + categories + "]";
    }

    @Transient
    public String getLogoPath() {
        if (this.id == null) return "/images/image-thumbnail.png";

        return "/brand-logos/" + this.id + "/" + this.logo;
    }
}


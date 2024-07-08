package com.skyitschool.skyitschoolecom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
//
//    @ManyToMany(mappedBy = "categories")
//    private Set<Brand> brands = new HashSet<>();

//    public Set<Brand> getBrands() {
//        return brands;
//    }
//
//    public void setBrands(Set<Brand> brands) {
//        this.brands = brands;
//    }

    private boolean enabled;


//
//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

    public Category() {
    }

    public Category(Integer id) {
        this.id = id;
    }
    public Category(String name) {
        this.name = name;
    }
    public Category(Integer id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }


}

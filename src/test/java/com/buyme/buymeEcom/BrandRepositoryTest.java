package com.buyme.buymeEcom;

import com.buyme.buymeEcom.entity.Brand;
import com.buyme.buymeEcom.entity.Category;
import com.buyme.buymeEcom.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository repo;

    @Test
    public void testCreateRootCategory() {
        Category mobile = new Category(4);
        Brand apple = new Brand("Apple");
        apple.getCategories().add(mobile);

        Brand savedBrand = repo.save(apple);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateBrand2() {
        Category laptops = new Category(2);
        Category desktop = new Category(5);
        Brand acer = new Brand("Acer");

        acer.getCategories().add(laptops);
        acer.getCategories().add(desktop);

        Brand savedBrand = repo.save(acer);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand3() {
        Category battery = new Category(7);
        Category monitor = new Category(6);
        Brand samsung = new Brand("Samsung");

        samsung.getCategories().add(battery);
        samsung.getCategories().add(monitor);

        Brand savedBrand = repo.save(samsung);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }
    @Test
    public void testFindAll() {
        Iterable<Brand> brands = repo.findAll();
        brands.forEach(System.out::println);

        assertThat(brands).isNotEmpty();
    }

    @Test
    public void testGetById() {
        Brand brand = repo.findById(2).get();

        assertThat(brand.getName()).isEqualTo("Acer");
    }
    @Test
    public void testUpdateName() {
        String newName = "Samsung Electronics";
        Brand samsung = repo.findById(3).get();
        samsung.setName(newName);
        Brand savedBrand = repo.save(samsung);
        assertThat(savedBrand.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete() {
        Integer id = 2;
        repo.deleteById(id);

        Optional<Brand> result = repo.findById(id);

        assertThat(result.isEmpty());
    }

}

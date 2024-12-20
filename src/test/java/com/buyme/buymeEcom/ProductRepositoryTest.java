package com.buyme.buymeEcom;


import com.buyme.buymeEcom.entity.Brand;
import com.buyme.buymeEcom.entity.Category;
import com.buyme.buymeEcom.entity.Product;
import com.buyme.buymeEcom.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)// without null pointer
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct(){
        Brand brand = entityManager.find(Brand.class, 4);
        Category category = entityManager.find(Category.class, 2);

        Product product = new Product();
        product.setName("Acer2 Aspire Laptop");
        product.setDescription("description for Acer2 Aspire Laptop");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(610);
        product.setCost(600);
        product.setEnabled(true);
        product.setInStock(true);
        product.setMainImage("image/png");
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAllProducts() {
        Iterable<Product> iterableProducts = repo.findAll();

        iterableProducts.forEach(System.out::println);
    }

    @Test
    public void testGetProduct() {
        Integer id = 2;
        Product product = repo.findById(id).get();
        System.out.println(product);

        assertThat(product).isNotNull();
    }
    @Test
    public void testUpdateProduct() {
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(596);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);

        assertThat(updatedProduct.getPrice()).isEqualTo(596);
    }

    @Test
    public void testDeleteProduct() {
        Integer id = 3;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

        assertThat(!result.isPresent());
    }

    @Test
    public void testSaveProductWithImages() {
        Integer productId = 1;
        Product product = repo.findById(productId).get();

        product.setMainImage("main image.jpg");

        Product savedProduct = repo.save(product);

        assertThat(savedProduct.getImages().size()).isEqualTo(0);
    }

}

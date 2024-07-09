package com.buyme.buymeEcom;

import com.buyme.buymeEcom.entity.Category;
import com.buyme.buymeEcom.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)// without null pointer
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateCategory() {
        Category category = new Category("Battery");
        Category savedCategory = repo.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }
    @Test
    public void testGetCategory() {
        Category category;
        category = repo.findById(2).get();
        System.out.println(category.getName());


      //  assertThat(category.isEnabled());
    }

    @Test
    public void testFindByName() {
        String name = "Desktop";
        Category category = repo.findByName(name);

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void testUpdateCategory() {
        String name = "Laptop";
        Category category = repo.findById(2).get();
           category.setName(name);
        assertThat(category).isNotNull();
        System.out.println(category);
      //  assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void testUpdateCategoryStatus() {
        boolean status = true;
        int categoryId = 2;
        Category category = repo.findById(categoryId).orElse(null);
        if (category != null) {
            category.setEnabled(status);
            Category updatedCategory = repo.save(category);

            assertThat(updatedCategory).isNotNull();
            assertThat(updatedCategory.isEnabled()).isEqualTo(status);
            System.out.println(updatedCategory);
        } else {
            System.out.println("Category not found");
        }
        //  assertThat(category.getName()).isEqualTo(name);
    }
}

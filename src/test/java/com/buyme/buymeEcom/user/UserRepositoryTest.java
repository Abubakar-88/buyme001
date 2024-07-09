package com.buyme.buymeEcom.user;

import com.buyme.buymeEcom.entity.Role;
import com.buyme.buymeEcom.entity.User;
import com.buyme.buymeEcom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userRahim = new User("rahim@skyitscholl.com", "rahim2023", "Abdur", "Rahim");
        userRahim.addRole(roleAdmin);

        User savedUser = repo.save(userRahim);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreate2ndUserWithOneRole() {
        Role roleShipper = entityManager.find(Role.class, 4);
        User userAbuBakar = new User("abubakar@skyitscholl.com", "abu2023", "Abu", "Siddique");
        userAbuBakar.addRole(roleShipper);

        User savedUser = repo.save(userAbuBakar);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userAbdullah = new User("abdullah@gmail.com", "abdullah2023", "Abdullah", "Tahmid");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userAbdullah.addRole(roleEditor);
        userAbdullah.addRole(roleAssistant);

        User savedUser = repo.save(userAbdullah);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }


    @Test
    public void testGetUserById() {
        User userAbdur  = repo.findById(1).get();
        System.out.println(userAbdur);
        assertThat(userAbdur).isNotNull();
    }


    @Test
    public void testUpdateUserDetails() {
        User userAbdur = repo.findById(1).get();
        userAbdur.setEnabled(true);
        userAbdur.setEmail("abdurjavaprogrammer@gmail.com");

        repo.save(userAbdur);
    }

    @Test
    public void testUpdateUserRoles() {
        User userAbdullah = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userAbdullah.getRoles().remove(roleEditor);
        userAbdullah.addRole(roleSalesperson);

        repo.save(userAbdullah);
    }
    @Test
    public void testDeleteUser() {
        Integer userId = 3;
        repo.deleteById(userId);

    }

//    @Test
//    public void testGetUserByEmail() {
//        String email = "abdullah@gmail.com";
//        User user = repo.getUserByEmail(email);
//
//        assertThat(user).isNotNull();
//    }

//    @Test
//    public void testCountById() {
//        Integer id = 1;
//        Long countById = repo.countById(id);
//
//        assertThat(countById).isNotNull().isGreaterThan(0);
//    }

//    @Test
//    public void testEnableUser() {
//        Integer id = 4;
//        repo.updateEnabledStatus(id, true);
//
//    }


}

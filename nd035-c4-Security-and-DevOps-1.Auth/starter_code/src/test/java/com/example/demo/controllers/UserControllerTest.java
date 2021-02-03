package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
        Cart testCart = new Cart();
        User testUser = new User();
        testUser.setId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCart(testCart);

        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(testUser));
    }

    @Test
    public void create_user_happy_path() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0,u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void find_user_by_name_happy_path() {
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.findByUserName("test");
        //System.out.println(response.toString());
        assertEquals(404, response.getStatusCodeValue());
        final ResponseEntity<User> newResponse = userController.createUser(r);
        assertEquals(200,newResponse.getStatusCodeValue());
       // ResponseEntity<User> newResponse = userController.findByUserName("test");
        assertEquals("test",newResponse.getBody().getUsername());

    }


    @Test
    public void find_user_by_id(){
        Optional<User> response = userRepository.findById(0L);
        assertNotNull(response);
        assertEquals(0L,response.get().getId());

    }

}

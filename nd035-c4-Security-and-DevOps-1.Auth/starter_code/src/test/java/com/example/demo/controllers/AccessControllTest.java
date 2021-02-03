package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessControllTest {
    private CartController cartController = mock(CartController.class);

    private UserController userController = mock(UserController.class);

    private OrderController orderController = mock(OrderController.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);


    @Before
    public void setUp() {
        userController = new UserController();
        cartController = new CartController();
        orderController = new OrderController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(orderController,"orderRepository", orderRepository);
        TestUtils.injectObjects(orderController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);
        //TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
//        when(userController.findByUserName("unknown")).thenReturn(null);
//        when(userRepository.findByUsername("unknown")).thenReturn(null);
        when(cartController.showCart("unknown")).thenReturn(null);
        when(orderController.getOrdersForUser("unknown")).thenReturn(null);
    }

    @Test
    public void testUnregisteredLogin(){
        ResponseEntity<User> unknown = userController.findByUserName("unknown");
        assertNotNull(unknown);
        assertEquals(404,unknown.getStatusCodeValue());
    }

    @Test
    public void testPurchaseHistory(){
        ResponseEntity<List<UserOrder>> unknown = orderController.getOrdersForUser("unknown");
        assertNotNull(unknown);
    }




//    @Test
//    public void testGettingUserprofileAsUnknownUser(){
//        ResponseEntity<Cart> unknown = cartController.showCart("unknown");
//        assertNull(unknown);
//        //assertEquals(404,unknown.getStatusCodeValue());
//    }

}

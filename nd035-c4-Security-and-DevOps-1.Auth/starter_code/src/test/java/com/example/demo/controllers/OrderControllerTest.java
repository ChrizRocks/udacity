package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController = mock(OrderController.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);


    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        Cart testCart = new Cart();
        User testUser = new User();
        Item item1 = new Item();
        item1.setDescription("Very cool Item");
        item1.setPrice(BigDecimal.valueOf(9.95));
        item1.setName("Banger");
        item1.setId(1L);
        Item item2 = new Item();
        item2.setName("Burger");
        item2.setId(2L);
        item2.setPrice(BigDecimal.valueOf(3.95));
        item2.setDescription("Like a woman, consumed to much it will cost you your life.");
        testCart.addItem(item1);
        testCart.addItem(item2);
        testUser.setId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCart(testCart);

        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(userRepository.findByUsername("failUser")).thenReturn(null);

    }

    @Test
    public void testSubmitOrder(){
        ResponseEntity<UserOrder> responseEntity = orderController.submit("testUser");
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
        UserOrder order = responseEntity.getBody();
        assertNotNull(order);
        assertEquals(2,order.getItems().size());
    }

    @Test
    public void testSubmitOrderWithUserNotFound(){
        ResponseEntity<UserOrder> responseEntity = orderController.submit("failUser");
        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser(){
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("testUser");
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());

        List<UserOrder> orders = responseEntity.getBody();
        assertNotNull(orders);
    }

    @Test
    public void testGetOrdersForUserNotExisting(){
        ResponseEntity<List<UserOrder>> notExistingUserOrders = orderController.getOrdersForUser("failUser");
        assertNotNull(notExistingUserOrders);
        assertEquals(404, notExistingUserOrders.getStatusCodeValue());
    }
}

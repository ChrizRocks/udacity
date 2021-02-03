package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController = mock(CartController.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"cartRepository",cartRepository);
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);
        Cart testCart = new Cart();
        User testUser = new User();
        testUser.setId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCart(testCart);

        when(userRepository.findByUsername("testUser")).thenReturn(testUser);

        /*
            "id": 2,
            "name": "Square Widget",
            "price": 1.99,
            "description": "A widget that is square"
         */
        Item testItem = new Item();
        testItem.setId(0L);
        testItem.setName("Square Widget");
        testItem.setPrice(BigDecimal.valueOf(1.99));
        testItem.setDescription("A widget that is square");

        when(itemRepository.findById(0L)).thenReturn(java.util.Optional.of(testItem));
        when(itemRepository.findByName("Square Widget")).thenReturn(Collections.singletonList(testItem));
    }


    /*
    @Test
public void should_invoke_Dao_on_delete_by_id() { // delete scenario
    // mockito mocks do nothing by default, no need to mock that

    when( this.dao.readById( ID_1 ) ).thenReturn( ALABAMA ); // ok this is needed

    this.bo.delete( ID_1 );

    verify( this.dao ).delete( ALABAMA ); // ok, you need to verify that the interaction happened
     */
    @Test
    public void testAddToCart(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setUsername("testUser");
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());

        Cart testCart = responseEntity.getBody();
        assertNotNull(testCart);
        Optional<Item> testI = itemRepository.findById(0L);
        BigDecimal testTotal =testI.get().getPrice().multiply(BigDecimal.valueOf(2));

        assertEquals(testTotal,testCart.getTotal());

    }

    @Test
    public void testRemoveFromCart(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("testUser");
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());

        Cart testCart = responseEntity.getBody();
        assertNotNull(testCart);
        ResponseEntity<Cart> newResponse = cartController.removeFromcart(modifyCartRequest);
        assertEquals(newResponse.getStatusCodeValue(), HttpStatus.OK.value());
        assertEquals(testCart.getItems().size(),0);

    }

    @Test
    public void testShowCart(){
        ResponseEntity<Cart> responseEntity = cartController.showCart("unknown");
        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());
        responseEntity = cartController.showCart("testUser");
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

}
/*
private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepository);
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Round Widget");
        item1.setPrice(BigDecimal.valueOf(2.99));
        item1.setDescription("A widget that is round");
        Item item2 = new Item();
        item2.setName("Square Widget");
        item2.setPrice(BigDecimal.valueOf(1.99));
        item2.setDescription("A widget that is square");
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item1));
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        when(itemRepository.findByName("Round Widget")).thenReturn(Collections.singletonList(item1));
    }

    @Test
    public void verify_getItems(){
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(1,items.size());
    }


 */
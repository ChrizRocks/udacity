package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

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
        Optional<Item> savedItem =itemRepository.findById(1L);
        assertNotNull(savedItem);
        assertEquals(items.get(0).getId(),savedItem.get().getId());
        //assertNotNull(items);
        //assertEquals(1,items.size());
    }

    @Test
    public void verify_findById(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody();
        assertNotNull(item);
        assertEquals("Round Widget", item.getName());
        assertEquals(Long.valueOf(1),item.getId());

    }

    @Test
    public void verify_findByName(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Round Widget");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody().get(0);
        assertNotNull(item);
        assertEquals("Round Widget",item.getName());
    }


//    private class ItemsList {
//        private List<Item> items;
//
//        public ItemsList(){
//            items = new ArrayList<>();
//        }
//
//        public List<Item> getItems() {
//            return items;
//        }
//
//        public void setItems(List<Item> items) {
//            this.items = items;
//        }
//    }



}

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    @Test
    public void verify_getItems(){
        RestTemplate restTemplate = new RestTemplate();
        List<Item> dummyItems = createDummyItems();
        when(itemController.getItems()).thenReturn(ResponseEntity.ok(dummyItems));
       // ItemsList response = restTemplate.getForObject(
      //          "http://localhost:8080/api/items", ItemsList.class);
       // when(itemController.getItems()).thenReturn(response.getItems());
       // ResponseEntity<List<Item>> response = restTemplate.exchange("http://localhost:8080/api/item/", HttpMethod.GET, null,
       //         new ParameterizedTypeReference<List<Item>>(dummyItems){});
//        when(itemController.getItems()).thenReturn(new ResponseEntity<List<Item>>(HttpStatus.OK,item1));
//        when(itemController.getItems()).thenReturn(new ResponseEntity<List<Item>>(dummyItems));
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertEquals(2,items.size());
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


    /*
    insert into item (name, price, description) values ('Round Widget', 2.99, 'A widget that is round');
    insert into item (name, price, description) values ('Square Widget', 1.99, 'A widget that is square');
     */
    private List<Item> createDummyItems(){
        List<Item> retList = new ArrayList<>();
        Item item1 = new Item();
        item1.setName("Round Widget");
        item1.setPrice(BigDecimal.valueOf(2.99));
        item1.setDescription("A widget that is round");
        Item item2 = new Item();
        item2.setName("Square Widget");
        item2.setPrice(BigDecimal.valueOf(1.99));
        item2.setDescription("A widget that is square");
        retList.add(item1);
        retList.add(item2);
        return retList;
    }

}

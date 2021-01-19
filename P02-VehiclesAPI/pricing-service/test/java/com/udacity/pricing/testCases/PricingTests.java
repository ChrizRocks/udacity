package com.udacity.pricing.testCases;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Test //Doesn't work....
//    public void getAllPrices (){
//        ResponseEntity<String> response =
//                this.restTemplate.getForEntity("http://localhost:"+ port + "/prices",String.class);
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//    }

    @Test
    public void getPrice(){
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity("http://localhost:"+port+ "/services/price?vehicleId=1",Price.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
    }

    @Test
    public void testVehicle(){
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=21", Price.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.NOT_FOUND));
    }
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    PricingService pricingService;
//
//    /**
//     * Checkig if theres a vehicle with ID=1 in the database, there schould be 20
//     * @throws Exception
//     */
//    @Test
//    public void getPrice() throws Exception {
//        mockMvc.perform(get("/services/price?vehicleId=1"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"currency\":\"USD\",\"vehicleId\":1}"));
//    }
//
//    @Test
//    public void getAllPrices() throws Exception{
//        for(int i=1; i< 20;i++) {
//            mockMvc.perform(get("/services/price?vehicleId=" + i))
//                    .andExpect(status().isOk())
//                    .andExpect(content().json("{\"currency\":\"USD\",\"vehicleId\":" + i + "}"));
//        }
//    }
}

//package com.example.demo.controller;
//
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class FailedPromoControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//    void testCreateFailedPromo() throws Exception {
//        String data = "{\"error_type\":\"value1\",\"error_message\":\"Some error message\",\"promocode_id\":123,\"promocode_code\":\"PROMO123\",\"promotion_id\":456,\"order_uuid\":\"uuid1234\",\"user_uuid\":\"user-uuid-5678\"}";
//
//        mockMvc.perform(post("/api/promo/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(data))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.error_type").value(1))
//                .andExpect(jsonPath("$.name").value("Black Friday"))
//                .andExpect(jsonPath("$.discount").value(20));
//    }
//}

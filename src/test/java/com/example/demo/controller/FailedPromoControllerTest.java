package com.example.demo.controller;


import com.example.demo.filter.FailedPromoFilter;
import com.example.demo.model.FailedPromoRecord;
import com.example.demo.producer.FailedPromoProducer;
import com.example.demo.repository.FailedPromoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(FailedPromoController.class)
public class FailedPromoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FailedPromoRepository failedPromoRepository;

    @MockitoBean
    private FailedPromoProducer producer;

    @Test
    void testGetFailedPromos() throws Exception {
        FailedPromoRecord record1 = new FailedPromoRecord(null, "value1", "Some error message", 123, "PROMO123", 456, "uuid1234", "user-uuid-5678", null, null);
        FailedPromoRecord record2 = new FailedPromoRecord(null, "value2", "Some error message", 321, "PROMO321", 654, "uuid4321", "user-uuid-5678", null, null);
        List<FailedPromoRecord> promoList = Arrays.asList(record1, record2);

        when(failedPromoRepository.findBy(any(FailedPromoFilter.class))).thenReturn(promoList);

        mockMvc.perform(get("/failed_promos")
                        .param("error_message", "error"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].errorMessage").value("Some error message"))
                .andExpect(jsonPath("$[1].errorMessage").value("Some error message"));
    }

    @Test
    void testPostFailedPromo() throws Exception {
        String data = "{\"error_type\":\"value1\",\"error_message\":\"Some error message\",\"promocode_id\":123,\"promocode_code\":\"PROMO123\",\"promotion_id\":456,\"order_uuid\":\"uuid1234\",\"user_uuid\":\"user-uuid-5678\"}";

        mockMvc.perform(post("/failed_promo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errorType").value("value1"))
                .andExpect(jsonPath("$.errorMessage").value("Some error message"))
                .andExpect(jsonPath("$.promocodeId").value(123))
                .andExpect(jsonPath("$.promocodeCode").value("PROMO123"))
                .andExpect(jsonPath("$.promotionId").value(456))
                .andExpect(jsonPath("$.orderUuid").value("uuid1234"))
                .andExpect(jsonPath("$.userUuid").value("user-uuid-5678"));

        verify(producer, times(1)).produceMsg(any(FailedPromoRecord.class));
    }

    @Test
    void testPostFailedPromoWhenInvalidParams() throws Exception {
        String data = "{\"error_type\":\"value1\",\"error_message\":\"Some error message\"}";

        mockMvc.perform(post("/failed_promo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isBadRequest());

        verify(producer, times(0)).produceMsg(any(FailedPromoRecord.class));
    }
}

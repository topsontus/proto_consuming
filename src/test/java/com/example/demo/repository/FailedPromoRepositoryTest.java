package com.example.demo.repository;

import com.example.demo.model.FailedPromoRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FailedPromoRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FailedPromoRepository failedPromoRepository;

    @Test
    void testCreateFailedPromo() {
        String insertSql = "INSERT INTO failed_promos (error_type, error_message, promocode_id, promocode_code, promotion_id, order_uuid, user_uuid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        FailedPromoRecord record = new FailedPromoRecord(null, "error_type", "error_message", 321, "promocode_code",
                123, "order_uuid", "user_uuid", null, null);

        when(jdbcTemplate.update(eq(insertSql), anyString(), anyString(), anyInt(), anyString(), anyInt(), anyString(), anyString())).thenReturn(1);

        int id = failedPromoRepository.create(record);

        verify(jdbcTemplate, times(1)).update(eq(insertSql), anyString(), anyString(), anyInt(), anyString(), anyInt(), anyString(), anyString());

        assertEquals(1, id);
    }
}

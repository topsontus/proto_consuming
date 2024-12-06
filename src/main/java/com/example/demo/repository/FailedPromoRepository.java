package com.example.demo.repository;

import com.example.demo.model.FailedPromoRecord;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FailedPromoRepository implements Repository<FailedPromoRecord> {
    private final JdbcTemplate jdbcTemplate;

    FailedPromoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(FailedPromoRecord record) throws DataAccessException {
        String sql = "INSERT INTO failed_promos (error_type, error_message, promocode_id, promocode_code, promotion_id, order_uuid, user_uuid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, record.errorType(), record.errorMessage(), record.promocodeId(), record.promocodeCode(), record.promotionId(), record.orderUuid(), record.userUuid());
    }
}

package com.example.demo.repository;

import com.example.demo.filter.FailedPromoFilter;
import com.example.demo.model.FailedPromoRecord;
import com.example.demo.model.FailedPromoRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FailedPromoRepository implements Repository<FailedPromoRecord> {
    private final JdbcTemplate jdbcTemplate;
    private final FailedPromoRowMapper failedPromoRowMapper;

    FailedPromoRepository(JdbcTemplate jdbcTemplate, FailedPromoRowMapper failedPromoRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.failedPromoRowMapper = failedPromoRowMapper;
    }

    public int create(FailedPromoRecord record) throws SQLException {
        String sql = "INSERT INTO failed_promos (error_type, error_message, promocode_id, promocode_code, promotion_id, order_uuid, user_uuid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, record.errorType(), record.errorMessage(), record.promocodeId(), record.promocodeCode(), record.promotionId(), record.orderUuid(), record.userUuid());
    }

    public List<FailedPromoRecord> findBy(FailedPromoFilter filter) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM failed_promos WHERE 1=1");

        List<Object> params = new ArrayList<>();
        if (filter.errorMessage() != null) {
            sql.append(" AND error_message ILIKE ?");
            params.add("%" + filter.errorMessage() + "%");
        }

        if (filter.beforeDateTime() != null) {
            sql.append(" AND created_at <= ?");
            params.add(filter.beforeDateTime());
        }

        return jdbcTemplate.query(sql.toString(), failedPromoRowMapper, params.toArray());
    }
}

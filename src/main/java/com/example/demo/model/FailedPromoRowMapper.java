package com.example.demo.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FailedPromoRowMapper implements RowMapper<FailedPromoRecord> {
    @Override
    public FailedPromoRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FailedPromoRecord(
                rs.getInt("id"),
                rs.getString("error_type"),
                rs.getString("error_message"),
                rs.getInt("promocode_id"),
                rs.getString("promocode_code"),
                rs.getInt("promotion_id"),
                rs.getString("order_uuid"),
                rs.getString("user_uuid"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}

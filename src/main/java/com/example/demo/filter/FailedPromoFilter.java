package com.example.demo.filter;

import java.time.LocalDateTime;

public record FailedPromoFilter(String errorMessage, LocalDateTime beforeDateTime) {
}

package com.example.demo.controller;

import com.example.demo.filter.FailedPromoFilter;
import com.example.demo.form.FailedPromoCreationForm;
import com.example.demo.model.FailedPromoMapper;
import com.example.demo.model.FailedPromoRecord;
import com.example.demo.producer.FailedPromoProducer;
import com.example.demo.producer.ProduceMessageException;
import com.example.demo.repository.FailedPromoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class FailedPromoController {

    private final FailedPromoRepository failedPromoRepository;
    private final FailedPromoProducer producer;


    public FailedPromoController(FailedPromoRepository failedPromoRepository, FailedPromoProducer producer) {
        this.failedPromoRepository = failedPromoRepository;
        this.producer = producer;
    }

    @GetMapping("/failed_promos")
    public ResponseEntity<?> getFailedPromos(
            @RequestParam(name = "error_message", required = false) String errorMessage,
            @RequestParam(name = "before_date_time", required = false) LocalDateTime beforeDateTime
    ) throws SQLException {
        FailedPromoFilter filter = new FailedPromoFilter(errorMessage, beforeDateTime);
        List<FailedPromoRecord> filteredFailedPromos = failedPromoRepository.findBy(filter);

        return ResponseEntity.ok(filteredFailedPromos);
    }

    @PostMapping("/failed_promo")
    public ResponseEntity<?> createFailedPromo(@RequestBody @Valid FailedPromoCreationForm failedPromoForm, BindingResult result) throws IOException, ProduceMessageException {
        if (result.hasErrors()) return ResponseEntity.badRequest().body(result.getAllErrors());

        FailedPromoRecord record = FailedPromoMapper.INSTANCE.failedPromoCreationFormToFailedPromoRecord(failedPromoForm);

        producer.produceMsg(record);

        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }
}
package com.example.fundloader.model;

import com.example.fundloader.model.interfaces.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Load implements Model {
    @Id
    @Column(name = "load_id")
    private final String load_id;
    @Column(name = "customer_id")
    private final String customer_id;
    @Column(name = "load_amount")
    private final BigDecimal load_amount;
    @Column(name = "time")
    private final String transactionDate;

    public Load(@JsonProperty("id") String load_id, @JsonProperty("customer_id") String customer_id, @JsonProperty("load_amount") String load_amount, @JsonProperty("time") String transactionDate) {
        this.load_id = load_id;
        this.customer_id = customer_id;
        this.load_amount = new BigDecimal(load_amount.substring(1));
        this.transactionDate = transactionDate;
    }

    public Load() {
        this.load_id = "";
        this.customer_id = "";
        this.load_amount = new BigDecimal(0);
        this.transactionDate = "";
    }

    public String generateOutputString(boolean accepted) {
        return String.format("{\"id\":\"%s\",\"customer_id\":\"%s\",\"accepted\":%b}",
                this.load_id, this.customer_id, accepted);
    }

    /* Getters */

    public String getLoadId() {
        return this.load_id;
    }

    public String getCustomerId() {
        return this.customer_id;
    }

    public BigDecimal getLoadAmount() {
        return this.load_amount;
    }

    public String getTransactionDateAsString() {
        return this.transactionDate;
    }

    public Instant getTransactionDateAsInstant() {
        return Instant.parse(this.transactionDate);
    }
}

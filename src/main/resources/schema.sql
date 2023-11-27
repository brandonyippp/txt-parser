create TABLE load
(
    load_id     VARCHAR(255)   NOT NULL,
    customer_id VARCHAR(255)   NOT NULL,
    load_amount DECIMAL(10, 2) NOT NULL,
    time        VARCHAR(255)   NOT NULL
);
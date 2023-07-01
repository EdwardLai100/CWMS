CREATE SEQUENCE cwms_queue_history_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE cwms_queue_history (
    entry VARCHAR(255),
    model VARCHAR(255),
    plate VARCHAR(255),
    segment VARCHAR(255),
    type VARCHAR(255),
    notes VARCHAR(255),
    adjustment FLOAT,
    state VARCHAR(255),
    PRIMARY KEY (entry)
);
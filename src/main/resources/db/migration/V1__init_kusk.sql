CREATE TABLE kusk
(
    id   UUID NOT NULL,
    navn VARCHAR(255),
    CONSTRAINT pk_kusk PRIMARY KEY (id)
);

CREATE TABLE kusk_runder
(
    kusk_id   UUID NOT NULL,
    start     TIMESTAMP WITHOUT TIME ZONE,
    "end"     TIMESTAMP WITHOUT TIME ZONE,
    bane_type SMALLINT
);

ALTER TABLE kusk_runder
    ADD CONSTRAINT fk_kusk_runder_on_kusk FOREIGN KEY (kusk_id) REFERENCES kusk (id);
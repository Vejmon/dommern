CREATE TABLE kusk
(
    id           UUID NOT NULL,
    name         VARCHAR(255),
    current_bane SMALLINT,
    CONSTRAINT pk_kusk PRIMARY KEY (id)
);

CREATE TABLE runde
(
    id         UUID NOT NULL,
    kusk_id UUID NOT NULL,
    start      TIMESTAMP WITHOUT TIME ZONE,
    stop       TIMESTAMP WITHOUT TIME ZONE,
    bane_type  SMALLINT,
    CONSTRAINT pk_runde PRIMARY KEY (id)
);

ALTER TABLE runde
    ADD CONSTRAINT FK_RUNDE_ON_KUSKID FOREIGN KEY (kusk_id) REFERENCES kusk (id);

ALTER TABLE kusk
    ADD CONSTRAINT uc_kusk_name UNIQUE (name);

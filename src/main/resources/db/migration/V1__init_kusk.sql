CREATE TABLE kusk
(
    id               UUID NOT NULL,
    name             VARCHAR(255),
    current_bane     SMALLINT,
    personal_best_id UUID,
    CONSTRAINT pk_kusk PRIMARY KEY (id)
);

CREATE TABLE runde
(
    id        UUID NOT NULL,
    kusk_id   UUID NOT NULL,
    start     TIMESTAMP WITHOUT TIME ZONE,
    stop      TIMESTAMP WITHOUT TIME ZONE,
    bane_type SMALLINT,
    CONSTRAINT pk_runde PRIMARY KEY (id)
);

ALTER TABLE kusk
    ADD CONSTRAINT uc_kusk_name UNIQUE (name);

ALTER TABLE kusk
    ADD CONSTRAINT FK_KUSK_ON_PERSONALBEST FOREIGN KEY (personal_best_id) REFERENCES runde (id);

ALTER TABLE runde
    ADD CONSTRAINT FK_RUNDE_ON_KUSK FOREIGN KEY (kusk_id) REFERENCES kusk (id);
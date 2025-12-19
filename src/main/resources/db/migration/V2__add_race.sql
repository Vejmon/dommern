CREATE TABLE laup
(
    id             UUID NOT NULL,
    fastest_lap_id UUID,
    time_limit     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_laup PRIMARY KEY (id)
);

ALTER TABLE kusk
    ADD laup_id UUID;

ALTER TABLE kusk
    ADD CONSTRAINT FK_KUSK_ON_LAUP FOREIGN KEY (laup_id) REFERENCES laup (id);

ALTER TABLE laup
    ADD CONSTRAINT FK_LAUP_ON_FASTESTLAP FOREIGN KEY (fastest_lap_id) REFERENCES runde (id);
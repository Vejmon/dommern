CREATE TABLE bil
(
    id             UUID NOT NULL,
    kusk_id        UUID NOT NULL,
    make           VARCHAR(255),
    model          VARCHAR(255),
    g_pr_cm_torque VARCHAR(255),
    k_rpm          FLOAT,
    based_make     VARCHAR(255),
    based_model    VARCHAR(255),
    CONSTRAINT pk_bil PRIMARY KEY (id)
);

ALTER TABLE runde
    ADD bil_id UUID;

ALTER TABLE runde
    ADD laup_id UUID;

ALTER TABLE kusk
    ADD current_bil_id UUID;

ALTER TABLE kusk
    ADD CONSTRAINT FK_KUSK_ON_CURRENTBIL FOREIGN KEY (current_bil_id) REFERENCES bil (id);

ALTER TABLE runde
    ALTER COLUMN bil_id SET NOT NULL;

ALTER TABLE bil
    ADD CONSTRAINT FK_BIL_ON_KUSK FOREIGN KEY (kusk_id) REFERENCES kusk (id);

ALTER TABLE runde
    ADD CONSTRAINT FK_RUNDE_ON_BIL FOREIGN KEY (bil_id) REFERENCES bil (id);

ALTER TABLE runde
    ADD CONSTRAINT FK_RUNDE_ON_LAUP FOREIGN KEY (laup_id) REFERENCES laup (id);
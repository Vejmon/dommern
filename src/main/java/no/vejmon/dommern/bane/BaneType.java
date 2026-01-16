package no.vejmon.dommern.bane;

import lombok.Getter;

@Getter
public enum BaneType {
    BESTEFAR_BANEN(true),
    FRODE_SPESIAL(true),
    UTEN_NAVN(true),
    KORTESTE_VEIEN(true),
    UTENFOR_BANEN(false),
    I_DEPO(false);

    private final boolean enabled;

    BaneType(boolean enabled) {
        this.enabled = enabled;
    }
}
package no.vejmon.dommern.lyttere;

import lombok.Getter;

@Getter
public enum LydType {
    DEFAULT("bane%drundelyd.wav"),
    RECORD("bane%drekordlyd.wav"),
    RACE_COUNTDOWN("nedtelling%dlyd.wav"),
    RACE_START("startlyd.wav"),
    RACE_FALSE_START("tjuvstartlyd.wav");

    private final String name;
    LydType(String name) {
        this.name = name;
    }
}

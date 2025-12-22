package no.vejmon.dommern.lyttere;

import lombok.Getter;

@Getter
public enum LydType {
    DEFAULT("bane%drundelyd.mp3"),
    RECORD("bane%drekordlyd.mp3"),
    RACE_COUNTDOWN("nedtelling%dlyd.mp3"),
    RACE_START("startlyd.mp3"),
    RACE_FALSE_START("tjuvstartlyd.mp3");

    private final String name;
    LydType(String name) {
        this.name = name;
    }
}

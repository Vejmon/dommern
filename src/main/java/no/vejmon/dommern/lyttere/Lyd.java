package no.vejmon.dommern.lyttere;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import no.vejmon.dommern.bane.BaneType;

@Getter
@Setter
@AllArgsConstructor
public class Lyd {

    private BaneType bane;

    private LydType lydType;

}

package no.vejmon.dommern.lyttere;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import no.vejmon.dommern.bane.BaneType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Lyd {

    private BaneType bane;

    private LydType lydType;

    public String hentLydNavn(List<BaneType> baneList) {
        Integer baneId = baneList
                .stream()
                .filter(bane -> bane == this.bane)
                .findFirst()
                .map(BaneType::getSoundNumber)
                .orElse(0);
        return lydType.getName().formatted(baneId);
    }

}

package no.vejmon.dommern.lyttere;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import no.vejmon.dommern.bane.BaneType;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Lyd {

    private BaneType bane;

    private LydType lydType;

    public String hentLydNavn(Map<BaneType, Integer> baneLydMap) {
        Integer baneId = baneLydMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey() == this.bane)
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(0);
        return lydType.getName().formatted(baneId);
    }

}

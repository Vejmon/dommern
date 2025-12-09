package no.vejmon.dommern.runde;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Fører {

    @Id
    private UUID id;

    private String navn;
    @Embedded
    private List<Runde> runder;

}

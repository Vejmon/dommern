package no.vejmon.dommern.bane;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.RepresentationModelProcessor;


@Configuration
public class KuskProcessor implements RepresentationModelProcessor<EntityModel<Kusk>> {

    private final RepositoryEntityLinks repositoryEntityLinks;

    public KuskProcessor(RepositoryEntityLinks repositoryEntityLinks) {
        this.repositoryEntityLinks = repositoryEntityLinks;
    }

    @Override
    public EntityModel<Kusk> process(EntityModel<Kusk> model) {
        model.add(repositoryEntityLinks.linkToSearchResource(Runde.class, LinkRelation.of("runder_by_kusk"))
                .withRel("paged-runder")
                .expand(model.getContent().getId()));
        return model;
    }
}

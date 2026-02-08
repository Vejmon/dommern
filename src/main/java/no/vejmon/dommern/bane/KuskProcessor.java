package no.vejmon.dommern.bane;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class KuskProcessor implements RepresentationModelProcessor<EntityModel<Kusk>> {

        @Override
        public EntityModel<Kusk> process(EntityModel<Kusk> model) {
            Links newlinks = Links.NONE;
            for (Link link: model.getLinks()){
                if (link.getName() == "rundes"){
                    newlinks.and(linkTo(methodOn(RundeRepository.class)))
                }

            }
            model.removeLinks();
            model.add(newlinks);
            return model;
        }
    }

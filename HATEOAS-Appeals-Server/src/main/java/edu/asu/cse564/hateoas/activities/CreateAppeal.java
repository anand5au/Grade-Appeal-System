/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.activities;

/**
 *
 * @author Anand
 */
import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.model.AppealStatus;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.Link;
import edu.asu.cse564.hateoas.representations.Representations;
import edu.asu.cse564.hateoas.representations.RestbucksUri;
import edu.asu.cse564.hateoas.repositories.AppealRepository;

public class CreateAppeal {
    public AppealRepresentation create(Appeal appeal, RestbucksUri requestUri) {
        appeal.setStatus(AppealStatus.PENDING);
                
        Identifier identifier = AppealRepository.current().store(appeal);
        
        RestbucksUri appealUri = new RestbucksUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        //RestbucksUri paymentUri = new RestbucksUri(requestUri.getBaseUri() + "/payment/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representations.RELATIONS_URI + "Delete", appealUri), 
                //new Link(Representations.RELATIONS_URI + "payment", paymentUri), 
                new Link(Representations.RELATIONS_URI + "update", appealUri),
                new Link(Representations.SELF_REL_VALUE, appealUri));
    }
}

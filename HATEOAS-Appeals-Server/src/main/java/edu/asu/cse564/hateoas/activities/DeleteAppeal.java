/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.activities;

import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.model.AppealStatus;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.repositories.AppealRepository;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.RestbucksUri;

/**
 *
 * @author Anand
 */



public class DeleteAppeal {
    public AppealRepresentation delete(RestbucksUri appealUri) throws Exception {
        // Discover the URI of the order that has been cancelled
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new Exception();
        }

        Appeal appeal = appealRepository.get(identifier);

        // Can't delete a ready or preparing order
        if (appeal.getStatus() == AppealStatus.APPROVED ) {
            throw new Exception();
        }

        if(appeal.getStatus() == AppealStatus.PENDING) { // A pending appeal is deleted 
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }
}

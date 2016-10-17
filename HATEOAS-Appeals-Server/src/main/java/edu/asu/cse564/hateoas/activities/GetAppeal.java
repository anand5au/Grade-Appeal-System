/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.activities;

import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.repositories.AppealRepository;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.Representations;
import edu.asu.cse564.hateoas.representations.RestbucksUri;


/**
 *
 * @author Anand
 */
public class GetAppeal {
    
        public AppealRepresentation retrieveByUri(RestbucksUri appealUri) throws Exception {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new Exception();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    
}

    
    
    
}

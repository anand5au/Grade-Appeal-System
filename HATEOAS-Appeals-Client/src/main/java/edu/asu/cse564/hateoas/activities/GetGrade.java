/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.activities;

import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.repositories.GradeRepository;
import edu.asu.cse564.hateoas.representations.GradeRepresentations;
import edu.asu.cse564.hateoas.representations.RestbucksUri;



/**
 *
 * @author Anand
 */
public class GetGrade {
    
        public GradeRepresentations retrieveByUri(RestbucksUri gradeUri) throws Exception {
        Identifier identifier  = gradeUri.getId();
        
        Grade grade = GradeRepository.current().get(identifier);
        
        if(grade == null) {
            throw new Exception();
        }
        
        return GradeRepresentations.createResponseGradeRepresentation(grade, gradeUri);
    
}

    
    
    
}
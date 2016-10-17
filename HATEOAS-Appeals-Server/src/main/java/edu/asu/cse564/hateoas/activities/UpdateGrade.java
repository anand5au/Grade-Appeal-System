/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.activities;


import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.representations.RestbucksUri;
import edu.asu.cse564.hateoas.representations.GradeRepresentations;
import edu.asu.cse564.hateoas.repositories.GradeRepository;
import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.representations.Representations;

/**
 *
 * @author Anand
 */
public class UpdateGrade {
    public GradeRepresentations update(Grade grade, RestbucksUri gradeUri) throws Exception {
        Identifier gradeIdentifier = gradeUri.getId();

        GradeRepository repository = GradeRepository.current();
        
        if (GradeRepository.current().gradeNotPlaced(gradeIdentifier)) { // Defensive check to see if we have the order
            throw new Exception();
        }

        if (!gradeCanBeChanged(gradeIdentifier)) {
            throw new Exception();
        }

        Grade storedGrade = repository.get(gradeIdentifier);
        
       
        //storedOrder.calculateCost();


        return GradeRepresentations.createResponseGradeRepresentation(storedGrade, gradeUri); 
    }
    
    private boolean gradeCanBeChanged(Identifier identifier) {
        return true;
    }
    
}

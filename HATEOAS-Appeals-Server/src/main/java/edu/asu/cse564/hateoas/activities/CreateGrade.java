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
import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.representations.GradeRepresentations;
import edu.asu.cse564.hateoas.representations.Link;
import edu.asu.cse564.hateoas.representations.Representations;
import edu.asu.cse564.hateoas.representations.RestbucksUri;
import edu.asu.cse564.hateoas.repositories.GradeRepository;

public class CreateGrade {

    public GradeRepresentations create(Grade grade, RestbucksUri requestUri) {
        //grade.setStatus(AppealStatus.PENDING);
                
        Identifier identifier = GradeRepository.current().store(grade);
        
        RestbucksUri gradeUri = new RestbucksUri(requestUri.getBaseUri() + "/grades/" + identifier.toString());
        //RestbucksUri paymentUri = new RestbucksUri(requestUri.getBaseUri() + "/payment/" + identifier.toString());
        return new GradeRepresentations(grade,
                new Link(Representations.SELF_REL_VALUE, gradeUri));
    }
}

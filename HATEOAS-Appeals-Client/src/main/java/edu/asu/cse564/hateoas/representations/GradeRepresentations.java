/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.representations;

/**
 *
 * @author Anand
 */
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.cse564.hateoas.model.Grade;

@XmlRootElement(name="grade", namespace = Representations.RESTBUCKS_NAMESPACE)
public class GradeRepresentations extends Representations{
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeRepresentations.class);

    @XmlElement(name = "gradeLetter", namespace = Representations.RESTBUCKS_NAMESPACE)
    private char gradeLetter;
    

    /**
     * For JAXB :-(
     */
    GradeRepresentations() {
        LOG.info("Executing GradeRepresentation constructor");
    }

    public static GradeRepresentations fromXmlString(String xmlRepresentation) {
        LOG.info("Creating a grade object from the XML = {}", xmlRepresentation);
                
        GradeRepresentations gradeRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(GradeRepresentations.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            gradeRepresentation = (GradeRepresentations) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        LOG.debug("Generated the object {}", gradeRepresentation);
        return gradeRepresentation;
    }
    
    public static GradeRepresentations createResponseGradeRepresentation(Grade grade, RestbucksUri gradeUri) 
    
    {
         LOG.info("Creating a Response Order for order = {} and order URI", grade.toString(), gradeUri.toString());
        
        GradeRepresentations gradeRepresentation = null; 
        
//        RestbucksUri paymentUri = new RestbucksUri(gradeUri.getBaseUri() + "/payment/" + gradeUri.getId().toString());
        
        LOG.debug("The order representation created for the Create Response Order Representation is {}", gradeRepresentation);
        
        return gradeRepresentation;
    }

    public GradeRepresentations(Grade grade, Link... links) {
        LOG.info("Creating an Order Representation for order = {} and links = {}", grade.toString(), links.toString());
        
        try {
            this.gradeLetter = grade.getValue();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            System.out.println("Error");
        }
        
        LOG.debug("Created the Grade Representation {}", this);
    }

    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(GradeRepresentations.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

public Grade getGrade() {
        

        Grade grade = new Grade(gradeLetter);
        
        return grade;
    }

    public Link getCancelLink() {
        return getLinkByName(RELATIONS_URI + "cancel");
    }

    public Link getPaymentLink() {
        return getLinkByName(RELATIONS_URI + "payment");
    }

    public Link getUpdateLink() {
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    
   
}    


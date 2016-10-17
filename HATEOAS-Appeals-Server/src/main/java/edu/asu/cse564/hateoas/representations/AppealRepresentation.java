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

import edu.asu.cse564.hateoas.representations.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.asu.cse564.hateoas.model.AppealStatus;
import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.representations.Representations;
import edu.asu.cse564.hateoas.representations.RestbucksUri;

@XmlRootElement(name = "appeal", namespace = Representations.RESTBUCKS_NAMESPACE)
public class AppealRepresentation extends Representations {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepresentation.class);

    @XmlElement(name = "AppealComments", namespace = Representations.RESTBUCKS_NAMESPACE)
    private StringBuilder AppealComments = new StringBuilder();
    
    @XmlElement(name = "status", namespace = Representations.RESTBUCKS_NAMESPACE)
    private AppealStatus status;

    /**
     * For JAXB :-(
     */
    AppealRepresentation() {
        LOG.info("Executing AppealRepresentation constructor");
    }

    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
        LOG.info("Creating an appeal object from the XML = {}", xmlRepresentation);
                
        AppealRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        LOG.debug("Generated the object {}", appealRepresentation);
        return appealRepresentation;
    }
    
    public static AppealRepresentation createResponseAppealRepresentation(Appeal appeal, RestbucksUri appealUri) {
        LOG.info("Creating a Response Order for order = {} and order URI", appeal.toString(), appealUri.toString());
        
        AppealRepresentation appealRepresentation = null; 
        
        
        
        if(appeal.getStatus() == AppealStatus.PENDING) {
            LOG.debug("The appeal status is {}", AppealStatus.PENDING);
            appealRepresentation = new AppealRepresentation(appeal, 
                    new Link(RELATIONS_URI + "Update", appealUri), 
                    new Link(RELATIONS_URI + "Delete", appealUri), 
                    //new Link(RELATIONS_URI + "Highlight/add Images", orderUri),
                    new Link(Representations.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.REJECTED) {
            LOG.debug("The appeal status is {}", AppealStatus.REJECTED);
                        appealRepresentation = new AppealRepresentation(appeal, new Link(Representations.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.APPROVED) {
            LOG.debug("The appeal status is {}", AppealStatus.APPROVED);
         appealRepresentation = new AppealRepresentation(appeal, new Link(Representations.SELF_REL_VALUE, appealUri));        
        }             
         else {
            LOG.debug("The order status is in an unknown status");
            throw new RuntimeException("Unknown Appeal Status");
        }
        
        LOG.debug("The appeal representation created for the Create Response appeal Representation is {}", appealRepresentation);
        
        return appealRepresentation;
    }

    public AppealRepresentation(Appeal appeal, Link... links) {
        LOG.info("Creating an Order Representation for order = {} and links = {}", appeal.toString(), links.toString());
        
        try {
            this.AppealComments = appeal.getComments();
            this.status=appeal.getStatus();
            this.links = java.util.Arrays.asList(links);
            
        } catch (Exception ex) {
            System.out.println("Error");
        }
        
        LOG.debug("Created the AppealRepresentation {}", this);
    }

    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Appeal getAppeal() {
      
        Appeal appeal = new Appeal(AppealComments);
        return appeal;
    }

    public Link getDeleteLink() {
        return getLinkByName(RELATIONS_URI + "Delete");
    }

    
    public Link getUpdateLink() {
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    public AppealStatus getStatus() {
        return status;
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.resources;

import edu.asu.cse564.hateoas.activities.CreateGrade;
import edu.asu.cse564.hateoas.activities.GetAppeal;
import edu.asu.cse564.hateoas.activities.GetGrade;
import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.repositories.GradeRepository;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.GradeRepresentations;
import edu.asu.cse564.hateoas.representations.Link;
import edu.asu.cse564.hateoas.representations.RestbucksUri;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anand
 */
@Path("/grades")
public class GradeResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeResource.class);

    private @Context UriInfo uriInfo;
    

    public GradeResource() {
        LOG.info("OrderResource constructor");
    }

    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * 
     * @param uriInfo
     */
    /*
    public GradeResource(UriInfo uriInfo) {
        LOG.info("OrderResource constructor with mock uriInfo {}", uriInfo);
        this.uriInfo = uriInfo;  
    }
    
    */
    @GET
    @Path("/{gradeId}")
    @Produces("application/vnd.cse564-appeals+xml ")
    public Response getTheGrade() {
        LOG.info("Retrieving a grade Resource");
        
        Response response;
        
        try {
            GradeRepresentations graderesponseRepresentation = new GetGrade().retrieveByUri(new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(graderesponseRepresentation).build();
        } catch(Exception nsoe) {
            LOG.debug("No such grade");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } 
        
        LOG.debug("Retrieved the grade resource", response);
        
        return response;
    }
    
    
    
    @POST
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response createGrade(String gradeRepresentation) {
        LOG.info("Creating a grade Resource");
        
        Response response;
        
        try {
            GradeRepresentations rq = GradeRepresentations.fromXmlString(gradeRepresentation);
            
           
            GradeRepresentations responseRepresentation=new CreateGrade().create(rq.getGrade(), new RestbucksUri(uriInfo.getRequestUri()));//new GradeRepresentations(GradeRepresentations.fromXmlString(gradeRepresentation).getGrade(), new Link("self", new RestbucksUri("localhost")));
           // return  Response.ok().entity(rep).build();
            response = Response.ok().entity(responseRepresentation).build();
        } catch (Exception ioe) {
            LOG.debug("Invalid link or URI {}", gradeRepresentation);
            response = Response.status(Status.BAD_REQUEST).build();
        }  
        
        LOG.debug("Resulting response for creating the grade resource is {}", response);
        
        return response;
    }
}

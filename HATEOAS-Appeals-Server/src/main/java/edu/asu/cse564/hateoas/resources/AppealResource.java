/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.resources;


import edu.asu.cse564.hateoas.activities.CreateAppeal;
import edu.asu.cse564.hateoas.activities.CreateGrade;
import edu.asu.cse564.hateoas.activities.GetAppeal;
import edu.asu.cse564.hateoas.activities.DeleteAppeal;
import edu.asu.cse564.hateoas.activities.UpdateAppeal;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.repositories.GradeRepository;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.GradeRepresentations;
import edu.asu.cse564.hateoas.representations.RestbucksUri;
import edu.asu.cse564.hateoas.model.Appeal;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anand
 */
@Path("/appeal")
public class AppealResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealResource.class);

    private @Context UriInfo uriInfo;
    

    public AppealResource() {
        LOG.info("AppealResource constructor");
    }

    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * 
     * @param uriInfo
     */
    /*public AppealResource(UriInfo uriInfo) {
        LOG.info("OrderResource constructor with mock uriInfo {}", uriInfo);
        this.uriInfo = uriInfo;  
    }*/
    @GET
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml ")
    public Response getTheAppeal() {
        LOG.info("Retrieving an appeal Resource");
        
        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new GetAppeal().retrieveByUri(new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch(Exception nsoe) {
            LOG.debug("No such order");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } 
        
        LOG.debug("Retrieved the appeal resource", response);
        
        return response;
    }
    
        @POST
    @Path("/{appealId}")
    @Consumes("application/vnd.cse564-appeals+xml ")
    @Produces("application/vnd.cse564-appeals+xml ")
    public Response updateAppeal(String appealrepresentation) {
        LOG.info("Updating an appeal Resource");
        
        Response response;
                try {
            AppealRepresentation responseRepresentation = new UpdateAppeal().update(AppealRepresentation.fromXmlString(appealrepresentation).getAppeal(), new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (Exception ioe) {
            LOG.debug("Something went wrong updating the order resource");
            response = Response.serverError().build();
        } 
        
        LOG.debug("Resulting response for updating the order resource is {}", response);
        
        return response;
     }

        @DELETE
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response removeAppeal() {
        LOG.info("Removing an Appeal Reource");
        
        Response response;
        
        try {
            AppealRepresentation removedAppeal = new DeleteAppeal().delete(new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(removedAppeal).build();
        } catch (Exception nsoe) {
            LOG.debug("Problems deleting the appeal resource");
            response = Response.serverError().build();
        }
        
        LOG.debug("Resulting response for deleting the appeal resource is {}", response);
        
        return response;
    }
    
    
    @POST
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
            public Response createAppeal(String appealRepresentation){
    Response response;
        
        try {
            AppealRepresentation ap = AppealRepresentation.fromXmlString(appealRepresentation);
            
            
            AppealRepresentation responseRepresentation=new CreateAppeal().create(ap.getAppeal(), new RestbucksUri(uriInfo.getRequestUri()));
//new GradeRepresentations(GradeRepresentations.fromXmlString(gradeRepresentation).getGrade(), new Link("self", new RestbucksUri("localhost")));
           // return  Response.ok().entity(rep).build();
            response = Response.ok().entity(responseRepresentation).build();
        } catch (Exception ioe) {
           // LOG.debug("Invalid Order - Problem with the orderrepresentation {}", gradeRepresentation);
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }  
        
        LOG.debug("Resulting response for creating the appeal resource is {}", response);
        
        return response;
    }
            
            
            
}
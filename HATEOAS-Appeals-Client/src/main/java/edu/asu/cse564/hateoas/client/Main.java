package edu.asu.cse564.hateoas.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.GradeRepresentations;
import edu.asu.cse564.hateoas.representations.Link;
import edu.asu.cse564.hateoas.representations.RestbucksUri;
import static edu.asu.cse564.hateoas.model.AppealStatus.APPROVED;
import static edu.asu.cse564.hateoas.model.AppealStatus.REJECTED;

public class Main
{

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final String HATEOAS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    private static final String GRADE_URI = "http://localhost:8080/HATEOAS-Appeals-adhanda1-netbeans-ws/webresources/grades";
    private static final String APPEAL_URI = "http://localhost:8080/HATEOAS-Appeals-adhanda1-netbeans-ws/webresources/appeal";

    public static void main(String[] args) throws Exception
    {
        URI gradeURI = new URI(GRADE_URI);
        URI appealURI = new URI(APPEAL_URI);
        happyPath(gradeURI, appealURI);
        AbandonAppealTest(appealURI);
        ForgottenAppealTest(appealURI);
        RejectedAppealTest(appealURI);
        System.out.println("\n\nAll Test cases executed successfully\n\n ");
    }

    private static void happyPath(URI serviceUri, URI serviceUri1) throws Exception
    {
        System.out.println("CSE564 HATEOAS Grade Appeal Client\n");

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Happy path test case");
        System.out.println("--------------------------------------------------------------------------------------------");
        LOG.info("Happy path test with URI {} ", serviceUri);
        LOG.info("*** Posting the grades");
        Client client = Client.create();
        Grade grade = new Grade('B');
        GradeRepresentations gradeRepresentation = client.resource(serviceUri).accept(HATEOAS_MEDIA_TYPE).type(HATEOAS_MEDIA_TYPE).post(GradeRepresentations.class, grade);
        LOG.debug("Created Grade Representation {} at the URI {}", gradeRepresentation, gradeRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Grades posted at [%s]", gradeRepresentation.getSelfLink().getUri().toString()));

        System.out.println();
        LOG.info("*** Appeal is created for the grade");
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("Appeal Request");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(HATEOAS_MEDIA_TYPE).type(HATEOAS_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created AppealRepresentation {} at the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeals posted at [%s]\n\n", appealRepresentation.getSelfLink().getUri().toString()));

        System.out.println();
        LOG.info("*** Reading the appeal");
        System.out.println(String.format("Requesting the appeal at [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
        Link appealLink = appealRepresentation.getSelfLink();
        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(HATEOAS_MEDIA_TYPE).get(AppealRepresentation.class);
        System.out.println(String.format("Appeal exists, current status [%s], at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink()));

        System.out.println();
        LOG.info("*** Professor read and approve the appeal");
        System.out.println(String.format("Updating the appeal at [%s] via POST", postAppealRepresentation.getUpdateLink().getUri().toString()));
        Appeal ap = new Appeal(postAppealRepresentation.getAppeal().getStatus());
        Link upLink = postAppealRepresentation.getUpdateLink();
        ap.setStatus(APPROVED);
        AppealRepresentation upRepresentation = client.resource(upLink.getUri()).accept(upLink.getMediaType()).type(upLink.getMediaType()).post(AppealRepresentation.class, ap);
        LOG.debug("Created updated Appeal representation link {}", upRepresentation);
        System.out.println(String.format("Appeal updated by Professor at [%s] and status is %s", upRepresentation.getSelfLink().getUri().toString(), ap.getStatus()));

        System.out.println();
        LOG.info("*** Reading the updated grade after appeal");
        System.out.println(String.format("Requesting the appeal at [%s] via GET", gradeRepresentation.getSelfLink().getUri().toString()));
        Link gradeLink = gradeRepresentation.getSelfLink();
        GradeRepresentations postGradeRepresentation = client.resource(gradeLink.getUri()).accept(HATEOAS_MEDIA_TYPE).get(GradeRepresentations.class);
        System.out.println(String.format("Updated grade is at [%s]", postGradeRepresentation.getSelfLink().getUri().toString()));

        Grade newgrade = new Grade('A');
        Link newLink = postGradeRepresentation.getUpdateLink();

        System.out.println();
        LOG.info("** Updating appeal with a incorrect ID");
        System.out.println(String.format("About to update appeal with incorrect ID [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
        StringBuilder AppealRequest1 = new StringBuilder();
        AppealRequest1.append("Bad Appeal request");
        Link badLink = new Link("bad", new RestbucksUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), HATEOAS_MEDIA_TYPE);
        LOG.debug("Create bad link {}", badLink);
        ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
        LOG.debug("Created Bad Update Response {}", badUpdateResponse);
        System.out.println(String.format("Tried to update appeal with incorrect ID at [%s] via POST, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));

        System.out.println();
        LOG.info("*** Update appeal with correct ID");
        System.out.println(String.format("About to update appeal at [%s] via POST", postAppealRepresentation.getUpdateLink().getUri().toString()));
        StringBuilder AppealRequest2 = new StringBuilder();
        AppealRequest2.append("Professor, I have more questions.");
        Appeal appeal3 = new Appeal(AppealRequest2);

        Link updateLink = postAppealRepresentation.getUpdateLink();
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, appeal3);
        LOG.debug("Created updated Appeal representation link {}", updatedRepresentation);
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));

    }

    private static void AbandonAppealTest(URI serviceUri1) throws Exception
    {
        Client client = Client.create();
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Appeal abandoned case: Students post appeal, and abandons the appeal before professor review");
        System.out.println("--------------------------------------------------------------------------------------------");
        LOG.info("*** Appeal is created");
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("Appeal Request");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(HATEOAS_MEDIA_TYPE).type(HATEOAS_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created AppealRepresentation {} at the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal is created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));

        System.out.println();
        LOG.info("*** Read the appeal");
        System.out.println(String.format("Requesting appeal from [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
        Link appealLink = appealRepresentation.getSelfLink();
        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(HATEOAS_MEDIA_TYPE).get(AppealRepresentation.class);
        System.out.println(String.format("Appeal exists, current status [%s], placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink()));

        System.out.println();
        LOG.info("*** Student abandons the appeal");
        System.out.println(String.format("Removing the Pendng appeal from [%s] via DELETE. ", postAppealRepresentation.getDeleteLink().getUri().toString()));
        Link deleteLink = postAppealRepresentation.getSelfLink();
        ClientResponse finalResponse = client.resource(deleteLink.getUri()).delete(ClientResponse.class);
        System.out.println(String.format("Deleted appeal, HTTP status [%d]", finalResponse.getStatus()));
        if (finalResponse.getStatus() == 200)
        {
            System.out.println(String.format("Appeal status [%s],", finalResponse.getEntity(AppealRepresentation.class).getStatus()));
        }
    }

    private static void ForgottenAppealTest(URI serviceUri1) throws Exception
    {
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Appeal forgotten case: Students post appeal and professor forgets the appeal, so student follows up.");
        System.out.println("--------------------------------------------------------------------------------------------");
        Client client = Client.create();
        LOG.info("*** Appeal is created");
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("Appeal request");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(HATEOAS_MEDIA_TYPE).type(HATEOAS_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));

        System.out.println();
        LOG.info("*** Student follows up");
        System.out.println(String.format("About to update appeal at [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString()));
        StringBuilder AppealRequest2 = new StringBuilder();
        AppealRequest2.append("Updated Appeal request - follow up");
        Appeal appeal3 = new Appeal(AppealRequest2);

        Link updateLink = appealRepresentation.getUpdateLink();
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, appeal3);
        LOG.debug("Updated Appeal representation link {}", updatedRepresentation);
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));

    }

    private static void RejectedAppealTest(URI serviceUri1) throws Exception
    {
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Appeal rejected case: Student appeals and it is rejecetd by Professor");
        System.out.println("--------------------------------------------------------------------------------------------");
        Client client = Client.create();
        System.out.println();
        LOG.info("*** Appeal is created");
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("Appeal request");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(HATEOAS_MEDIA_TYPE).type(HATEOAS_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeals created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));

        System.out.println();
        LOG.info("*** Read the appeal");
        System.out.println(String.format("Requesting appeal from [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
        Link appealLink = appealRepresentation.getSelfLink();
        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(HATEOAS_MEDIA_TYPE).get(AppealRepresentation.class);
        System.out.println(String.format("Appeal exists, current status [%s], placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink()));

        System.out.println();
        LOG.info("*** Professor read and rejects the appeal");
        System.out.println(String.format("Updating appeal at [%s] via POST", postAppealRepresentation.getUpdateLink().getUri().toString()));
        Appeal app = new Appeal(postAppealRepresentation.getAppeal().getStatus());
        Link upLin = postAppealRepresentation.getUpdateLink();
        app.setStatus(REJECTED);
        AppealRepresentation uRepresentation = client.resource(upLin.getUri()).accept(upLin.getMediaType()).type(upLin.getMediaType()).post(AppealRepresentation.class, app);
        LOG.debug("Created updated Appeal representation link {}", uRepresentation);
        System.out.println(String.format("Updated appeal is at [%s] and status is %s", uRepresentation.getSelfLink().getUri().toString(), app.getStatus()));

    }

}

package edu.asu.cse564.hateoas.activities;

import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.model.AppealStatus;
import edu.asu.cse564.hateoas.model.Identifier;
import edu.asu.cse564.hateoas.repositories.AppealRepository;
import edu.asu.cse564.hateoas.representations.AppealRepresentation;
import edu.asu.cse564.hateoas.representations.RestbucksUri;


public class UpdateAppeal {
    public AppealRepresentation update(Appeal appeal, RestbucksUri appealUri) throws Exception {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        
        if (AppealRepository.current().appealNotPlaced(appealIdentifier)) { // Defensive check to see if we have the order
            throw new Exception();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new Exception();
        }

        Appeal storedAppeal = repository.get(appealIdentifier);
        
        storedAppeal.setStatus(appeal.getStatus());
        //storedOrder.calculateCost();


        return AppealRepresentation.createResponseAppealRepresentation(storedAppeal, appealUri); 
    }
    
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.PENDING;
    }
}

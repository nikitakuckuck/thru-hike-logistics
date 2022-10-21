package learn.thruhike.domain;

import learn.thruhike.data.TrailRepository;
import learn.thruhike.models.Trail;
import org.assertj.core.internal.CommonValidations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailService {
    private final TrailRepository repository;

    public TrailService(TrailRepository repository) {
        this.repository = repository;
    }

    public List<Trail> findAll(){
        return repository.findAll();
    }

    public Trail findById(int id){
        return repository.findById(id);
    }

    public Result<Trail> add(Trail trail){
        Result<Trail> result = validate(trail);
        if(!result.isSuccess()){
            return result;
        }
        if (trail.getTrailId() != 0){
            result.addErrorMessage("The trail ID cannot be set before adding a trail.");
        }
        result.setPayload(repository.add(trail));
        return result;

    }

    public Result<Trail> update(Trail trail){
        Result<Trail> result = validate(trail);
        if(!result.isSuccess()){
            return result;
        }
        if(trail.getTrailId()==0){
            result.addErrorMessage("A trail ID is required for updating a trail.");
            return result;
        }
        if(!repository.update(trail)){
           result.addErrorMessage(messageWithId(trail.getTrailId()));
        }
        return result;
    }

    public Result<Trail> deleteById(int id){
        Result<Trail> result = new Result<>();
        if(!repository.deleteById(id)){
            result.addErrorMessage(messageWithId(id));
        }
        return result;
    }

    //validation: trail name is required
    private Result<Trail> validate(Trail trail){
        Result<Trail> result = new Result<>();
        if(trail==null){
            result.addErrorMessage("Trail cannot be null.");
            return result;
        }

        if(trail.getTrailName()==null || trail.getTrailName().isBlank()){
            result.addErrorMessage("Trail name is required.");
        }
        boolean isUnique = true;
        for(Trail t : repository.findAll()){
            if(t.getTrailName().equalsIgnoreCase(trail.getTrailName())){
                isUnique= false;
                break;
            }
        }
        if(!isUnique){
            result.addErrorMessage("Trail name cannot be the same as an existing trail name. Please add an identifying feature to the name. For example, if you want to add each state of the Pacific Crest Trail as a separate trail, you could write \"Pacific Crest Trail, WA\".");
        }
        return result;
    }

    //error message which shows the id
    private String messageWithId(int id){
        return String.format("A trail with the ID: %s was not found.",id);
    }

}

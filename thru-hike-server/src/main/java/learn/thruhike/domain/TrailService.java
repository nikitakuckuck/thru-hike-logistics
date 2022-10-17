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
            result.addErrorMessage("The trail id cannot be set before adding a trail");
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
            result.addErrorMessage("A trail id is required for updating a trail");
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
            result.addErrorMessage("Trail cannot be null");
            return result;
        }
        if(trail.getTrailName()==null || trail.getTrailName().isBlank()){
            result.addErrorMessage("Trail name is required");
        }
        return result;
    }

    //error message which shows the id
    private String messageWithId(int id){
        return String.format("A trail with the ID: %s was not found",id);
    }

}

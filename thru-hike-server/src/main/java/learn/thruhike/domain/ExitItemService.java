package learn.thruhike.domain;

import learn.thruhike.data.ExitItemRepository;
import learn.thruhike.models.ExitItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExitItemService {
    private final ExitItemRepository repository;

    public ExitItemService(ExitItemRepository repository) {
        this.repository = repository;
    }

    public List<ExitItem> findAll(){
        return repository.findAll();
    }

    public List<ExitItem> findAllIncomplete(){
        return repository.findAllIncomplete();
    }

    public ExitItem findById(int id){
        return repository.findById(id);
    }

    public Result<ExitItem> add(ExitItem townExit){
        Result<ExitItem> result = validate(townExit);
        if(!result.isSuccess()){
            return result;
        }
        if(townExit.getExitItemId() != 0){
            result.addErrorMessage("The exit item ID cannot be set before adding an item.");
        }
        result.setPayload(repository.add(townExit));
        return result;
    }

    public Result<ExitItem> update(ExitItem townExit){
        Result<ExitItem> result = validate(townExit);
        if(!result.isSuccess()){
            return result;
        }
        if(townExit.getExitItemId() == 0){
            result.addErrorMessage("An exit item ID is required in order to update an item.");
            return result;
        }
        if(!repository.update(townExit)){
            result.addErrorMessage(messageWithId(townExit.getExitItemId()));
        }
        return result;
    }

    public Result<ExitItem> deleteById(int id){
        Result<ExitItem> result = new Result<>();
        if(!repository.deleteById(id)){
            result.addErrorMessage(messageWithId(id));
        }
        return result;
    }

    //for validation: item name and goodToGo boolean are required.
    private Result<ExitItem> validate(ExitItem townExit){
        Result<ExitItem> result = new Result<>();
        if(townExit==null){
            result.addErrorMessage("Exit item cannot be null");
            return result;
        }
        if(townExit.getExitItemName()== null || townExit.getExitItemName().isBlank()){
            result.addErrorMessage("Exit item name is required");
        }
        return result;
    }

    //error message which shows id
    private String messageWithId(int id){ return String.format("An exit item with the ID: %s was not found.",id);}
}

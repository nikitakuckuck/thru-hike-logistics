package learn.thruhike.domain;

import learn.thruhike.data.TownContactRepository;
import learn.thruhike.data.TrailSectionRepository;
import learn.thruhike.models.TownContact;
import learn.thruhike.models.TrailSection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownContactService {
    private final TownContactRepository townContactRepository;
    private final TrailSectionRepository trailSectionRepository;

    public TownContactService(TownContactRepository repository, TrailSectionRepository trailSectionRepository) {
        this.townContactRepository = repository;
        this.trailSectionRepository = trailSectionRepository;
    }

    public List<TownContact> findAll(){
        return townContactRepository.findAll();
    }

    public List<TownContact> findByTrailSection(int sectionId){
        return townContactRepository.findByTrailSection(sectionId);
    }

    public TownContact findById(int id){
        return townContactRepository.findById(id);
    }

    public Result<TownContact> add(TownContact contact){
        Result<TownContact> result = validateContact(contact);
        if(!result.isSuccess()){
            return result;
        }
        if(contact.getTownContactId() !=0){
            result.addErrorMessage("The town contact entry ID cannot be set before adding a contact.");
        }
        result.setPayload(townContactRepository.add(contact));
        return result;
    }

    public Result<TownContact> update(TownContact contact){
        Result<TownContact> result = validateContact(contact);
        if(!result.isSuccess()){
            return result;
        }
        if(contact.getTownContactId() == 0){
            result.addErrorMessage("A town contact ID is required for updating a contact.");
            return result;
        }
        if(!townContactRepository.update(contact)){
            result.addErrorMessage(messageWithId(contact.getTownContactId()));
        }
        return result;
    }

    public Result<TownContact> deleteById(int id){
        Result<TownContact> result = new Result<>();
        if(!townContactRepository.deleteById(id)){
            result.addErrorMessage(messageWithId(id));
        }
        return result;
    }

  /*validation requirements: category, content,  trail section id are all required.
  Other notes is not required. Trail section id must be for a valid section.
    */

    private Result<TownContact> validateContact(TownContact contact){
        Result<TownContact> result = new Result<>();
        if(result==null){
            result.addErrorMessage("Town Contact cannot be null.");
            return result;
        }
        if(contact.getTrailSectionId()==0){
            result.addErrorMessage("Trail section is required.");
        } else if(trailSectionRepository.findById(contact.getTrailSectionId())==null){
            result.addErrorMessage("This trail section does not exist.");
        }

        if(contact.getCategory()==null||contact.getCategory().isBlank()){
            result.addErrorMessage("Category is required.");
        }
        if(contact.getContent()==null||contact.getContent().isBlank()){
            result.addErrorMessage("Content is required.");
        }
        return result;
    }

    private String messageWithId(int id){
        return String.format("A town contact entry with the ID: %s was not found",id);
    }
}

package learn.thruhike.domain;

import learn.thruhike.data.SectionAlertRepository;
import learn.thruhike.data.TrailSectionRepository;
import learn.thruhike.models.SectionAlert;
import learn.thruhike.models.TrailSection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionAlertService {
    private final SectionAlertRepository alertRepository;
    private final TrailSectionRepository trailSectionRepository;

    public SectionAlertService(SectionAlertRepository repository, TrailSectionRepository trailSectionRepository) {
        this.alertRepository = repository;
        this.trailSectionRepository = trailSectionRepository;
    }

    public List<SectionAlert> findAll(){
        return alertRepository.findAll();
    }

    public List<SectionAlert> findByTrailSectionId(int id){
        return alertRepository.findByTrailSectionId(id);
    }

    public Result<SectionAlert> add(SectionAlert alert){
        Result<SectionAlert> result = validateAlert(alert);
        if(!result.isSuccess()){
            return result;
        }
        if(alert.getAlertId() !=0){
            result.addErrorMessage("Section alert ID cannot be set before adding.");
        }
        result.setPayload(alertRepository.add(alert));
        return result;
    }

    private Result<SectionAlert> validateAlert(SectionAlert alert){
        Result<SectionAlert> result = new Result<>();
        if(alert==null){
            result.addErrorMessage("Section alert cannot be null.");
            return result;
        }
        if(alert.getTrailSectionId()==0){
            result.addErrorMessage("Trail section is required.");
        }
        boolean sectionExists = false;
        for(TrailSection s: trailSectionRepository.findAll()){
            if(s.getTrailSectionId()==alert.getTrailSectionId()){
                sectionExists = true;
                break;
            }
        }
        if(!sectionExists){
            result.addErrorMessage("Trail section does not exist.");
        }
        if(alert.getAlertCategoryId()<=0 || alert.getAlertCategoryId()>8){
            result.addErrorMessage("Alert category is required, and must be one of the specified options.");
        }

        if(alert.getAlertContent()==null || alert.getAlertContent().isBlank()){
            result.addErrorMessage("Alert content is required.");
        }

        return result;
    }

    private String messageWithId(int id){
        return String.format("A section alert with the ID: %s was not found",id);
    }
}

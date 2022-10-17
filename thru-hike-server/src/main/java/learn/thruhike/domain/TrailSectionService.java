package learn.thruhike.domain;

import learn.thruhike.data.TrailRepository;
import learn.thruhike.data.TrailSectionRepository;
import learn.thruhike.models.Trail;
import learn.thruhike.models.TrailSection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailSectionService {
    private final TrailSectionRepository sectionRepository;
    private final TrailRepository trailRepository;

    public TrailSectionService(TrailSectionRepository repository, TrailRepository trailRepository) {
        this.sectionRepository = repository;
        this.trailRepository = trailRepository;
    }

    public List<TrailSection> findAll(){
        return sectionRepository.findAll();
    }

    public List<TrailSection> findAllUpcoming(){
        return sectionRepository.findAllUpcoming();
    }

    public List<TrailSection> findByTrailId(int id){
        return sectionRepository.findByTrailId(id);
    }

    public TrailSection findById(int id){
        return sectionRepository.findById(id);
    }

    public TrailSection findBySectionNickname(String nickname){
        return sectionRepository.findBySectionNickname(nickname);
    }
//
//    public  Result<TrailSection> add(TrailSection section){
//
//    }
//
//    public Result<TrailSection> update(TrailSection section){
//
//    }
//
//    public Result<TrailSection> deleteById(int id){
//
//    }

    /*
    validation requirements: trail id, section nickname, section start,
    section end, section days, and upcoming are all required. Unique nickname is required. Trail id must match an existing trail.
    Trail days must be 30 or fewer, because anything above that is almost certainly a typo or beyond the scope of this app's design.
    */

    //TODO: validate numbers,  latitude, longitude, sectionLength;
    private Result<TrailSection> validateSection(TrailSection section){
        Result<TrailSection> result = new Result<>();
        if(section==null){
            result.addErrorMessage("Trail section cannot be null.");
            return result;
        }
        if(section.getTrailId() == 0){
            result.addErrorMessage("Trail id is required.");
        }
        boolean trailExists = false;
        for(Trail t : trailRepository.findAll()){
            if(t.getTrailId()== section.getTrailId()){
                trailExists = true;
            }
        }
        if(!trailExists){
            result.addErrorMessage("A trail for this section does not exist. The corresponding trail must be created first before a section for the trail can be added.");
        }

        if(section.getSectionNickname() == null || section.getSectionNickname().isBlank()){
            result.addErrorMessage("Trail section nickname is required. An example of a common nickname format might be \"Starting Town to Ending Town\".");
        }
        // nicknames must be unique as a way to easily identify sections, since there may be different routes between the same two towns
        boolean unique = true;
        for(TrailSection s : sectionRepository.findAll()){
            if (s.getSectionNickname().equalsIgnoreCase(section.getSectionNickname())){
                unique = false;
            }
        }
        if(!unique){
            result.addErrorMessage("Nickname cannot be the same as another trail section nickname.");
        }

        if(section.getSectionStart()==null || section.getSectionStart().isBlank()){
            result.addErrorMessage("Section start is required.");
        }
        if(section.getSectionEnd()==null || section.getSectionEnd().isBlank()){
            result.addErrorMessage("Section end is required.");
        }
        if(section.getSectionDays()==0){
            result.addErrorMessage("Number of days is required.");
        }
        if(section.getSectionDays()>30){
            result.addErrorMessage("Number of days cannot be greater than 30. " +
                    "Please create a shorter section, go into town, and get some food so you don't starve.");
        }
        return result;
    }

    private String messageWithId(int id){
        return String.format("A trail section with the ID: %s was not found",id);
    }
}

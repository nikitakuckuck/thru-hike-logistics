package learn.thruhike.domain;

import learn.thruhike.data.TrailRepository;
import learn.thruhike.data.TrailSectionRepository;
import learn.thruhike.models.Trail;
import learn.thruhike.models.TrailSection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TrailSectionServiceTest {

    @Autowired
    TrailSectionService service;

    @MockBean
    TrailSectionRepository sectionRepository;
    @MockBean
    TrailRepository trailRepository;

    @Test
    void shouldFindById(){
        TrailSection section = makeNewSection();
        section.setTrailSectionId(1);
        Mockito.when(sectionRepository.findById(1)).thenReturn(section);
        assertNotNull(service.findById(1));
    }

    @Test
    void shouldFindByTrailId(){
        TrailSection section = makeNewSection();
        section.setTrailSectionId(1);
        List<TrailSection> list = new ArrayList<>();
        list.add(section);
        Mockito.when(sectionRepository.findByTrailId(1)).thenReturn(list);
        assertNotNull(service.findByTrailId(1));
    }

    @Test
    void shouldAddValidSection(){
        TrailSection section = makeNewSection();
        Mockito.when(trailRepository.findAll()).thenReturn(new ArrayList<>(List.of(makeNewTrail())));
        Result<TrailSection> result = service.add(section);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddInvalidSection(){
        TrailSection section = new TrailSection();
        Mockito.when(trailRepository.findAll()).thenReturn(new ArrayList<>(List.of(makeNewTrail())));
        Result<TrailSection> result = service.add(section);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("Section start is required."));
        assertTrue(result.getErrorMessages().contains("Section end is required."));
        assertTrue(result.getErrorMessages().contains("Number of days is required."));
        assertTrue(result.getErrorMessages().contains("Section length is required and must be between 1 and 500."));

        section.setStartLatitude(800);
        result = service.add(section);
        assertTrue(result.getErrorMessages().contains("Latitude must be between -90 and 90."));

        section.setStartLongitude(-900);
        result = service.add(section);
        assertTrue(result.getErrorMessages().contains("Longitude must be between -180 and 180."));

        section.setSectionDays(31);
        result = service.add(section);
        assertTrue(result.getErrorMessages().contains("Number of days cannot be greater than 30. Please create a shorter section, go into town, and get some food so you don't starve."));

    }

    @Test
    void shouldUpdateValidSection(){
        TrailSection section = makeNewSection();
        section.setTrailSectionId(1);
        section.setUpcoming(false);
        Mockito.when(trailRepository.findAll()).thenReturn(new ArrayList<>(List.of(makeNewTrail())));
        Mockito.when(sectionRepository.update(section)).thenReturn(true);
        Result<TrailSection> result = service.update(section);
        assertTrue(result.isSuccess());

    }

    @Test
    void shouldNotUpdateInvalidSection(){
        TrailSection section = makeNewSection();
        Mockito.when(trailRepository.findAll()).thenReturn(new ArrayList<>(List.of(makeNewTrail())));
        Mockito.when(sectionRepository.update(section)).thenReturn(true);
        Result<TrailSection> result = service.update(section);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("A trail section ID is required for updating a trail."));
    }
    @Test
    void shouldDelete(){
        Mockito.when(sectionRepository.deleteById(1)).thenReturn(true);
        Result<TrailSection> result = service.deleteById(1);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteInvalid(){
        Mockito.when(sectionRepository.deleteById(1)).thenReturn(true);
        Result<TrailSection> result = service.deleteById(6);
        assertFalse(result.isSuccess());
    }

    private TrailSection makeNewSection(){
        TrailSection section = new TrailSection();
        section.setTrailId(1);
        section.setSectionStart("First Town");
        section.setSectionEnd("Second Town");
        section.setStartLongitude(80);
        section.setStartLatitude(80);
        section.setSectionLength(90);
        section.setSectionDays(5);
        section.setUpcoming(true);
        return section;
    }

    private Trail makeNewTrail(){
        Trail trail = new Trail();
        trail.setTrailId(1);
        trail.setTrailName("Hayduke Trail");
        trail.setTrailAbbreviation("Hayduke");
        return trail;
    }

}
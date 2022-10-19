package learn.thruhike.data;

import learn.thruhike.models.Trail;
import learn.thruhike.models.TrailSection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TrailSectionJdbcTemplateRepositoryTest {

    @Autowired
    TrailSectionJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate template;

    static boolean setupHasRun = false;

    @BeforeEach
    void setup(){
        if(!setupHasRun){
            setupHasRun = true;
            template.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindAll(){
        List<TrailSection> all = repository.findAll();
        assertTrue(all.size()>2 && all.size()<5);
    }

    @Test
    void shouldFindUpcoming(){
        List<TrailSection> upcomingSections = repository.findAllUpcoming();
        assertTrue(upcomingSections.size()>=2);
    }

    @Test
    void shouldFindByTrailId(){
        List<TrailSection> sectionsOfPct = repository.findByTrailId(1);
        assertTrue(sectionsOfPct.size()>0 && sectionsOfPct.size()<4);
    }

    @Test
    void shouldFindById(){
        TrailSection section = repository.findById(1);
        assertNotNull(section);
        assertEquals("Pie Town", section.getSectionEnd());
    }
    @Test
    void shouldFindByNickname(){
        TrailSection section = repository.findBySectionNickname("Doc Campbells to Pie Town");
        assertNotNull(section);
        assertEquals(1,section.getTrailSectionId());
    }

    @Test
    void shouldAdd(){
        TrailSection newSection = repository.add(makeSectionOne());
        assertNotNull(newSection);
        assertEquals("Pacific Crest Trail",newSection.getTrail().getTrailName());
    }

    @Test
    void shouldUpdate(){
        TrailSection section = repository.findById(3);
        assertEquals("Mexican Border", section.getSectionStart());
        section.setSectionStart("Updated Town");
        section.setSectionDays(12);
        assertTrue(repository.update(section));
        assertEquals("Updated Town", repository.findById(3).getSectionStart());
    }
    @Test
    void shouldUpdateSectionTrail(){
        TrailSection section = repository.findById(1);
        section.setTrailId(2);
        assertTrue(repository.update(section));
        assertEquals("CDT", repository.findById(1).getTrail().getTrailAbbreviation());
    }

    @Test
    void shouldNotUpdateInvalidSection(){
        TrailSection section = makeSectionOne();
        section.setTrailSectionId(50);
        assertFalse(repository.update(section));
    }

    @Test
    void shouldDeleteValidSection(){
        assertTrue(repository.deleteById(2));
    }

    @Test
    void shouldNotDeleteInvalidSection(){
        assertFalse(repository.deleteById(400));
    }

    private TrailSection makeSectionOne(){
        TrailSection section = new TrailSection();
        section.setAppUserId(1);
        section.setTrailId(1);
        section.setSectionNickname("Leg two");
        section.setSectionStart("town two");
        section.setSectionEnd("town three");
        section.setSectionLength(80);
        section.setSectionDays(5);
        section.setUpcoming(true);

        return section;
    }

}
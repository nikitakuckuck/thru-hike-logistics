package learn.thruhike.data;

import learn.thruhike.domain.SectionAlertService;
import learn.thruhike.models.AlertCategory;
import learn.thruhike.models.SectionAlert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SectionAlertJdbcTemplateRepositoryTest {

    @Autowired
    SectionAlertJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate template;

    static boolean setUpHasRun = false;

    @BeforeEach
    void setUp(){
        if(!setUpHasRun){
            setUpHasRun=true;
            template.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindAll(){
        List<SectionAlert> all = repository.findAll();
        assertTrue(all.size()>3 && all.size()<7);
    }

    @Test
    void shouldFindById(){
        SectionAlert alert = repository.findById(1);
        assertNotNull(alert);
    }

    @Test
    void shouldAdd(){
    SectionAlert alert = repository.add(makeSection());
    assertNotNull(alert);
    assertEquals("OTHER", alert.getAlertCategory().getAlertCategoryName());
    }

    @Test
    void shouldDeleteAlert(){
        assertTrue(repository.deleteById(2));
    }

    @Test
    void shouldNotDeleteInvalidAlert(){
        assertFalse(repository.deleteById(92));
    }

    private SectionAlert makeSection(){
        SectionAlert alert = new SectionAlert();
        alert.setAppUserId(1);
        alert.setAlertCategoryId(1);
        alert.setTrailSectionId(2);
        alert.setAlertContent("Trail Closed");
        return alert;
    }

}
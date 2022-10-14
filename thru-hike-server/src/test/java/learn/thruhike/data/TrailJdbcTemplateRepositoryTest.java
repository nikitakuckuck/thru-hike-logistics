package learn.thruhike.data;

import learn.thruhike.models.Trail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TrailJdbcTemplateRepositoryTest {

    @Autowired
    TrailJdbcTemplateRepository repository;
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
        List<Trail> allTrails = repository.findAll();
        assertTrue(allTrails.size()>1 && allTrails.size()<5);
    }

    @Test
    void shouldFindValidTrail(){
        Trail trail = repository.findById(2);
        assertNotNull(trail);
        assertEquals("CDT", trail.getTrailAbbreviation());
    }

    @Test
    void shouldNotFindInvalidTrail(){
        Trail trail = repository.findById(799);
        assertNull(trail);
    }

    @Test
    void shouldAddValidTrail(){
        Trail newTrail = repository.add(trailOne());
        assertNotNull(newTrail);
        assertEquals("AT", newTrail.getTrailAbbreviation());
    }

    @Test
    void shouldUpdateValidTrail(){
        Trail toUpdate = repository.findById(2);
        assertNotNull(toUpdate);
        toUpdate.setTrailName("Crispy Donut Treat");
        repository.update(toUpdate);
        assertEquals("Crispy Donut Treat", repository.findById(2).getTrailName());
    }

    @Test
    void shouldNotUpdateInvalidTrail(){
        Trail toUpdate = trailOne();
        toUpdate.setTrailId(80);
        assertFalse(repository.update(toUpdate));
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(54));
    }



    private Trail trailOne(){
        Trail newTrail = new Trail();
        newTrail.setAppUserId(1);
        newTrail.setTrailName("Arizona Trail");
        newTrail.setTrailAbbreviation("AT");

        return newTrail;
    }


}
package learn.thruhike.data;

import learn.thruhike.models.TownContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.NONE)
class TownContactJdbcTemplateRepositoryTest {

    @Autowired
    TownContactJdbcTemplateRepository repository;

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
        List<TownContact> all = repository.findAll();
        assertTrue(all.size()>1 && all.size()<5);
    }

    @Test
    void shouldFindByTrailSection(){
        List<TownContact> byTrail = repository.findByTrailSection(2);
        assertEquals(2,byTrail.size());
    }

    @Test
    void shouldFindById(){
        TownContact contact = repository.findById(2);
        assertEquals("PO general delivery address", contact.getCategory());
    }

    @Test
    void shouldAdd(){
        TownContact contact = new TownContact();
        contact.setTrailSectionId(1);
        contact.setCategory("Rides");
        contact.setContent("Phone number: 111-111-1111");
        TownContact newSection = repository.add(contact);
        assertNotNull(newSection);
        assertEquals("Rides", newSection.getCategory());
    }

    @Test
    void shouldUpdate(){
        TownContact toUpdate = new TownContact();
        toUpdate.setCategory("Ride into town");
        toUpdate.setContent("123-456-7890");
        toUpdate.setTownContactId(1);
        assertTrue(repository.update(toUpdate));
        assertEquals("Ride into town", repository.findById(1).getCategory());

    }

    @Test
    void shouldNotUpdateInvalid(){
        TownContact toUpdate = new TownContact();
        toUpdate.setCategory("Ride into town");
        toUpdate.setContent("123-456-7890");
        toUpdate.setTownContactId(35);
        assertFalse(repository.update(toUpdate));
    }

    @Test
    void shouldDeleteValidContact(){
        assertTrue(repository.deleteById(3));
    }

    @Test
    void shouldNotDeleteInvalidContact(){
        assertFalse(repository.deleteById(99));
    }

}
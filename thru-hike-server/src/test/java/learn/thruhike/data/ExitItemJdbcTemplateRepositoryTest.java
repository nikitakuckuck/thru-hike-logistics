package learn.thruhike.data;

import learn.thruhike.models.ExitItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExitItemJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate template;

    @Autowired
    ExitItemJdbcTemplateRepository repository;
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
        List<ExitItem> items = repository.findAll();
        assertTrue(items.size()>2 && items.size()<6);
    }

    @Test
    void shouldFindAllIncomplete(){
        List<ExitItem> items = repository.findAllIncomplete();
        assertTrue(items.size()>1 && items.size()<5);
    }

    @Test
    void shouldFindValidItem(){
        ExitItem exitItem = repository.findById(2);
        assertNotNull(exitItem);
        assertEquals("resupply", exitItem.getExitItemName());
    }

    @Test
    void shouldNotFindInvalidItem(){
        ExitItem exitItem = repository.findById(400);
        assertNull(exitItem);
    }

    @Test
    void shouldAdd(){
        ExitItem exitItem = new ExitItem();
        exitItem.setExitItemName("fill up waterbottles");
        exitItem.setGoodToGo(true);
        ExitItem added = repository.add(exitItem);
        assertNotNull(added);
        assertTrue(added.isGoodToGo());
    }

    @Test
    void shouldUpdateValidItem(){
        ExitItem exitItem = new ExitItem();
        exitItem.setExitItemId(2);
        exitItem.setExitItemName("resupply");
        exitItem.setGoodToGo(true);
        repository.update(exitItem);
        assertTrue(repository.findById(2).isGoodToGo());
    }

    @Test
    void shouldNotUpdateInvalidItem(){
        ExitItem exitItem = new ExitItem();
        exitItem.setExitItemId(25);
        exitItem.setExitItemName("resupply");
        exitItem.setGoodToGo(true);
        assertFalse(repository.update(exitItem));
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(1));
    }

    @Test
    void shouldNotDeleteInvalidItem(){
        assertFalse(repository.deleteById(89));
    }
}
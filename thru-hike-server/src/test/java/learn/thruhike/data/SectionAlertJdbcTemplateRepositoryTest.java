package learn.thruhike.data;

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

}
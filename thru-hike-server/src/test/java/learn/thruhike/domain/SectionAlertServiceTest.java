package learn.thruhike.domain;

import learn.thruhike.data.SectionAlertRepository;
import learn.thruhike.models.AlertCategory;
import learn.thruhike.models.SectionAlert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SectionAlertServiceTest {
    @Autowired
    SectionAlertService service;

    @MockBean
    SectionAlertRepository repository;

    @Test
    void shouldFindByTrailSectionId(){
        SectionAlert alert = makeAlert();
        alert.setAlertId(1);
        assertNotNull(service.findByTrailSectionId(1));
    }

    @Test
    void shouldDelete(){
        Mockito.when(repository.deleteById(2)).thenReturn(true);
        Result<SectionAlert> result = service.deleteById(2);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteInvalidAlert(){
        Mockito.when(repository.deleteById(2)).thenReturn(true);
        Result<SectionAlert> result = service.deleteById(90);
        assertFalse(result.isSuccess());
    }

    private SectionAlert makeAlert(){
        AlertCategory category = new AlertCategory();
        category.setAlertCategoryName("OTHER");
        category.setAlertCategoryId(1);

        SectionAlert alert = new SectionAlert();
        alert.setAlertContent("Spring is running dry");
        alert.setAlertCategory(category);
        alert.setAlertCategoryId(1);
        alert.setTrailSectionId(1);

        return alert;
    }

}
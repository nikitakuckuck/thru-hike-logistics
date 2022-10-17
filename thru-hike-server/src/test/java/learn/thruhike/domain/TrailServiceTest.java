package learn.thruhike.domain;

import learn.thruhike.data.TrailRepository;
import learn.thruhike.models.Trail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TrailServiceTest {
    @Autowired
    TrailService service;
    @MockBean
    TrailRepository repository;

    @Test
    void shouldFindById(){
        Mockito.when(repository.findById(1)).thenReturn(makeTrailOne());
        Trail trail = service.findById(1);
        assertEquals("Hayduke", trail.getTrailAbbreviation());
    }

    @Test
    void shouldAdd(){
        Trail toAdd = new Trail();
        toAdd.setTrailName("Rogue River Trail");
        Result<Trail> result = service.add(toAdd);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddInvalidTrail(){
        Trail toAdd = new Trail();
        Result<Trail> result = service.add(toAdd);
        assertFalse(result.isSuccess());

        toAdd.setTrailId(5);
        toAdd.setTrailName("Test Trail");
        result = service.add(toAdd);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("The trail id cannot be set before adding a trail"));
    }

    @Test
    void shouldUpdate(){
        Mockito.when(repository.findById(1)).thenReturn(makeTrailOne());
        Trail toUpdate = new Trail();
        toUpdate.setTrailId(1);
        toUpdate.setTrailName("Hayduke Trail");
        toUpdate.setTrailAbbreviation("HT");
        Mockito.when(repository.update(toUpdate)).thenReturn(true);

        Result<Trail> result = service.update(toUpdate);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalid(){
        Mockito.when(repository.update(makeTrailOne())).thenReturn(true);

        Result<Trail> result = service.update(makeTrailOne());
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDelete(){
        Mockito.when(repository.deleteById(3)).thenReturn(true);
        Result<Trail> result = service.deleteById(3);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteInvalid(){
        Result<Trail> result = service.deleteById(3);
        assertFalse(result.isSuccess());
    }

    private Trail makeTrailOne(){
        Trail trail = new Trail();
        trail.setTrailId(1);
        trail.setTrailName("Hayduke Trail");
        trail.setTrailAbbreviation("Hayduke");
        return trail;
    }

}
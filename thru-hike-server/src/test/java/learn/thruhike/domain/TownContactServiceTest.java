package learn.thruhike.domain;

import learn.thruhike.data.TownContactRepository;
import learn.thruhike.data.TrailSectionRepository;
import learn.thruhike.models.TownContact;
import learn.thruhike.models.TrailSection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TownContactServiceTest {

    @Autowired
    TownContactService service;
    @MockBean
    TownContactRepository townContactRepository;
    @MockBean
    TrailSectionRepository trailSectionRepository;

    @Test
    void shouldFindById(){
        TownContact contact = makeNewContact();
        contact.setTownContactId(1);
        Mockito.when(townContactRepository.findById(1)).thenReturn(contact);
        assertNotNull(service.findById(1));
    }

    @Test
    void shouldAddValidContact(){
        TownContact contact = makeNewContact();
        Mockito.when(trailSectionRepository.findById(1)).thenReturn(makeNewTrailSection());
        Result<TownContact> result = service.add(contact);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidContact(){
        TownContact contact = new TownContact();
        Mockito.when(trailSectionRepository.findById(1)).thenReturn(makeNewTrailSection());
        Result<TownContact> result = service.add(contact);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("Trail section is required."));
        assertTrue(result.getErrorMessages().contains("Category is required."));
        assertTrue(result.getErrorMessages().contains("Content is required."));

    }

    @Test
    void shouldUpdateValidContact(){
        TownContact contact = makeNewContact();
        contact.setTownContactId(1);
        Mockito.when(trailSectionRepository.findById(1)).thenReturn(makeNewTrailSection());
        Mockito.when(townContactRepository.update(contact)).thenReturn(true);
        Result<TownContact> result = service.update(contact);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateContactWithoutId(){
        TownContact contact = makeNewContact();
        Mockito.when(trailSectionRepository.findById(1)).thenReturn(makeNewTrailSection());
        Mockito.when(townContactRepository.update(contact)).thenReturn(true);
        Result<TownContact> result = service.update(contact);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDelete(){
        Mockito.when(townContactRepository.deleteById(1)).thenReturn(true);
        Result<TownContact> result = service.deleteById(1);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteInvalid(){
        Mockito.when(townContactRepository.deleteById(1)).thenReturn(true);
        Result<TownContact> result = service.deleteById(8);
        assertFalse(result.isSuccess());
    }


    private TownContact makeNewContact(){
        TownContact contact = new TownContact();
        contact.setContent("Bob's Shuttle");
        contact.setCategory("trail angel");
        contact.setTrailSectionId(1);
        contact.setOtherNotes("great shuttle driver");
        return contact;
    }

    private TrailSection makeNewTrailSection(){
        TrailSection section = new TrailSection();
        section.setTrailSectionId(1);
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

}
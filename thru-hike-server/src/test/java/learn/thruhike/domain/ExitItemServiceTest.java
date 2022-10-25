package learn.thruhike.domain;

import learn.thruhike.data.ExitItemRepository;
import learn.thruhike.models.ExitItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExitItemServiceTest {

    @Autowired
    ExitItemService service;

    @MockBean
    ExitItemRepository repository;

    @Test
    void shouldFindById(){
        Mockito.when(repository.findById(1)).thenReturn(makeItem());
        ExitItem exitItem = service.findById(1);
        assertEquals("sleep", exitItem.getExitItemName());
    }

    @Test
    void shouldAddValid(){
        ExitItem exitItem = new ExitItem();
        exitItem.setExitItemName("sleep");
        exitItem.setGoodToGo(true);
        Result<ExitItem> result = service.add(exitItem);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidItem(){
        ExitItem exitItem = new ExitItem();
        exitItem.setGoodToGo(true);
        Result<ExitItem> result = service.add(exitItem);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("Exit item name is required"));

        exitItem.setExitItemName("sleep");
        exitItem.setExitItemId(3);
        result = service.add(exitItem);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("The exit item ID cannot be set before adding an item."));
    }

    @Test
    void shouldUpdateValidItem(){
        Mockito.when(repository.findById(1)).thenReturn(makeItem());
        ExitItem toUpdate = new ExitItem();
        toUpdate.setExitItemId(1);
        toUpdate.setExitItemName("eat pizza");
        toUpdate.setGoodToGo(false);
        Mockito.when(repository.update(toUpdate)).thenReturn(true);

        Result<ExitItem> result = service.update(toUpdate);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalid(){
        ExitItem toUpdate = new ExitItem();
        toUpdate.setExitItemName("eat pizza");
        Mockito.when(repository.update(toUpdate)).thenReturn(true);
        Result<ExitItem> result = service.update(toUpdate);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("An exit item ID is required in order to update an item."));
    }

    @Test
    void shouldDelete(){
        Mockito.when(repository.deleteById(3)).thenReturn(true);
        Result<ExitItem> result = service.deleteById(3);
        assertTrue(result.isSuccess());
    }

    private ExitItem makeItem(){
        ExitItem exitItem = new ExitItem();
        exitItem.setExitItemId(1);
        exitItem.setExitItemName("sleep");
        exitItem.setGoodToGo(true);
        return exitItem;
    }

}
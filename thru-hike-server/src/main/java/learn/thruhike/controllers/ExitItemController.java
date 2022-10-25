package learn.thruhike.controllers;

import learn.thruhike.domain.ExitItemService;
import learn.thruhike.domain.Result;
import learn.thruhike.models.ExitItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exit-items")
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000"})
public class ExitItemController {

    private final ExitItemService service;

    public ExitItemController(ExitItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<ExitItem> findAll(){
        return service.findAll();
    }

    @GetMapping("/incomplete")
    public List<ExitItem> findAllInComplete(){
        return service.findAllIncomplete();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExitItem> findById(@PathVariable int id){
        ExitItem exitItem = service.findById(id);
        if(exitItem==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exitItem,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ExitItem exitItem){
        Result<ExitItem> result = service.add(exitItem);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ExitItem exitItem){
        if(exitItem.getExitItemId() != id){
            return new ResponseEntity<>("The request ID does not match the exit item ID.", HttpStatus.CONFLICT);
        }
        Result<ExitItem> result = service.update(exitItem);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Result<ExitItem> result = service.deleteById(id);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

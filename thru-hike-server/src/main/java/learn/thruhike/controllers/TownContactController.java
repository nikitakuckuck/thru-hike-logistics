package learn.thruhike.controllers;

import learn.thruhike.domain.Result;
import learn.thruhike.domain.TownContactService;
import learn.thruhike.models.TownContact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000"})
public class TownContactController {
    private final TownContactService service;

    public TownContactController(TownContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<TownContact> findAll(){
        return service.findAll();
    }

    @GetMapping("/by-section/{trailSectionId}")
    public List<TownContact> findByTrailSection(@PathVariable int trailSectionId){
        return service.findByTrailSection(trailSectionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TownContact> findById(@PathVariable int id){
        TownContact  contact = service.findById(id);
        if(contact==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contact,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody TownContact contact){
        Result<TownContact> result = service.add(contact);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody TownContact contact){
        if(contact.getTownContactId() != id){
            return new ResponseEntity<>("The request ID does not match the section ID.", HttpStatus.CONFLICT);
        }
        Result<TownContact> result = service.update(contact);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        Result<TownContact> result = service.deleteById(id);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

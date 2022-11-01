package learn.thruhike.controllers;

import learn.thruhike.domain.Result;
import learn.thruhike.domain.SectionAlertService;
import learn.thruhike.models.SectionAlert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000"})
public class SectionAlertController {
    private final SectionAlertService service;

    public SectionAlertController(SectionAlertService service) {
        this.service = service;
    }

    @GetMapping
    public List<SectionAlert> findAll(){ return service.findAll();}

    @GetMapping("/section/{id}")
    public List<SectionAlert> findByTrailSectionId(@PathVariable int id){
        return service.findByTrailSectionId(id);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody SectionAlert alert){
        Result<SectionAlert> result = service.add(alert);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        Result<SectionAlert> result = service.deleteById(id);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

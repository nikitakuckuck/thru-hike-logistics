package learn.thruhike.controllers;

import learn.thruhike.domain.Result;
import learn.thruhike.domain.TrailSectionService;
import learn.thruhike.models.TrailSection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000"})
public class TrailSectionController {


    private final TrailSectionService service;

    public TrailSectionController(TrailSectionService service) {
        this.service = service;
    }

    @GetMapping
    public List<TrailSection> findAll(){
        return service.findAll();
    }

    @GetMapping("/upcoming")
    public List<TrailSection> findAllUpcoming(){
        return service.findAllUpcoming();
    }

    @GetMapping("/by-trail/{id}")
    public List<TrailSection> findByTrailId(@PathVariable int id){
        return service.findByTrailId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrailSection> findById(@PathVariable int id){
        TrailSection section = service.findById(id);
        if(section==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<TrailSection> findActive(){
        TrailSection section = service.findActive();
        if(section==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(section,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody TrailSection section){
        Result<TrailSection> result = service.add(section);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody TrailSection section){
        if(section.getTrailSectionId() != id){
            return new ResponseEntity<>("The request ID does not match the section ID.", HttpStatus.CONFLICT);
        }
        Result<TrailSection> result = service.update(section);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(),HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Result<TrailSection> result = service.deleteById(id);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package learn.thruhike.controllers;

import learn.thruhike.domain.Result;
import learn.thruhike.domain.TrailService;
import learn.thruhike.models.Trail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trail")
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000"})
public class TrailController {
    private final TrailService trailService;

    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }
    @GetMapping
    public List<Trail> findAll(){
        return trailService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trail> findById(@PathVariable int id){
        Trail trail = trailService.findById(id);
        if(trail==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trail, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody Trail trail){
        Result<Trail> result = trailService.add(trail);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Trail trail) {
        if (trail.getTrailId() != id){
            return new ResponseEntity<>("The request ID does not match the trail ID", HttpStatus.CONFLICT);
        }
        Result<Trail> result = trailService.update(trail);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Result<Trail> result = trailService.deleteById(id);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

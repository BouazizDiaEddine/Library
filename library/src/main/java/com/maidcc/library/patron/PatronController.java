package com.maidcc.library.patron;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patron")
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        return ResponseEntity.ok(patronService.getAllPatrons());
    }

    @GetMapping("{id}")
    public ResponseEntity<Patron> getPatron(@PathVariable Long id){
        return patronService.getPatron(id).map(ResponseEntity::ok).orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<?> createPatron(@RequestBody Patron patron) {
        try {
            return ResponseEntity.ok(patronService.savePatron(patron));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePatron(@PathVariable long id, @RequestBody Patron patron) {
        try {
            return ResponseEntity.ok(patronService.updatePatron(id, patron));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        try {
            patronService.deletePatron(id);
            return ResponseEntity.ok("Patron with the id : "+id+" was deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

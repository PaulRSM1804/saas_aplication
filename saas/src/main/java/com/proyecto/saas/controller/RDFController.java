package com.proyecto.saas.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.saas.service.RDFService;

@RestController
@RequestMapping("/api/rdf")
@CrossOrigin(origins = "http://localhost:4200")

public class RDFController {

    private final RDFService rdfService;

    public RDFController(RDFService rdfService) {
        this.rdfService = rdfService;
    }

    @GetMapping("/users")
    public ResponseEntity<String> getRDFUsers() {
        String rdfData = rdfService.createRDFForUsers();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(rdfData);
    }

    @GetMapping("/courses")
    public ResponseEntity<String> getRDFCourses() {
        String rdfData = rdfService.createRDFForCourses();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(rdfData);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<String> getRDFSubscriptions() {
        String rdfData = rdfService.createRDFForSubscriptions();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(rdfData);
    }

    @GetMapping("/query")
    public ResponseEntity<String> queryRDF(@RequestParam String queryString) {
        String result = rdfService.queryRDF(queryString);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<String> getCombinedRDF() {
        String rdfData = rdfService.createCombinedRDF();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(rdfData);
    }
}

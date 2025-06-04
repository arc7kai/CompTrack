package com.comptrack.controller;

import com.comptrack.dto.ComplaintDTO;
import com.comptrack.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) {
        try {
            ComplaintDTO createdComplaint = complaintService.createComplaint(complaintDTO);
            return ResponseEntity.ok(createdComplaint);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDTO> getComplaintById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(complaintService.getComplaintById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDTO> updateComplaint(@PathVariable Long id, @RequestBody ComplaintDTO complaintDTO) {
        try {
            return ResponseEntity.ok(complaintService.updateComplaint(id, complaintDTO));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        try {
            complaintService.deleteComplaint(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
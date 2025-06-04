package com.comptrack.service;

import com.comptrack.dto.ComplaintDTO;
import com.comptrack.model.Complaint;
import com.comptrack.model.Status;
import com.comptrack.model.User;
import com.comptrack.repository.ComplaintRepository;
import com.comptrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    public ComplaintDTO createComplaint(ComplaintDTO complaintDTO) {
        Complaint complaint = new Complaint();
        complaint.setTitle(complaintDTO.getTitle());
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setStatus(complaintDTO.getStatus() != null ? complaintDTO.getStatus() : Status.valueOf("OPEN"));

        Optional<User> user = userRepository.findById(complaintDTO.getUserId());
        if (user.isPresent()) {
            complaint.setUser(user.get());
        } else {
            throw new RuntimeException("User not found");
        }

        Complaint savedComplaint = complaintRepository.save(complaint);
        return mapToDTO(savedComplaint);
    }

    public List<ComplaintDTO> getAllComplaints() {
        return complaintRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ComplaintDTO getComplaintById(Long id) {
        Optional<Complaint> complaint = complaintRepository.findById(id);
        return complaint.map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
    }

    public ComplaintDTO updateComplaint(Long id, ComplaintDTO complaintDTO) {
        Optional<Complaint> existingComplaint = complaintRepository.findById(id);
        if (existingComplaint.isPresent()) {
            Complaint complaint = existingComplaint.get();
            complaint.setTitle(complaintDTO.getTitle());
            complaint.setDescription(complaintDTO.getDescription());
            complaint.setStatus(complaintDTO.getStatus());
            Complaint updatedComplaint = complaintRepository.save(complaint);
            return mapToDTO(updatedComplaint);
        } else {
            throw new RuntimeException("Complaint not found");
        }
    }

    public void deleteComplaint(Long id) {
        if (complaintRepository.existsById(id)) {
            complaintRepository.deleteById(id);
        } else {
            throw new RuntimeException("Complaint not found");
        }
    }

    private ComplaintDTO mapToDTO(Complaint complaint) {
        ComplaintDTO dto = new ComplaintDTO();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setStatus(complaint.getStatus());
        dto.setCreatedAt(complaint.getCreatedAt());
        dto.setUserId(complaint.getUser() != null ? complaint.getUser().getId() : null);
        return dto;
    }
}
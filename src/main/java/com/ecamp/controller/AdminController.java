package com.ecamp.controller;

import com.ecamp.dto.CampSummaryDTO;
import com.ecamp.model.Camp;
import com.ecamp.repository.CampRepository;
import com.ecamp.repository.FeedbackRepository;
import com.ecamp.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private CampRepository campRepo;
    @Autowired private FeedbackRepository feedbackRepo;
    @Autowired private PhotoRepository photoRepo;

    @GetMapping("/camp-summary")
    public List<CampSummaryDTO> getCampSummaries() {
        return campRepo.findAll().stream().map(camp -> new CampSummaryDTO(
                camp.getName(),
                camp.getRegistrations().size(), // ✅ This line does the child count
                feedbackRepo.countByCamp(camp),
                photoRepo.countByCamp(camp)
        )).toList();
    }
}

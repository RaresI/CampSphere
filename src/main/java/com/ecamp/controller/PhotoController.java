package com.ecamp.controller;

import com.ecamp.dto.PhotoDTO;
import com.ecamp.model.Photo;
import com.ecamp.repository.PhotoRepository;
import com.ecamp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired private PhotoRepository repo;
    @Autowired private FeedbackRepository fbRepo;
    private final Path uploadDir = Paths.get("uploads");

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Photo upload(@RequestParam Long feedbackId,
                        @RequestParam MultipartFile file) throws IOException {
        var fb = fbRepo.findById(feedbackId).orElseThrow();
        String fn = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), uploadDir.resolve(fn), StandardCopyOption.REPLACE_EXISTING);
        Photo p = new Photo();
        p.setFeedback(fb);
        p.setCamp(fb.getCamp());
        p.setFilePath("/uploads/"+fn);
        p.setUploadedAt(java.time.Instant.now());
        return repo.save(p);
    }

    @GetMapping
    public List<PhotoDTO> list() {
        return repo.findAll().stream().map(p -> new PhotoDTO(
                p.getId(),
                p.getFilePath(),
                p.getUploadedAt(),
                p.getFeedback().getId(),
                p.getCamp().getName()
        )).toList();
    }

}

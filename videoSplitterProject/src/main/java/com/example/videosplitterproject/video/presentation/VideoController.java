package com.example.videosplitterproject.video.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.videosplitterproject.video.service.VideoServiceImpl;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/video")
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {

    @Autowired
    private VideoServiceImpl videoSplitService;

    @PostMapping("/process")
    public ResponseEntity<String> uploadAndSplit(
            @RequestParam("file") MultipartFile file,
            @RequestParam("timeFrames") String timeFramesJson
    ) {
        String uploadDir = "C:/Users/Mary/Documents/uploadedVideos/";
        String outputDir = "C:/Users/Mary/Documents/videosSegments/";

        try {
            File uploadedFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(uploadedFile);

            if (timeFramesJson == null || timeFramesJson.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: 'timeFrames' parameter is empty or missing.");
            }

            System.out.println("Received timeFramesJson: " + timeFramesJson);

            ObjectMapper objectMapper = new ObjectMapper();
            List<TimeFrame> timeFrames = objectMapper.readValue(
                    timeFramesJson,
                    new TypeReference<List<TimeFrame>>() {}
            );

            videoSplitService.splitVideo(uploadedFile.getAbsolutePath(), timeFrames);

            return ResponseEntity.ok("Uploaded and split successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


}

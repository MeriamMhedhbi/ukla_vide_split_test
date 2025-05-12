package com.example.videosplitterproject.video.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.videosplitterproject.video.service.VideoServiceImpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {

    private final VideoServiceImpl videoSplitService;

    public VideoController(VideoServiceImpl videoSplitService) {
        this.videoSplitService = videoSplitService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> uploadAndSplit(
            @RequestParam("file") MultipartFile file,
            @RequestParam("timeFrames") String timeFramesJson
    ) {
        //change this to your chosen file to test it
        String uploadDir = "C:/Users/Mary/Documents/uploadedVideos/";

        try {
            File uploadedFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(uploadedFile);

            if (timeFramesJson == null || timeFramesJson.trim().isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Error: 'timeFrames' parameter is empty or missing.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            System.out.println("Received timeFramesJson: " + timeFramesJson);

            ObjectMapper objectMapper = new ObjectMapper();
            List<TimeFrame> timeFrames = objectMapper.readValue(
                    timeFramesJson,
                    new TypeReference<>() {
                    }
            );

            videoSplitService.splitVideo(uploadedFile.getAbsolutePath(), timeFrames);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Uploaded and split successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


}

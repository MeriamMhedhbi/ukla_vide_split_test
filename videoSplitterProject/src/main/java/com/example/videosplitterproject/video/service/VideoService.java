package com.example.videosplitterproject.video.service;

import com.example.videosplitterproject.video.presentation.TimeFrame;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    public void splitVideo(String videoPath, List<TimeFrame> timeFrames) throws IOException;
    }

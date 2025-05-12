package com.example.videosplitterproject.video.service;

import com.example.videosplitterproject.video.persistence.VideoRepository;
import com.example.videosplitterproject.video.presentation.TimeFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    //change to a path of your own to test it
    private final String outputDirectory = "C:/Users/Mary/Documents/videosSegments/";


    public void splitVideo(String videoPath, List<TimeFrame> timeFrames) throws IOException {
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int stepNumber = 1;
        for (TimeFrame timeFrame : timeFrames) {
            String segmentFilePath = outputDirectory + "step_" + stepNumber + ".mp4";
            splitVideoSegment(videoPath, segmentFilePath, timeFrame.getStart(), timeFrame.getEnd());
            stepNumber++;
        }
    }

    private void splitVideoSegment(String videoPath, String outputPath, int startTime, int endTime) throws IOException {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        grabber.start();

        int imageWidth = grabber.getImageWidth();
        int imageHeight = grabber.getImageHeight();
        double frameRate = grabber.getFrameRate();
        int audioChannels = grabber.getAudioChannels();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputPath, imageWidth, imageHeight, audioChannels > 0 ? audioChannels : 0);
        recorder.setFormat("mp4");
        recorder.setVideoCodec(grabber.getVideoCodec());
        recorder.setFrameRate(frameRate);

        if (audioChannels > 0) {
            recorder.setAudioCodec(grabber.getAudioCodec());
            recorder.setSampleRate(grabber.getSampleRate());
        }

        recorder.start();

        grabber.setTimestamp((long) (startTime * 1_000_000L));

        while (grabber.getTimestamp() < endTime * 1_000_000L) {
            Frame frame = grabber.grabFrame();
            if (frame == null) break;
            recorder.record(frame);
        }

        recorder.stop();
        recorder.release();
        grabber.stop();
        grabber.release();
    }
}




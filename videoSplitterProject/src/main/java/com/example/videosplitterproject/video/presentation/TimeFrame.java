package com.example.videosplitterproject.video.presentation;

public class TimeFrame {
    private int start;
    private int end;

    private String startFormatted;

    private String endFormatted;

    // Getters and setters
    public int getStart() {
        return start;
    }

    public String getStartFormatted() {
        return startFormatted;
    }

    public void setStartFormatted(String startFormatted) {
        this.startFormatted = startFormatted;
    }

    public String getEndFormatted() {
        return endFormatted;
    }

    public void setEndFormatted(String endFormatted) {
        this.endFormatted = endFormatted;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}


package com.example.videosplitterproject.video.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.videosplitterproject.video.persistence.model.VideoSegment;

@Repository
public interface VideoRepository extends JpaRepository<VideoSegment, Long> {

}
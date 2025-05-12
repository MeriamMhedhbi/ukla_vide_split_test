package com.example.videosplitterproject;

import org.springframework.boot.SpringApplication;

public class TestVideoSplitterProjectApplication {

	public static void main(String[] args) {
		SpringApplication.from(VideoSplitterProjectApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

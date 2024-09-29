package com.huylhfx13483.jobsearchproject.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String saveFile(MultipartFile file, String directoryName);
}

package com.huylhfx13483.jobsearchproject.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String saveFile(MultipartFile file, String directoryName) {

		// Xu ly ten tep tranh ky tu dac biet hoac ten tep doc hai
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// tao ten file duy nhat
		String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

		// Duong dan luu file
		Path staticPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "assets",
				directoryName);
		try {
			// Kiem tra va tao thu muc neu chua ton tai
			if (!Files.exists(staticPath)) {
				Files.createDirectories(staticPath);
			}

			// Duong dan file voi ten file
			Path filePath = staticPath.resolve(uniqueFileName);

			// Luu file vao he thong
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// Tao duong dan luu trong database
			String dtPath = "/assets/" + directoryName + "/" + uniqueFileName;

			return dtPath;
		} catch (Exception e) {
			
			return null;
		}

	}

}
